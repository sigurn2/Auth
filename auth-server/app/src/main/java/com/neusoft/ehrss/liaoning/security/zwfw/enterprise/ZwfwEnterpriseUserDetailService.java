package com.neusoft.ehrss.liaoning.security.zwfw.enterprise;

import com.neusoft.sl.si.authserver.base.domains.user.*;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;
import com.neusoft.sl.si.authserver.uaa.userdetails.company.EnterpriseCAUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 扩展UserDetailService政务网的实现
 */
@Service
public class ZwfwEnterpriseUserDetailService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(ZwfwEnterpriseUserDetailService.class);

     @Autowired
     private UserRepository userRepository;

    @Autowired
    private ThinUserRepository thinUserRepository;


    @Autowired
    private UserCustomService userCustomService;


    @Autowired
    private ThinUserWithRoleRepository thinUserWithRoleRepository;


    /**
     * 载入用户
     */

    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        log.debug("================ZwfwEnterpriseUserDetailService获取用户信息username={}========", account);
        //统一信用代码
        String orgCode = account.split("@@")[0];
        String name = account.split("@@")[1];
        String mobile = account.split("@@")[2];
        String idNumber = account.split("@@")[3];
        String uuid =account.split("@@")[4];
        log.debug("===============ZwfwEnterpriseUserDetailService获取用户信息用以信用代码={}， name = {}, mobile = {}， orgCode = {}========", idNumber, name, mobile, orgCode);
        ThinUser thinUser = thinUserRepository.findByOrgCode(orgCode);
        if (null == thinUser || !"1".equals(thinUser.getUserTypeString())) {
            // thinUserPerson = thinUserRepository.findByIdNumber(username);
            // if (null == thinUserPerson ||
            // !"2".equals(thinUserPerson.getUserTypeString())) {

            try {
                // 一切顺利，创建user
                EnterpriseUserFactory factory = new EnterpriseUserFactory();
                User user = factory.createEnterpriseUser("0", orgCode, name, "", mobile, null);
                user.setOrgCode(orgCode);
                user.inactiveUser();
                user.setEmail(uuid);
                user.setPassword("zwfw_auto_password");
                user.setExtension("zwfw_register");

                //user.setIdNumber( orgCode+"@@"+ idNumber);
                user.setIdNumber(idNumber);
                user.setAccount(orgCode);
                User newUser = userCustomService.createEnterpriseUserWithOrgCode(user);
                UserLogManager.saveRegisterLog(SystemType.Enterprise.toString(), "Zwfw", newUser, null);
            } catch (Exception e) {
                log.error("zwfw创建用户失败", e);
                throw new BadCredentialsException(e.getMessage());
            }
            // 再次查询
            ThinUserWithRole user = thinUserWithRoleRepository.findByOrgCode(orgCode);
            if (null == user || !"1".equals(user.getUserTypeString())) {
                throw new BadCredentialsException("未找到有效用户");
            }
        }
        ThinUserWithRole user = thinUserWithRoleRepository.findByOrgCode(orgCode);

        //uuid 改造
        if (!user.getEmail().equals(uuid)){
            userCustomService.updateEmailForZwfwEnterprise(idNumber,uuid);
        }



      //  userCustomService.updateUserForEntAndRoleZwfw(user.getOrgCode(),name);

        User userinfo = userRepository.findByOrgCode(orgCode);
//        if (!thinUser.isActivated()) {
//            throw new BadCredentialsException("您输入的账号未激活");
//        }
//		if (thinUser.isRealNameAuthed() || thinUser.isBindCardAuthed()) {
//			userCustomService.updateUserForPersonAndRole(thinUser.getIdNumber());
//		}
        return new EnterpriseCAUserDetails(userinfo);
    }
}
