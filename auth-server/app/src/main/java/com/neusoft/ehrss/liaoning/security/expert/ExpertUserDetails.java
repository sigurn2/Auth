package com.neusoft.ehrss.liaoning.security.expert;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.uaa.log.enums.ClientType;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

public class ExpertUserDetails extends SaberUserDetails implements UserDetails {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * 克隆构造函数
	 * 
	 * @param u
	 */
	public ExpertUserDetails(ThinUser u) {
		super(u.getAccount(), u.getName(), u.getPassword());
		super.setMobile(u.getMobile());
		super.setIdNumber(u.getIdNumber());
		super.setSystemType("Expert");
		super.setClientType(ClientType.PC.toString());
	}

	public ExpertUserDetails(User u) {
		super(u.getAccount(), u.getName(), u.getPassword());
		super.setMobile(u.getMobile());
		super.setIdNumber(u.getIdNumber());
		super.setSystemType("Expert");
		super.setClientType(ClientType.PC.toString());
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
