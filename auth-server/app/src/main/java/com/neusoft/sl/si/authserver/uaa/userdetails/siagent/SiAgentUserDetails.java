package com.neusoft.sl.si.authserver.uaa.userdetails.siagent;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

/**
 * 基层受理用户安全详情
 * 
 * @author mojf
 *
 */
public class SiAgentUserDetails extends SaberUserDetails implements UserDetails {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1648434918682803219L;

	/**
	 * 克隆构造函数
	 * 
	 * @param u
	 */
	public SiAgentUserDetails(User u) {
		super(u.getAccount(), u.getName(), u.getPassword());
	}

	public SiAgentUserDetails(ThinUser u) {
		super(u.getAccount(), u.getName(), u.getPassword());
	}

	/**
	 * 获取权限
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_SIAGENT_USER");
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
