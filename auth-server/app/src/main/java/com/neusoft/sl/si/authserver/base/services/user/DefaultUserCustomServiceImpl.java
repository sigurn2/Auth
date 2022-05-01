package com.neusoft.sl.si.authserver.base.services.user;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.neusoft.sl.girder.security.oauth2.domain.CompanyDTO;
import com.neusoft.sl.si.authserver.base.domains.company.*;
import com.neusoft.sl.si.authserver.base.domains.person.*;
import com.neusoft.sl.si.authserver.simis.SimisWebService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.ehrss.liaoning.provider.GongAn.GaAuthService;
import com.neusoft.ehrss.liaoning.utils.RoleUtil;
import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.girder.utils.LongDateUtils;
import com.neusoft.sl.girder.utils.UuidUtils;
import com.neusoft.sl.si.authserver.base.domains.expert.PersonExpert;
import com.neusoft.sl.si.authserver.base.domains.expert.PersonExpertRepository;
import com.neusoft.sl.si.authserver.base.domains.family.Family;
import com.neusoft.sl.si.authserver.base.domains.family.FamilyRepository;
import com.neusoft.sl.si.authserver.base.domains.org.OrganizationRepository;
import com.neusoft.sl.si.authserver.base.domains.person.authed.PersonAuthedLogBack;
import com.neusoft.sl.si.authserver.base.domains.person.authed.PersonAuthedLogBackRepository;
import com.neusoft.sl.si.authserver.base.domains.role.InitRoleProvider;
import com.neusoft.sl.si.authserver.base.domains.role.Role;
import com.neusoft.sl.si.authserver.base.domains.role.RoleRepository;
import com.neusoft.sl.si.authserver.base.domains.user.AccountExistsException;
import com.neusoft.sl.si.authserver.base.domains.user.SiInfoNotExistsException;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.domains.user.UserRepository;
import com.neusoft.sl.si.authserver.base.domains.user.company.UserCompanyEntity;
import com.neusoft.sl.si.authserver.base.domains.user.company.UserCompanyEntityRepository;
import com.neusoft.sl.si.authserver.base.domains.user.family.UserFamilyEntity;
import com.neusoft.sl.si.authserver.base.domains.user.family.UserFamilyEntityRepository;
import com.neusoft.sl.si.authserver.base.services.password.PasswordEncoderService;
import com.neusoft.sl.si.authserver.base.services.redis.RedisAccountService;
import com.neusoft.sl.si.authserver.base.services.redis.ResetPasswordTokenService;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.AtmVerifyResultDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.CompanyInfoDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.EnterpriseUserDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PassWordResetDetailDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTOAssembler;
import com.neusoft.sl.si.authserver.uaa.controller.role.RoleDTO;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.ResetPasswordForTokenDTO;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;
import com.neusoft.sl.si.authserver.uaa.exception.CaptchaErrorException;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.CaptchaRequestService;
import com.neusoft.sl.si.authserver.uaa.validate.identity.IdentityCard;
import com.neusoft.sl.si.authserver.uaa.validate.login.PasswordErrorRedisManager;
import com.neusoft.sl.si.authserver.uaa.validate.password.ValidatePassword;

/**
 * 用户管理扩展服务默认实现
 *
 * @author mojf
 */
@Service
public class DefaultUserCustomServiceImpl implements UserCustomService {

    @Resource
    private UserService userService;

    /**
     * 日志
     */
    protected static Logger LOGGER = LoggerFactory.getLogger(DefaultUserCustomServiceImpl.class);

    @Resource
    private UserRepository userRepository;
    @Autowired
    private ThinUserRepository thinUserRepository;
    @Resource
    private PersonRepository personRepository;
    @Resource
    private PersonRepositoryExtend personRepositoryExtend;
    @Resource
    private CompanyRepository companyRepository;
    @Resource
    private OrganizationRepository organizationRepository;
    @Resource
    private CompanySiService companySiService;

    @Resource
    private RedisAccountService redisAccountService;

    @Value("${saber.auth.password.company.default}")
    private String password;

    @Resource(name = "${saber.auth.password.encoder}")
    private PasswordEncoderService passwordEncoderService;
    @Resource(name = "enterpriseUserRoleProvider")
    private InitRoleProvider enterpriseUserRoleProvider;
    @Resource(name = "personUserRoleProvider")
    private InitRoleProvider personUserRoleProvider;
    @Resource(name = "siagentUserRoleProvider")
    private InitRoleProvider siagentUserRoleProvider;

    @Autowired
    private PersonAuthedLogBackRepository authedLogBackRepository;


    @Autowired
    private GaAuthService gaAuthService;

    @Autowired
    private PersonService personService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordErrorRedisManager passwordErrorRedisManager;

    @Autowired
    private CaptchaRequestService captchaRequestService;

    @Autowired
    private ResetPasswordTokenService resetPasswordTokenService;

    @Autowired
    private UserCompanyEntityRepository userCompanyEntityRepository;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private UserFamilyEntityRepository userFamilyEntityRepository;

    @Autowired
    private PersonExpertRepository personExpertRepository;

    @Autowired
    private CompanySiRepository companySiRepository;

    @Autowired
    private SimisWebService simisWebService;

    private static final List<String> socialRole = new ArrayList<String>(Arrays.asList("ROLE_CA_ENTERPRISE_USER", "ROLE_CA_DEPARTMENT_USER"));

