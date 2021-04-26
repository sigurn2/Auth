package com.neusoft.sl.si.authserver.uaa.userdetails;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * 用户详细信息
 * 
 * @author mojf
 *
 */
public class SaberUserDetails implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4364679387806863445L;

    /** 账户名 */
    private String account;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 证件号码
     */
    private String idNumber;

    /** 是否可用 */
    private boolean activated = false;

    /** 是否通过实名认证 */
    private boolean realNameAuthed = false;

    public SaberUserDetails(String account, String name, String password) {
        this.account = account;
        this.name = name;
        this.password = password;
        // 激活用户
        this.activated = true;
        // 没有进行实名认证
        this.realNameAuthed = false;
    }

    /**
     * 判断登录方式：PC 手机 一体机 等等
     */
    private String clientType;
    /**
     * 网厅类型
     */
    private String systemType;
    
    /**
     * 日志id，主要用户登出时设置登出时间及更新退出原因
     */
    private String logonlogid;

    /**
     * @return the clientType
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * @param clientType the clientType to set
     */
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    /**
     * @return the systemType
     */
    public String getSystemType() {
        return systemType;
    }

    /**
     * @param systemType the systemType to set
     */
    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the activated
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * @param activated the activated to set
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * @return the realNameAuthed
     */
    public boolean isRealNameAuthed() {
        return realNameAuthed;
    }

    /**
     * @param realNameAuthed the realNameAuthed to set
     */
    public void setRealNameAuthed(boolean realNameAuthed) {
        this.realNameAuthed = realNameAuthed;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the idNumber
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * @param idNumber the idNumber to set
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

	public String getLogonlogid() {
		return logonlogid;
	}

	public void setLogonlogid(String logonlogid) {
		this.logonlogid = logonlogid;
	}

}
