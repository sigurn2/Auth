package com.neusoft.sl.si.authserver.uaa.filter.captcha.img;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.neusoft.sl.si.authserver.uaa.exception.ImageCaptchaException;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.CaptchaRequestService;

/**
 * 通过图片验证码 校验HTTP头部包含手机号码、短信验证码
 * 
 * @author mojf
 *
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ImgCaptchaRequestHeaderFilter extends OncePerRequestFilter {

	@Autowired
	private CaptchaRequestService captchaRequestService;

	@Value("#{'${filtered.captcha.img.uris:^$}'.split(',')}")
	private List<String> captchaImgFilteredUris;

	@Value("${filtered.captcha.img.valid}")
	private Boolean captchaImgValid = false;

	private final List<Pattern> captchaImgFilteredUriPatterns = new ArrayList<Pattern>();

	/** 日志 */
	protected static Logger LOGGER = LoggerFactory.getLogger(ImgCaptchaRequestHeaderFilter.class);

	@PostConstruct
	public void initialize() {
		for (String uri : this.captchaImgFilteredUris) {
			try {
				this.captchaImgFilteredUriPatterns.add(Pattern.compile(uri));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(String.format("符合 '%s' 格式的URI,将进入ImgCaptchaRequestHeaderFilter .", uri));
				}
			} catch (PatternSyntaxException patternSyntaxException) {
				LOGGER.error("Invalid regular expression pattern in filtered.captcha.img.uris: " + uri);
			}
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 判断是否是在校验范围内
		String requestUri = request.getRequestURI();
		if (isCaptchaImgFilteredUri(requestUri)) {
			LOGGER.debug("==进入 ImgCaptchaRequestHeaderFilter，当前uri为{}==", requestUri);
			String img = captchaRequestService.imgCaptchaRequest(request);
			if (!"".equals(img)) {
				throw new ImageCaptchaException(img);
			}
		}
		filterChain.doFilter(request, response);
	}

	private boolean isCaptchaImgFilteredUri(final String uri) {
		if (StringUtils.isEmpty(uri)) {
			return false;
		}

		for (Pattern pattern : this.captchaImgFilteredUriPatterns) {
			// Making sure that the pattern matches
			if (pattern.matcher(uri).find()) {
				return true;
			}
		}
		return false;
	}
}