    public void resetPasswordForFace(HttpServletRequest request) {
        String token = request.getHeader("token");
        LOGGER.debug("刷脸重置密码，token={}", token);

        ResetPasswordForTokenDTO dto = resetPasswordTokenService.getResetPassword(token);
        if (dto == null) {
            LOGGER.error("根据token={}未获取到缓存信息", token);
            throw new BusinessException("未获取到有效数据，请重新操作");
        }

        ThinUser user = thinUserRepository.findByIdNumber(dto.getIdNmuber());
        if (null == user) {
            throw new ResourceNotFoundException("未找到该用户信息");
        }
        if (!user.getName().equals(dto.getName())) {
            throw new ResourceNotFoundException("未找到该用户信息");
        }

        // 加密密码
        user.setPassword(passwordEncoderService.encryptPassword(dto.getNewPassword()));
        // 保存user对象
        thinUserRepository.save(user);
        redisAccountService.modifyOnlyRedisAccount(user.getAccount());
        passwordErrorRedisManager.removePasswordErrorCount(user.getAccount());
        resetPasswordTokenService.remove(token);
    }


    //政务网身份证和本地不一样
    public void updateIdNumberForZwfwApp(String account,String idNumber){
        User user = userRepository.findByAccount(account);
        user.setIdNumber(idNumber);
        userRepository.save(user);
    }

    public void updateEmailForZwfw(String idNumber,String email){
        User user = userRepository.findByIdNumber(idNumber);
        user.setEmail(email);
        userRepository.save(user);
    }
    public void updateMobileForZwfw(String idNumber,String mobile){
        User user = userRepository.findByIdNumber(idNumber);
        user.setMobile(mobile);
        userRepository.save(user);
    }
    public void updateEmailForZwfwEnterprise(String orgcode,String email){
        User user = userRepository.findByOrgCode(orgcode);
        user.setEmail(email);
        userRepository.save(user);
    }
    public void updateMobileForZwfwEnterprise(String orgcode,String mobile){
        User user = userRepository.findByOrgCode(orgcode);
        user.setMobile(mobile);
        userRepository.save(user);
    }

    //政务网姓名和本地不一样
    public void updateNameForZwfwApp(String account,String name){
        User user = userRepository.findByAccount(account);
        user.setName(name);
        userRepository.save(user);
    }

    /**
     * 重置密码
     *
     * @param passWordResetDetailDTO
     */
    public void resetPassword(PassWordResetDetailDTO passWordResetDetailDTO, HttpServletRequest request) {
        String idNumber = passWordResetDetailDTO.getIdNumber();
        String name = passWordResetDetailDTO.getName();
        String newPassword = passWordResetDetailDTO.getNewPassword();

        Validate.notBlank(passWordResetDetailDTO.getName(), "请输入姓名");
        // 证件号码
        Validate.notBlank(idNumber, "请输入有效证件号码");
        Validate.notBlank(newPassword, "请输入新密码");
        //ValidatePassword.verifyPassword(newPassword);
        // 查询账号
        ThinUser user = thinUserRepository.findByIdNumber(idNumber);
        if (null == user) {
            throw new ResourceNotFoundException("根据您输入的证件号码未查询到有效的账户，请重新输入");
        }
//        if (!user.getName().equals(name)) {
//            throw new ResourceNotFoundException("您输入的姓名与账户信息不匹配，请重新输入");
//        }
//        String mobilenumber = user.getMobile();
//        if (StringUtils.isEmpty(mobilenumber)) {
//            throw new BusinessException("未预留手机号");
//        }
//校验短信验证码
       // String sms = captchaRequestService.smsCaptchaRequest(mobilenumber, request);
//        if (!"".equals(sms)) {
//            throw new CaptchaErrorException(sms);
//        }
//        加密密码
        user.setPassword(passwordEncoderService.encryptPassword(newPassword));
        // 保存user对象
        thinUserRepository.save(user);
        redisAccountService.modifyOnlyRedisAccount(user.getAccount());
        passwordErrorRedisManager.removePasswordErrorCount(user.getAccount());
    }

    public void resetPasswordForExpert(PassWordResetDetailDTO passWordResetDetailDTO, HttpServletRequest request) {
        String idNumber = passWordResetDetailDTO.getIdNumber();
        String name = passWordResetDetailDTO.getName();
        String newPassword = passWordResetDetailDTO.getNewPassword();

        Validate.notBlank(passWordResetDetailDTO.getName(), "请输入姓名");
        Validate.notBlank(idNumber, "请输入证件号码");
        Validate.notBlank(passWordResetDetailDTO.getPersonNumber(), "请输入专家编号");
        Validate.notBlank(newPassword, "请输入密码");
        ValidatePassword.verifyPassword(newPassword);
        // 查询账号
        ThinUser user = thinUserRepository.findByExpertAccount(idNumber);
        if (null == user) {
            throw new ResourceNotFoundException("根据您输入的证件号码未查询到有效的账户，请重新输入");
        }
        if (!user.getName().equals(name)) {
            throw new ResourceNotFoundException("您输入的姓名与账户信息不匹配，请重新输入");
        }
        PersonExpert expert = verifyPersonExpert(passWordResetDetailDTO.getPersonNumber(), idNumber, name);

        // 校验短信验证码
        String sms = captchaRequestService.smsCaptchaRequest(expert.getMobile(), request);
        if (!"".equals(sms)) {
            throw new CaptchaErrorException(sms);
        }

        // 加密密码
        user.setPassword(passwordEncoderService.encryptPassword(newPassword));
        // 保存user对象
        thinUserRepository.save(user);
        redisAccountService.modifyOnlyRedisAccount(user.getAccount());
    }

