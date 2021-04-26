package com.neusoft.ehrss.liaoning.security.password.mobile.person;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

public class RlzyscPersonUserDetails extends SaberUserDetails implements UserDetails {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * 克隆构造函数
	 * 
	 * @param u
	 */
	public RlzyscPersonUserDetails(ThinUser u) {
		super(u.getAccount(), u.getName(), u.getPassword());
		super.setMobile(u.getMobile());
		super.setIdNumber(u.getIdNumber());
		super.setSystemType(SystemType.Person.toString());
		super.setClientType("RLZYSC");
	}

	public RlzyscPersonUserDetails(User u) {
		super(u.getAccount(), u.getName(), u.getPassword());
		super.setMobile(u.getMobile());
		super.setIdNumber(u.getIdNumber());
		super.setSystemType(SystemType.Person.toString());
		super.setClientType("RLZYSC");
	}

	/**
	 * 获取权限
	 */

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_RLZYSC");
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
