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
 * <p>CertificateConstraintsType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CertificateConstraintsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SubjectDNConstraints" type="{http://cxf.apache.org/configuration/security}DNConstraintsType" minOccurs="0"/&gt;
 *         &lt;element name="IssuerDNConstraints" type="{http://cxf.apache.org/configuration/security}DNConstraintsType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CertificateConstraintsType", namespace = "http://cxf.apache.org/configuration/security", propOrder = {
    "subjectDNConstraints",
    "issuerDNConstraints"
})
public class CertificateConstraintsType {

    @XmlElement(name = "SubjectDNConstraints")
    protected DNConstraintsType subjectDNConstraints;
    @XmlElement(name = "IssuerDNConstraints")
    protected DNConstraintsType issuerDNConstraints;

    /**
     * 获取subjectDNConstraints属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DNConstraintsType }
     *     
     */
    public DNConstraintsType getSubjectDNConstraints() {
        return subjectDNConstraints;
    }

    /**
     * 设置subjectDNConstraints属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DNConstraintsType }
     *     
     */
    public void setSubjectDNConstraints(DNConstraintsType value) {
        this.subjectDNConstraints = value;
    }

    /**
     * 获取issuerDNConstraints属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DNConstraintsType }
     *     
     */
    public DNConstraintsType getIssuerDNConstraints() {
        return issuerDNConstraints;
    }

    /**
     * 设置issuerDNConstraints属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DNConstraintsType }
     *     
     */
    public void setIssuerDNConstraints(DNConstraintsType value) {
        this.issuerDNConstraints = value;
    }

}
