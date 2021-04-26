package com.neusoft.ehrss.liaoning.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @program: auth-server
 * @description: wechat 医保公众平台加密算法
 * @author: lgy
 * @create: 2019-08-13 10:26
 **/

public class AESUtil {
    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";//默认的加密算法

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param password 加密密码
     * @param iv 使用CBC模式，需要一个向量iv，可增加加密算法的强度
     * @return 加密数据
     */
    public static byte[] encrypt(String content, String password,String iv) {
        try {
            //创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //密码key(超过16字节即128bit的key，需要替换jre中的local_policy.jar和US_export_policy.jar，否则报错：Illegal key size)
            SecretKeySpec keySpec = new SecretKeySpec(password.getBytes("utf-8"),KEY_ALGORITHM);

            //向量iv
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("utf-8"));

            //初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivParameterSpec);

            //加密
            byte[] byteContent = content.getBytes("utf-8");
            byte[] result = cipher.doFinal(byteContent);

            return result;
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }

        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content 密文
     * @param password 密码
     * @param iv 使用CBC模式，需要一个向量iv，可增加加密算法的强度
     * @return 明文
     */
    public static String decrypt(String content, String password,String iv) {

        try {
            //创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            byte[] encrypt = new BASE64Decoder().decodeBuffer(content);
            //密码key
            SecretKeySpec keySpec = new SecretKeySpec(password.getBytes("utf-8"),KEY_ALGORITHM);

            //向量iv
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("utf-8"));

            //初始化为解密模式的密码器
            cipher.init(Cipher.DECRYPT_MODE,keySpec,ivParameterSpec);

            //执行操作
            byte[] result = cipher.doFinal(encrypt);

            return new String(result,"utf-8");
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }

        return null;
    }



}
