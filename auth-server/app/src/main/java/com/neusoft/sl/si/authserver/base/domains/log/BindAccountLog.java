package com.neusoft.sl.si.authserver.base.domains.log;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: liaoning-auth
 * @description: 绑定统一信用代码日志
 * @author: lgy
 * @create: 2020-03-09 09:24
 **/
@Entity
@Table(
        name = "LOG_USER_BIND"
)
public class BindAccountLog implements Serializable {
    private static final long serialVersionUID = 6782485246124253456L;
    private String id;
    @Column(
            name = "BINDIP"
    )
    private String bindIp;
    @Column(
            name = "BINDTIME"
    )
    private String bindTime;
    private String unbindTime;
    private String successFlag;
    private String failReason;
    private String unbindReason;
    private String systemType;
    private String userName;
    private String userAccount;
    private String clientType;

    public BindAccountLog() {
    }

    public BindAccountLog(String id) {
        this.id = id;
    }

    public BindAccountLog(String id, String bindIp, String bindTime, String unbindTime, String successFlag, String failReason, String unbindReason, String systemType, String userName, String userAccount, String clientType) {
        this.id = id;
        this.bindIp = bindIp;
        this.bindTime = bindTime;
        this.unbindTime = unbindTime;
        this.successFlag = successFlag;
        this.failReason = failReason;
        this.unbindReason = unbindReason;
        this.systemType = systemType;
        this.userName = userName;
        this.userAccount = userAccount;
        this.clientType = clientType;
    }

    @Id
    @GeneratedValue(
            generator = "system-uuid"
    )
    @GenericGenerator(
            name = "system-uuid",
            strategy = "uuid"
    )
    @Column(
            name = "ID",
            unique = true,
            nullable = false,
            length = 32
    )
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(
            name = "BIND_IP",
            length = 39
    )
    public String getBindIp() {
        return this.bindIp;
    }

    public void setBindIp(String bindIp) {
        this.bindIp = bindIp;
    }

    @Column(
            name = "BIND_TIME",
            precision = 20,
            scale = 0
    )
    public String getBindTime() {
        return this.bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    @Column(
            name = "UNBIND_TIME",
            precision = 20,
            scale = 0
    )
    public String getUnbindTime() {
        return this.unbindTime;
    }

    public void setUnbindTime(String unbindTime) {
        this.unbindTime = unbindTime;
    }

    @Column(
            name = "SUCCESS_FLAG",
            length = 1
    )
    public String getSuccessFlag() {
        return this.successFlag;
    }

    public void setSuccessFlag(String successFlag) {
        this.successFlag = successFlag;
    }

    @Column(
            name = "FAIL_REASON",
            length = 1000
    )
    public String getFailReason() {
        return this.failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    @Column(
            name = "LOGOFF_REASON",
            length = 200
    )
    public String getunbindReason() {
        return this.unbindReason;
    }

    public void setunbindReason(String unbindReason) {
        this.unbindReason = unbindReason;
    }

    @Column(
            name = "SYSTEM_TYPE",
            length = 255
    )
    public String getSystemType() {
        return this.systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    @Column(
            name = "USER_NAME",
            length = 100
    )
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(
            name = "USER_ACCOUNT",
            length = 32
    )
    public String getUserAccount() {
        return this.userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    @Column(
            name = "CLIENT_TYPE",
            length = 255
    )
    public String getClientType() {
        return this.clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}

