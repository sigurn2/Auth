package com.neusoft.ehrss.liaoning.config;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.neusoft.sl.si.authserver.uaa.log.enums.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.neusoft.ehrss.liaoning.security.password.atm.AtmAuthenticationProvider;
import com.neusoft.ehrss.liaoning.security.password.atm.AtmUserDetailsService;
import com.neusoft.ehrss.liaoning.security.password.ecard.EcardGatewayAuthenticationProvider;
import com.neusoft.ehrss.liaoning.security.password.ecard.EcardGatewayUserDetailsService;
import com.neusoft.ehrss.liaoning.security.password.idnumbername.IdNumberNameAuthenticationProvider;
import com.neusoft.ehrss.liaoning.security.password.idnumbername.IdNumberNameUserDetailsService;
import com.neusoft.ehrss.liaoning.security.password.mobile.MobilePasswordAuthenticationProvider;
import com.neusoft.ehrss.liaoning.security.password.mobile.MobileUserDetailsService;
import com.neusoft.ehrss.liaoning.security.password.mobile.company.RlzyscCompanyAuthenticationProvider;
import com.neusoft.ehrss.liaoning.security.password.mobile.company.RlzyscCompanyUserDetailService;
import com.neusoft.ehrss.liaoning.security.password.mobile.pattern.MobilePatternAuthenticationProvider;
import com.neusoft.ehrss.liaoning.security.password.mobile.person.RlzyscPersonAuthenticationProvider;
import com.neusoft.ehrss.liaoning.security.password.mobile.person.RlzyscPersonUserDetailsService;
import com.neusoft.ehrss.liaoning.security.password.refresh_token.RefreshTokenDetailsService;
import com.neusoft.ehrss.liaoning.security.userdetails.IdNumberUserDetails;
import com.neusoft.ehrss.liaoning.security.userdetails.PersonNumberUserDetails;
import com.neusoft.sl.girder.security.oauth2.Oauth2Constant;
import com.neusoft.sl.si.authserver.base.domains.role.RoleRepository;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.domains.user.pattern.UserPatternRepository;
import com.neusoft.sl.si.authserver.base.services.password.PasswordEncoderService;
import com.neusoft.sl.si.authserver.base.services.user.UserService;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.UserAssembler;
import com.neusoft.sl.si.authserver.uaa.service.code.SaberInCacheAuthorizationCodeServices;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;
import com.neusoft.sl.si.authserver.uaa.validate.login.PasswordErrorRedisManager;

/**
 * Oauth2 服务配置
 * 
 * @author mojf
 *
 */
@Configuration
public class OAuth2ServerConfig {

	protected static class CustomTokenEnhancer extends JwtAccessTokenConverter {
		private static final String USER_ALLOWED = "_USER_ALLOWED";
		@Resource
		private UserService userService;
		@Resource
		private RoleRepository roleRepository;
		@Autowired
		private RedisTemplate<String, Object> redisTemplate;

		@Override
		public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