    /**
     * 同步user中的角色和姓名
     */
    @Transactional
    public void updateUserForPersonAndRole(String idNumber) {
        // 查询person
        List<Person> personList = personRepositoryExtend.findByCertificate(idNumber);

        // 判断是否存在person
        if (personList.size() != 0) {
            User user = userRepository.findByIdNumber(idNumber);

            // 临时角色，需要同步到user中的
            Set<Role> temp = new HashSet<Role>();
            Set<String> names = new HashSet<String>();
            boolean roleTrue = false;
            for (Person person : personList) {
                names.add(person.getName());
                // 设置为已参保role
                if ("0".equals(person.getRetireStatus())) {
                    // 在职
                    addPersonRoleForTemp(temp, "21");
                } else {
                    // 离退休
                    addPersonRoleForTemp(temp, "22");
                }
            }
            // 更新名字
            if (names.size() == 1) {
                String name = names.iterator().next();
                if (!user.getName().equals(name)) {
                    roleTrue = true;
                    user.setName(name);
                }
            }

            // 数据库中保存的关联角色
            Set<Role> roles = new HashSet<Role>(user.getRoles());
            // 如果不存在数据库中，加入到数据库中
            for (Role role : temp) {// 这是对的，需要同步到user
                if (!roles.contains(role)) {
                    roleTrue = true;
                    user.addRole(role);
                }
            }
            // 更改后的需要保存的关联角色
            roles = new HashSet<Role>(user.getRoles());// 加到user后，需要从user中删除temp中没有的
            // 去掉不包含在临时角色中的角色
            for (Role userRole : roles) {
                if (!temp.contains(userRole)) {
                    roleTrue = true;
                    user.removeRole(userRole);
                }
            }
            if (roleTrue) {
                userRepository.save(user);
                redisAccountService.modifyOnlyRedisAccount(user.getAccount());
            }
        }
    }


    /**
     * 老用户以及虚拟单位
     *
     * @param unitId
     */
    @Override
    public void updateUserForEntAndRole(Long unitId) {

        List<CompanyDTO> companys = companySiService.findByUnitId(unitId);
        LOGGER.debug("companys = {}", companys);
        User user = userRepository.findByUnitId(unitId);
        Set<Role> temp = new HashSet<>();
        temp.add(roleRepository.findByName("ROLE_ENTERPRISE_LABOUR_USER"));
        user.setRoles(temp);
        LOGGER.debug("account = {}", user.getAccount());
        userRepository.save(user);
    }


    @Override
    public void updateUserForEntAndRoleZwfw(String orgCode, String companyName) {
        List<CompanyDTO> companys = companySiService.findByOrgCode(orgCode, companyName, null);
        LOGGER.debug("companys = {}", companys);
        User user = userRepository.findByOrgCode(orgCode);
        Set<Role> temp = new HashSet<>();

        //只有社保角色
        if (companys.size() == 1 && companys.get(0).getName() != null) {
            LOGGER.debug("companys.getLevel = {}", companys.get(0).getLevel());
            if (companys.get(0).getLevel() == 0) {
                temp.add(roleRepository.findByName("ROLE_ENTERPRISE_SIMIS_USER" + companys.get(0).getSocialPoolCode()));
                temp.add(roleRepository.findByName("ROLE_ENTERPRISE_PRILABOUR_USER"));
            } else if (companys.get(0).getLevel() == 1) {
                temp.add(roleRepository.findByName("ROLE_ENTERPRISE_SIMIS_LEVEL_ONE_USER" + companys.get(0).getSocialPoolCode()));
                temp.add(roleRepository.findByName("ROLE_ENTERPRISE_PRILABOUR_USER"));
            } else {
                temp.add(roleRepository.findByName("ROLE_ENTERPRISE_SIMIS_LEVEL_TWO_USER" + companys.get(0).getSocialPoolCode()));
                temp.add(roleRepository.findByName("ROLE_ENTERPRISE_PRILABOUR_USER"));
            }

            if (!"LSPY".equalsIgnoreCase(user.getExtension())) {
                user.setAccount(companys.get(0).getCompanyNumber());
            }
            user.setUnitId(Long.valueOf(companys.get(0).getCompanyNumber()));
            user.setRoles(temp);
        }
        //既没有社保也没有就业
        else if (companys.size() == 1 && companys.get(0).getName() == null) {
            temp.add(roleRepository.findByName("ROLE_ENTERPRISE_PRISIMIS_USER"));
            temp.add(roleRepository.findByName("ROLE_ENTERPRISE_PRILABOUR_USER"));
            user.setRoles(temp);
        }
        //有就业没有社保
        else if (companys.size() == 2 && companys.get(1).getName() == null) {
            temp.add(roleRepository.findByName("ROLE_ENTERPRISE_PRISIMIS_USER"));
            temp.add(roleRepository.findByName("ROLE_ENTERPRISE_LABOUR_USER"));
            user.setRoles(temp);
        }
        ////既有社保又有就业
        else {
            LOGGER.debug("companys.getLevel = {}", companys.get(1).getLevel() == 1);

            if (companys.get(1).getLevel() == 0) {

                temp.add(roleRepository.findByName("ROLE_ENTERPRISE_LABOUR_USER"));
            } else if (companys.get(1).getLevel() == 1) {

                temp.add(roleRepository.findByName("ROLE_ENTERPRISE_LABOUR_USER"));
            } else {

                temp.add(roleRepository.findByName("ROLE_ENTERPRISE_LABOUR_USER"));
            }
            user.setRoles(temp);
            if (!user.getExtension().startsWith("LSPY")) {
                user.setAccount(companys.get(1).getCompanyNumber());
            }
            user.setUnitId(Long.valueOf(companys.get(1).getCompanyNumber()));
        }

        user.setName(companyName);
        LOGGER.debug("account = {}", user.getAccount());
        //虚拟单位且密码为123456单独授权
        if (user.getExtension().startsWith("LSPY") && !user.getPassword().equals(passwordEncoderService.encryptPassword("123456"))) {
            Set<Role> virtualTemp = new HashSet<>();
            virtualTemp.add(roleRepository.findByName("ROLE_ENTERPRISE_LABOUR_USER"));
            user.setRoles(virtualTemp);
        } else if (user.getExtension().startsWith("LSPY") && user.getPassword().equals(passwordEncoderService.encryptPassword("123456"))) {
            LOGGER.debug("校验虚拟单位密码123456  ");
            Set<Role> virtualTemp = new HashSet<>();
            virtualTemp.add(roleRepository.findByName("ROLE_ENTERPRISE_LABOUR_USER"));
            user.setRoles(virtualTemp);
        }
        userRepository.save(user);

    }


