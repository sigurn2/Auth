package com.neusoft.sl.si.authserver.uaa.controller.interfaces.message;

/**
 * 消息类型
 * 
 * @author y_zhang.neu
 *
 */
public enum MsgType {
    Default, // 默认，无匹配使用
    Email, // 邮件
    Webpage, // 网页
    Sms, // 短信
    Push // 推送
}
