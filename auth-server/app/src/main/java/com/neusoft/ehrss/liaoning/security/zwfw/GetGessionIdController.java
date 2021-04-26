package com.neusoft.ehrss.liaoning.security.zwfw;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.ehrss.liaoning.utils.Base64Tools;
import com.neusoft.ehrss.liaoning.utils.HttpClientTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: liaoning-auth
 * @description:
 * @author: lgy
 * @create: 2020-02-17 09:17
 **/
@Controller
@RequestMapping("idstools")
@Slf4j
@CrossOrigin
public class GetGessionIdController {

//   private static final String coAppName="cysrst";

    @Value("${saber.auth.zwfw.coAppName}")
    private   String coAppName;
    @Value("${saber.auth.zwfw.idsUrl}")
    private   String baseUrl;

    @Value("${saber.auth.zwfw.redirectURL}")
    private String redirectUrl = "";
    
    
    @Value("${saber.auth.zwfw.returnPersonUrl}")
    private  String returnPerurl;



    @Value("${saber.auth.zwfw.returnEntUrl}")
    private  String returnEnturl;

    @Value("${saber.auth.zwfw.person.surl}")
    private String surlperson;

    @Value("${saber.auth.zwfw.enterprise.surl}")
    private String surlenterprise;


    @RequestMapping("/getGssionid/person")
    private String getGssionidPer(HttpSession httpSession){
        log.debug(surlperson);
        String coSessionId=httpSession.getId();
        //String surl="http://127.0.0.1:9999/uaa/idstools/getSso";
        //String surl="http://127.0.0.1:9999/uaa/api/zwfw/person/login";
        coSessionId=Base64Tools.encode(coSessionId.getBytes());
        System.out.print(Base64Tools.encode(coAppName.getBytes()));
        String idsurl = redirectUrl+"LoginServlet?coAppName="+Base64Tools.encode(coAppName.getBytes())+"&coSessionId="+coSessionId+"&surl="+ Base64Tools.encode(surlperson.getBytes());
        Map<String, String> params=new HashMap<>();
        //JSONObject jsonObject = HttpClientTools.httpPost(idsurl,params);
        return "redirect:"+idsurl;
//            jsonObject.getString("com.trs.idm.gSessionId");
//            return jsonObject;
    }


    @RequestMapping("/getGssionid/enterprise")
    private String getGssionidEnt(HttpSession httpSession){
        log.debug(surlenterprise);
        String coSessionId=httpSession.getId();
        //String surl="http://127.0.0.1:9999/uaa/idstools/getSso";
        //String surl="http://127.0.0.1:9999/uaa/api/zwfw/person/login";
        coSessionId=Base64Tools.encode(coSessionId.getBytes());
        System.out.print(Base64Tools.encode(coAppName.getBytes()));
        String idsurl = redirectUrl+"LoginServlet?coAppName="+Base64Tools.encode(coAppName.getBytes())+"&coSessionId="+coSessionId+"&surl="+ Base64Tools.encode(surlenterprise.getBytes());
        Map<String, String> params=new HashMap<>();
        //JSONObject jsonObject = HttpClientTools.httpPost(idsurl,params);
        return "redirect:"+idsurl;
//            jsonObject.getString("com.trs.idm.gSessionId");
//            return jsonObject;
    }
//
//    @RequestMapping("/getSso")
//    @ResponseBody
//    private Object getSso(HttpSession httpSession, HttpServletRequest request, HttpServletResponse reponse) throws IOException {
//        String gSessionId= request.getParameter("com.trs.idm.gSessionId");
//        String trsidsssosessionid =  request.getParameter("trsidsssosessionid");
//        String ssoSessionId = StringUtils.isBlank(gSessionId)?trsidsssosessionid:gSessionId;
//        httpSession.setAttribute("ssoSessionId",ssoSessionId);
//        String httpSessionid=httpSession.getId();
//        String httpclienturl=baseUrl+"service?idsServiceType=httpssoservice&serviceName=findUserBySSOID&coAppName="+coAppName+"&ssoSessionId="+ssoSessionId+"&coSessionId="+httpSessionid+"&type=json";
//        JSONObject jsonObject = HttpClientTools.httpGet(httpclienturl);
//        String loginurl=baseUrl+"custom/liaoning/login.jsp?coAppName="+Base64Tools.encode(coAppName.getBytes())+"&coSessionId="+httpSessionid+"&gSessionId="+ssoSessionId+"&surl="+Base64Tools.encode(returnurl.getBytes());
//        if("200".equals(jsonObject.get("code"))){
//            System.out.println("success");
//        }else if("404".equals(jsonObject.get("code")+"")){
//            reponse.sendRedirect(loginurl);
//            return null;
//        }else if("500".equals(jsonObject.get("code"))){
//            System.out.println("error");
//        }
//        return jsonObject;
//    }


}

