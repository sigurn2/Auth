package com.neusoft.ehrss.liaoning.family.dto;

/**
 * 家庭成员
 * 
 * @author zhou.haidong
 */
public class FamilyQueryDTO extends FamilyDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String personId;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

}
