package com.neusoft.ehrss.liaoning.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class DesHelp {

	/**
	 * 加密
	 *
	 * @param datasource
	 * @return
	 */
	public static String desCrypto(String datasource, String key) {
		return Base64.encodeBase64String(desCrypto(datasource.getBytes(StandardCharsets.UTF_8), key));
	}

	/**
	 * 加密
	 *
	 * @param datasource
	 * @param password
	 * @return
	 */
	private static byte[] desCrypto(byte[] datasource, String password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes(StandardCharsets.UTF_8));
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 *
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String src, String key) throws Exception {
		return StringUtils.newStringUtf8(decrypt(Base64.decodeBase64(src), key));
	}

	/**
	 * 解密
	 *
	 * @param src
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] src, String password) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return cipher.doFinal(src);
	}

	public static void main(String[] args) {
		String username ="KqqD8CgIiFIT2j2ZOMfRdh+kc3viUJX4";

		String username2 = null;
		try {
			username2 = DesHelp.decrypt(username,"CNAizCDnDg97VCZ519NE29vB");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(username2);
		String id="110101198001010117";
		String id2 = DesHelp.desCrypto(id,"CNAizCDnDg97VCZ519NE29vB");
		System.out.println(id2);
	}

}
