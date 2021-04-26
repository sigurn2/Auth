/**
 * 
 */
package com.neusoft.sl.si.authserver.simis.support.gateway;

import com.neusoft.sl.si.yardman.server.message.AbstractRequest;

/**
 * @author mojf
 * 
 */
public interface ClientGateWay {

    /**
     * 查询交易
     * 
     * @param <V>
     * 
     * @param responseClassType
     * @param request
     * @param bodyClassType
     * @return
     */
    public <T, V> Object query(Class<T> responseClassType, AbstractRequest request, Class<V> bodyClassType);

    /**
     * 查询交易
     * 
     * @param <V>
     * @param serverNode
     * @param responseClassType
     * @param request
     * @param bodyClassType
     * @return
     */
    public <T, V> Object query(String serverNode, Class<T> responseClassType, AbstractRequest request,
                               Class<V> bodyClassType);

    /**
     * 发送交易
     *
     * @param <V>
     * @param responseClassType
     * @param request
     * @param bodyClassType
     * @return
     */
    public <T, V> Object send(Class<T> responseClassType, AbstractRequest request, Class<V> bodyClassType);

    /**
     * 发送交易
     *
     * @param <V>
     * @param serverNode
     * @param responseClassType
     * @param request
     * @param bodyClassType
     * @return
     */
    public <T, V> Object send(String serverNode, Class<T> responseClassType, AbstractRequest request,
                              Class<V> bodyClassType);

}
