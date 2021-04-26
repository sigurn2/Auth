package com.neusoft.sl.si.authserver.uaa.service.msg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

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
public class MsgServiceImpl implements MsgService {
	/** 日志 */
	private static final Logger log = LoggerFactory.getLogger(MsgServiceImpl.class);

	private RandomWordFactory wordFactory;

	private static final String MESSAGE_PREFIX = "验证码：";
	private static final String MESSAGE_SUFFIX = "，此验证码5分钟内有效，请尽快完成验证。";

	@Value("${pile.message.address}")
	private String address = "http://162.17.51.1/pile/message";

	private static final String SEND_URL = "/send";

	/** 验证码仓储 */
	@Autowired
	private SmCaptchaRepository smCaptchaRepository;
	
	@Autowired
	private SmCaptchaSendManager smCaptchaSendManager;

	@PostConstruct
	protected void init() {
		wordFactory = new RandomWordFactory();
		// 随机字符生成器,去除掉容易混淆的字母和数字,如o和0等
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
		WordBean wordBean = wordFactory.getNextWord();
		// 持久化对象
		SmCaptcha smCaptcha = new SmCaptcha(mobilenumber, wordBean.getWord());
		// 发送验证码
		String message = MESSAGE_PREFIX + smCaptcha.getWord() + MESSAGE_SUFFIX;
		BatchMsgDto dto = new BatchMsgDto();
		dto.setBusinessType(businessType);
		dto.setClientType(clientType);
		dto.setMsgType(msgType);
		dto.setSystemType(systemType);
		dto.setUserType(userType);
		List<MsgDto> msglst = new ArrayList<MsgDto>();
		MsgDto msg = new MsgDto();
		msg.setContent(message);
		msg.setMobile(mobilenumber);
		msg.setWebacc(webacc);
		// 渠道编号
		msg.setChannel(channel);
		// 用户编号
		msg.setSender(sender);
		msglst.add(msg);
		dto.setMsglst(msglst);
		// 调用消息平台发送消息
		smCaptchaSendManager.verifySendCount(mobilenumber);
		this.sendMsg(dto);
		smCaptchaSendManager.updateSendCount(mobilenumber);
		// 存储验证码
		smCaptcha = smCaptchaRepository.save(smCaptcha);
		return smCaptcha;
	}
	
	@Override
	public SmsCaptchaDTO nextCaptchaNoSave(String mobilenumber, String webacc, MsgType msgType, SystemType systemType, UserType userType, BusinessType businessType, ClientType clientType, String channel, String sender) {
		WordBean wordBean = wordFactory.getNextWord();
		// 持久化对象
		SmCaptcha smCaptcha = new SmCaptcha(mobilenumber, wordBean.getWord());
		// 发送验证码
		String message = MESSAGE_PREFIX + smCaptcha.getWord() + MESSAGE_SUFFIX;
		BatchMsgDto dto = new BatchMsgDto();
		dto.setBusinessType(businessType);
		dto.setClientType(clientType);
		dto.setMsgType(msgType);
		dto.setSystemType(systemType);
		dto.setUserType(userType);
		List<MsgDto> msglst = new ArrayList<MsgDto>();
		MsgDto msg = new MsgDto();
		msg.setContent(message);
		msg.setMobile(mobilenumber);
		msg.setWebacc(webacc);
		// 渠道编号
		msg.setChannel(channel);
		// 用户编号
		msg.setSender(sender);
		msglst.add(msg);
		dto.setMsglst(msglst);
		// 调用消息平台发送消息
		this.sendMsg(dto);
		return new SmsCaptchaDTO(mobilenumber, smCaptcha.getWord());
	}

	@Override
	public boolean validateCaptcha(String mobile, String verifycode) {
		SmCaptcha captcha = smCaptchaRepository.getCaptcha(mobile);
		if (null == captcha) {
			return false;
		} else {
			log.debug("==存储的captchaWord为{}==", captcha.getWord());
			if (captcha.verify(verifycode)) {
				// 从仓储移除此验证码
				smCaptchaRepository.remove(mobile);
				return true;
			}
		}
		return false;
	}

	@Override
	public void sendMsg(BatchMsgDto dto) {
		log.debug("调用消息平台发送短信");
		if (log.isDebugEnabled()) {
			List<MsgDto> msgDtos = dto.getMsglst();
			for (MsgDto msgDto : msgDtos) {
				log.debug("{}|{}", msgDto.getContent(), msgDto.getMobile());
			}
		}
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
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
			// http状态码
			String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
			if (!statusCode.startsWith("2")) {
				String rs = EntityUtils.toString(resEntity, "UTF-8");
				log.debug("请求成功，短信发送失败={}", rs);
				throw new MessageException(getErrMsg(rs));
			}
			response.close();
			log.debug("短信发送成功");
		} catch (ClientProtocolException e) {
			log.debug("短信发送失败", e);
			throw new MessageException(e.getMessage());
		} catch (IOException e) {
			log.debug("短信发送失败", e);
			throw new MessageException(e.getMessage());
		} finally {
			// 关闭连接,释放资源
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
			throw new JsonMapperException("将json串转换为JsonNode错误", e);
		}
	}

}
