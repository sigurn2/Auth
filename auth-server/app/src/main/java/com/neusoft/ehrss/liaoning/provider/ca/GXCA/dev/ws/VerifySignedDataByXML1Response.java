//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.11 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.07.24 时间 03:37:55 PM CST 
//


package com.neusoft.ehrss.liaoning.provider.ca.GXCA.dev.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>VerifySignedDataByXML1Response complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="VerifySignedDataByXML1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="VerifySignedDataByXML1" type="{http://www.w3.org/2001/XMLSchema}boolean" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerifySignedDataByXML1Response", propOrder = {
    "verifySignedDataByXML1"
})
public class VerifySignedDataByXML1Response {

    @XmlElement(name = "VerifySignedDataByXML1")
    protected boolean verifySignedDataByXML1;

    /**
     * 获取verifySignedDataByXML1属性的值。
     * 
     */
    public boolean isVerifySignedDataByXML1() {
        return verifySignedDataByXML1;
    }

    /**
     * 设置verifySignedDataByXML1属性的值。
     * 
     */
    public void setVerifySignedDataByXML1(boolean value) {
        this.verifySignedDataByXML1 = value;
    }

}
