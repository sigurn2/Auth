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
 * <p>TrustManagersType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="TrustManagersType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="keyStore" type="{http://cxf.apache.org/configuration/security}KeyStoreType" minOccurs="0"/&gt;
 *         &lt;element name="certStore" type="{http://cxf.apache.org/configuration/security}CertStoreType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="provider" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="factoryAlgorithm" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrustManagersType", namespace = "http://cxf.apache.org/configuration/security", propOrder = {
    "keyStore",
    "certStore"
})
public class TrustManagersType {

    protected KeyStoreType keyStore;
    protected CertStoreType certStore;
    @XmlAttribute(name = "provider")
    protected String provider;
    @XmlAttribute(name = "factoryAlgorithm")
    protected String factoryAlgorithm;
    @XmlAttribute(name = "ref")
    protected String ref;

    /**
     * 获取keyStore属性的值。
     * 
     * @return
     *     possible object is
     *     {@link KeyStoreType }
     *     
     */
    public KeyStoreType getKeyStore() {
        return keyStore;
    }

    /**
     * 设置keyStore属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link KeyStoreType }
     *     
     */
    public void setKeyStore(KeyStoreType value) {
        this.keyStore = value;
    }

    /**
     * 获取certStore属性的值。
     * 
     * @return
     *     possible object is
     *     {@link CertStoreType }
     *     
     */
    public CertStoreType getCertStore() {
        return certStore;
    }

    /**
     * 设置certStore属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link CertStoreType }
     *     
     */
    public void setCertStore(CertStoreType value) {
        this.certStore = value;
    }

    /**
     * 获取provider属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvider() {
        return provider;
    }

    /**
     * 设置provider属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvider(String value) {
        this.provider = value;
    }

    /**
     * 获取factoryAlgorithm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFactoryAlgorithm() {
        return factoryAlgorithm;
    }

    /**
     * 设置factoryAlgorithm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFactoryAlgorithm(String value) {
        this.factoryAlgorithm = value;
    }

    /**
     * 获取ref属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRef() {
        return ref;
    }

    /**
     * 设置ref属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRef(String value) {
        this.ref = value;
    }

}
