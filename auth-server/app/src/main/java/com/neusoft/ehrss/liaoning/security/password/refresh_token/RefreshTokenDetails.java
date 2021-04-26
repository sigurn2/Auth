package com.neusoft.ehrss.liaoning.security.password.refresh_token;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

public class RefreshTokenDetails extends SaberUserDetails implements UserDetails {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public RefreshTokenDetails(ThinUser u) {
		super(u.getAccount(), u.getName(), u.getPassword());
		super.setMobile(u.getMobile());
		super.setIdNumber(u.getIdNumber());
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_PERSON_USER");
	}

	public String getUsername() {
		return super.getAccount();
	}

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
