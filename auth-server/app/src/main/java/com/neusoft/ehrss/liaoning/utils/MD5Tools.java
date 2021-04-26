package com.neusoft.ehrss.liaoning.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Tools {
	/**
	 * 获取MD5加密
	 * 
	 * @param pwd
	 *            需要加密的字符串
	 * @return String字符串 加密后的字符串
	 */
	public static String getPwd(String pwd, boolean isUpCase) {
		try {

			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] bs = new byte[0];
			if (StringUtils.isNotBlank(pwd)) {
				bs = digest.digest(pwd.getBytes());
			}

			String hexString = "";
			for (byte b : bs) {

				int temp = b & 255;
				if (temp < 16 && temp >= 0) {

					hexString = hexString + "0" + Integer.toHexString(temp);
				} else {
					hexString = hexString + Integer.toHexString(temp);
				}
			}
			if (isUpCase) {
				return hexString.toUpperCase();
			} else {
				return hexString.toLowerCase();
			}

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 利用MD5进行加密
	 * 
	 * @param inStr
	 *            待加密的字符串
	 * @return 加密后的字符串
	 * @throws NoSuchAlgorithmException
	 *             没有这种产生消息摘要的算法
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		byte[] byteArray = null;
		try {
			byteArray = inStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = md5Bytes[i] & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static void main(String[] args) {


	}
}
