//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.11 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.07.24 时间 03:37:55 PM CST 
//


package com.neusoft.ehrss.liaoning.provider.ca.GXCA.dev.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DNConstraintsType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DNConstraintsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RegularExpression" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="combinator" type="{http://cxf.apache.org/configuration/security}CombinatorType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DNConstraintsType", namespace = "http://cxf.apache.org/configuration/security", propOrder = {
    "regularExpression"
})
public class DNConstraintsType {

    @XmlElement(name = "RegularExpression")
    protected List<String> regularExpression;
    @XmlAttribute(name = "combinator")
    protected CombinatorType combinator;

    /**
     * Gets the value of the regularExpression property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the regularExpression property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegularExpression().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRegularExpression() {
        if (regularExpression == null) {
            regularExpression = new ArrayList<String>();
        }
        return this.regularExpression;
    }

    /**
     * 获取combinator属性的值。
     * 
     * @return
     *     possible object is
     *     {@link CombinatorType }
     *     
     */
    public CombinatorType getCombinator() {
        return combinator;
    }

    /**
     * 设置combinator属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link CombinatorType }
     *     
     */
    public void setCombinator(CombinatorType value) {
        this.combinator = value;
    }

}
