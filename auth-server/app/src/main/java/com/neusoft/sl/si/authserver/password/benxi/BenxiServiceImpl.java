package com.neusoft.sl.si.authserver.password.benxi;

import com.neusoft.sl.si.authserver.password.CheckPwdService;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @program: liaoning-auth
 * @description: 哈尔滨密码serviceImpl
 * @author: lgy
 * @create: 2020-06-19 18:23
 **/
@Service(value = "benxiCheckPwdService")
public class BenxiServiceImpl implements CheckPwdService {


    @Override
    public String encrypt(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.reset();
        byte[] bytes = password.getBytes();
        byte[] out = messageDigest.digest(bytes);
        BASE64Encoder enc = new BASE64Encoder();
        return enc.encode(out);
    }


    /**
     * 使用指定算法加密指定字符串
     * @param message 待加密的字符串
     * @param algorithmName 加密算法
     * @return 加密后的字符串
     */
    public static String benxiEncrypt(String message, String algorithmName){
        MessageDigest messageDigest = null;
        try {
            if(algorithmName == null) algorithmName = "MD5";
            messageDigest = MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.reset();
        byte[] bytes = message.getBytes();
        byte[] out = messageDigest.digest(bytes);
        BASE64Encoder enc = new BASE64Encoder();
        return enc.encode(out);
    }


    public static void main(String[] args) {
        System.out.println(benxiEncrypt("123456","MD5"));
    }

}
