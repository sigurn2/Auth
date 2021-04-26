package com.neusoft.ehrss.liaoning.provider.ca.GXCA;

public interface GXCAGatewayService {

	/**
	 * 验证数据签名
	 * 
	 * @param base64EncodeCert
	 * @param inData
	 * @param signValue
	 * @return
	 */
	boolean verifySignedData(String base64EncodeCert, String inData, String signValue);

	/**
	 * 验证证书
	 * 
	 * @param base64EncodeCert
	 * @return
	 */
	boolean validateCert(String base64EncodeCert);

}