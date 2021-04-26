package com.neusoft.ehrss.liaoning.security.implicit.alipay.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.ehrss.liaoning.provider.ecard.EcardService;
import com.neusoft.ehrss.liaoning.provider.ecard.response.EcardValidResponse;
import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto.AuthorizeCodeUser;
import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto.AuthorizeRequest;
import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto.AuthorizeResponse;
import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto.BizContent;
import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto.ResultInfo;
import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.enums.BusinessTypeEnum;
import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.redis.ClientDetailsRedisManager;
import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.utils.SignUtils;
import com.neusoft.sl.girder.utils.LongDateUtils;
import com.neusoft.sl.si.authserver.base.domains.person.Person;
import com.neusoft.sl.si.authserver.base.domains.person.PersonRepositoryExtend;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;

@RestController
@RequestMapping("/ws/authorize")
public class AuthorizeController {

	private static final Logger log = LoggerFactory.getLogger(AuthorizeController.class);

	@Autowired
	private EcardService ecardService;

	@Autowired
	private AuthorizeCodeInRedisManager authorizeCodeInRedis;

	@Autowired
	private ClientDetailsRedisManager clientDetailsRedisManager;

	@Autowired
	private PersonRepositoryExtend personRepositoryExtend;

	@Value("${saber.ecard.valid.time:true}")
	private boolean validTime = true;

	@PostMapping("/{type}")
	public AuthorizeResponse authorize(@RequestBody AuthorizeRequest dto, @PathVariable String type) {
		// long startTime = System.currentTimeMillis();
		AuthorizeResponse response = new AuthorizeResponse();

		ResultInfo resultInfo = new ResultInfo();

		UserDetails userDetails = clientDetailsRedisManager.load(dto.getClientId());
		try {
			if (StringUtils.isAnyBlank(dto.getClientId(), dto.getNoncestr(), dto.getSign()) || dto.getBizContent() == null) {
				throw new IllegalArgumentException("请求参数非法");
			}
			if (validTime) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = DateUtils.addMinutes(sdf.parse(dto.getNoncestr()), 10);
				if (date.before(new Date())) {
					throw new BusinessException("noncestr已失效");
				}
			}
			// 校验签名
			verifySign(dto, type, userDetails.getPassword());

			// 校验签名完成，校验电子社保卡
			log.debug("授权码code授权");
			BizContent bizContent = dto.getBizContent();
			// long p1 = System.currentTimeMillis();
			EcardValidResponse ecardValidResponse = getPersonInfo(bizContent, type);
			// log.error("==================================【sign={}
			// 调用电子社保卡耗时={}】==================================", dto.getSign(),
			// System.currentTimeMillis() - p1);
			// EcardValidResponse ecardValidResponse = new EcardValidResponse();
			// ecardValidResponse.setIdNumber("622600199504038530");
			// ecardValidResponse.setPersonNumber("62260019");
			// ecardValidResponse.setSignLevel("1");
			// ecardValidResponse.setSiType(true);
			// ecardValidResponse.setSscNo("222");

			// 生成code
			String code = genCode(bizContent, type);

			// 缓存数据
			AuthorizeCodeUser user = new AuthorizeCodeUser();
			user.setType(type);
			user.setIdNumber(ecardValidResponse.getIdNumber());
			user.setPersonNumber(ecardValidResponse.getPersonNumber());
			user.setSignLevel(ecardValidResponse.getSignLevel());
			user.setSiType(ecardValidResponse.isIsSiType());
			user.setSscNo(ecardValidResponse.getSscNo());
			user.setClientId(userDetails.getUsername());
			authorizeCodeInRedis.put(code, user);

			bizContent.setCode(code);
			bizContent.setBusiSeq(null);

			// 组装返回的业务信息
			response.setBizContent(bizContent);

			resultInfo.setMsg("处理成功！");
		} catch (Exception e) {
			log.error("处理失败", e);
			resultInfo.setErrorMsg(e.getMessage());
		}
		response.setResultInfo(resultInfo);
		response.setClientId(dto.getClientId());
		response.setNoncestr(LongDateUtils.toSecondString(new Date()));
		response.setSign(sign(response, type, userDetails.getPassword()));

