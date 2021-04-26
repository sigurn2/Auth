package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

import java.util.List;

public class AuthenticatedCasUserByPerson {

	/** 关联个人 */
	private List<AtmPersonResultDTO> associatedPersons;

	public List<AtmPersonResultDTO> getAssociatedPersons() {
		return associatedPersons;
	}

	public void setAssociatedPersons(List<AtmPersonResultDTO> associatedPersons) {
		this.associatedPersons = associatedPersons;
	}

}
