package com.neusoft.ehrss.liaoning.provider.ca.GXCA.product.config;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.neusoft.ehrss.liaoning.provider.ca.GXCA.product.ws.ObjectFactory;
import com.neusoft.ehrss.liaoning.provider.ca.GXCA.product.ws.ValidateCert;
import com.neusoft.ehrss.liaoning.provider.ca.GXCA.product.ws.ValidateCertResponse;
import com.neusoft.ehrss.liaoning.provider.ca.GXCA.product.ws.VerifySignedData2;
import com.neusoft.ehrss.liaoning.provider.ca.GXCA.product.ws.VerifySignedData2Response;

public class GXCAProductWSClient extends WebServiceGatewaySupport {

	/**
	 * 验证数据签名
	 * 
	 * @param appName
	 * @param base64EncodeCert
	 * @param inData
	 * @param signValue
	 * @return
	 */
	public VerifySignedData2Response verifySignedData(String appName, String base64EncodeCert, String inData, String signValue) {
		ObjectFactory ob = new ObjectFactory();
		VerifySignedData2 verifySignedData = ob.createVerifySignedData2();
		verifySignedData.setAppName(appName);//
		verifySignedData.setBase64EncodeCert(base64EncodeCert);// 证书
		verifySignedData.setInData(inData);// 待验证的原文
		verifySignedData.setSignValue(signValue);// 签名值
		return (VerifySignedData2Response) getWebServiceTemplate().marshalSendAndReceive(verifySignedData);
	}

	/**
	 * 验证证书
	 * 
	 * @param appName
	 * @param base64EncodeCert
	 * @return
	 */
	public ValidateCertResponse validateCert(String appName, String base64EncodeCert) {
		ObjectFactory ob = new ObjectFactory();
		ValidateCert validateCert = ob.createValidateCert();
		validateCert.setAppName(appName);//
		validateCert.setBase64EncodeCert(base64EncodeCert);// 证书
		return (ValidateCertResponse) getWebServiceTemplate().marshalSendAndReceive(validateCert);
	}

}
