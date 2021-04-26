package com.neusoft.ehrss.liaoning.security.password.mobile;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.ehrss.liaoning.security.zwfw.dto.ZwfwUserDTO;
import com.neusoft.ehrss.liaoning.utils.DemoDesUtil;
import com.neusoft.ehrss.liaoning.utils.HttpClientTools;
import com.neusoft.sl.si.authserver.base.domains.person.Person;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTOAssembler;
import com.neusoft.sl.si.authserver.uaa.exception.CaptchaErrorException;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.CaptchaRequestService;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;

import java.util.List;

@Service
public class MobileUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(MobileUserDetailsService.class);

    @Resource
    private ThinUserRepository thinUserRepository;

    @Value("${saber.auth.zwfw.app.url}")
    private String zwfwAppUrl;
    @Value("${saber.http.proxy.host}")
    private String host = "";


    private String port = "noproxy";

    @Autowired
    private UserCustomService userCustomService;

    @Resource
    private CaptchaRequestService captchaRequestService;

    @SneakyThrows
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("================MobileUserDetailsService获取用户信息username={}========", username);
        String[] array = username.split("@@");
        String[] arrays = username.split("@@");
        username = arrays[0];
        String password = array[1];
        String captchaId = arrays[2];
        String captchaWord = arrays[3];


//        String img = captchaRequestService.imgCaptchaRequest(captchaId, captchaWord);
//        if (!"".equals(img)) {
//            throw new CaptchaErrorException(img);
//        }
        ThinUser thinUser = thinUserRepository.findByAccount(username);
        //后门单位
        if (username.equals("210102196404224110") || username.equals("211204195011250531") || username.equals("210882197801190318") || username.equals("211121196005113413") || username.equals("211225197402262813") || username.equals("211225193012273025")) {
            if(null == thinUser)
            {
            	throw new BadCredentialsException("本地库中，该帐号不存在！");
            }
        	return new MobileUserDetails(thinUser);
        } else {
            if (null == thinUser) {
                //获取政务服务登录用户信息
                JSONObject json = checkUserPwd(username, password);
                if (!"0000".equals(json.get("code"))) {
                    throw new BadCredentialsException("登录失败," + json.get("msg"));
                } else {
                    ZwfwUserDTO userDTO = JSONObject.parseObject(json.get("result").toString(), ZwfwUserDTO.class);
                    //app 注册的账号和网厅个人注册的账号不同，但是只会注册生成一个账号
                    if (thinUserRepository.findByIdNumber(userDTO.getIdNumber()) != null) {
                        thinUser = thinUserRepository.findByIdNumber(userDTO.getIdNumber());
                    } else {
                        try {
                            PersonUserDTO personUserDTO = new PersonUserDTO(userDTO.getUsername(), userDTO.getIdNumber(), userDTO.getName(), userDTO.getMobile(), password);
                            User user = PersonUserDTOAssembler.crtfromDTO(personUserDTO);
                            user.setIdType("01");
                            user.setExtension("zwfw_app_autoReg");
                            user.setEmail(userDTO.getUuid());
                            // 保存user对象
                            user = userCustomService.createPerson(user);
                            thinUser = thinUserRepository.findByIdNumber(userDTO.getIdNumber());
                            UserLogManager.saveRegisterLog(SystemType.Person.toString(), "ZwfAPP", user, null);
                        } catch (Exception e) {
                            log.error("zwfw创建用户失败", e);
                            throw new BadCredentialsException(e.getMessage());
                        }
                    }

                }
                return new MobileUserDetails(thinUser);


            } else {
                JSONObject json = checkUserPwd(username, password);
                if ("0000".equals(json.get("code"))) {
                    if (StringUtils.isEmpty(thinUser.getEmail())){
                        ZwfwUserDTO userDTO = JSONObject.parseObject(json.get("result").toString(), ZwfwUserDTO.class);
                        thinUser.setEmail(userDTO.getUuid());
                        thinUserRepository.save(thinUser);
                    }
                    return new MobileUserDetails(thinUser);
                } else {
                    throw new BadCredentialsException("用户名密码错误");
                }
            }
        }
    }

    private JSONObject checkUserPwd(String username, String password) {
        String request = null;
        try {
            request = DemoDesUtil.encrypt("{\"score\":\"1\",\"username\":\"" + username + "\",\"password\":\"" + DemoDesUtil.encPwd(password) + "\",\"method\":\"loginnp\",\"service\":\"user\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}", DemoDesUtil.getDtKey());
        } catch (Exception e) {
            throw new BadCredentialsException("加解密错误");
        }
        log.debug("================调取政务网获取用户信息request ={}========", request);
        String encryptUser = HttpClientTools.httpPostToApp(zwfwAppUrl, request, host, port);
        log.debug("11111 encryptUser={} ",encryptUser);
        JSONObject json = null;
        try {
            json = JSONObject.parseObject(DemoDesUtil.decrypt(encryptUser, DemoDesUtil.getDtKey()));
            log.debug("json为",json);
        } catch (Exception e) {
            log.error(" error json={}",json);
            throw new BadCredentialsException("加解密错误");
        }
        log.debug("登录校验json = {}", json);
        return json;
    }

}