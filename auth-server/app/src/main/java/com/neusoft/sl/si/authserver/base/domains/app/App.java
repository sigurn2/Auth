package com.neusoft.sl.si.authserver.base.domains.app;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neusoft.sl.girder.ddd.hibernate.entity.UuidEntityBase;

/**
 * 表示一个系统
 * 
 * <pre>
 * 唯一区别系统的业务主键是"系统名称"，因此系统创建以后不允许修改系统名称
 * </pre>
 * 
 * @author mojf
 * 
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "T_APP")
public class App extends UuidEntityBase<App, String> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 454375936833528029L;

	/**
	 * 系统名
	 */
	@Column(unique = true)
	private String name;

	/**
	 * 系统描述
	 */
	@Column(name = "descript")
	private String desc;

	/**
	 * 系统地址
	 */
	@Column(name = "url")
	private String url;

	/**
	 * 排序字段
	 */
	@Column(name = "SORTBY")
	private Long sortby;

	public Long getSortby() {
		return sortby;
	}

	public void setSortby(Long sortby) {
		this.sortby = sortby;
	}

	@Override
	@JsonIgnoreProperties
	public String getPK() {
		return name;
	}

	/**
	 * 创建App
	 * 
	 * @param name
	 * @param desc
	 */
	public App(String name, String desc) {
		Validate.notBlank(name, "系统名称不能为空");
		this.name = name;
		this.desc = desc;
	}

	public App(String name, String desc, String url) {
		Validate.notBlank(name, "系统名称不能为空");
		this.name = name;
		this.desc = desc;
		this.url = url;
	}

	/**
	 * 默认构造函数
	 */
	protected App() {
		// for jpa
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
