package com.neusoft.sl.si.authserver.uaa.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.util.JSONPObject;

import io.swagger.annotations.ApiOperation;

/**
 * Session 控制器
 * 
 * @author mojf
 *
 */
@RestController
public class SessionController {

    /** 日志 */
    private static Logger LOGGER = LoggerFactory.getLogger(SessionController.class);

    /**
     * 
     * @return
     */
    @ApiOperation(value = "GET获取cookie", tags = "Session操作接口", notes = "获取cookie，SessionController", response = String.class)
    @RequestMapping(value = "/getcookie", method = RequestMethod.GET)
    public String getCookie(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        LOGGER.debug("...获取cookie...");
        response.setHeader("p3p",
                "CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR");
        Cookie cookie = new Cookie("MysessionID", session.getId());
        // cookie.setDomain("localhost");
        cookie.setPath(request.getContextPath() + "/");
        cookie.setDomain(".localhost");
//        response.addCookie(cookie);
        return "";
    }

    @ApiOperation(value = "GET获取ssoSessionId2", tags = "Session操作接口", notes = "获取ssoSessionId2，SessionController")
    @RequestMapping(value = "/ssoSessionId2", method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject getSSOSessionId2(HttpSession session, @RequestParam String callback) {
        LOGGER.debug("...获取cookie...");
        ModelAndView model = new ModelAndView();
        model.addObject("sessionId", "");
        return new JSONPObject(callback, model);
    }

    @ApiOperation(value = "GET获取ssoSessionId", tags = "Session操作接口", notes = "获取ssoSessionId,SessionController")
    @RequestMapping(value = "/ssoSessionId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSSOSessionId(HttpSession session) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ssoSessionId", "");
        return model;
    }
}
