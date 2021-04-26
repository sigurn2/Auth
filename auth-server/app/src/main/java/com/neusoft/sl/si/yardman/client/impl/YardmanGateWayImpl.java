package com.neusoft.sl.si.yardman.client.impl;
import	java.util.List;

import com.neusoft.sl.si.authserver.simis.BuzzNumberEnum;
import com.neusoft.sl.si.authserver.simis.support.gateway.ServerNodeEnum;
import org.apache.commons.lang3.Validate;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.neusoft.sl.si.yardman.client.SpringContextUtils;
import com.neusoft.sl.si.yardman.client.YardmanGateWay;
import com.neusoft.sl.si.yardman.client.dispatch.ServerConfig;
import com.neusoft.sl.si.yardman.server.exception.WebServiceBusinessException;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.message.MessageEncoder;
import com.neusoft.sl.si.yardman.server.message.RequestHead;
import com.neusoft.sl.si.yardman.server.service.NeuWebService;

/**
 * 交易接口实现类
 *
 * @author wuyf
 *
 */
public class YardmanGateWayImpl implements YardmanGateWay {

    /** 中心端服务接口 */
    private NeuWebService neuWebService;

    private SystemCodeProvider systemCodeProvider;

    private ServerConfig serverConfig;

    /** 日志 */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(YardmanGateWayImpl.class);

    /**
     * 操作类交易请求
     */
    @Override
    public <T> T send(Class<T> responseClassType, AbstractRequest request) {
        return this.send(null, responseClassType, request);
    }

    /**
     * 查询类交易请求
     */
    @Override
    public <T> T query(Class<T> responseClassType, AbstractRequest request) {
        return this.query(null, responseClassType, request);
    }

    /**
     * 数据格式检查无误以后签名
     *
     * @param request
     * @param serverNode
     */
    private void signRequest(AbstractRequest request, String serverNode) {
        Validate.notNull(request, "交易消息不能为空");
        RequestHead head = request.getHead();
        // 检查交易报文头
        Validate.notNull(head, "交易消息头不能为空");
        Validate.notNull(head.getBuzzNumber(), "交易类型编码不能为空");
        Validate.notNull(head.getVersion(), "交易版本不能为空");
        Validate.notNull(head.getTransactionId(), "交易流水号不能为空");
        Validate.notNull(head.getOperatorName(), "交易操作员不能为空");
        Validate.notNull(head.getDatetime(), "交易日期时间不能为空");
        // 设置发送接收系统编码
        head.setSender(systemCodeProvider.getSender());
//        if (null == serverNode) {
//            head.setReciver(systemCodeProvider.getReciver());
//        } else {
//            head.setReciver(serverNode);
//        }
        if (null == serverNode) {
            head.setReciver(this.systemCodeProvider.getReciver());
        } if(ServerNodeEnum.城镇职工.toString() .equals(serverNode) ){
            head.setSender("NEUSOFT_GGFW_WEB");
            head.setReciver(serverNode);
        } if(head.getBuzzNumber().equals("GGFW_LN_GRWT_006")||head.getBuzzNumber().equals("GGFW_LN_GRWT_007")||head.getBuzzNumber().equals("GGFW_LN_GRWT_001")){
            head.setSender("NEUSOFT_GRWT_WEB");
            head.setReciver(serverNode);
        }else {
            head.setReciver(serverNode);
        }
    }

    /**
     * 数据格式检查无误以后签名
     *
     * @param request
     * @param serverNode
     */
    private void signQueryRequest(AbstractRequest request, String serverNode) {
        Validate.notNull(request, "交易消息不能为空", new Object[0]);
        RequestHead head = request.getHead();
        Validate.notNull(head, "交易消息头不能为空", new Object[0]);
        Validate.notNull(head.getBuzzNumber(), "交易类型编码不能为空", new Object[0]);
        Validate.notNull(head.getVersion(), "交易版本不能为空", new Object[0]);
        Validate.notNull(head.getDatetime(), "交易日期时间不能为空", new Object[0]);
        head.setSender(this.systemCodeProvider.getSender());
        if (null == serverNode) {
            head.setReciver(this.systemCodeProvider.getReciver());
        } if("002".equals(serverNode) ){
            head.setSender("NEUSOFT_GGFW_WEB");
            head.setReciver(serverNode);
        } if(head.getBuzzNumber().equals("GGFW_LN_GRWT_006")||head.getBuzzNumber().equals("GGFW_LN_GRWT_007")||head.getBuzzNumber().equals("GGFW_LN_GRWT_001")||head.getBuzzNumber().equals("GGFW_LN_GRWT_033")){
            head.setSender("NEUSOFT_GRWT_WEB");
            head.setReciver(serverNode);
        }else {
            head.setReciver(serverNode);
        }

    }

