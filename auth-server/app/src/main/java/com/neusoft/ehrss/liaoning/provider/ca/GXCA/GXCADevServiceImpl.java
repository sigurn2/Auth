package com.neusoft.ehrss.liaoning.provider.ca.GXCA;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.neusoft.ehrss.liaoning.provider.ca.GXCA.dev.config.GXCADevWSClient;
import com.neusoft.ehrss.liaoning.provider.ca.GXCA.dev.ws.ValidateCertResponse;
import com.neusoft.ehrss.liaoning.provider.ca.GXCA.dev.ws.VerifySignedDataResponse;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;

@Profile({ "dev" })
@Service
public class GXCADevServiceImpl implements GXCAGatewayService {

	private static final Logger log = LoggerFactory.getLogger(GXCADevServiceImpl.class);

	@Value("${saber.gxca.appName}")
	private String appName;

	@Resource
	private GXCADevWSClient gxcaDevWSClient;

	/**
	 * 验证数据签名
	 * 
	 * @param base64EncodeCert
	 * @param inData
	 * @param signValue
	 * @return
	 */
	@Override
	public boolean verifySignedData(String base64EncodeCert, String inData, String signValue) {
		log.debug("GXCA验证数据签名inData={}", inData);
		VerifySignedDataResponse verifySignedDataResponse = gxcaDevWSClient.verifySignedData(appName, base64EncodeCert, inData, signValue);
		if (verifySignedDataResponse == null)
			throw new BusinessException("数据验签失败");
		log.debug("GXCA验证数据签名verifySignedDataResult={}", verifySignedDataResponse.isVerifySignedDataResult());
		if (verifySignedDataResponse.isVerifySignedDataResult()) {
			return true;
		}
		throw new BusinessException("数据验签失败");
	}

	/**
	 * 验证证书
	 * 
	 * @param base64EncodeCert
	 * @return
	 */
	@Override
	public boolean validateCert(String base64EncodeCert) {
		log.debug("GXCA验证证书");
		ValidateCertResponse validateCertResponse = gxcaDevWSClient.validateCert(appName, base64EncodeCert);
		if (validateCertResponse == null)
			throw new BusinessException("证书验证失败");
		log.debug("GXCA验证证书validateCert={}", validateCertResponse.getValidateCert());
		int result = validateCertResponse.getValidateCert();
		switch (result) {
		case 1:
			return true;
		case -1:
			throw new BusinessException("验证证书失败，证书不是所信任的根");
		case -2:
			throw new BusinessException("验证证书失败，证书超过有效期");
		case -3:
			throw new BusinessException("验证证书失败，证书为作废证书");
		case -4:
			throw new BusinessException("验证证书失败，证书已加入黑名单");
		case -5:
			throw new BusinessException("验证证书失败，证书未生效");
		case 0:
			throw new BusinessException("验证证书失败，未知错误");
		default:
			throw new BusinessException("验证证书失败");
		}
	}

}
