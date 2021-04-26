package com.neusoft.sl.si.authserver.simis.support.gateway;

import com.neusoft.sl.si.authserver.uaa.exception.WebserviceApiException;
import com.neusoft.sl.si.yardman.client.YardmanGateWay;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.message.RequestHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 客户端调用webservice代理，处理异常和返回的DTO
 * 
 * @author mojf
 * 
 */
@Service
public class ClientGateWayImpl implements ClientGateWay {
    /** 日志 */
    private static Logger LOGGER = LoggerFactory.getLogger(ClientGateWay.class);

    private YardmanGateWay yardmanGateWay;

    /*
     * (non-Javadoc)
     *
     * @see
     * com.neusoft.sl.si.siagent.regident.service.SiagentGateWay#query(java.
     * lang.Class, com.neusoft.sl.si.yardman.server.message.AbstractRequest)
     */
    public <T, V> Object query(String serverNode, Class<T> responseClassType, AbstractRequest request,
            Class<V> bodyClassType) {
        String transactionId = UUID.randomUUID().toString();
        RequestHead head = request.getHead();
        LOGGER.debug("sender = {}",head.getSender());
        head.setTransactionId(transactionId);
        head.setOperatorName(fetchOperator());
        request.setHead(head);
        T response = yardmanGateWay.query(serverNode, responseClassType, request);
        return respBody(transactionId, response, bodyClassType);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     *
     * com.neusoft.sl.si.siagent.regident.service.SiagentGateWay#send(java.lang
     * .Class, com.neusoft.sl.si.yardman.server.message.AbstractRequest)
     */
    public <T, V> Object send(String serverNode, Class<T> responseClassType, AbstractRequest request,
            Class<V> bodyClassType) {
        String transactionId = UUID.randomUUID().toString();
        RequestHead head = request.getHead();
        head.setTransactionId(transactionId);
        head.setOperatorName(fetchOperator());
        request.setHead(head);
        T response = yardmanGateWay.send(serverNode, responseClassType, request);
        return respBody(transactionId, response, bodyClassType);
    }

    /**
     * 解析出响应体，出错抛出异常
     * 
     * @param transactionId
     * @param response
     * @param bodyClassType
     * @return
     */
    private <T, V> Object respBody(String transactionId, T response, Class<V> bodyClassType) {
        Method methodIsSuccess = null, methodGetBody = null, methodGetErrorMsg = null;
        boolean isSuccess = false;
        String errMsg = "====解析交易返回信息失败，流水号：[" + transactionId + "]====";
        try {
            methodIsSuccess = response.getClass().getMethod("isSuccess");
            methodGetBody = response.getClass().getMethod("getBody");
            methodGetErrorMsg = response.getClass().getMethod("getErrorMsg");
            isSuccess = (Boolean) methodIsSuccess.invoke(response);
            if (isSuccess) {
                LOGGER.debug("====交易成功，流水号：[" + transactionId + "]====");
                Object result = methodGetBody.invoke(response);
                if (result == null) {
                    result = bodyClassType.newInstance();
                }
                return result;
            } else {
                errMsg = (String) methodGetErrorMsg.invoke(response);
                LOGGER.error("====交易失败，流水号：[" + transactionId + "]====" + errMsg);
                throw new WebserviceApiException(errMsg);
            }
        } catch (InstantiationException e) {
            LOGGER.error(errMsg, e);
            throw new WebserviceApiException(errMsg, e);
        } catch (NoSuchMethodException e) {
            LOGGER.error(errMsg, e);
            throw new WebserviceApiException(errMsg, e);
        } catch (SecurityException e) {
            LOGGER.error(errMsg, e);
            throw new WebserviceApiException(errMsg, e);
        } catch (IllegalAccessException e) {
            LOGGER.error(errMsg, e);
            throw new WebserviceApiException(errMsg, e);
        } catch (IllegalArgumentException e) {
            LOGGER.error(errMsg, e);
            throw new WebserviceApiException(errMsg, e);
        } catch (InvocationTargetException e) {
            LOGGER.error(errMsg, e);
            throw new WebserviceApiException(errMsg, e);
        }
    }

    /**
     * 获取用户名
     * 
     * @return
     */
    private String fetchOperator() {
        // String operator = "si_enterprise";
        String operator = "xbbg8888";
        // try {
        // Subject currentUser = SecurityUtils.getSubject();
        // operator = (String) currentUser.getPrincipal();
        // } catch (UnavailableSecurityManagerException e) {
        // LOGGER.debug("====交易中获取用户名失败====", e);
        // }
        return operator;
    }

    /**
     * @return the yardmanGateWay
     */
    public YardmanGateWay getYardmanGateWay() {
        return yardmanGateWay;
    }

    /**
     * @param yardmanGateWay
     *            the yardmanGateWay to set
     */
    public void setYardmanGateWay(YardmanGateWay yardmanGateWay) {
        this.yardmanGateWay = yardmanGateWay;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.neusoft.sl.si.siagent.regident.service.SiagentGateWay#query(java.
     * lang.Class, com.neusoft.sl.si.yardman.server.message.AbstractRequest)
     */
    public <T, V> Object query(Class<T> responseClassType, AbstractRequest request, Class<V> bodyClassType) {
        String transactionId = UUID.randomUUID().toString();
        RequestHead head = request.getHead();
        head.setTransactionId(transactionId);
        head.setOperatorName(fetchOperator());
        request.setHead(head);
        T response = yardmanGateWay.query(responseClassType, request);
        return respBody(transactionId, response, bodyClassType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.neusoft.sl.si.siagent.regident.service.SiagentGateWay#send(java.lang
     * .Class, com.neusoft.sl.si.yardman.server.message.AbstractRequest)
     */
    public <T, V> Object send(Class<T> responseClassType, AbstractRequest request, Class<V> bodyClassType) {
        String transactionId = UUID.randomUUID().toString();
        RequestHead head = request.getHead();
        head.setTransactionId(transactionId);
        head.setOperatorName(fetchOperator());
        request.setHead(head);
        T response = yardmanGateWay.send(responseClassType, request);
        return respBody(transactionId, response, bodyClassType);
    }

}
