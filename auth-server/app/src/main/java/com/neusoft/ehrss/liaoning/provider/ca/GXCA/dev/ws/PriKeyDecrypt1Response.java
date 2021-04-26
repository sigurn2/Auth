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
 * <p>PriKeyDecrypt1Response complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="PriKeyDecrypt1Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PriKeyDecrypt1" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PriKeyDecrypt1Response", propOrder = {
    "priKeyDecrypt1"
})
public class PriKeyDecrypt1Response {

    @XmlElement(name = "PriKeyDecrypt1")
    protected byte[] priKeyDecrypt1;

    /**
     * 获取priKeyDecrypt1属性的值。
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getPriKeyDecrypt1() {
        return priKeyDecrypt1;
    }

    /**
     * 设置priKeyDecrypt1属性的值。
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setPriKeyDecrypt1(byte[] value) {
        this.priKeyDecrypt1 = value;
    }

}
