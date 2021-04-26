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
 * <p>TLSServerParametersType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="TLSServerParametersType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="keyManagers" type="{http://cxf.apache.org/configuration/security}KeyManagersType" minOccurs="0"/&gt;
 *         &lt;element name="trustManagers" type="{http://cxf.apache.org/configuration/security}TrustManagersType" minOccurs="0"/&gt;
 *         &lt;element name="cipherSuites" type="{http://cxf.apache.org/configuration/security}CipherSuites" minOccurs="0"/&gt;
 *         &lt;element name="cipherSuitesFilter" type="{http://cxf.apache.org/configuration/security}FiltersType" minOccurs="0"/&gt;
 *         &lt;element name="secureRandomParameters" type="{http://cxf.apache.org/configuration/security}SecureRandomParameters" minOccurs="0"/&gt;
 *         &lt;element name="clientAuthentication" type="{http://cxf.apache.org/configuration/security}ClientAuthentication" minOccurs="0"/&gt;
 *         &lt;element name="certConstraints" type="{http://cxf.apache.org/configuration/security}CertificateConstraintsType" minOccurs="0"/&gt;
 *         &lt;element name="certAlias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/all&gt;
 *       &lt;attribute name="jsseProvider" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="secureSocketProtocol" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TLSServerParametersType", namespace = "http://cxf.apache.org/configuration/security", propOrder = {

})
public class TLSServerParametersType {

    protected KeyManagersType keyManagers;
    protected TrustManagersType trustManagers;
    protected CipherSuites cipherSuites;
    protected FiltersType cipherSuitesFilter;
    protected SecureRandomParameters secureRandomParameters;
    protected ClientAuthentication clientAuthentication;
    protected CertificateConstraintsType certConstraints;
    protected String certAlias;
    @XmlAttribute(name = "jsseProvider")
    protected String jsseProvider;
    @XmlAttribute(name = "secureSocketProtocol")
    protected String secureSocketProtocol;

    /**
     * 获取keyManagers属性的值。
     * 
     * @return
     *     possible object is
     *     {@link KeyManagersType }
     *     
     */
    public KeyManagersType getKeyManagers() {
        return keyManagers;
    }

    /**
     * 设置keyManagers属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link KeyManagersType }
     *     
     */
    public void setKeyManagers(KeyManagersType value) {
        this.keyManagers = value;
    }

    /**
     * 获取trustManagers属性的值。
     * 
     * @return
     *     possible object is
     *     {@link TrustManagersType }
     *     
     */
    public TrustManagersType getTrustManagers() {
        return trustManagers;
    }

    /**
     * 设置trustManagers属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link TrustManagersType }
     *     
     */
    public void setTrustManagers(TrustManagersType value) {
        this.trustManagers = value;
    }

    /**
     * 获取cipherSuites属性的值。
     * 
     * @return
     *     possible object is
     *     {@link CipherSuites }
     *     
     */
    public CipherSuites getCipherSuites() {
        return cipherSuites;
    }

    /**
     * 设置cipherSuites属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link CipherSuites }
     *     
     */
    public void setCipherSuites(CipherSuites value) {
        this.cipherSuites = value;
    }

    /**
     * 获取cipherSuitesFilter属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FiltersType }
     *     
     */
    public FiltersType getCipherSuitesFilter() {
        return cipherSuitesFilter;
    }

    /**
     * 设置cipherSuitesFilter属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FiltersType }
     *     
     */
    public void setCipherSuitesFilter(FiltersType value) {
        this.cipherSuitesFilter = value;
    }

    /**
     * 获取secureRandomParameters属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SecureRandomParameters }
     *     
     */
    public SecureRandomParameters getSecureRandomParameters() {
        return secureRandomParameters;
    }

    /**
     * 设置secureRandomParameters属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SecureRandomParameters }
     *     
     */
    public void setSecureRandomParameters(SecureRandomParameters value) {
        this.secureRandomParameters = value;
    }

    /**
     * 获取clientAuthentication属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ClientAuthentication }
     *     
     */
    public ClientAuthentication getClientAuthentication() {
        return clientAuthentication;
    }

    /**
     * 设置clientAuthentication属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ClientAuthentication }
     *     
     */
    public void setClientAuthentication(ClientAuthentication value) {
        this.clientAuthentication = value;
    }

    /**
     * 获取certConstraints属性的值。
     * 
     * @return
     *     possible object is
     *     {@link CertificateConstraintsType }
     *     
     */
    public CertificateConstraintsType getCertConstraints() {
        return certConstraints;
    }

    /**
     * 设置certConstraints属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateConstraintsType }
     *     
     */
    public void setCertConstraints(CertificateConstraintsType value) {
        this.certConstraints = value;
    }

    /**
     * 获取certAlias属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertAlias() {
        return certAlias;
    }

    /**
     * 设置certAlias属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertAlias(String value) {
        this.certAlias = value;
    }

    /**
     * 获取jsseProvider属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJsseProvider() {
        return jsseProvider;
    }

    /**
     * 设置jsseProvider属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJsseProvider(String value) {
        this.jsseProvider = value;
    }

    /**
     * 获取secureSocketProtocol属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecureSocketProtocol() {
        return secureSocketProtocol;
    }

    /**
     * 设置secureSocketProtocol属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecureSocketProtocol(String value) {
        this.secureSocketProtocol = value;
    }

}