    /**
     * @param neuWebService
     *            the neuWebService to set
     */
    public void setNeuWebService(NeuWebService neuWebService) {
        this.neuWebService = neuWebService;
    }

    /**
     * @param systemCodeProvider
     *            the systemCodeProvider to set
     */
    public void setSystemCodeProvider(SystemCodeProvider systemCodeProvider) {
        this.systemCodeProvider = systemCodeProvider;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.neusoft.sl.si.yardman.client.YardmanGateWay#send(java.lang.String,
     * java.lang.Class,
     * com.neusoft.sl.si.yardman.server.message.AbstractRequest)
     */
    @Override
    public <T> T send(String serverNode, Class<T> responseClassType,
                      AbstractRequest request) {
        Validate.notNull(responseClassType, "返回交易消息对象类型不能为空");
        // 对请求签名
        signRequest(request, serverNode);
        // 编码请求为报文
        String content = MessageEncoder.encode(request);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("发起交易\r\n" + content);
        }
        // 获取webservice
        NeuWebService webservice = getWebService(serverNode);

        if (BuzzNumberEnum.单位基本信息查询.toString().equals(request.getBuzzNumber())){
            RequestHead head = request.getHead();
            head.setSender("NEUSOFT_GGFW_WEB");
            head.setReciver("002");
        }
        // 发起交易
        LOGGER.error("sender = {}, receiver = {}", request.getSender(),request.getReciver());
        String msg = webservice.send(request.getSender(),
                request.getBuzzNumber(), request.getVersion(),
                request.getTransactionId(), request.getOperatorName(), content);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("返回交易结果\r\n" + msg);
        }

        // 返回交易结果
        return MessageEncoder.decode(responseClassType, msg);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.neusoft.sl.si.yardman.client.YardmanGateWay#query(java.lang.String,
     * java.lang.Class,
     * com.neusoft.sl.si.yardman.server.message.AbstractRequest)
     */
    @Override
    public <T> T query(String serverNode, Class<T> responseClassType,
                       AbstractRequest request) {
        Validate.notNull(responseClassType, "返回交易消息对象不能为空");
        // 对请求签名
        signQueryRequest(request, serverNode);
        // 编码请求为报文
        String content = MessageEncoder.encode(request);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("发起交易\r\n" + content);
        }
        // 获取webservice
        NeuWebService webservice = getWebService(serverNode);
        // 发起交易
        String msg = webservice.query(request.getSender(),
                request.getBuzzNumber(), request.getVersion(), content);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("返回交易结果\r\n" + msg);
        }

        // 返回交易结果
        return MessageEncoder.decode(responseClassType, msg);
    }

    /**
     * @param serverNode
     * @return
     */
    private NeuWebService getWebService(String serverNode) {
        if (null == serverNode) {
            return neuWebService;
        }
        if (null == serverConfig) {
            throw new WebServiceBusinessException("板块调用系统错误：未配置服务端路由");
        }
        String add = serverConfig.getConfigMap().get(serverNode);
        if (null == add) {
            throw new WebServiceBusinessException("板块调用系统错误：未查询到serverNode为"
                    + serverNode + "的服务地址URL");
        }
        ApplicationContext context = loadSpringContext();
        ClientProxyFactoryBean frontendClientFactory = (ClientProxyFactoryBean) context
                .getBean("frontendClientFactory");
        frontendClientFactory.setAddress(add);
        NeuWebService neuWebService = (NeuWebService) frontendClientFactory
                .create();
        return neuWebService;
    }

    /**
     * 加载Spring配置文件
     *
     * @return
     */
    private ApplicationContext loadSpringContext() {
//        ApplicationContext context = new ClassPathXmlApplicationContext(
//                new String[] { "classpath:/applicationContext-yardman-client.xml" });
        ApplicationContext context = SpringContextUtils.getApplicationContext();
        return context;
    }

    /**
     * @param serverConfig
     *            the serverConfig to set
     */
    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

}