		// long stopTime = System.currentTimeMillis();
		// log.error("==================================【sign={}
		// 共耗时={}】==================================", dto.getSign(), stopTime -
		// startTime);
		return response;
	}

	private EcardValidResponse getPersonInfo(BizContent bizContent, String type) {
		EcardValidResponse ecardValidResponse;
		switch (type) {
		case BusinessTypeEnum.BUSINESS_TYPE_P90000:
			Validate.notBlank(bizContent.getChannelNo(), "渠道号为空");
			Validate.notBlank(bizContent.getSignNo(), "签发号为空");
			ecardValidResponse = ecardService.valid(bizContent);
			return ecardValidResponse;
		case BusinessTypeEnum.BUSINESS_TYPE_P90001:
			Validate.notBlank(bizContent.getChannelNo(), "渠道号为空");
			Validate.notBlank(bizContent.getSignNo(), "签发号为空");
			ecardValidResponse = ecardService.valid(bizContent);
			return ecardValidResponse;
		case BusinessTypeEnum.BUSINESS_TYPE_P92001:
			Validate.notBlank(bizContent.getIdNumber(), "身份证号为空");
			Validate.notBlank(bizContent.getName(), "姓名为空");
			List<Person> list = personRepositoryExtend.findAllByCertificate(bizContent.getIdNumber(), bizContent.getName());
			if (null == list || list.size() == 0) {
				throw new BusinessException("根据身份证号和姓名未查询到参保记录，请核实您的信息");
			} else if (list.size() > 1) {
				throw new BusinessException("根据身份证号和姓名查询到多条参保记录，请您做合并后再试");
			}
			Person person = list.get(0);
			EcardValidResponse response = new EcardValidResponse();
			response.setIdNumber(person.getCertificate().getNumber());
			response.setName(person.getName());
			response.setPersonNumber(person.getPersonNumberString());
			return response;
		default:
			throw new BusinessException("信息校验方式暂未实现type=" + type);
		}
	}

	public String sign(AuthorizeResponse response, String type, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("clientId", response.getClientId());
		map.put("key", password);
		map.put("noncestr", response.getNoncestr());

		switch (type) {
		case BusinessTypeEnum.BUSINESS_TYPE_P90001:
			if (response.getResultInfo().result()) {
				BizContent biz = response.getBizContent();
				map.put("out_channel", biz.getChannelNo());
				map.put("out_sign_no", biz.getSignNo());
				map.put("out_busi_seq", biz.getBusiSeq());
				map.put("code", biz.getCode());
			}
			break;
		case BusinessTypeEnum.BUSINESS_TYPE_P92001:
			if (response.getResultInfo().result()) {
				BizContent biz = response.getBizContent();
				map.put("idNumber", biz.getIdNumber());
				map.put("name", biz.getName());
				map.put("code", biz.getCode());
			}
			break;
		default:
			break;
		}
		return SignUtils.sign(map);
	}

	private void verifySign(AuthorizeRequest dto, String type, String password) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, String> map = assembleMap(dto, type, password);
		boolean rs = SignUtils.verify(map, dto.getSign());
		if (!rs) {
			throw new BusinessException("签名验证失败");
		}
	}

	private Map<String, String> assembleMap(AuthorizeRequest dto, String type, String password) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("clientId", dto.getClientId());
		map.put("key", password);
		map.put("noncestr", dto.getNoncestr());

		BizContent biz = dto.getBizContent();

		switch (type) {
		case BusinessTypeEnum.BUSINESS_TYPE_P90000:
			break;
		case BusinessTypeEnum.BUSINESS_TYPE_P90001:
			map.put("out_channel", biz.getChannelNo());
			map.put("out_sign_no", biz.getSignNo());
			map.put("out_busi_seq", biz.getBusiSeq());
			break;
		case BusinessTypeEnum.BUSINESS_TYPE_P92001:
			map.put("idNumber", biz.getIdNumber());
			map.put("name", biz.getName());
			break;
		default:
			throw new BusinessException("暂未实现");
		}
		return map;
	}

	private String genCode(BizContent bizContent, String type) {
		Map<String, String> random = new LinkedHashMap<>();
		switch (type) {
		case BusinessTypeEnum.BUSINESS_TYPE_P90000:
			random.put("signNo", bizContent.getSignNo());
			random.put("channelNo", bizContent.getChannelNo());
			break;
		case BusinessTypeEnum.BUSINESS_TYPE_P90001:
			random.put("signNo", bizContent.getSignNo());
			random.put("channelNo", bizContent.getChannelNo());
			break;
		case BusinessTypeEnum.BUSINESS_TYPE_P92001:
			random.put("idNumber", bizContent.getIdNumber());
			random.put("name", bizContent.getName());
			break;
		default:
			break;
		}
		String code = defaultGenRandrom(random);
		return code;
	}

	private String defaultGenRandrom(Map<String, String> map) {
		map.put("random", RandomStringUtils.randomAlphanumeric(32));
		return DigestUtils.md5Hex(map.toString());
	}

}
