package com.neusoft.ehrss.liaoning.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

public class AesUtil {
	private final static String ENCODING = "UTF-8";
	private static final String PASSWORD = "wx_password_wechat";

	/**
	 * AES加密-十六进制
	 * 
	 * @param content
	 * @param password
	 * @return String
	 * 
	 */
	public static String aesEncryptHex(String content) throws Exception {
		byte[] encryptResult = encrypt(content);
		return Hex.encodeHexString(encryptResult);
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String aesEncrypt(String content) throws Exception {
		byte[] encryptResult = encrypt(content);
		return Base64.encodeBase64String(encryptResult);
	}

	/**
	 * AES解密-十六进制
	 * 
	 * @param encryptResultStr
	 * @return String
	 * 
	 */
	public static String aesDecryptHex(String encryptResultStr) throws Exception {
		byte[] decryptResult = decrypt(Hex.decodeHex(encryptResultStr.toCharArray()));
		return StringUtils.newStringUtf8(decryptResult);
	}

	/**
	 * AES解密
	 * 
	 * @param encryptResultStr
	 * @return
	 * @throws Exception
	 */
	public static String aesDecrypt(String encryptResultStr) throws Exception {
		byte[] decryptResult = decrypt(Base64.decodeBase64(encryptResultStr));
		return StringUtils.newStringUtf8(decryptResult);
	}

	/**
	 * 加密
	 * 
	 * @param content
	 * @param password
	 * @return byte[]
	 * 
	 */
	private static byte[] encrypt(String content) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// 防止linux下 随机生成key
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(PASSWORD.getBytes());
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 创建密码器
		byte[] byteContent = content.getBytes(ENCODING);
		cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(byteContent);
		return result; // 加密
	}

	/**
	 * 解密
	 * 
	 * @param content
	 * @param password
	 * @return byte[]
	 * 
	 */
	private static byte[] decrypt(byte[] content) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// 防止linux下 随机生成key
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(PASSWORD.getBytes());
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 创建密码器
		cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(content);
		return result; // 加密
	}

}
