package com.neusoft.sl.si.authserver.uaa.controller;

import com.neusoft.ehrss.liaoning.security.userdetails.redis.PersonRedisUser;
import com.neusoft.ehrss.liaoning.security.userdetails.redis.PersonRedisUserManager;
import com.neusoft.ehrss.liaoning.utils.HttpResultDTO;
import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.girder.security.oauth2.domain.AuthenticatedCasUser;
import com.neusoft.sl.si.authserver.base.domains.log.BindAccountLog;
import com.neusoft.sl.si.authserver.base.domains.person.Person;
import com.neusoft.sl.si.authserver.base.domains.person.PersonRepositoryExtend;
import com.neusoft.sl.si.authserver.base.domains.role.RoleRepository;
import com.neusoft.sl.si.authserver.base.domains.user.EnterpriseUser;
import com.neusoft.sl.si.authserver.base.domains.user.PersonalUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.services.log.BindAccountLogService;
import com.neusoft.sl.si.authserver.base.services.redis.RedisAccountService;
import com.neusoft.sl.si.authserver.base.services.redis.ResetPasswordTokenService;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.base.services.user.UserService;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PassWordResetDetailDTO;
import com.neusoft.sl.si.authserver.uaa.controller.user.UserManageRestController;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.*;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.rlzysc.CompanyOpenDTO;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.rlzysc.PersonOpenDTO;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.CaptchaRequestService;
import com.neusoft.sl.si.authserver.uaa.service.msg.MsgService;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;
import com.neusoft.sl.si.authserver.uaa.validate.password.ValidatePassword;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.List;

/**
 * @program: liaoning-auth
 * @description: 绑定老用户控制器（包括单位、个人）
 * @author: lgy
 * @create: 2020-03-06 10:33
 **/



@RestController
public class BindingController {


    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(BindingController.class);

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
    @Value("${saber.auth.getLabour.url}")
    private String enterpriseLabourUrl = "";
    @Resource
    private BindAccountLogService bindAccountLogService;
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
    private RedisTemplate<String, Object> redisTemplate;


    @Autowired
    private MsgService msgService;

    @Autowired
    private PersonRedisUserManager personRedisUserManager;


//    /**
//     * 获取用户信息
//     *
//     * @param user
//     * @return
//     */
//    @ApiOperation(value = "POST绑定对应账户密码", tags = "用户操作接口", notes = "POST绑定对应账户密码BindingController")
//    @RequestMapping(value = "/user/account/bind/{type}", method = RequestMethod.GET)
//    @ResponseBody
//    public HttpResultDTO bindUser(Principal user, @PathVariable("type") String type, @RequestBody BindRequestDTO bindRequestDTO, HttpServletRequest request) {
//        OAuth2Authentication authentication = (OAuth2Authentication) user;
//        Authentication userAuth = authentication.getUserAuthentication();
//        Object principal = userAuth.getPrincipal();
//        String account = "";
//        account = (String) principal;
//        try{
//            Validate.notNull(type,"绑定类型不能为空");
//        }catch (Exception e){
//            bindAccountLogService.bindAccountLogFail((SaberUserDetails) principal,request);
//            return new HttpResultDTO("-1",e.getMessage());
//        }else {
//            if ("simis".equals(type)){
//                //TODO
//            }else{
//
//            }
//        }
//
//
//
//
//    }


}
