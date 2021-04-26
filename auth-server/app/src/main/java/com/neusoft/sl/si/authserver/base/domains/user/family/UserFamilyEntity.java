package com.neusoft.sl.si.authserver.base.domains.user.family;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_USER_FAMILY")
public class UserFamilyEntity {

	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "FAMILY_ID")
	private String familyId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

}
