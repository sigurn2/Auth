package com.neusoft.ehrss.liaoning.security.csrf;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws")
public class CsrfController {
	
	/** 日志 */
    private static Logger LOGGER = LoggerFactory.getLogger(CsrfController.class);
	
	@RequestMapping(value="/token")
	public Map<String, String> token(HttpServletRequest request) {
		LOGGER.debug("===========csrf token============");
		Map<String, String> map = new HashMap<String, String>();
		String randrom = RandomStringUtils.randomAlphanumeric(24);
		map.put("randrom", randrom);
		request.getSession().setAttribute("randomData", randrom);
		return map;
	}

}
