package com.neusoft.sl.si.authserver.uaa.config.web;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.neusoft.sl.girder.ddd.core.exception.NestedSystemException;
import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.si.authserver.base.domains.user.AccountExistsException;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;
import com.neusoft.sl.si.authserver.uaa.exception.CaRevokedException;
import com.neusoft.sl.si.authserver.uaa.exception.CaptchaErrorException;
import com.neusoft.sl.si.authserver.uaa.exception.ImageCaptchaException;
import com.neusoft.sl.si.authserver.uaa.exception.MessageException;
import com.neusoft.sl.si.authserver.uaa.exception.MobileBadTokenException;
import com.neusoft.sl.si.authserver.uaa.exception.MobileExistsException;
import com.neusoft.sl.si.authserver.uaa.exception.WebserviceApiException;
import com.neusoft.sl.si.authserver.uaa.exception.WechatException;
import com.neusoft.sl.si.authserver.uaa.exception.WechatNotBindException;

import cz.jirutka.spring.exhandler.RestHandlerExceptionResolver;
import cz.jirutka.spring.exhandler.RestHandlerExceptionResolverBuilder;
import cz.jirutka.spring.exhandler.support.HttpMessageConverterUtils;

/**
 * 统一例外处理
 * 
 * @author wuyf
 *
 */
@Configuration
public class GlobalRestFulExceptionHandlingConfig extends WebMvcConfigurerAdapter {

	/**
	 * 增加例外处理配置
	 */
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		resolvers.add(exceptionHandlerExceptionResolver()); // resolves
															// @ExceptionHandler
		resolvers.add(restExceptionResolver());
	}

	/**
	 * 自定义例外处理
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public RestHandlerExceptionResolver restExceptionResolver() {

		RestHandlerExceptionResolverBuilder builder = RestHandlerExceptionResolver.builder();
		builder.messageSource(httpErrorMessageSource()).defaultContentType(MediaType.APPLICATION_JSON)//
				// 404
				.addErrorMessageHandler(EmptyResultDataAccessException.class, HttpStatus.NOT_FOUND)//
				// 验证码错误
				.addErrorMessageHandler(ImageCaptchaException.class, HttpStatus.BAD_REQUEST)//
				// 资源定位错误
				.addErrorMessageHandler(ResourceNotFoundException.class, HttpStatus.NOT_FOUND)//
				// 默认运行期错误
				.addErrorMessageHandler(NestedSystemException.class, HttpStatus.BAD_REQUEST)//
				// 异常配置
				.addErrorMessageHandler(AccountExistsException.class, HttpStatus.BAD_REQUEST)//
				// 未授权访问
				.addErrorMessageHandler(MobileBadTokenException.class, HttpStatus.UNAUTHORIZED)//
				// CA吊销异常
				.addErrorMessageHandler(CaRevokedException.class, HttpStatus.BAD_REQUEST)//
				// 业务异常
				.addErrorMessageHandler(BusinessException.class, HttpStatus.BAD_REQUEST)//
				// 验证码错误
				.addErrorMessageHandler(CaptchaErrorException.class, HttpStatus.BAD_REQUEST)//
				// 发送消息例外
				.addErrorMessageHandler(MessageException.class, HttpStatus.BAD_REQUEST)//
				// 错误
				.addErrorMessageHandler(BadCredentialsException.class, HttpStatus.BAD_REQUEST)//
				// 非法入参
				.addErrorMessageHandler(IllegalArgumentException.class, HttpStatus.BAD_REQUEST)//
				// webservice异常
				.addErrorMessageHandler(WebserviceApiException.class, HttpStatus.BAD_REQUEST)//
				// 手机号存在异常
				.addErrorMessageHandler(MobileExistsException.class, HttpStatus.PRECONDITION_FAILED)//
				// 微信异常
				.addErrorMessageHandler(WechatException.class, HttpStatus.BAD_REQUEST)//
				// 未绑定异常
				.addErrorMessageHandler(WechatNotBindException.class, HttpStatus.BAD_REQUEST);

		return builder.build();
	}

	/**
	 * 错误消息
	 * 
	 * @return
	 */
	@Bean
	public MessageSource httpErrorMessageSource() {
		ReloadableResourceBundleMessageSource m = new ReloadableResourceBundleMessageSource();
		m.setBasename("classpath:locale/messages");
		m.setDefaultEncoding("UTF-8");
		return m;
	}

	/**
	 * 错误解析对象
	 * 
	 * @return
	 */
	@Bean
	public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver() {
		ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
		resolver.setMessageConverters(HttpMessageConverterUtils.getDefaultHttpMessageConverters());
		return resolver;
	}
}
