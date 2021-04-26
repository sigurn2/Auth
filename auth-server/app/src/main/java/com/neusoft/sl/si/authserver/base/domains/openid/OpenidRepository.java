package com.neusoft.sl.si.authserver.base.domains.openid;

/**
 * T_USER_OPENID Repository
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OpenidRepository extends JpaRepository<Openid, String>, JpaSpecificationExecutor<Openid> {

	public Openid findByOpenid(String openid);

	public Openid findByIdNumberAndWechatAccountType(String idNumber, String wechatAccountType);

}
