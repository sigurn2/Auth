package com.neusoft.sl.si.authserver.uaa.service.msg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.neusoft.ehrss.liaoning.security.password.idnumbername.RedisService;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.message.ForgetMsgDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bingoohuang.patchca.word.RandomWordFactory;
import com.github.bingoohuang.patchca.word.WordBean;
import com.neusoft.sl.girder.utils.JsonMapper;
import com.neusoft.sl.girder.utils.JsonMapperException;
import com.neusoft.sl.si.authserver.base.domains.user.enums.UserType;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.message.BatchMsgDto;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.message.MsgDto;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.message.MsgType;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.SmsCaptchaDTO;
import com.neusoft.sl.si.authserver.uaa.exception.MessageException;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.domain.sms.SmCaptcha;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.domain.sms.SmCaptchaRepository;
import com.neusoft.sl.si.authserver.uaa.log.enums.BusinessType;
import com.neusoft.sl.si.authserver.uaa.log.enums.ClientType;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;

/**
 * @author y_zhang.neu
 *
 */
@Service
public class
MsgServiceImpl implements MsgService {
	/** ?????? */
	private static final Logger log = LoggerFactory.getLogger(MsgServiceImpl.class);

	private RandomWordFactory wordFactory;

	private static final String MESSAGE_PREFIX = "????????????";
	private static final String MESSAGE_SUFFIX = "???????????????1??????????????????????????????????????????";

	@Value("${pile.message.address}")
	private String address = "http://162.17.51.1/piles/message";

	private static final String SEND_URL = "/v1/sms";

	/** ??????????????? */
	@Autowired
	private SmCaptchaRepository smCaptchaRepository;
	@Autowired
	private RedisService redisService;
	@Autowired
	private SmCaptchaSendManager smCaptchaSendManager;

	@PostConstruct
	protected void init() {
		wordFactory = new RandomWordFactory();
		// ?????????????????????,???????????????????????????????????????,???o???0???
		wordFactory.setCharacters("1234567890");
		wordFactory.setMaxLength(6);
		wordFactory.setMinLength(6);
	}

	@Override
	public SmCaptcha nextCaptchaPersonSms(String mobilenumber, String webacc, BusinessType businessType, ClientType clientType, String channel, String sender) {
		return nextCaptcha(mobilenumber, webacc, MsgType.Sms, SystemType.Person, UserType.PERSON, businessType, clientType, channel, sender);
	}

	@Override
	public SmCaptcha nextCaptchaSms(String mobilenumber, String webacc, SystemType systemType, UserType userType, BusinessType businessType, ClientType clientType, String channel, String sender) {
		return nextCaptcha(mobilenumber, webacc, MsgType.Sms, systemType, userType, businessType, clientType, channel, sender);
	}

	@Override
	public SmCaptcha nextCaptcha(String mobilenumber, String webacc, MsgType msgType, SystemType systemType, UserType userType, BusinessType businessType, ClientType clientType, String channel, String sender) {

		smCaptchaRepository.remove("BX_SMS_"+mobilenumber);

		WordBean wordBean = wordFactory.getNextWord();
		// ???????????????
		SmCaptcha smCaptcha = new SmCaptcha(mobilenumber, wordBean.getWord());
		// ???????????????
		String message = MESSAGE_PREFIX + smCaptcha.getWord() + MESSAGE_SUFFIX;

		ForgetMsgDTO dto = new ForgetMsgDTO();
		dto.setAppNumber("benxi-si");
		dto.setContent(message);
		dto.setMobile(mobilenumber);

		// ??????????????????????????????
		smCaptchaSendManager.verifySendCount(mobilenumber);
		this.sendMsg(dto);
		smCaptchaSendManager.updateSendCount(mobilenumber);
		// ???????????????
		redisService.set("BX_SMS_"+mobilenumber,smCaptcha,1, TimeUnit.MINUTES);

		log.debug("??????????????????,??????????????? mobilenumber={},??????????????? ={}",mobilenumber,smCaptcha.getWord());

	//	smCaptcha = smCaptchaRepository.save(smCaptcha);

		return smCaptcha;
	}
	
	@Override
	public SmsCaptchaDTO nextCaptchaNoSave(String mobilenumber, String webacc, MsgType msgType, SystemType systemType, UserType userType, BusinessType businessType, ClientType clientType, String channel, String sender) {
		WordBean wordBean = wordFactory.getNextWord();
		// ???????????????
		SmCaptcha smCaptcha = new SmCaptcha(mobilenumber, wordBean.getWord());
		// ???????????????
		String message = MESSAGE_PREFIX + smCaptcha.getWord() + MESSAGE_SUFFIX;
		ForgetMsgDTO dto = new ForgetMsgDTO();
		dto.setAppNumber("benxi-si");
		dto.setContent(message);
		dto.setMobile(mobilenumber);
		// ??????????????????????????????
		this.sendMsg(dto);
		return new SmsCaptchaDTO(mobilenumber, smCaptcha.getWord());
	}

	@Override
	public boolean validateCaptcha(String mobile, String verifycode) {

		String captcha = (String) redisService.get("BX_SMS_"+mobile);

		if (null == captcha) {
			return false;
		} else {
			log.debug("==?????????captchaWord???{}==", captcha);
			if (captcha.equals(verifycode)) {
				// ???????????????????????????
				smCaptchaRepository.remove("BX_SMS_"+mobile);
				return true;
			}
		}
		return false;
	}


	public void sendMsg(ForgetMsgDTO dto) {
		log.debug("??????????????????????????????");

		// ???????????????httpClient??????.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// ??????httppost
		HttpPost httppost = new HttpPost(address + SEND_URL);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(5000).setSocketTimeout(10000).build();
		httppost.setConfig(requestConfig);
		StringEntity entity = new StringEntity(JsonMapper.toJson(dto), "UTF-8");
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httppost.setEntity(entity);
		try {
			CloseableHttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			// http?????????
			String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
			if (!statusCode.startsWith("2")) {
				String rs = EntityUtils.toString(resEntity, "UTF-8");
				log.debug("?????????????????????????????????={}", rs);
				throw new MessageException(getErrMsg(rs));
			}
			response.close();
			log.debug("??????????????????");
		} catch (ClientProtocolException e) {
			log.debug("??????????????????", e);
			throw new MessageException(e.getMessage());
		} catch (IOException e) {
			log.debug("??????????????????", e);
			throw new MessageException(e.getMessage());
		} finally {
			// ????????????,????????????
			try {
				httpclient.close();
			} catch (IOException e) {
				throw new MessageException(e.getMessage());
			}
		}
	}

	private String getErrMsg(String errmsg) {
		try {
			if (StringUtils.isNotBlank(errmsg)) {
				JsonNode jsonNode = fromJsonNode(errmsg);
				if (jsonNode.hasNonNull("error")) {
					return jsonNode.get("error").asText();
				}
				if (jsonNode.hasNonNull("message")) {
					return jsonNode.get("message").asText();
				}
				if (jsonNode.hasNonNull("detail")) {
					return jsonNode.get("detail").asText();
				}
			}
		} catch (Exception e) {
			return errmsg;
		}
		return errmsg;
	}

	private JsonNode fromJsonNode(String content) {
		if (null == content) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readTree(content);
		} catch (IOException e) {
			throw new JsonMapperException("???json????????????JsonNode??????", e);
		}
	}

}
