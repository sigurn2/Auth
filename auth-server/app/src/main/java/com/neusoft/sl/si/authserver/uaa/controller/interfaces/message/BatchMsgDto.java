package com.neusoft.sl.si.authserver.uaa.controller.interfaces.message;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.neusoft.sl.si.authserver.base.domains.user.enums.UserType;
import com.neusoft.sl.si.authserver.uaa.log.enums.BusinessType;
import com.neusoft.sl.si.authserver.uaa.log.enums.ClientType;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;

/*
 * 批量发送消息类
 */
public class BatchMsgDto implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	private List<MsgDto> msglst;
	// 消息类型
	private MsgType msgType;
	// 用户类型
	private UserType userType;
	// 消息业务类型
	private BusinessType businessType;
	// 接入渠道类型
	private ClientType clientType;
	// 系统类别
	private SystemType systemType;

	public BatchMsgDto() {
		super();
	}

	public BatchMsgDto(List<MsgDto> msglst, MsgType msgType, UserType userType, BusinessType businessType, ClientType clientType, SystemType systemType) {
		super();
		this.msglst = msglst;
		this.msgType = msgType;
		this.userType = userType;
		this.businessType = businessType;
		this.clientType = clientType;
		this.systemType = systemType;
	}

	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	public List<MsgDto> getMsglst() {
		return msglst;
	}

	public void setMsglst(List<MsgDto> msglst) {
		this.msglst = msglst;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public SystemType getSystemType() {
		return systemType;
	}

	public void setSystemType(SystemType systemType) {
		this.systemType = systemType;
	}

}
