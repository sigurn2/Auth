package com.neusoft.ehrss.liaoning.security.implicit.cloudbae.userdetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neusoft.ehrss.liaoning.provider.cloudbae.CloudbaeService;
import com.neusoft.ehrss.liaoning.provider.cloudbae.response.OauthTokenResponse;
import com.neusoft.ehrss.liaoning.provider.cloudbae.response.UserInfoResponse;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;

@Service
public class CloudbaeUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(CloudbaeUserDetailsService.class);

	@Autowired
	private ThinUserRepository thinUserRepository;

	@Autowired
	private UserCustomService userCustomService;

	@Autowired
	private CloudbaeService cloudbaeService;

	public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
		log.debug("================CloudbaeUserDetailsService获取用户信息code={}========", code);

		log.debug("云宝宝用户中心接入code={}", code);
		OauthTokenResponse oauthTokenResponse = cloudbaeService.getToken(code);
		UserInfoResponse infoResponse = cloudbaeService.queryPersonInfo(oauthTokenResponse.getAccessToken());
//		UserInfoResponse infoResponse = new UserInfoResponse();
//		infoResponse.setIdNumber("420682198804215018");
//		infoResponse.setName("牛红亮");
//		infoResponse.setMobile("16112345678");
		log.debug("云宝宝登录用户idNumber={},name={},mobile={}", infoResponse.getIdNumber(), infoResponse.getName(), infoResponse.getMobile());

		ThinUser user = thinUserRepository.findByIdNumber(infoResponse.getIdNumber());
		if (null == user || !"2".equals(user.getUserTypeString())) {
			// PersonRedisUser idNumberUser = idNumberUserRedis.load(username);
			// return new CloudbaeUserDetails(idNumberUser);
			try {
				User newUser = userCustomService.createPersonCloudbae(infoResponse.getIdNumber(),infoResponse.getIdNumber(), infoResponse.getName(), infoResponse.getMobile(), "cloudbae_register","");
				UserLogManager.saveRegisterLog(SystemType.Person.toString(), "Cloudbae", newUser, null);
			} catch (Exception e) {
				log.error("Cloudbae创建用户失败", e);
				throw new BadCredentialsException(e.getMessage());
			}
			// 再次查询
			user = thinUserRepository.findByIdNumber(infoResponse.getIdNumber());
			if (null == user || !"2".equals(user.getUserTypeString())) {
				throw new BadCredentialsException("未找到有效用户");
			}
		}

		if (user.isRealNameAuthed() || user.isBindCardAuthed()) {
			// 更新角色和人员信息
			userCustomService.updateUserForPersonAndRole(user.getIdNumber());
		}
		return new CloudbaeUserDetailsForAccount(user);
	}

}