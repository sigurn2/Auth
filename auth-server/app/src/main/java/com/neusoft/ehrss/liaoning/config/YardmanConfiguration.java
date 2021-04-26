package com.neusoft.ehrss.liaoning.config;


import com.eesb.sdk.util.Client;
import com.eesb.sdk.util.ClientUtil;
import com.neusoft.sl.si.authserver.simis.support.gateway.ClientGateWayImpl;
import com.neusoft.sl.si.authserver.simis.support.gateway.ServerNodeEnum;
import com.neusoft.sl.si.yardman.client.SpringContextUtils;
import com.neusoft.sl.si.yardman.client.dispatch.ServerConfig;
import com.neusoft.sl.si.yardman.client.impl.SystemCodeProvider;
import com.neusoft.sl.si.yardman.client.impl.YardmanGateWayImpl;
import com.neusoft.sl.si.yardman.server.callback.PasswordCallBack;
import com.neusoft.sl.si.yardman.server.interceptor.DecAliasOutInterceptor;
import com.neusoft.sl.si.yardman.server.service.NeuWebService;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Yardman 配置
 *
 * @author mojf
 */
@Configuration
public class YardmanConfiguration extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(YardmanConfiguration.class);

    @Value("${yardman.ticket}")
    private String sender;
    @Value("${yardman.serverNode}")
    private String reciver;
    @Value("${yardman.client.key.password}")
    private String keypass;
    @Value("${yardman.client.user}")
    private String user;
    @Value("${yardman.client.encryptionUser}")
    private String encryptionUser;
    @Value("${yardman.url}")
    private String url;

    /**协同平台api接口id*/
    @Value("${yardman.API_ID}")
    private String API_ID;

    /**接入端id*/
    @Value("${yardman.CLIENT_ID}")
    private String CLIENT_ID;

    /**接入端秘钥*/
    @Value("${yardman.CLIENT_SECRET}")
    private String CLIENT_SECRET;

    /**协同平台api接口id*/
    @Value("${yardman.API_ID_EMP}")
    private String API_ID_EMP;

    /**接入端id*/
    @Value("${yardman.CLIENT_ID_EMP}")
    private String CLIENT_ID_EMP;

    /**接入端秘钥*/
    @Value("${yardman.CLIENT_SECRET_EMP}")
    private String CLIENT_SECRET_EMP;

    /**城乡居民服务方地址*/
    @Value("${yardman.CXURI}")
    private String URI;

    /**协同平台地址*/
    @Value("${yardman.ADDRESS}")
    private String ADDRESS;

    /**机关查询地址*/
    @Value("${yardman.OFFICE_ADDRESS}")
    private String OFFICE_ADDRESS;

    /**职工查询地址*/
    @Value("${yardman.ENTERPRISE_ADDRESS.url}")
    private String ENTERPRISE_ADDRESS;

    @Value("${yardman.archive}")
    private String archive;

    /*
     * 2.配置发送接收系统编码
     */
    private SystemCodeProvider getSystemCodeProvider() {
        SystemCodeProvider systemCodeProvider = new SystemCodeProvider();
        systemCodeProvider.setSender(sender);
        systemCodeProvider.setReciver(reciver);
        return systemCodeProvider;
    }

    /*
     * 3.定义wss出站拦截器和密码回调类
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private DecAliasOutInterceptor getDecAliasOutInterceptor() {
        DecAliasOutInterceptor decAliasOutInterceptor = null;
        Map args = new HashMap();
        args.put(WSHandlerConstants.ACTION, "Signature Encrypt");
        args.put(WSHandlerConstants.USER, user);
        args.put(WSHandlerConstants.SIG_PROP_FILE, "classpath:/yardman-client.properties");
        args.put(WSHandlerConstants.SIG_KEY_ID, "DirectReference");
        args.put(WSHandlerConstants.ENCRYPTION_USER, encryptionUser);
        args.put(WSHandlerConstants.ENC_PROP_FILE, "yardman-client.properties");
        args.put(WSHandlerConstants.ENC_KEY_ID, "SKIKeyIdentifier");
        PasswordCallBack passwordCallBack = new PasswordCallBack();
        passwordCallBack.setKeypass(keypass);
        args.put(WSHandlerConstants.PW_CALLBACK_REF, passwordCallBack);
        decAliasOutInterceptor = new DecAliasOutInterceptor(args);
        return decAliasOutInterceptor;
    }

    /*
     * 4.定义远程接口服务类，出站拦截器中增加wss
     */

    @Bean(name = "frontendClientFactory")
    @Lazy
    public ClientProxyFactoryBean frontendClientFactory() {
        ClientProxyFactoryBean clientProxyFactoryBean = new JaxWsProxyFactoryBean();
        clientProxyFactoryBean.setServiceClass(NeuWebService.class);
        clientProxyFactoryBean.setAddress(url);
        List interceptors = new ArrayList();
        // interceptors.add(getDecAliasOutInterceptor());
        interceptors.add(new LoggingOutInterceptor());
        clientProxyFactoryBean.setOutInterceptors(interceptors);
        List interceptors1 = new ArrayList();
        // interceptors.add(getDecAliasOutInterceptor());
        interceptors1.add(new LoggingInInterceptor());
        clientProxyFactoryBean.setInInterceptors(interceptors1);
        Map properties = new HashMap();
//        properties.put("mtom-enabled", true);
        clientProxyFactoryBean.setProperties(properties);
        return clientProxyFactoryBean;
    }

    @Autowired
    private ClientProxyFactoryBean clientProxyFactoryBean;

    private NeuWebService getNeuWebService() {
        NeuWebService neuWebService = (NeuWebService) clientProxyFactoryBean.create();
        return neuWebService;
    }

    /*
     * 远程服务路由
     */
    private ServerConfig serverConfig() {
        ServerConfig serverConfig = new ServerConfig();
        Map configMap = new HashMap();
        //configMap.put(ServerNodeEnum.城乡居民.toString(),ADDRESS);
//        configMap.put(ServerNodeEnum.机关.toString(), OFFICE_ADDRESS);
//        configMap.put(ServerNodeEnum.档案.toString(), archive);
        configMap.put(ServerNodeEnum.城乡居民.toString(), ADDRESS+"&bcpSign="+sign()+"&bcpClientId="+CLIENT_ID);
        configMap.put(ServerNodeEnum.城镇职工.toString(), ENTERPRISE_ADDRESS+"&bcpSign="+signEmp()+"&bcpClientId="+CLIENT_ID_EMP);
        configMap.put(ServerNodeEnum.社保.toString(), ENTERPRISE_ADDRESS+"&bcpSign="+signEmp()+"&bcpClientId="+CLIENT_ID_EMP);
        System.out.println("中心端路径："+ENTERPRISE_ADDRESS+"&bcpSign="+signEmp()+"&bcpClientId="+CLIENT_ID_EMP);
        serverConfig.setConfigMap(configMap);
        return serverConfig;
    }

    /**
     * 5.定义统一接口服务类
     *
     * @return
     */
    @Bean
    public ClientGateWayImpl clientGateWay() {
        YardmanGateWayImpl yardmanGateWay = new YardmanGateWayImpl();
        yardmanGateWay.setNeuWebService(getNeuWebService());
        yardmanGateWay.setSystemCodeProvider(getSystemCodeProvider());
        yardmanGateWay.setServerConfig(serverConfig());
        ClientGateWayImpl clientGateWay = new ClientGateWayImpl();
        clientGateWay.setYardmanGateWay(yardmanGateWay);
        return clientGateWay;
    }

    @Bean(name = "yardman.springContextUtils")
    public SpringContextUtils yardmanSpringContextUtils() {
        return new SpringContextUtils();
    }

    @Bean(name = "springContextUtils")
    public com.neusoft.sl.girder.ddd.hibernate.utils.SpringContextUtils reportSpringContextUtils() {
        return new com.neusoft.sl.girder.ddd.hibernate.utils.SpringContextUtils();
    }

    /**
     * 请求签名
     * 入参：
     * API接口ID
     * API请求地址
     * 接入端密钥
     *
     * @return String 签名结果
     */
    public String sign() {
        ClientUtil clientUtil = ClientUtil.getSingleton();
        return clientUtil.jwtSign(Client.builder().apiId(API_ID)
                .timestamp(String.valueOf(System.currentTimeMillis()))
                .url(URI).build(), CLIENT_SECRET);
    }

    /**
     * 请求签名
     * 入参：
     * API接口ID
     * API请求地址
     * 接入端密钥
     *
     * @return String 签名结果
     */
    public String signEmp() {
        ClientUtil clientUtil = ClientUtil.getSingleton();
        return clientUtil.jwtSign(Client.builder().apiId(API_ID_EMP)
                .timestamp(String.valueOf(System.currentTimeMillis()))
                .url(URI).build(), CLIENT_SECRET_EMP);
    }


}