			Authentication userAuth = authentication.getUserAuthentication();
			// 处理client登录，无用户信息场景
			if (null == userAuth) {
				return super.enhance(accessToken, authentication);
			}
			Object principal = userAuth.getPrincipal();
			String account = "";
			if (principal instanceof SaberUserDetails) {
				account = ((SaberUserDetails) principal).getAccount();
			} else if (principal instanceof org.springframework.security.core.userdetails.User) {
				account = ((org.springframework.security.core.userdetails.User) principal).getUsername();
			} else {
				account = (String) principal;
			}
			DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);

			Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());

			String cacheKey = account + USER_ALLOWED;

			ValueOperations<String, Object> ops = redisTemplate.opsForValue();
			if (principal instanceof PersonNumberUserDetails) {
				ops.set(cacheKey, UserAssembler.toAllowedDTOByPersonNumber(account), 24, TimeUnit.HOURS);
			} else if (principal instanceof IdNumberUserDetails) {
				if (((IdNumberUserDetails) principal).getClientType().equals(ClientType.ATM.name())){
					ops.set(cacheKey, UserAssembler.toAllowedDTOByAtm(account), 24, TimeUnit.HOURS);
				}else {
					ops.set(cacheKey, UserAssembler.toAllowedDTOByIdNumber(account), 24, TimeUnit.HOURS);
				}
			} else {
				// 24个小时过期
				User userInfo = userService.findByAccount(account);
				ops.set(cacheKey, UserAssembler.toAllowedDTO(userInfo), 24, TimeUnit.HOURS);
			}
			info.put(Oauth2Constant.OAUTH2_USER, cacheKey);
			customAccessToken.setAdditionalInformation(info);
			return super.enhance(customAccessToken, authentication);
		}

	}

	/**
	 * 配置OAuth2安全
	 *
	 * @author wuyf
	 *
	 */
	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

		// @Autowired
		// private AuthenticationManager authenticationManager;

		@Autowired
		private DataSource dataSource;

		@Autowired
		private RefreshTokenDetailsService thinUserDetailsService;

		@Autowired
		private MobileUserDetailsService mobileUserDetailsService;

		@Autowired
		private RlzyscCompanyUserDetailService rlzyscCompanyUserDetailService;

		@Autowired
		private RlzyscPersonUserDetailsService rlzyscPersonUserDetailsService;

		@Autowired
		private IdNumberNameUserDetailsService idNumberNameUserDetailsService;

		@Autowired
		private EcardGatewayUserDetailsService ecardGatewayUserDetailsService;

		@Autowired
		private AtmUserDetailsService atmUserDetailsService;

		@Resource(name = "${saber.auth.password.encoder}")
		private PasswordEncoderService passwordEncoderService;

		@Autowired
		private UserPatternRepository userPatternRepository;

		@Autowired
		private PasswordErrorRedisManager passwordErrorRedisManager;

		@Bean
		public TokenStore getTokenStore() {
			return new JdbcTokenStore(dataSource);
		}

		@Bean
		protected AuthorizationCodeServices authorizationCodeServices() {
			return new JdbcAuthorizationCodeServices(dataSource);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			// 如果使用接入管理，需要边界配置clientID和clientSecret。不同的接入渠道使用不同的边界，通过边界控制渠道是否可用
			clients.jdbc(dataSource);
			// clients.inMemory().withClient("acme")// 基于浏览器
			// .secret("acmesecret").authorizedGrantTypes("authorization_code",
			// "password").scopes("openid")
			// .autoApprove(true);// 自动授权，不需要用户确认

		}

		// -------------Add JWT Token Support
		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter() {
			JwtAccessTokenConverter converter = new CustomTokenEnhancer();
			KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "foobar".toCharArray()).getKeyPair("test");
			converter.setKeyPair(keyPair);
			return converter;
		}

		private AuthenticationManager authenticationManager() throws Exception {
			MobilePasswordAuthenticationProvider mobileProvider = new MobilePasswordAuthenticationProvider();
			mobileProvider.setUserDetailsService(mobileUserDetailsService);
			mobileProvider.setPasswordEncoder(passwordEncoderService.build());
			mobileProvider.setHideUserNotFoundExceptions(false);
			mobileProvider.setPasswordErrorRedisManager(passwordErrorRedisManager);

			RlzyscCompanyAuthenticationProvider companyProvider = new RlzyscCompanyAuthenticationProvider();
			companyProvider.setUserDetailsService(rlzyscCompanyUserDetailService);
			companyProvider.setPasswordEncoder(passwordEncoderService.build());
			companyProvider.setHideUserNotFoundExceptions(false);

			RlzyscPersonAuthenticationProvider personProvider = new RlzyscPersonAuthenticationProvider();
			personProvider.setUserDetailsService(rlzyscPersonUserDetailsService);
			personProvider.setPasswordEncoder(passwordEncoderService.build());
			personProvider.setHideUserNotFoundExceptions(false);

			MobilePatternAuthenticationProvider patternProvider = new MobilePatternAuthenticationProvider();
			patternProvider.setUserDetailsService(mobileUserDetailsService);
			patternProvider.setPasswordEncoder(passwordEncoderService.build());
			patternProvider.setHideUserNotFoundExceptions(false);
			patternProvider.setUserPatternRepository(userPatternRepository);

			IdNumberNameAuthenticationProvider idNumberNameProvider = new IdNumberNameAuthenticationProvider();
			idNumberNameProvider.setUserDetailsService(idNumberNameUserDetailsService);
			idNumberNameProvider.setHideUserNotFoundExceptions(false);

			EcardGatewayAuthenticationProvider ecardGatewayProvider = new EcardGatewayAuthenticationProvider();
			ecardGatewayProvider.setUserDetailsService(ecardGatewayUserDetailsService);
			ecardGatewayProvider.setHideUserNotFoundExceptions(false);

			AtmAuthenticationProvider atmProvider = new AtmAuthenticationProvider();
			atmProvider.setUserDetailsService(atmUserDetailsService);
			atmProvider.setHideUserNotFoundExceptions(false);
			return new ProviderManager(Arrays.asList(mobileProvider, patternProvider, idNumberNameProvider, ecardGatewayProvider, atmProvider, companyProvider, personProvider));
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authorizationCodeServices(new SaberInCacheAuthorizationCodeServices())//
					.authenticationManager(authenticationManager())//
					.accessTokenConverter(jwtAccessTokenConverter())//
					.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)//
					.userDetailsService(thinUserDetailsService);
		}

		/**
		 * JWT认证接口
		 */
		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		}

	}

	@Configuration
	@EnableResourceServer
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.resourceId("auth-resource").stateless(true);
		}

	}

}
