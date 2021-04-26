package com.neusoft.ehrss.liaoning.processor.thirdparty;

import org.apache.commons.lang3.Validate;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPQueryPersonSiInfoDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPQueryPersonSiInfoQueryDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.request.TPQueryPersonSiInfoRequest;
import com.neusoft.ehrss.liaoning.processor.thirdparty.response.TPQueryPersonSiInfoResponse;
import com.neusoft.sl.si.yardman.server.processor.AbstractBusinessProcessor;
import com.neusoft.sl.si.yardman.server.processor.BusinessProcessor;

/**
 * 查询用户信息
 * 
 * @author mojf
 *
 */
//@Service(value = "tpqueryPersonSiInfoProcess")
public class TPQueryPersonSiInfoProcess extends AbstractBusinessProcessor<TPQueryPersonSiInfoRequest, TPQueryPersonSiInfoResponse>
		implements BusinessProcessor<TPQueryPersonSiInfoRequest, TPQueryPersonSiInfoResponse> {

//	@Resource
//	private SocialSecurityCardsService socialSecurityCardsService;

	@Override
	public boolean isTransaction() {
		return false;
	}

	@Override
	protected TPQueryPersonSiInfoResponse processBusiness(TPQueryPersonSiInfoRequest request) {
		TPQueryPersonSiInfoQueryDTO rdto = request.getTpQueryPersonSiInfoQueryDTO();
		String idNumber = rdto.getIdNumber();
		String name = rdto.getName();

		LOGGER.debug("查询社保信息DTO={}", rdto);
		TPQueryPersonSiInfoDTO dto = new TPQueryPersonSiInfoDTO();
		try {
			Validate.notBlank(name, "请输入姓名");
			Validate.notBlank(idNumber, "请输入证件号码");
			dto.setIdNumber(idNumber);
			dto.setName(name);
			dto.setCardNumber("");
//			String cardNum = socialSecurityCardsService.findByNameAndIdNumber(name, idNumber);
			// 如果这个人有社保卡号
//			if (null != cardNum && cardNum.length() > 0) {
//				dto.setCardNumber(cardNum);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return new TPQueryPersonSiInfoResponse(request, e.getMessage());
		}
		TPQueryPersonSiInfoResponse result = new TPQueryPersonSiInfoResponse(request, dto);
		return result;
	}
}