    //政务服务专用提升角色
    @Transactional
    @Override
    public void updateUserForPersonAndRoleByZwfw(String idNumber, String name) {
        // 查询person
        LOGGER.debug("idNumber= {}. name = {}");
        List<PersonBasicInfo> personListAll = personService.findByIdNumberAndName(idNumber, name);
        //List<PersonBasicInfo> personList = simisWebService.queryPersonInfo(idNumber, name, "").getPersonBasicInfoList();
        LOGGER.debug("idNumber= {}. name = {}, personList = {}", idNumber, name, personListAll);
        User user = userRepository.findByIdNumber(idNumber);
        Set<Role> temp = new HashSet<>();
        //查询就业
        // 判断是否存在person
        if (personListAll.size() == 0) {
            temp.add(roleRepository.findByName("ROLE_PERSON_ORDINARY_USER"));
        } else {
            for (PersonBasicInfo person : personListAll
            )
            //{
            //    LOGGER.debug("person.getClientType())= {}", person.getClientType());
            //    temp.add(roleRepository.findByName(person.getClientType()));
            //}
            temp.add(roleRepository.findByName("ROLE_PERSON_ORDINARY_USER"));
            user.setRoles(temp);
            userRepository.save(user);
            redisAccountService.modifyOnlyRedisAccount(user.getAccount());
        }
    }


//    @Transactional
//    @Override
//    public void updateUserForPersonAndRoleByZwfw(String idNumber, String name) {
//        // 查询person
//        LOGGER.debug("idNumber= {}. name = {}");
//        List<PersonBasicInfo> personListAll = personService.findByIdNumberAndName(idNumber,name);
//        List<PersonBasicInfo> personList = simisWebService.queryPersonInfo(idNumber, name, "").getPersonBasicInfoList();
//        LOGGER.debug("idNumber= {}. name = {}, personList = {}", idNumber, name, personList);
//        //查询就业
//        // 判断是否存在person
//        if (personList != null && personList.size() != 0) {
//            User user = userRepository.findByIdNumber(idNumber);
//            user.addRole(roleRepository.findByName("ROLE_PERSON_CX"));
//            // 临时角色，需要同步到user中的
//            Set<Role> temp = new HashSet<Role>();
//            Set<String> names = new HashSet<String>();
//            temp.add(roleRepository.findByName("ROLE_PERSON_CX"));
//            boolean roleTrue = false;
////            for (PersonBasicInfo person : personList) {
////                names.add(person.getAAC003());
////                // 设置为已参保role
////                if ("0".equals(person.getAAC008())) {
////                    // 在职
////                    addPersonRoleForTemp(temp, "21");
////                } else {
////                    // 离退休
////                    addPersonRoleForTemp(temp, "22");
////                }
////            }
//            // 更新名字
//            if (names.size() == 1) {
//                String aac003 = names.iterator().next();
//                if (!user.getName().equals(aac003)) {
//                    roleTrue = true;
//                    user.setName(name);
//                }
//            }
//            user.setRoles(temp);
//            // 数据库中保存的关联角色
//            Set<Role> roles = new HashSet<Role>(user.getRoles());
//            // 如果不存在数据库中，加入到数据库中
//            for (Role role : temp) {// 这是对的，需要同步到user
//                if (!roles.contains(role)) {
//                    roleTrue = true;
//                    user.addRole(role);
//                }
//            }
//            // 更改后的需要保存的关联角色
//            roles = new HashSet<Role>(user.getRoles());// 加到user后，需要从user中删除temp中没有的
//            // 去掉不包含在临时角色中的角色
//            for (Role userRole : roles) {
//                if (!temp.contains(userRole)) {
//                    roleTrue = true;
//                    user.removeRole(userRole);
//                }
//            }
//            if (roleTrue) {
//                userRepository.save(user);
//                redisAccountService.modifyOnlyRedisAccount(user.getAccount());
//            }
//        }
//    }

