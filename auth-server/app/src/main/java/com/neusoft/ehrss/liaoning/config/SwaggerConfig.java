package com.neusoft.ehrss.liaoning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({ "uaa-api-doc" })
public class SwaggerConfig {
	private static String CONTACT = "东软社保公共服务团队";
	private static String TERMS_OF_SERVICE_URL = "http://www.neusoft.com";
	private static String TITLE = "Authserver Core API";
	private static String DESCRIPTION = "Swagger-UI查询与服务接口";
	private static String VERSION = "1.2.1.1-SNAPSHOT";

	@Bean
	public Docket swaggerSpringfoxDocket() {
		Docket swaggerSpringMvcPlugin = new Docket(DocumentationType.SWAGGER_2).groupName("auth-api").enableUrlTemplating(true).select().apis(RequestHandlerSelectors.basePackage("com.neusoft"))
				.paths(PathSelectors.any()).build().apiInfo(authApiInfo());
		return swaggerSpringMvcPlugin;
	}

	private ApiInfo authApiInfo() {
		return new ApiInfoBuilder().title(TITLE).description(DESCRIPTION).termsOfServiceUrl(TERMS_OF_SERVICE_URL).contact(new Contact(CONTACT, TERMS_OF_SERVICE_URL, "mojf@neusoft.com")).version(VERSION)
				.build();
	}
}