package com.neusoft.ehrss.liaoning.processor.thirdparty;

import javax.annotation.Resource;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPQueryPersonDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPQueryPersonQueryDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.request.TPQueryPersonRequest;
import com.neusoft.ehrss.liaoning.processor.thirdparty.response.TPQueryPersonResponse;
import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.yardman.server.processor.AbstractBusinessProcessor;
import com.neusoft.sl.si.yardman.server.processor.BusinessProcessor;

/**
 * 查询用户信息
 * 
 * @author mojf
 *
 */
//@Service(value = "tpqueryPersonProcess")
public class TPQueryPersonProcess extends AbstractBusinessProcessor<TPQueryPersonRequest, TPQueryPersonResponse> implements BusinessProcessor<TPQueryPersonRequest, TPQueryPersonResponse> {

	@Resource
	private ThinUserRepository thinUserRepository;

	@Override
	public boolean isTransaction() {
		return false;
	}

	@Override
	protected TPQueryPersonResponse processBusiness(TPQueryPersonRequest request) {

		TPQueryPersonQueryDTO rdto = request.getTPQueryPersonQueryDTO();
		String idNumber = rdto.getIdNumber();
		LOGGER.debug("实名认证查询DTO={}", rdto);
		TPQueryPersonDTO dto = new TPQueryPersonDTO();
		try {
			// 按身份证idnumber查找个人用户数据情况
			ThinUser user = thinUserRepository.findByIdNumber(idNumber);
			if (null == user) {
				throw new ResourceNotFoundException("根据" + idNumber + "未找到有效的用户");
			}
			// 转为DTOList对象
			dto = PersonInfoAssembler.toDTO(user);
		} catch (Exception e) {
			e.printStackTrace();
			return new TPQueryPersonResponse(request, e.getMessage());
		}
		TPQueryPersonResponse result = new TPQueryPersonResponse(request, dto);
		return result;
	}
}
