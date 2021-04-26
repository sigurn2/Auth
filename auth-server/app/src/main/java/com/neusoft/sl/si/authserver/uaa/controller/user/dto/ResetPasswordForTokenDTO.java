package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

public class ResetPasswordForTokenDTO extends ResetPasswordTokenDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String newPassword;

	private String idNmuber;

	private String name;

	public String getIdNmuber() {
		return idNmuber;
	}

	public void setIdNmuber(String idNmuber) {
		this.idNmuber = idNmuber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public ResetPasswordForTokenDTO(String token, String newPassword, String idNmuber, String name) {
		super(token);
		this.newPassword = newPassword;
		this.idNmuber = idNmuber;
		this.name = name;
	}

}
