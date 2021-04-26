package com.neusoft.sl.si.authserver.base.services.redis;

import com.neusoft.sl.si.authserver.uaa.controller.user.dto.ResetPasswordForTokenDTO;

public interface ResetPasswordTokenService {

	public ResetPasswordForTokenDTO saveResetPassword(ResetPasswordForTokenDTO dto);

	public ResetPasswordForTokenDTO getResetPassword(String token);

	public void remove(String token);
}
