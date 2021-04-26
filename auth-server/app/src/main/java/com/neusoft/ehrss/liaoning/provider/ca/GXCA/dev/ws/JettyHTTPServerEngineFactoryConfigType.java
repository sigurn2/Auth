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
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>JettyHTTPServerEngineFactoryConfigType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="JettyHTTPServerEngineFactoryConfigType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identifiedTLSServerParameters" type="{http://cxf.apache.org/transports/http-jetty/configuration}TLSServerParametersIdentifiedType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="identifiedThreadingParameters" type="{http://cxf.apache.org/transports/http-jetty/configuration}ThreadingParametersIdentifiedType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="engine" type="{http://cxf.apache.org/transports/http-jetty/configuration}JettyHTTPServerEngineConfigType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="bus" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JettyHTTPServerEngineFactoryConfigType", namespace = "http://cxf.apache.org/transports/http-jetty/configuration", propOrder = {
    "identifiedTLSServerParameters",
    "identifiedThreadingParameters",
    "engine"
})
public class JettyHTTPServerEngineFactoryConfigType {

    @XmlElement(nillable = true)
    protected List<TLSServerParametersIdentifiedType> identifiedTLSServerParameters;
    @XmlElement(nillable = true)
    protected List<ThreadingParametersIdentifiedType> identifiedThreadingParameters;
    @XmlElement(nillable = true)
    protected List<JettyHTTPServerEngineConfigType> engine;
    @XmlAttribute(name = "bus")
    protected String bus;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the identifiedTLSServerParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identifiedTLSServerParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentifiedTLSServerParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TLSServerParametersIdentifiedType }
     * 
     * 
     */
    public List<TLSServerParametersIdentifiedType> getIdentifiedTLSServerParameters() {
        if (identifiedTLSServerParameters == null) {
            identifiedTLSServerParameters = new ArrayList<TLSServerParametersIdentifiedType>();
        }
        return this.identifiedTLSServerParameters;
    }

    /**
     * Gets the value of the identifiedThreadingParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identifiedThreadingParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentifiedThreadingParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ThreadingParametersIdentifiedType }
     * 
     * 
     */
    public List<ThreadingParametersIdentifiedType> getIdentifiedThreadingParameters() {
        if (identifiedThreadingParameters == null) {
            identifiedThreadingParameters = new ArrayList<ThreadingParametersIdentifiedType>();
        }
        return this.identifiedThreadingParameters;
    }

    /**
     * Gets the value of the engine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the engine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEngine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JettyHTTPServerEngineConfigType }
     * 
     * 
     */
    public List<JettyHTTPServerEngineConfigType> getEngine() {
        if (engine == null) {
            engine = new ArrayList<JettyHTTPServerEngineConfigType>();
        }
        return this.engine;
    }

    /**
     * 获取bus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBus() {
        return bus;
    }

    /**
     * 设置bus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBus(String value) {
        this.bus = value;
    }

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
