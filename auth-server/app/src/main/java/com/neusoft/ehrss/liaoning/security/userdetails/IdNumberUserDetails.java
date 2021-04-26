package com.neusoft.ehrss.liaoning.security.userdetails;

import org.springframework.security.core.userdetails.UserDetails;

import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

/**
 * 
 * @author mojf
 *
 */
public class IdNumberUserDetails extends SaberUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdNumberUserDetails(String account, String idNumber, String name, String password, String mobile, String systemType, String clientType) {
		super(account, name, password);
		super.setMobile(mobile);
		super.setIdNumber(idNumber);
		super.setSystemType(systemType);
		super.setClientType(clientType);
	}

	public String getUsername() {
		return super.getAccount();
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

}
