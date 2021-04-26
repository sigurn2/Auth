//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.11 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.07.24 时间 03:37:55 PM CST 
//


package com.neusoft.ehrss.liaoning.provider.ca.GXCA.dev.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>JettyHTTPServerEngineConfigType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="JettyHTTPServerEngineConfigType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tlsServerParameters" type="{http://cxf.apache.org/configuration/security}TLSServerParametersType" minOccurs="0"/&gt;
 *         &lt;element name="tlsServerParametersRef" type="{http://cxf.apache.org/transports/http-jetty/configuration}ParametersRefType" minOccurs="0"/&gt;
 *         &lt;element name="threadingParameters" type="{http://cxf.apache.org/transports/http-jetty/configuration}ThreadingParametersType" minOccurs="0"/&gt;
 *         &lt;element name="threadingParametersRef" type="{http://cxf.apache.org/transports/http-jetty/configuration}ParametersRefType" minOccurs="0"/&gt;
 *         &lt;element name="connector" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *         &lt;element name="handlers" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *         &lt;element name="sessionSupport" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="reuseAddress" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="port" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="host" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="continuationsEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="maxIdleTime" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="sendServerVersion" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JettyHTTPServerEngineConfigType", namespace = "http://cxf.apache.org/transports/http-jetty/configuration", propOrder = {
    "tlsServerParameters",
    "tlsServerParametersRef",
    "threadingParameters",
    "threadingParametersRef",
    "connector",
    "handlers",
    "sessionSupport",
    "reuseAddress"
})
public class JettyHTTPServerEngineConfigType {

    protected TLSServerParametersType tlsServerParameters;
    protected ParametersRefType tlsServerParametersRef;
    protected ThreadingParametersType threadingParameters;
    protected ParametersRefType threadingParametersRef;
    protected Object connector;
    protected Object handlers;
    protected Boolean sessionSupport;
    protected Boolean reuseAddress;
    @XmlAttribute(name = "port", required = true)
    protected int port;
    @XmlAttribute(name = "host")
    protected String host;
    @XmlAttribute(name = "continuationsEnabled")
    protected Boolean continuationsEnabled;
    @XmlAttribute(name = "maxIdleTime")
    protected Integer maxIdleTime;
    @XmlAttribute(name = "sendServerVersion")
    protected Boolean sendServerVersion;

    /**
     * 获取tlsServerParameters属性的值。
     * 
     * @return
     *     possible object is
     *     {@link TLSServerParametersType }
     *     
     */
    public TLSServerParametersType getTlsServerParameters() {
        return tlsServerParameters;
    }

    /**
     * 设置tlsServerParameters属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link TLSServerParametersType }
     *     
     */
    public void setTlsServerParameters(TLSServerParametersType value) {
        this.tlsServerParameters = value;
    }

    /**
     * 获取tlsServerParametersRef属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ParametersRefType }
     *     
     */
    public ParametersRefType getTlsServerParametersRef() {
        return tlsServerParametersRef;
    }

    /**
     * 设置tlsServerParametersRef属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ParametersRefType }
     *     
     */
    public void setTlsServerParametersRef(ParametersRefType value) {
        this.tlsServerParametersRef = value;
    }

    /**
     * 获取threadingParameters属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ThreadingParametersType }
     *     
     */
    public ThreadingParametersType getThreadingParameters() {
        return threadingParameters;
    }

    /**
     * 设置threadingParameters属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ThreadingParametersType }
     *     
     */
    public void setThreadingParameters(ThreadingParametersType value) {
        this.threadingParameters = value;
    }

    /**
     * 获取threadingParametersRef属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ParametersRefType }
     *     
     */
    public ParametersRefType getThreadingParametersRef() {
        return threadingParametersRef;
    }

    /**
     * 设置threadingParametersRef属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ParametersRefType }
     *     
     */
    public void setThreadingParametersRef(ParametersRefType value) {
        this.threadingParametersRef = value;
    }

    /**
     * 获取connector属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getConnector() {
        return connector;
    }

    /**
     * 设置connector属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setConnector(Object value) {
        this.connector = value;
    }

    /**
     * 获取handlers属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getHandlers() {
        return handlers;
    }

    /**
     * 设置handlers属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setHandlers(Object value) {
        this.handlers = value;
    }

    /**
     * 获取sessionSupport属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSessionSupport() {
        return sessionSupport;
    }

    /**
     * 设置sessionSupport属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSessionSupport(Boolean value) {
        this.sessionSupport = value;
    }

    /**
     * 获取reuseAddress属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReuseAddress() {
        return reuseAddress;
    }

    /**
     * 设置reuseAddress属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReuseAddress(Boolean value) {
        this.reuseAddress = value;
    }

    /**
     * 获取port属性的值。
     * 
     */
    public int getPort() {
        return port;
    }

    /**
     * 设置port属性的值。
     * 
     */
    public void setPort(int value) {
        this.port = value;
    }

    /**
     * 获取host属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHost() {
        return host;
    }

    /**
     * 设置host属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHost(String value) {
        this.host = value;
    }

    /**
     * 获取continuationsEnabled属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isContinuationsEnabled() {
        return continuationsEnabled;
    }

    /**
     * 设置continuationsEnabled属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setContinuationsEnabled(Boolean value) {
        this.continuationsEnabled = value;
    }

    /**
     * 获取maxIdleTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxIdleTime() {
        return maxIdleTime;
    }

    /**
     * 设置maxIdleTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxIdleTime(Integer value) {
        this.maxIdleTime = value;
    }

    /**
     * 获取sendServerVersion属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSendServerVersion() {
        return sendServerVersion;
    }

    /**
     * 设置sendServerVersion属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSendServerVersion(Boolean value) {
        this.sendServerVersion = value;
    }

}
