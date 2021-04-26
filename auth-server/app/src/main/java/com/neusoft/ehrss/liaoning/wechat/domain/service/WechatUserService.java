package com.neusoft.ehrss.liaoning.wechat.domain.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.ehrss.liaoning.utils.OpenidObject;
import com.neusoft.ehrss.liaoning.utils.OpenidUtil;
import com.neusoft.ehrss.liaoning.wechat.domain.dto.WechatUserDTO;
import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.si.authserver.base.domains.openid.Openid;
import com.neusoft.sl.si.authserver.base.domains.openid.OpenidRepository;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.services.password.PasswordEncoderService;
import com.neusoft.sl.si.authserver.uaa.exception.WechatException;

/**
 * 绑定微信openid
 * 
 * @author ZHOUHD
 *
 */
@Service
public class WechatUserService {

	private static final Logger log = LoggerFactory.getLogger(WechatUserService.class);

	@Resource(name = "${saber.auth.password.encoder}")
	private PasswordEncoderService passwordEncoderService;

	@Autowired
	private OpenidRepository openidRepository;

	@Autowired
	private ThinUserRepository thinUserRepository;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void bind(WechatUserDTO wechatUserBindDTO) {
		String pseudo_openid = wechatUserBindDTO.getOpenid();
		// 解码openid
		OpenidObject object = OpenidUtil.dec(pseudo_openid);
		String openid = object.getOpenid();
		log.debug("解码出的OPENID={}", openid);

		ThinUser thinUser = thinUserRepository.findByIdNumber(wechatUserBindDTO.getIdNumber());
		if (thinUser == null) {
			log.error("未注册网厅账号，证件号码={}", wechatUserBindDTO.getIdNumber());
			throw new WechatException("您输入的账号不存在，请重新输入");
		}

		if (!wechatUserBindDTO.getName().equals(thinUser.getName())) {
			log.error("姓名有误，存储的name={}，输入的name={}，证件号码={}", thinUser.getName(), wechatUserBindDTO.getName(), wechatUserBindDTO.getIdNumber());
			throw new WechatException("您输入的姓名与账户信息不匹配，请重新输入");
		}

		Openid open = openidRepository.findByIdNumberAndWechatAccountType(thinUser.getIdNumber(), object.getType());
		// 先查询身份证号
		if (open != null) {
			// 此证件号码已经绑定了一个公众号
			if (openid.equals(open.getOpenid())) {
				log.error("证件号码={} 已绑定成功", thinUser.getIdNumber());
				throw new WechatException("您输入的账号已经绑定成功，无需重复操作");
			} else {
				// 绑定的不是这个公众号，直接更新掉公众号就可以了
				log.error("证件号码={} 已经绑定了其它微信用户，直接更新openid={}", thinUser.getIdNumber(), openid);
				// throw new WechatException("证件号码：" +
				// wechatUserBindDTO.getIdNumber() + " 已绑定其它微信用户");
				open.setOpenid(openid);
				openidRepository.save(open);
			}
		} else {
			// 未找到此身份证号，查询openid
			open = openidRepository.findByOpenid(openid);
			if (open != null) {
				// 存在openid，更新身份证号信息
				open.setIdNumber(thinUser.getIdNumber());
				open.setWechatAccountType(object.getType());
				openidRepository.save(open);
			} else {// 否则直接绑定即可
				Openid u_openid = new Openid();
				u_openid.setIdNumber(thinUser.getIdNumber());
				u_openid.setOpenid(openid);
				u_openid.setWechatAccountType(object.getType()); // 微信公众号类型
				// 保存openid
				openidRepository.save(u_openid);
			}
		}
		log.debug("绑定OPENID成功，证件号码={}", wechatUserBindDTO.getIdNumber());
	}

	// public UserOpenidDTO findOpenid(String idNumber, String
	// wechatAccountType) {
	// Validate.notBlank(idNumber, "证件号码为空");
	// Validate.notBlank(wechatAccountType, "公众号类型为空");
	//
	// ThinUser thinUser = thinUserRepository.findByIdNumber(idNumber);
	// if (thinUser == null)
	// throw new ResourceNotFoundException("证件号码 " + idNumber + " 未注册网厅");
	//
	// Openid open =
	// openidRepository.findByIdNumberAndWechatAccountType(idNumber,
	// wechatAccountType);
	// if (open == null) {
	// throw new ResourceNotFoundException("证件号码 " + idNumber + " 未绑定微信公众号");
	// }
	//
	// UserOpenidDTO dto = new UserOpenidDTO();
	// dto.setIdNumber(idNumber);
	// dto.setOpenid(open.getOpenid());
	// dto.setWechatAccountType(open.getWechatAccountType());
	// return dto;
	// }

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void unbind(String account, String type) {
		ThinUser thinUser = this.thinUserRepository.findByAccount(account);
		if (null == thinUser) {
			log.error("微信解绑用户时，根据account={}未找到有效的用户", account);
			throw new ResourceNotFoundException("未找到有效的用户");
		}
		Openid open = openidRepository.findByIdNumberAndWechatAccountType(thinUser.getIdNumber(), type);
		if (null != open) {
			openidRepository.delete(open);
		}
	}

}
