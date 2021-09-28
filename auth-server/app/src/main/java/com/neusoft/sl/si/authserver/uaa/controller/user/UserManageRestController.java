package com.neusoft.sl.si.authserver.uaa.controller.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.neusoft.ehrss.liaoning.security.password.idnumbername.RedisService;
import com.neusoft.sl.si.authserver.base.domains.company.Company;
import com.neusoft.sl.si.authserver.base.domains.user.*;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.*;
import com.neusoft.sl.si.authserver.uaa.controller.role.RoleDTO;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.*;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.EnterpriseUserDTO;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.neusoft.ehrss.liaoning.security.userdetails.redis.PersonRedisUser;
import com.neusoft.ehrss.liaoning.security.userdetails.redis.PersonRedisUserManager;
import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.girder.security.oauth2.domain.AuthenticatedCasUser;
import com.neusoft.sl.girder.utils.LongDateUtils;
import com.neusoft.sl.si.authserver.base.domains.person.Person;
import com.neusoft.sl.si.authserver.base.domains.person.PersonRepositoryExtend;
import com.neusoft.sl.si.authserver.base.domains.resource.Menu;
import com.neusoft.sl.si.authserver.base.domains.role.Role;
import com.neusoft.sl.si.authserver.base.domains.role.RoleRepository;
import com.neusoft.sl.si.authserver.base.services.redis.RedisAccountService;
import com.neusoft.sl.si.authserver.base.services.redis.ResetPasswordTokenService;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.base.services.user.UserService;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.rlzysc.CompanyOpenDTO;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.rlzysc.PersonOpenDTO;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;
import com.neusoft.sl.si.authserver.uaa.exception.CaptchaErrorException;
import com.neusoft.sl.si.authserver.uaa.exception.UserNotFoundException;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.CaptchaRequestService;
import com.neusoft.sl.si.authserver.uaa.log.enums.BusinessType;
import com.neusoft.sl.si.authserver.uaa.log.enums.ClientType;
import com.neusoft.sl.si.authserver.uaa.service.msg.MsgService;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;
import com.neusoft.sl.si.authserver.uaa.validate.password.ValidatePassword;

import io.swagger.annotations.ApiOperation;

/**
 * User 控制器
 *
 * @author mojf
 */
@Slf4j
@RestController
public class UserManageRestController {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(UserManageRestController.class);

    // private final String COMPANY_TYPE = "1";
    // private final String PERSON_TYPE = "2";
    @Resource
    private UserService userService;
    // @Autowired
    // private UserRepository userRepository;
    @Resource
    private RoleRepository roleRepository;
    @Autowired
    private ThinUserRepository userRepository;

    @Resource
    private UserActiveRepository userActiveRepository;
    // @Autowired
    // PersonRepository personRepository;
    @Autowired
    private PersonRepositoryExtend personRepositoryExtend;
    @Resource
    private RedisAccountService redisAccountService;
    // @Resource(name = "${saber.auth.password.encoder}")
    // private PasswordEncoderService passwordEncoderService;

    @Autowired
    private CaptchaRequestService captchaRequestService;

    @Autowired
    private DataSource dataSource;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    @Autowired
    private ResetPasswordTokenService resetPasswordTokenService;

    @Autowired
    private UserCustomService userCustomService;




    @Autowired
    private MsgService msgService;

    @Autowired
    private PersonRedisUserManager personRedisUserManager;

    @Autowired
    RedisService redisService;

    final String PROFIX="REDIS_USER:";

    @ApiOperation(value = "GET获取人员参保状态等信息", tags = "用户操作接口", notes = "获取人员参保状态等信息UserManageRestController")
    @GetMapping(value = "/user/person")
    public PersonResultDTO userPerson(Principal user, @RequestParam("idNumber") String idNumber, @RequestParam("name") String name) {
        String decode = name;
        try {
            decode = URLDecoder.decode(name, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("解码失败", e);
        }
        List<Person> persons = personRepositoryExtend.findAllByCertificate(idNumber, decode);
        PersonResultDTO dto = new PersonResultDTO(idNumber, name);
        if (persons.size() > 0) {
            dto.setPersons(PersonAssembler.toPersonInfoDTOs(persons));
            dto.setIsSocial("1");
        } else {
            dto.setIsSocial("0");
        }
        return dto;
    }

    @ApiOperation(value = "POST重置密码前获取token", tags = "密码操作接口", notes = "重置密码前获取token UserManageRestController")
    @RequestMapping(value = "/password/reset/token", method = RequestMethod.POST)
    public ResetPasswordTokenDTO genTokenForResetPassword(Principal principal, @RequestBody PassWordResetDetailDTO dto) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) token.getPrincipal();

