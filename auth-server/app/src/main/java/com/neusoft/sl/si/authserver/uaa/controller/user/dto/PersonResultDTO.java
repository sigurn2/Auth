package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author mojf
 *
 */
public class PersonResultDTO implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 证件号码
	 */
	private String idNumber;

	/**
	 * 姓名
	 */
	private String name;

	private List<PersonInfoDTO> persons;

	/**
	 * 是否已参保，1-目前已参保，0-未参保
	 */
	private String isSocial;

	public PersonResultDTO(String idNumber, String name) {
		super();
		this.idNumber = idNumber;
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIsSocial() {
		return isSocial;
	}

	public void setIsSocial(String isSocial) {
		this.isSocial = isSocial;
	}

	public List<PersonInfoDTO> getPersons() {
		return persons;
	}

	public void setPersons(List<PersonInfoDTO> persons) {
		this.persons = persons;
	}

}
