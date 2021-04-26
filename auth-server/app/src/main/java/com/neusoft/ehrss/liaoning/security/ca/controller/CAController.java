package com.neusoft.ehrss.liaoning.security.ca.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/ws/ca")
public class CAController {

	/** 日志 */
	private static Logger log = LoggerFactory.getLogger(CAController.class);

	private static final int RANDOM_LEN = 24;

	@GetMapping(value = "/randrom/{type}")
	public Map<String, String> randrom(@PathVariable("type") String type, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String randrom = null;
		if ("gx".equals(type)) {
			randrom = defaultGenRandrom();
		} else if ("df".equals(type)) {
			randrom = defaultGenRandrom();
		} else {
			throw new ResourceNotFoundException("请求的资源不存在");
		}
		log.debug("CA登录获取随机数={}", randrom);
		map.put("randrom", randrom);
		request.getSession().setAttribute("randomData", randrom);
		return map;
	}

	private String defaultGenRandrom() {
		return RandomStringUtils.randomAlphanumeric(RANDOM_LEN);
	}

}