        String name = dto.getName();
        String idNumber = dto.getIdNumber();
        String newPassword = dto.getNewPassword();

        Validate.notBlank(name, "请输入您的姓名");
        Validate.notBlank(idNumber, "请输入正确位数的证件号码");
        Validate.notBlank(newPassword, "请输入新密码");

        ValidatePassword.verifyPassword(newPassword);

        User sysuser = userService.findByIdNumber(idNumber);
        if (null == sysuser) {
            throw new ResourceNotFoundException("根据您输入的证件号码未查询到有效的账户，请重新输入");
        }
        if (!sysuser.getName().equals(name)) {
            throw new ResourceNotFoundException("您输入的姓名与账户信息不匹配，请重新输入");
        }

        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ClientDetails client = clientDetailsService.loadClientByClientId(user.getUsername());
        UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(idNumber, "");
        OAuth2Request storedRequest = new OAuth2Request(null, user.getUsername(), token.getAuthorities(), true, client.getScope(), null, null, null, null);
        OAuth2Authentication authentication = new OAuth2Authentication(storedRequest, userAuthentication);
        String key = authenticationKeyGenerator.extractKey(authentication);
        LOGGER.info("身份证号idNumber={},生成的token={}", idNumber, key);
        ResetPasswordForTokenDTO save = new ResetPasswordForTokenDTO(key, newPassword, idNumber, name);
        resetPasswordTokenService.saveResetPassword(save);
        ResetPasswordTokenDTO rs = new ResetPasswordTokenDTO(key);
        return rs;
    }

    /**
     * 第三方接口获取用户信息
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "第三方接口GET获取用户信息", tags = "用户操作接口", notes = "第三方接口获取用户信息，登录时候反馈用户信息")
    @RequestMapping("/open/user")
    public Object userOpen(Principal user) {
        OAuth2Authentication authentication = (OAuth2Authentication) user;
        Authentication userAuth = authentication.getUserAuthentication();
        Object principal = userAuth.getPrincipal();
        String account = "";
        account = (String) principal;
        User userInfo = userService.findByAccount(account);
        if (userInfo == null) {
            throw new ResourceNotFoundException("用户未授权");
        }
        LOGGER.debug("userInfo.getUserTypeString()" + userInfo.getUserTypeString());
        if (userInfo instanceof PersonalUser) {
            PersonOpenDTO personOpenDTO = new PersonOpenDTO();
            personOpenDTO.setUserType(userInfo.getUserTypeString());
            personOpenDTO.setIdNumber(userInfo.getIdNumber());
            personOpenDTO.setName(userInfo.getName());
            personOpenDTO.setMobile(userInfo.getMobile());
            return personOpenDTO;
        } else if (userInfo instanceof EnterpriseUser) {
            CompanyOpenDTO openDTO = new CompanyOpenDTO();
            openDTO.setUserType(userInfo.getUserTypeString());
            openDTO.setName(userInfo.getName());
            openDTO.setOrgCode(userInfo.getOrgCode());
            openDTO.setAccount(userInfo.getAccount());
            return openDTO;
        }
        return null;
    }

    @GetMapping(value = "/sid")
    public Object getSid(Principal user) {
        OAuth2Authentication authentication = (OAuth2Authentication) user;
        Authentication userAuth = authentication.getUserAuthentication();
        Object principal = userAuth.getPrincipal();
        String account = "";
        account = (String) principal;
        User userInfo = userService.findByAccount(account);
        Map<String, String> map = new HashMap<String, String>();

        if (userInfo.getOrgCode() != null){
            map.put("sid", (String) redisService.get("SID:"+userInfo.getOrgCode()));
        }
        else {
            map.put("sid", (String) redisService.get("SID:"+userInfo.getIdNumber()));
        }
        return map;
    }

    @GetMapping("/SID")
    public Map<String, String> getSID(HttpServletRequest request){
        Map<String, String> map = new HashMap<String, String>();
        map.put("sid", (String) redisService.get("SID:"+request.getParameter("account")));
        return map;
    }

    /**
     * 获取用户信息
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "GET获取用户信息", tags = "用户操作接口", notes = "获取用户信息，登录时候反馈用户信息UserManageRestController")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public Object user(Principal user) {
        OAuth2Authentication authentication = (OAuth2Authentication) user;
        Authentication userAuth = authentication.getUserAuthentication();
        Object principal = userAuth.getPrincipal();
        String account = "";
        account = (String) principal;

        if (redisService.hasKey(PROFIX+account)) {
          return (AuthenticatedCasUser) redisService.get(PROFIX+account);
      }
        OAuth2Request auth2Request = authentication.getOAuth2Request();
        if ("acme_atm".equals(auth2Request.getClientId())) {
            return UserAssembler.toATMDTOByPerson(account);
        }

        User userInfo = userService.findByAccount(account);
        userInfo = userService.updateAccountCache(account);
        LOGGER.debug("userinfo = {}",userService.findByAccount(account));
        if (userInfo == null) {
            PersonRedisUser personRedisUser = personRedisUserManager.load(account);
            if (personRedisUser != null) {
                AuthenticatedCasUserByRedis redis = UserAssembler.toDTOByRedis(personRedisUser);
                return UserAssembler.redisToDTO(redis, authentication);
            }
        }
        AuthenticatedCasUser userDTO = UserAssembler.toDTO(userInfo);
        return userDTO;
    }

    /**
     * 分析用户拥有哪些角色
     *
     * @param authorities
     * @return
     */
    @SuppressWarnings("unused")
    private Set<Role> roleType(Collection<? extends GrantedAuthority> authorities) {
        Set<Role> roles = new HashSet<Role>();
        for (GrantedAuthority authority : authorities) {
            Role role = roleRepository.findByName(authority.toString());
            if (role != null) {
                roles.add(role);
            }
        }
        return roles;
    }

    /**
     * 获取用户能够访问的某个系统的菜单列表
     *

     * @param appid
     * @return
     */
    @ApiOperation(value = "GET获取用户能够访问的某个系统的菜单列表", tags = "用户操作接口", notes = "获取用户能够访问的某个系统的菜单列表UserManageRestController", response = MenuDTO.class, responseContainer = "List")
    @RequestMapping(value = "/user/{appid}/menus", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuDTO> menus(Principal principal, @PathVariable("appid") String appid) {
        OAuth2Authentication authentication = (OAuth2Authentication) principal;
        Authentication userAuth = authentication.getUserAuthentication();
        String account = (String) userAuth.getPrincipal();
        // 获取用户菜单
        User user = userService.findByAccount(account);
        Set<Role> roleSet = user.getRoles();
        Set<Menu> menusResult = new HashSet<Menu>();
        for (Role role : roleSet) {
            Set<Menu> menus = userService.getUserMenus(appid, role);
            menusResult.addAll(menus);
        }
        return UserAssembler.constructMenus(menusResult);
    }

    /**
     * 用户修改密码
     *
     * @param passWordChangeDetailDTO
     */
    @ApiOperation(value = "PUT用户修改密码", tags = "密码操作接口", notes = "用户修改密码,UserManageRestController")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = {"/password", "/password/person"}, method = RequestMethod.POST, consumes = {"application/json"})
    public void changePassword(Principal user, @RequestBody PassWordChangeDetailDTO passWordChangeDetailDTO) {
        OAuth2Authentication auth = (OAuth2Authentication) user;
        String account = (String) auth.getUserAuthentication().getPrincipal();
        ValidatePassword.verifyPassword(passWordChangeDetailDTO.getNewPassword());
        userService.updatePassWord(account, passWordChangeDetailDTO.getOldPassword(), passWordChangeDetailDTO.getNewPassword());
        redisAccountService.modifyOnlyRedisAccount(account);
    }

    @ApiOperation(value = "POST核实用户登录密码", tags = "密码操作接口", notes = "核实用户登录密码，需要登录,UserManageRestController")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/password/person/verify", method = RequestMethod.POST, consumes = {"application/json"})
    public void verifyPersonPassword(Principal user, @RequestBody PassWordVerifyDTO passWordVerifyDTO) {
        OAuth2Authentication auth = (OAuth2Authentication) user;
        String account = (String) auth.getUserAuthentication().getPrincipal();
        if (StringUtils.isEmpty(passWordVerifyDTO.getPassword())) {
            throw new BusinessException("请输入登录密码");
        }
        userService.verifyPassWord(account, passWordVerifyDTO.getPassword());
    }

    /**
     * 修改个人用户手机号码
     *

     * @return
     */
    @ApiOperation(value = "PUT修改个人用户手机号码", tags = "手机号码操作接口", notes = "修改个人用户手机号码，需要登录；处理了redis缓存数据信息;UserManageRestController")
    @RequestMapping(value = "/user/person/mobilenumber", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyUserMoibleNumber(Principal user, @RequestBody PersonUserMobileNumberDTO personUserMobileNumberDTO, HttpServletRequest request) {
        String newMobileNumber = personUserMobileNumberDTO.getNewMobileNumber();
        LOGGER.debug("个人用户手机号码修改DTO={}", newMobileNumber);
        if (StringUtils.isEmpty(newMobileNumber)) {
            throw new ResourceNotFoundException("请输入新手机号码");
        }
        OAuth2Authentication authentication = (OAuth2Authentication) user;
        Authentication userAuth = authentication.getUserAuthentication();
        Object principal = userAuth.getPrincipal();
        String account = "";
        if (principal instanceof SaberUserDetails) {
            account = ((SaberUserDetails) principal).getAccount();
        } else {
            account = (String) principal;
        }
        // 校验短信验证码
        String sms = captchaRequestService.smsCaptchaRequest(newMobileNumber, request);
        if (!"".equals(sms)) {
            throw new CaptchaErrorException(sms);
        }
        User userInfo = userService.findByAccount(account);
        if (userInfo == null) {
            throw new ResourceNotFoundException("未找到有效用户");
        }
        // 数据库手机号为空，不需要比对
        if (!StringUtils.isEmpty(userInfo.getMobile())) {
            if (newMobileNumber.equals(userInfo.getMobile())) {
                throw new BusinessException("新手机号码与预留手机号码相同，无需进行修改操作");
            }
        }
        User mobile = userService.findByMobileNumber(newMobileNumber);
        if (mobile != null) {
            mobile.setMobile(null);
            userService.updateUser(mobile);
            redisAccountService.modifyOnlyRedisAccount(mobile.getAccount());
            // throw new BusinessException("此手机号码已注册");
        }
        userInfo.setMobile(newMobileNumber);
        // 保存user对象
        userService.updateUser(userInfo);
        // 去掉redis历史缓存
        redisAccountService.modifyOnlyRedisAccount(account);
    }

    /**
     * 为用户添加角色
     *
     * @param account

     */
    @ApiOperation(value = "POST为用户添加角色", tags = "用户操作接口", notes = "为用户添加角色,UserManageRestController")
    @RequestMapping(value = "/user/{account}/role/{rolename}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserRole(@PathVariable("account") String account, @PathVariable("rolename") String name) {
        LOGGER.debug("为用户添加角色", account, name);
        User user = userService.findByAccount(account);
        Role role = roleRepository.findByName(name);
        LOGGER.debug("待授权的角色", role.getName());
        user.addRole(role);
        // 保存user对象
        userService.updateUser(user);
    }

    /**
     * 为用户撤销某个角色的授权
     *
     * @param account

     */
    @ApiOperation(value = "DELETE为用户撤销某个角色的授权", tags = "用户操作接口", notes = "为用户撤销某个角色的授权,UserManageRestController")
    @RequestMapping(value = "/user/{account}/role/{rolename}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserRole(@PathVariable("account") String account, @PathVariable("rolename") String name) {
        LOGGER.debug("为用户撤销角色的授权", account, name);
        User user = userService.findByAccount(account);
        Role role = roleRepository.findByName(name);
        LOGGER.debug("待撤销权限的角色", role.getName());
        user.removeRole(role);
        // 保存user对象
        userService.updateUser(user);
    }

    /**
     * 根据手机号 发送短信验证码，用于修改手机号
     *
     * @param mobilenumber
     */
    @ApiOperation(value = "POST发送手机验证码到新的手机号，需要登录", tags = "手机验证码操作接口", notes = "发送手机验证码到新的手机号，需要登录")
    @RequestMapping(value = "/user/captcha/sm/{mobilenumber}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void sendCaptchaSmForModifyMoible(Principal user, @PathVariable("mobilenumber") String mobilenumber, HttpServletRequest request) {
        LOGGER.debug("根据手机号 发送短信验证码 手机号码={}", mobilenumber);
        OAuth2Authentication authentication = (OAuth2Authentication) user;
        Authentication userAuth = authentication.getUserAuthentication();
        Object principal = userAuth.getPrincipal();
        String account = "";
        if (principal instanceof SaberUserDetails) {
            account = ((SaberUserDetails) principal).getAccount();
        } else {
            account = (String) principal;
        }
        User userInfo = userService.findByAccount(account);
        if (userInfo == null) {
            throw new ResourceNotFoundException("未找到有效用户");
        }
        // 校验图形验证码
        String sms = captchaRequestService.imgCaptchaRequest(request);
        if (!"".equals(sms)) {
            throw new CaptchaErrorException(sms);
        }
        msgService.nextCaptchaPersonSms(mobilenumber, mobilenumber, BusinessType.Mobile, ClientType.PC, "GRWSBS2", "20000");
    }

    @ApiOperation(value = "PUT绑定社保卡", tags = "用户操作接口", notes = "绑定社保卡,UserManageRestController")
    @RequestMapping(value = "/user/card/{cardNumber}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void changeUserRole(Principal user, @PathVariable("cardNumber") String cardNumber) {
        LOGGER.debug("绑定社保卡cardNumber={}", cardNumber);
        OAuth2Authentication auth = (OAuth2Authentication) user;
        String account = (String) auth.getUserAuthentication().getPrincipal();
        User userInfo = userService.findByAccount(account);
        if (userInfo == null) {
            throw new UserNotFoundException("未找到有效用户");
        }
        if (userInfo.isBindCardAuthed()) {
            throw new UserNotFoundException("您已经绑定，无需重复操作");
        }

        List<Person> persons = personRepositoryExtend.findByCertificate(userInfo.getIdNumber());
        if (persons.size() == 0) {
            throw new ResourceNotFoundException("未查询到该人员的社保卡记录");
        }

        for (Person person : persons) {
            if (cardNumber.equalsIgnoreCase(person.getSocialSecurityCardNumber())) {
                String date = LongDateUtils.nowAsSecondLong().toString();
                userInfo.setBindCardAuthed(true);
                userInfo.setBindCardDate(date);
                // 保存实名认证日志
                userCustomService.savePersonAuthedLogBack(userInfo, "pc", "userCard", cardNumber, date);
                userService.updateUser(userInfo);
                redisAccountService.modifyOnlyRedisAccount(account);
                userCustomService.updateUserForPersonAndRole(userInfo.getIdNumber());
                return;
            }
        }
        throw new ResourceNotFoundException("您输入的社保卡号与记录的不匹配，请重新输入");
    }


//    /**
//     * 获取用户信息
//     *
//     * @param user
//     * @return
//     */
//    @ApiOperation(value = "GET获取绑定激活码用户信息", tags = "用户操作接口", notes = "获取用户信息，登录时候反馈用户信息UserManageRestController")
//    @RequestMapping(value = "/enterprise/active/account", method = RequestMethod.GET)
//    @ResponseBody
//    public ActiveResultDTO getCompanyNumber(Principal user, @RequestParam("activeCode") String activeCode, @RequestParam("account") String inAccount) {
//
//        ActiveResultDTO resultDTO = new ActiveResultDTO();
//
//        String companyNumber = userActiveRepository.findByActiveCode(activeCode);
//        if (StringUtils.isEmpty(companyNumber)) {
//            resultDTO.setCode("-1");
//            resultDTO.setMessage("传入信息与用户认证信息不同，无法激活");
//            return resultDTO;
//        } else {
//            resultDTO.setCode("0");
//            resultDTO.setMessage("0");
//            resultDTO.setCompanyNumber(companyNumber);
//            return resultDTO;
//        }
//
//
//    }

//    @ApiOperation(value = "GET激活用户", tags = "用户操作接口", notes = "UserManageRestController")
//    @RequestMapping(value = "/simis/code/active", method = RequestMethod.GET)
//    @ResponseBody
//    public ActiveResultDTO user(Principal user, @RequestParam("companyNumber") String companyNumber, @RequestParam("orgCode") String orgCode) {
//        ActiveResultDTO activeResultDTO = new ActiveResultDTO();
//        OAuth2Authentication authentication = (OAuth2Authentication) user;
//        Authentication userAuth = authentication.getUserAuthentication();
//        Object principal = userAuth.getPrincipal();
//        String account = "";
//        account = (String) principal;
//        LOGGER.debug("account = {}", account);
//        String cacheKey = account + "_USER_ALLOWED";
//
//        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
//        ThinUser userinfo = userRepository.findByAccount(account);
//        if (userinfo.getAccount().equals(companyNumber) && userinfo.getOrgCode().equals(orgCode)) {
//            userinfo.setActivated(true);
//            userinfo.setAccount(companyNumber);
//            userRepository.save(userinfo);
//            // 24个小时过期
//            User user1 = userService.findByAccount(account);
//            ops.set(cacheKey,UserAssembler.toAllowedDTO(user1), 24, TimeUnit.HOURS);
//            activeResultDTO.setMessage("绑定成功");
//            activeResultDTO.setCode("0");
//            activeResultDTO.setCompanyNumber(companyNumber);
//        }else {
//            activeResultDTO.setMessage("关键信息不匹配");
//            activeResultDTO.setCode("-1");
//            activeResultDTO.setCompanyNumber(companyNumber);
//        }
//        userinfo.setActivated(true);
//        userRepository.save(userinfo);
//        return activeResultDTO;
//
//    }

    /**
     * 中心创建企业用户
     *
     * @param userDTO
     * @param request
     * @return
     */
    @ApiOperation(value = "POST中心创建企业用户", tags = "simis企业用户操作接口", notes = "simis创建企业用户EnterpriseUserRestController")
    @PostMapping(value = "/ws/enterprise/simis")
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAnyAuthority('ROLE_CA')")
    public DealResultDTO createEnterpriseUserSi(@RequestBody EnterpriseUserDTO userDTO, HttpServletRequest request) {
        try {
            LOGGER.debug("si单位用户注册account={}", userDTO.getAccount());
            Validate.notBlank(userDTO.getAccount(), "account is null");
            LOGGER.debug("si单位用户注册account={}", userDTO.getAccount());

            ThinUser user = userRepository.findByAccount(userDTO.getAccount());
            if (user != null) {
                if (user.isActivated()) {
                    return new DealResultDTO(-1, userDTO.getAccount() + "此账号已经存在");
                } else {
                    return new DealResultDTO(-2, userDTO.getAccount() + "此账号已被金庸");
                }
            }


            // 一切顺利，创建user
            EnterpriseUserFactory factory = new EnterpriseUserFactory();
            User user1 = factory.createEnterpriseUser("0", userDTO.getOrgCode(), userDTO.getName(), "", userDTO.getMobile(), null);
            user1.setExtension("simis_register");
            user1.setPassword(userDTO.getPassword());
            User newUser = userCustomService.createEnterpriseUser(user1);
            UserLogManager.saveRegisterLog(SystemType.Enterprise.toString(), "simis", user1, null);
            return new DealResultDTO(0, "注册成功");
        } catch (Exception e) {
            return new DealResultDTO(-3, "注册失败，系统错误");
        }
    }


}
