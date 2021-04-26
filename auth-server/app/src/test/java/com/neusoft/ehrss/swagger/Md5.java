package com.neusoft.ehrss.swagger;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class Md5 {
	
	public static void main(String[] args) throws DecoderException {
		
		
		
		String str = "4QrcOUm6Wau+VuBX8g+IPg==";
		System.out.println(str+"转换为"+Hex.encodeHexString(Base64.decodeBase64(str)));
		
		
		String data = "e10adc3949ba59abbe56e057f20f883e";
		System.out.println(data+"转换为"+Base64.encodeBase64String(Hex.decodeHex(data.toCharArray())));
		
		
		byte[] b = DigestUtils.md5(data);
		System.out.println(b);
	}

}
