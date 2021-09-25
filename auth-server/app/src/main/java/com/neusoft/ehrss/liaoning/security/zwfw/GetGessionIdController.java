package com.neusoft.ehrss.liaoning.security.zwfw;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.ehrss.liaoning.utils.Base64Tools;
import com.neusoft.ehrss.liaoning.utils.HttpClientTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private String getGssionidPer(HttpSession httpSession, HttpServletRequest request){
        log.debug(surlperson);
        String coSessionId=httpSession.getId();
        coSessionId=Base64Tools.encode(coSessionId.getBytes());
        System.out.print(Base64Tools.encode(coAppName.getBytes()));
        String sid= request.getParameter("sid");
        Optional<String> surl = Optional.ofNullable(sid);
        String benxi_surl=surlperson+"?sid="+sid;
        String idsurl;
        if (!surl.isPresent()){
             idsurl = redirectUrl+"LoginServlet?coAppName="+Base64Tools.encode(coAppName.getBytes())+"&coSessionId="+coSessionId+"&surl="+ Base64Tools.encode(surlperson.getBytes());
             log.debug("benxi_redirecturl={}",idsurl);
        }
        else {
             idsurl = redirectUrl+"LoginServlet?coAppName="+Base64Tools.encode(coAppName.getBytes())+"&coSessionId="+coSessionId+"&surl="+ Base64Tools.encode(benxi_surl.getBytes());
            log.debug("benxi_redirecturl={}",redirectUrl+"LoginServlet?coAppName="+Base64Tools.encode(coAppName.getBytes())+"&coSessionId="+coSessionId+"&surl="+ benxi_surl);
        }
        log.debug("benxi_redirecturl={}",redirectUrl+"LoginServlet?coAppName="+Base64Tools.encode(coAppName.getBytes())+"&coSessionId="+coSessionId+"&surl="+ benxi_surl);
        Map<String, String> params=new HashMap<>();
        return "redirect:"+idsurl;

    }


    @RequestMapping("/getGssionid/enterprise")
    private String getGssionidEnt(HttpSession httpSession,HttpServletRequest request){
        log.debug(surlenterprise);
        String coSessionId=httpSession.getId();

        String sid = request.getParameter("sid");
        Optional<String> surl = Optional.ofNullable(sid);

        String benxi_surl=surlenterprise+"?sid="+sid;
        coSessionId=Base64Tools.encode(coSessionId.getBytes());
        System.out.print(Base64Tools.encode(coAppName.getBytes()));
        String idsurl;
        if (!surl.isPresent()){
            idsurl = redirectUrl+"LoginServlet?coAppName="+Base64Tools.encode(coAppName.getBytes())+"&coSessionId="+coSessionId+"&surl="+ Base64Tools.encode(surlenterprise.getBytes());
            log.debug("benxi_redirecturl={}",idsurl);
        }
        else {
            idsurl = redirectUrl+"LoginServlet?coAppName="+Base64Tools.encode(coAppName.getBytes())+"&coSessionId="+coSessionId+"&surl="+ Base64Tools.encode(benxi_surl.getBytes());
            log.debug("benxi_redirecturl={}",redirectUrl+"LoginServlet?coAppName="+Base64Tools.encode(coAppName.getBytes())+"&coSessionId="+coSessionId+"&surl="+ benxi_surl);

        }
        Map<String, String> params=new HashMap<>();
        return "redirect:"+idsurl;

    }



}