    private void addPersonRoleForTemp(Set<Role> temp, String type) {
        for (Role role : personUserRoleProvider.getUserInitRole(type)) {
            temp.add(role);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.neusoft.sl.saber.authserver.service.user.UserCustomService#
     * createEnterpriseUser(com.neusoft.sl.saber.authserver.domain.model.user.
     * User)
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User createEnterpriseUser(User user) {
        verifyAccountExists(user);
        setEnterprisePassword(user);
        Set<Role> virtualTemp = new HashSet<>();
        virtualTemp.add(roleRepository.findByName("ROLE_ENTERPRISE_LABOUR_USER"));
        user.setRoles(virtualTemp);

//		Set<Company> companys = associateCompany(user);
//		user.setCompanys(companys);
        // 注册时间
        user.setRegistedDate(LongDateUtils.nowAsSecondLong().toString());
        // 登记用户
        return userRepository.save(user);
    }



    private Set<Company> associateCompany(User user) {
        // 关联单位
        Set<Company> companys = new HashSet<Company>();
        for (Company company : user.getCompanys()) {
            String companyNumber = company.getCompanyNumber();
            company = companyRepository.findByCompanyNumber(companyNumber);
            if (company == null) {
                throw new SiInfoNotExistsException(companyNumber);
            }
            UserCompanyEntity entity = userCompanyEntityRepository.findByCompanyid(company.getId().toString());
            if (null != entity) {
                User temp = userRepository.findById(entity.getUserid());
                if (temp == null) {
                    throw new IllegalArgumentException("存在垃圾数据，单位编号：" + companyNumber + "存在关联关系，但是无账号信息");
                }
                throw new IllegalArgumentException("单位编号：" + companyNumber + "已经关联到用户Account：" + temp.getAccount());
            }
            companys.add(company);
        }
        return companys;
    }

    private Set<Role> addUserRoleByRoleName(User user) {
        // 加入用户权限
        Set<Role> roles = new HashSet<Role>();
        for (Role role : user.getRoles()) {
            String roleName = role.getName();
            role = roleRepository.findByName(roleName);
            if (role == null) {
                throw new ResourceNotFoundException("角色[" + roleName + "]不存在");
            }
            if (socialRole.contains(roleName)) {
                CompanySi companySi = companySiRepository.findByCompanyNumber(user.getAccount());
                if (companySi == null) {
                    throw new BusinessException("角色选择不正确，主账号 " + user.getAccount() + " 未参保");
                }
            }
            roles.add(role);
        }
        return roles;
    }

    private void verifyAccountExists(User user) {
        // 验证用户账号是否存在
        if (userService.isAccountExists(user.getAccount())) {
            throw new AccountExistsException(user.getAccount());
        }
    }

    private void setEnterprisePassword(User user) {
        // 如果提供了密码加密密码
        if (StringUtils.isNotBlank(user.getPassword())) {
            // 加密密码
            user.setPassword(passwordEncoderService.encryptPassword(user.getPassword()));
        } else {
            // 默认密码
            user.setPassword(passwordEncoderService.encryptPassword(password));
        }
    }

    // @Override
    // @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    // public User createEnterpriseUser(User user, Set<String> userType) {
    // verifyAccountExistsWithAccount(user);
    //
    // setEnterprisePassword(user);
    //
    // // 加入用户初始权限
    // for (String type : userType) {
    // for (Role role : enterpriseUserRoleProvider.getUserInitRole(type)) {
    // user.addRole(role);
    // }
    // }
    //
    // Set<Company> companys = associateCompany(user);
    //
    // // 添加主单位
    // // Company mainCompany =
    // // companyRepository.findByCompanyNumber(user.getMainCompanyNum());
    // // if (mainCompany == null) {
    // // throw new SiInfoNotExistsException(user.getMainCompanyNum());
    // // }
    // // companys.add(mainCompany);
    // user.setCompanys(companys);
    // // 注册时间
    // user.setRegistedDate(LongDateUtils.nowAsSecondLong().toString());
    // // 登记用户
    // return userRepository.save(user);
    // }

    public AtmVerifyResultDTO verifyPersonUserATM(String mobilenumber, String cardNumber) {
        List<Person> persons = personRepository.findBySocialSecurityCardNumber(cardNumber);
        if (persons == null || persons.size() == 0) {
            throw new BusinessException("根据此社保卡号未获取到人员基本信息");
        }
        Person person = persons.get(0);
        AtmVerifyResultDTO dto = new AtmVerifyResultDTO();

        Family family = familyRepository.findByCardNumber(cardNumber);
        if (null == family) {
            // 社保卡未被绑定
            ThinUser thinUser = thinUserRepository.findByMobile(mobilenumber);
            if (null == thinUser) {
                // 手机号未注册
                thinUser = thinUserRepository.findByIdNumber(person.getCertificate().getNumber());
                if (null == thinUser) {
                    // 社保卡关联的身份证号未注册
                    dto.setCode("00");
                    dto.setMessage("手机号码未注册，社保卡未被绑定");
                } else {
                    // 社保卡关联的身份证号已注册 -手机号码未注册，社保卡未被绑定
                    dto.setCode("01");
                    dto.setMessage("此社保卡关联的身份证号已注册，请使用该身份证号登录南宁人社APP绑定该手机号码，或使用其它身份证号注册并绑定该手机号码");
                }
            } else {
                if (thinUser.isRealNameAuthed() || thinUser.getIdNumber().equals(person.getCertificate().getNumber())) {
                    dto.setCode("00");
                    dto.setMessage("手机号码已注册，社保卡未被绑定");
                } else {
                    dto.setCode("01");
                    dto.setMessage("此手机号码注册的账号未实名，无法绑定其它社保卡");
                }
            }
        } else {
            // 社保卡已被绑定
            User thinUser = userRepository.findByMobile(mobilenumber);
            if (null == thinUser) {
                // 手机号未注册
                List<UserFamilyEntity> list = userFamilyEntityRepository.findByFamilyid(family.getId());
                if (list.size() > 1) {
                    throw new BusinessException("此社保卡已经被多个账号关联");
                } else if (list.size() == 0) {
                    dto.setCode("01");
                    dto.setMessage("手机号码未注册，社保卡绑定的用户不存在");
                } else {
                    // 存在用户
                    User temp = userRepository.findById(list.get(0).getUserId());
                    if (null == temp) {
                        dto.setCode("01");
                        dto.setMessage("手机号码未注册，社保卡绑定的用户不存在");
                    } else {
                        if (temp.getIdNumber().equals(person.getCertificate().getNumber())) {
                            dto.setCode("01");
                            dto.setMessage("手机号码未注册，社保卡已绑定到此社保卡关联的身份证号用户下");
                        } else {
                            dto.setCode("01");
                            dto.setMessage("手机号码未注册，社保卡已被其他用户绑定");
                        }
                    }
                }
            } else {
                // 手机号已注册
                Set<Family> sets = thinUser.getFamilies();
                if (sets.contains(family)) {
                    dto.setCode("01");
                    dto.setMessage("手机号码已注册，社保卡已绑定成功");
                } else {
                    dto.setCode("01");
                    dto.setMessage("手机号码已注册，社保卡已被其他用户绑定");
                }

            }
        }
        LOGGER.debug(cardNumber + "_" + dto.getCode() + "_" + dto.getMessage());
        return dto;
    }

    /**
     * 一体机创建用户
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User createPersonUserATM(User user) {
        String idNumber = user.getIdNumber();
        String mobile = user.getMobile();
        // 证件号码
        Validate.notBlank(user.getIdType(), "证件类型不能为空");
        Validate.notBlank(idNumber, "证件号码不能为空");
        Validate.notBlank(mobile, "手机号码不能为空");
        idNumber = idNumber.toUpperCase();
        // 校验手机号码是否已经注册过，注册过直接返回账号
        User thinUser = userRepository.findByMobile(mobile);
        if (null != thinUser) {
            return thinUser;
        }
        // 校验证件号码是否已经注册过
        verifyIdNumberExists(idNumber);
        // 用户密码
        // ValidatePassword.verifyPassword(user.getPassword());
        // 添加角色
        // 加入用户初始权限 个人未参保用户
        addPersonRole(user, "20");
        // 注册时间
        user.setRegistedDate(LongDateUtils.nowAsSecondLong().toString());
        // 保存账户信息
        User newUser = userService.createUserWithPassWord(user);
        return newUser;
    }

    public User createPersonCloudbae(String username, String idNumber, String name, String mobile, String extension,String email) {
        LOGGER.debug("政务服务网创建用户idNumber={},name={},mobile={},username = {}", username, idNumber, name, mobile);
        PersonUserDTO userDTO = new PersonUserDTO();
        userDTO.setAccount(username);
        userDTO.setIdNumber(idNumber);
        userDTO.setEmail(email);
        userDTO.setIdType("01");
        userDTO.setMobilenumber(mobile);
        userDTO.setName(name);
        userDTO.setPassword(UuidUtils.getRandomUUIDString());
        User user = PersonUserDTOAssembler.crtfromDTO(userDTO);
        // user.setExtension("cloudbae_register");
        user.setExtension(extension);
        return createPersonUserCloudbae(user);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User createPersonUserCloudbae(User user) {
        String idNumber = user.getIdNumber();
        String mobilenumber = user.getMobile();
        // 证件号码
        // Validate.notBlank(user.getIdType(), "证件类型不能为空");
        // Validate.notBlank(mobilenumber, "手机号码不能为空");
        // 证件号码
        // verifyIdentityCard(idNumber);
        // 校验证件号码是否已经注册过
        verifyIdNumberExists(idNumber);
        // 处理手机号
//		if (StringUtils.isNotBlank(mobilenumber)) {
//			// 手机号是否已注册
//			ThinUser thinUser = thinUserRepository.findByMobile(mobilenumber);
//			// 取回手机号
//			if (thinUser != null) {
//				thinUser.setMobile(null);
//				thinUserRepository.save(thinUser);
//				redisAccountService.modifyOnlyRedisAccount(thinUser.getAccount());
//			}
//		}
        // 添加角色
        addUserRoleByPerson(user);
        addPersonRole(user, "21");
        String date = LongDateUtils.nowAsSecondLong().toString();
        user.setRegistedDate(date);// 注册时间
        user.setBindCardAuthed(true);
        user.setRealNameAuthed(true);
        // 暂不实名
        // user.setRealNameAuthed(true);// 主账号实名
        // user.setRealNameDate(date);// 账号实名时间
        // 保存账户信息
        User newUser = userService.createUserWithPassWord(user);
        if (newUser.isRealNameAuthed()) {
            savePersonAuthedLogBack(newUser, "cloudbae", "cloudbaeAuth", "", date);
        }
        return newUser;
    }

    public void savePersonAuthedLogBack(User user, String authClient, String authType, String personCardNumber, String authDate) {
        // 保存实名认证日志
        PersonAuthedLogBack authedLogBack = new PersonAuthedLogBack();
        authedLogBack.setAuthClient(authClient);
        authedLogBack.setAuthDate(authDate);
        authedLogBack.setPersonAccount(user.getAccount());
        authedLogBack.setPersonIdNumber(user.getIdNumber());
        authedLogBack.setPersonName(user.getName());
        authedLogBack.setAuthType(authType);
        authedLogBack.setPersonCardNumber(personCardNumber);
        authedLogBackRepository.save(authedLogBack);
    }

    private void addUserRoleByPerson(User user) {
        // 根据身份证号、姓名查询社保个人信息

        List<Person> personList = personRepositoryExtend.findByCertificate(user.getIdNumber());
        if (personList.size() == 0) {
            // 加入用户初始权限 个人未参保用户
            addPersonRole(user, "20");
        } else {
            for (Person person : personList) {
                if ("0".equals(person.getRetireStatus())) {
                    addPersonRole(user, "21");// 在职
                } else {
                    addPersonRole(user, "22");// 离退休
                }
            }
        }
    }

    private void verifyIdentityCard(String idNumber) {
        Validate.notBlank(idNumber, "证件号码不能为空");
        IdentityCard card = new IdentityCard(idNumber);
        if (!card.verify()) {
            throw new IllegalArgumentException("证件号码不合法");
        }
        if (idNumber.contains("x")) {
            throw new IllegalArgumentException("证件号码不合法，X需大写");
        }
    }

    private void verifyIdNumberExists(String idNumber) {
        // 增加查询已经注册过的用户
        ThinUser thinUser = thinUserRepository.findByIdNumber(idNumber);
        if (null != thinUser) {
            throw new BusinessException("您输入的证件号码已注册，请直接登录");
        }
    }

    private void verifySiInfo(String idNumber, String name) {
        // 根据身份证号、姓名查询社保个人信息
        List<Person> personList = personRepositoryExtend.findByCertificate(idNumber);
        if (personList.size() != 0) {
            List<Person> persons = filterPersonForName(personList, name);
            if (persons.size() == 0) {
                throw new ResourceNotFoundException("您输入的姓名与记录的不匹配，请重新输入");
            }
        }
        // if (personList.size() > 1) {
        // throw new BusinessException("该身份证号码已关联多个社保编号，请到当地社保服务大厅窗口办理人员合并");
        // }
        // if (!name.equals(personList.get(0).getName())) {
        // throw new BusinessException("姓名有误，请修改");
        // }
        // 校验社保编号
    }

    private List<Person> filterPersonForName(List<Person> personList, String name) {
        List<Person> filters = new ArrayList<Person>();
        for (Person person : personList) {
            if (name.equals(person.getName())) {
                filters.add(person);
            }
        }
        return filters;
    }

    /**
     * 创建用户，查询公安接口校验公安，默认未参保用户
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User createPerson(User user) {
        String idNumber = user.getIdNumber();
        String mobile = user.getMobile();
        String name = user.getName();
        Validate.notBlank(user.getIdType(), "证件类型不能为空");
        Validate.notBlank(mobile, "手机号码不能为空");
        // 证件号码
        verifyIdentityCard(idNumber);
        // 校验证件号码是否已经注册过
        verifyIdNumberExists(idNumber);
        // 用户密码
        //ValidatePassword.verifyPassword(user.getPassword());
        // 查询公安 校验信息
        // gaAuthService.identityAuth(idNumber, name);
        // 主要校验名字
       // verifySiInfo(idNumber, name);
        // 加入用户初始权限 个人未参保用户
        Set<Role> virtualTemp = new HashSet<>();
        virtualTemp.add(roleRepository.findByName("ROLE_PERSON_ORDINARY_USER"));
        user.setRoles(virtualTemp);
        // 注册时间
        user.setRegistedDate(LongDateUtils.nowAsSecondLong().toString());
        // 保存账户信息
        User newUser = userService.createUserWithPassWord(user);
        return newUser;
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User createPersonBenxi(User user) {
        String idNumber = user.getIdNumber();
        String mobile = user.getMobile();
        String name = user.getName();
        Validate.notBlank(user.getIdType(), "证件类型不能为空");
        // 证件号码
        verifyIdentityCard(idNumber);
        // 校验证件号码是否已经注册过
        verifyIdNumberExists(idNumber);
        // 用户密码
        //ValidatePassword.verifyPassword(user.getPassword());
        // 查询公安 校验信息
        // gaAuthService.identityAuth(idNumber, name);
        // 主要校验名字
        // verifySiInfo(idNumber, name);
        // 加入用户初始权限 个人未参保用户
        Set<Role> virtualTemp = new HashSet<>();
        virtualTemp.add(roleRepository.findByName("ROLE_PERSON_ORDINARY_USER"));
        user.setRoles(virtualTemp);
        // 注册时间
        user.setRegistedDate(LongDateUtils.nowAsSecondLong().toString());
        // 保存账户信息
        User newUser = userService.createUserWithPassWord(user);
        return newUser;
    }




    /**
     * 创建用户，查询公安接口校验公安，默认未参保用户
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User createPersonZwfw(User user) {
        String idNumber = user.getIdNumber();
        String mobile = user.getMobile();
        String name = user.getName();

        Validate.notBlank(user.getIdType(), "证件类型不能为空");
        Validate.notBlank(mobile, "手机号码不能为空");
        // 证件号码
        verifyIdentityCard(idNumber);
        // 校验证件号码是否已经注册过
        verifyIdNumberExists(idNumber);
        // 用户密码
        //ValidatePassword.verifyPassword(user.getPassword());
        // 查询公安 校验信息
        // gaAuthService.identityAuth(idNumber, name);
        // 主要校验名字
        verifySiInfo(idNumber, name);

        // 手机号是否已注册
        ThinUser thinUser = thinUserRepository.findByMobile(mobile);
        // 取回手机号
        if (thinUser != null) {
            thinUser.setMobile(null);
            thinUserRepository.save(thinUser);
            redisAccountService.modifyOnlyRedisAccount(thinUser.getAccount());
        }
        // 加入用户初始权限 个人未参保用户
        addPersonRole(user, "20");
        // 注册时间
        user.setRegistedDate(LongDateUtils.nowAsSecondLong().toString());
        // 保存账户信息
        User newUser = userService.createUserWithPassWordZwfw(user);
        return newUser;
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User createExpertUser(User user, String personNumber, HttpServletRequest request) {
        String idNumber = user.getExpertAccount();
        String name = user.getName();

        // 校验证件号码是否已经激活
        verifyExpertAccountExists(idNumber);
        // 验证专家编号和身份证号姓名是否一致
        PersonExpert expert = verifyPersonExpert(personNumber, idNumber, name);
        // 用户密码
        ValidatePassword.verifyPassword(user.getPassword());
        // 校验短信验证码
        String sms = captchaRequestService.smsCaptchaRequest(expert.getMobile(), request);
        if (!"".equals(sms)) {
            throw new CaptchaErrorException(sms);
        }
        // 加入用户初始权限 专家用户
        addPersonRole(user, "40");
        // 注册时间
        user.setRegistedDate(LongDateUtils.nowAsSecondLong().toString());
        // 保存账户信息
        User newUser = userService.createUserWithPassWord(user);
        return newUser;
    }

    private PersonExpert verifyPersonExpert(String personNumber, String idNumber, String name) {
        PersonExpert expert = personExpertRepository.findById(personNumber);
        if (expert == null) {
            throw new BusinessException("根据您输入的专家编号 " + personNumber + " 未查询到关联的专家信息，请仔细核对");
        }

        if (!idNumber.equals(expert.getIdNumber())) {
            throw new BusinessException("您输入的证件号码与登记的证件号码不一致，请仔细核对");
        }

        if (!name.equals(expert.getName())) {
            throw new BusinessException("您输入的姓名与登记的姓名不一致，请仔细核对");
        }
        return expert;
    }

    private void verifyExpertAccountExists(String idNumber) {
        // 增加查询已经注册过的用户
        ThinUser thinUser = thinUserRepository.findByExpertAccount(idNumber);
        if (null != thinUser) {
            throw new BusinessException("您输入的证件号码已激活，请直接登录");
        }
    }

    private void addPersonRole(User user, String type) {
        for (Role role : personUserRoleProvider.getUserInitRole(type)) {
            user.addRole(role);
        }
    }

    /**
     * 根据个人编号过滤
     *

     * @return
     */
    // private List<Person> filterPerson(List<Person> personList, String
    // personNumber) {
    // List<Person> filters = new ArrayList<Person>();
    // for (Person person : personList) {
    // if (personNumber.equals(person.getPersonNumber().getNumber())) {
    // filters.add(person);
    // }
    // }
    // return filters;
    // }

    /*
     * (non-Javadoc)
     *
     * @see com.neusoft.sl.saber.authserver.service.user.UserCustomService#
     * createPersonUserWithSiInfo(com.neusoft.sl.saber.authserver.domain.model.
     * user.User)
     */

    // @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    // public User createPersonUserWithSiInfo(User user) {
    // return this.createPersonUserWithSiInfo(user, null);
    // }

    /*
     * (non-Javadoc)
     *
     * @see com.neusoft.sl.saber.authserver.service.user.UserCustomService#
     * createSiAgentUser(com.neusoft.sl.saber.authserver.domain.model.user.User)
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User createSiAgentUser(User user) {
        // 加入用户初始权限
        for (Role role : siagentUserRoleProvider.getUserInitRole(user.getUserTypeString())) {
            user.addRole(role);
        }
        // 保存账户信息
        User newUser = userService.createUserWithPassWord(user);
        return newUser;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User createSiAgentUserWithUserType(User user, String userTypeCustom) {
        if ("".equals(userTypeCustom) || null == userTypeCustom) {
            throw new ResourceNotFoundException("未设置用户类型");
        }
        // 加入用户初始权限
        for (Role role : siagentUserRoleProvider.getUserInitRole(userTypeCustom)) {
            user.addRole(role);
        }
        // 保存账户信息
        User newUser = userService.createUserWithPassWord(user);
        return newUser;
    }
    /*
     * (non-Javadoc)
     *
     * @see com.neusoft.sl.saber.authserver.service.user.UserCustomService#
     * activeOhwyaa(com.neusoft.sl.saber.authserver.domain.model.user.User)
     */

    public User activeOhwyaa(User user) {
        user.activeOhwyaa();
        return userService.updateUser(user);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.neusoft.sl.saber.authserver.service.user.DefaultUserCustomImpl#
     * createEnterpriseUserWithActive(com.neusoft.sl.saber.authserver.domain.
     * model.user. User)
     */

    public User createEnterpriseUserWithOrgCode(User user) {

        user.addRole(roleRepository.findByName("ROLE_ENTERPRISE_LABOUR_USER"));
        // verifyAccountExistsWithAccount(user);
        //setEnterprisePassword(user);
        //Set<Role> roles = addUserRoleByRoleName(user);
        List<Company> companyList = new ArrayList<Company>();
        List<CompanyDTO> companys = companySiService.findByOrgCode(user.getOrgCode(), user.getName(), null);
        LOGGER.debug("companys = {},size = {}", companys, companys.size());
        if (companys.size() != 1) {
            throw new RuntimeException("一个统一信用代码对应多个单位！");
        }
        // 注册时间
        LOGGER.debug("user.role = {}", user.getRoles());
        user.setRegistedDate(LongDateUtils.nowAsSecondLong().toString());
        // 登记用户
        LOGGER.debug("user.account = {}", user.getAccount());
        return userService.createUserWithPassWord(user);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User addCompanyForEnterpriseUser(User user, List<CompanyInfoDTO> associatedCompanys) {
        for (CompanyInfoDTO dto : associatedCompanys) {
            String companyNumber = dto.getCompanyNumber();
            Company company = companyRepository.findByCompanyNumber(companyNumber);
            if (company == null) {
                throw new SiInfoNotExistsException(companyNumber);
            }
            UserCompanyEntity entity = userCompanyEntityRepository.findByCompanyid(company.getId().toString());
            if (null != entity) {
                User temp = userRepository.findById(entity.getUserid());
                if (temp == null) {
                    throw new IllegalArgumentException("存在垃圾数据，单位编号：" + companyNumber + "存在关联关系，但是无账号信息");
                }
                throw new IllegalArgumentException("单位编号：" + companyNumber + "已经关联到用户Account：" + temp.getAccount());
            }
            user.addCompany(company);
        }
        User u = userRepository.save(user);
        redisAccountService.modifyRedis(user.getAccount());
        return u;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User removeCompanyForEnterpriseUser(User user, List<CompanyInfoDTO> associatedCompanys) {
        for (CompanyInfoDTO dto : associatedCompanys) {
            String companyNumber = dto.getCompanyNumber();
            if (user.getAccount().equals(companyNumber)) {
                throw new IllegalArgumentException("不允许解绑账号的主单位编号：" + companyNumber);
            }
            Company company = companyRepository.findByCompanyNumber(companyNumber);
            if (company == null) {
                throw new SiInfoNotExistsException(companyNumber);
            }
            user.removeCompany(company);
        }
        User u = userRepository.save(user);
        redisAccountService.modifyRedis(user.getAccount());
        return u;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User modifyEnterpriseUser(User user, EnterpriseUserDTO edto) {
        Set<Role> roles = new HashSet<Role>();
        if (null != edto.getRoles()) {
            RoleUtil.allowRoles(edto.getRoles());
            for (RoleDTO dto : edto.getRoles()) {
                String roleName = dto.getName();
                Role role = roleRepository.findByName(roleName);
                if (role == null) {
                    throw new ResourceNotFoundException("角色[" + roleName + "]不存在");
                }
                if (socialRole.contains(roleName)) {
                    CompanySi companySi = companySiRepository.findByCompanyNumber(user.getAccount());
                    if (companySi == null) {
                        throw new BusinessException("角色选择不正确，主账号 " + user.getAccount() + " 未参保");
                    }
                }
                roles.add(role);
            }
        }

        if (!roles.isEmpty()) {
            user.setRoles(roles);
        }
        if (StringUtils.isNotBlank(edto.getName())) {
            user.setName(edto.getName());
        }
        User u = userRepository.save(user);
        redisAccountService.modifyOnlyRedisAccount(user.getAccount());
        return u;
    }

}
