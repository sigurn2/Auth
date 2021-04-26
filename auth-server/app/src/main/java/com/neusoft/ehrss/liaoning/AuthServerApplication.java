package com.neusoft.ehrss.liaoning;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//1.4.2RELEASE
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
//1.3.8RELEASE
//import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.client.RestTemplate;

import com.neusoft.sl.si.authserver.base.domains.role.DefaultInitRoleProvider;
import com.neusoft.sl.si.authserver.base.domains.role.InitRoleProvider;

/**
 * 南宁认证服务
 *
 * @author mojf
 *
 */
@EnableCaching
@SpringBootApplication
// @EnableResourceServer
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 28800, redisNamespace = "neu-leaf-cloud-auth-liaoning")
@ComponentScan(basePackages = { "com.neusoft.sl.si.authserver", "com.neusoft.ehrss.liaoning", "com.neusoft.ehrss.leaf.cloud.wechat", "com.neusoft.elk" })
@EntityScan(basePackages = { "com.neusoft.sl.si.authserver", "com.neusoft.ehrss.liaoning", "com.neusoft.ehrss.leaf.cloud.wechat" })
@EnableJpaRepositories(basePackages = { "com.neusoft.sl.si.authserver", "com.neusoft.ehrss.liaoning", "com.neusoft.ehrss.leaf.cloud.wechat" })
public class AuthServerApplication {
	public static void main(String[] args) {
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				if (getRequestorType() == RequestorType.PROXY) {
					String prot = getRequestingProtocol().toLowerCase();
					String host = System.getProperty(prot + ".proxyHost", "");
					String port = System.getProperty(prot + ".proxyPort", "80");
					String user = System.getProperty(prot + ".proxyUser", "");
					String password = System.getProperty(prot + ".proxyPassword", "");
					if (getRequestingHost().equalsIgnoreCase(host)) {
						if (Integer.parseInt(port) == getRequestingPort()) {
							return new PasswordAuthentication(user, password.toCharArray());
						}
					}
				}
				return null;
			}
		});
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@Bean
	public RestTemplate customRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML, MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));

		return restTemplateBuilder//
				.additionalMessageConverters(jackson2HttpMessageConverter, stringHttpMessageConverter)//
				.setConnectTimeout(10000)//
				.setReadTimeout(30000)//
				.build();//
	}

	@Bean
	public MessageSourceAccessor messageSourceAccessor() {
		return new MessageSourceAccessor(getMessageSource(), Locale.SIMPLIFIED_CHINESE);
	}

	/**
	 * 中文化
	 *
	 * @return
	 */
	@Bean(name = "messageSource")
	public ResourceBundleMessageSource getMessageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("locale/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	/**
	 * 企业用户默认角色
	 *
	 * @return
	 */
	@Bean(name = "enterpriseUserRoleProvider")
	public InitRoleProvider getRoleProvider() {
		DefaultInitRoleProvider provider = new DefaultInitRoleProvider();
		return provider;
	}

	/**
	 * 个人用户默认角色
	 *
	 * @return
	 */
	@Bean(name = "personUserRoleProvider")
	public InitRoleProvider getPersonUserRoleProvider() {
		DefaultInitRoleProvider provider = new DefaultInitRoleProvider();
		Map<String, List<String>> userRolesMap = new HashMap<String, List<String>>();
		List<String> roles = new ArrayList<String>();
		// 个人系统 未参保用户
		roles.add("2230B0EC6BA306F0E053DD81A8C091D7");
		userRolesMap.put("20", roles);
		// 个人系统 参保用户
		roles = new ArrayList<String>();
		roles.add("2230B0EC6BA306F0E053DD81A8C091D6");
		userRolesMap.put("21", roles);
		// 个人离退休用户
		roles = new ArrayList<String>();
		roles.add("2230B0EC6BA306F0E053DD81A8C091D5");
		userRolesMap.put("22", roles);

		// 专家角色
		roles = new ArrayList<String>();
		roles.add("3230B0EC6BA306F0E053DD81A8C091D8");
		userRolesMap.put("40", roles);

		provider.setUserRolesMap(userRolesMap);
		return provider;
	}

	/**
	 * 居民用户默认角色
	 *
	 * @return
	 */
	@Bean(name = "siagentUserRoleProvider")
	public InitRoleProvider getSiAgentUserRoleProvider() {
		DefaultInitRoleProvider provider = new DefaultInitRoleProvider();
		return provider;
	}
}
