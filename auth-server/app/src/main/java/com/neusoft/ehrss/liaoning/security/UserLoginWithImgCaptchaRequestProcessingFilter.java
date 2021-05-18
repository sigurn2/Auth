package com.neusoft.ehrss.liaoning.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.enums.AreaCodeEnum;
import com.neusoft.sl.girder.ddd.hibernate.utils.SpringContextUtils;
import com.neusoft.sl.si.authserver.base.domains.user.*;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.password.CheckPwdService;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTOAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import com.neusoft.ehrss.liaoning.utils.DesHelp;
import com.neusoft.sl.si.authserver.uaa.exception.CaptchaErrorException;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.CaptchaRequestService;
import com.neusoft.sl.si.authserver.uaa.validate.login.PasswordErrorRedisManager;

import static com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.enums.AreaCodeEnum.ST;

/**
 * 前后台分离处理用户名密码验证码Filter
 */
public class UserLoginWithImgCaptchaRequestProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(UserLoginWithImgCaptchaRequestProcessingFilter.class);

    private CaptchaRequestService captchaRequestService;

    private PasswordErrorRedisManager passwordErrorRedisManager;

    private TempUserRepository tempUserRepository;
   
    private PersonTempUserRepository personTempUserRepository;


    private ThinUserRepository thinUserRepository;

    private UserCustomService userCustomService;

    public UserLoginWithImgCaptchaRequestProcessingFilter(String filterProcessesUrl) {
        super(filterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = request.getParameter("username");
        String userpassword = request.getParameter("password");
        String areaCode = request.getHeader("areaCode");
        if (StringUtils.isEmpty(username)) {
            throw new CaptchaErrorException("请输入账号");
        }
        if (StringUtils.isEmpty(userpassword)) {
            throw new CaptchaErrorException("请输入密码");
        }

        //本溪个人老项目静默注册
        //校验是否已经转换，如果已经转换则不需要查询转换表
        //先查询是否存在tuser 存在则校验是否是转换用户，如果是 则 锁定用户，否则校验转换用户名密码
        if (null == thinUserRepository.findByIdNumber(username) && request.getRequestURI().contains("person")) {
            
            CheckPwdService checkPwdService = SpringContextUtils.getBean("benxiCheckPwdService");

            log.debug("idNumber = {}",username);
            PersonTempUser personTempUser = personTempUserRepository.findByIdNumber(username);
            log.debug("repo = {}",personTempUserRepository);
            if (null != personTempUser && thinUserRepository.findByIdNumber(username)== null) {
                //匹配密码 生成新用户
                if (checkPwdService.matchbenxi(userpassword, personTempUser.getPassword())) {
                    //塞数据
                    PersonUserDTO personUserDTO = new PersonUserDTO();
                    personUserDTO.setIdNumber(personTempUser.getIdNumber());
                    personUserDTO.setName(personTempUser.getName());
                    personUserDTO.setPassword(personTempUser.getPassword());
                    personUserDTO.setAccount(personTempUser.getIdNumber());
                    User user = PersonUserDTOAssembler.crtfromDTO(personUserDTO);
                    user.setIdType("01");
                    user.setExtension("benxi_old_autoReg");
                    // 保存user对象
                    userCustomService.createPersonBenxi(user);
                }

            } else if (null == personTempUser &&thinUserRepository.findByIdNumber(username)== null){
                throw new BadCredentialsException("用户名密码错误");
            }

        }
        
        
        
        //校验是否已经转换，如果已经转换则不需要查询转换表
        //先查询是否存在tuser 存在则校验是否是转换用户，如果是 则 锁定用户，否则校验转换用户名密码
        if (null == thinUserRepository.findByAccount(username) && !request.getRequestURI().contains("person")) {
            areaCode = "210500";
            if (StringUtils.isEmpty(areaCode)) {
                areaCode = "210500";
            }
            //获取老用户名密码是否匹配

                username = areaCode + "@@" + username;
             //   try{
             //       CheckPwdService checkPwdService = SpringContextUtils.getBean(AreaCodeEnum.getValue(areaCode) + "CheckPwdService");
             //   }catch(Exception e){
             //       throw new BadCredentialsException("请更换到虚拟网厅入口或市级单位网厅原账号登录入口登录！");
             //   }
                CheckPwdService checkPwdService = SpringContextUtils.getBean("benxiCheckPwdService");
                //校验是否老用户
                log.debug("repo = {},tempuser= {},username = {}, areacode = {}",tempUserRepository, tempUserRepository.findByUsernameAndAreaCode(username.substring(8), areaCode),username.substring(8),areaCode);
                TempUser tempUser = tempUserRepository.findByUsernameAndAreaCode(username.substring(8), areaCode);
                if (null != tempUser && thinUserRepository.findByAccount(username)== null) {
                    //匹配密码 生成新用户
                    if (checkPwdService.match(userpassword, tempUser.getPassword())) {
                        EnterpriseUserFactory factory = new EnterpriseUserFactory();
                        User user = factory.createEnterpriseUser("0", username, tempUser.getCompanyName(), "", "", null);
                        user.setPassword(userpassword);
                        user.setExtension(areaCode + "_transfer");
                        user.setAccount(username);

                        user.setUnitId(Long.valueOf(tempUser.getCompanyNumber()));
                        //先把单位编号设置为统一信用代码
                        if (!StringUtils.isEmpty(tempUser.getOrgCode())) {
                            user.setOrgCode(tempUser.getOrgCode());
                        }
                        userCustomService.createEnterpriseUser(user);
                    }

                } else if (null == tempUser &&thinUserRepository.findByAccount(username)== null){
                    throw new BadCredentialsException("用户名密码错误");
                }

        }



        String img = captchaRequestService.imgCaptchaRequest(request);
        if (!"".equals(img)) {
            throw new CaptchaErrorException(img);
        }

        passwordErrorRedisManager.verifyPasswordErrorCount(request.getSession().getId(), username);

        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, userpassword);

        setDetails(request, authRequest);
        try {
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (Exception e) {
            if (e instanceof UsernameNotFoundException) {
                passwordErrorRedisManager.updatePasswordErrorCount(request.getSession().getId(), username);
            }
            throw e;
        }
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public PasswordErrorRedisManager getPasswordErrorRedisManager() {
        return passwordErrorRedisManager;
    }

    public void setPasswordErrorRedisManager(PasswordErrorRedisManager passwordErrorRedisManager) {
        this.passwordErrorRedisManager = passwordErrorRedisManager;
    }

    public CaptchaRequestService getCaptchaRequestService() {
        return captchaRequestService;
    }

    public void setCaptchaRequestService(CaptchaRequestService captchaRequestService) {
        this.captchaRequestService = captchaRequestService;
    }

    public TempUserRepository getTempUserRepository() {
        return tempUserRepository;
    }

    public void setTempUserRepository(TempUserRepository tempUserRepository) {
        this.tempUserRepository = tempUserRepository;
    }

    public PersonTempUserRepository getPersonTempUserRepository() {
        return personTempUserRepository;
    }

    public void setPersonTempUserRepository(PersonTempUserRepository personTempUserRepository) {
        this.personTempUserRepository = personTempUserRepository;
    }

    public ThinUserRepository getThinUserRepository() {
        return thinUserRepository;
    }

    public void setThinUserRepository(ThinUserRepository thinUserRepository) {
        this.thinUserRepository = thinUserRepository;
    }

    public UserCustomService getUserCustomService() {
        return userCustomService;
    }

    public void setUserCustomService(UserCustomService userCustomService) {
        this.userCustomService = userCustomService;
    }
}
