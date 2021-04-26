package com.neusoft.sl.si.authserver.uaa.userdetails.company;

import com.neusoft.sl.si.authserver.base.domains.user.*;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

/**
 * 扩展UserDetailService企业用户的实现
 */
@Service
public class EnterpriseUserDetailService implements UserDetailsService {

    @Autowired
    private ThinUserWithRoleRepository thinUserWithRoleRepository;

    @Autowired
    private TempUserRepository tempUserRepository;

    @Autowired
    private ThinUserRepository userRepository;

    @Autowired
    private UserCustomService userCustomService;
    private static final Logger log = LoggerFactory.getLogger(EnterpriseUserDetailService.class);

    /**
     * 载入用户
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("================获取企业用户信息username={}========================", username);
        ThinUser user = userRepository.findByAccount(username);
        if (null == user || !"1".equals(user.getUserTypeString())) {
            throw new BadCredentialsException("您输入的账号不存在，请重新输入");
        }

        userCustomService.updateUserForEntAndRole(user.getUnitId());

        return new EnterpriseUserDetails(user);
    }
}
