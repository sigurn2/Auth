package com.neusoft.sl.si.authserver.uaa.userdetails.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.neusoft.sl.si.authserver.base.domains.role.Role;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserWithRole;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.uaa.log.enums.ClientType;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

/**
 * 企业用户安全详情
 * 
 * 通过CA登录的用户
 * 
 * @author mojf
 *
 */
public class EnterpriseCAUserDetails extends SaberUserDetails implements UserDetails {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1648434918682803219L;

	private Set<Role> roles = new HashSet<Role>();

	/**
	 * 克隆构造函数
	 * 
	 * @param u
	 */
	public EnterpriseCAUserDetails(User u) {
		super(u.getAccount(), u.getName(), u.getPassword());
		super.setSystemType(SystemType.Enterprise.toString());
		super.setClientType(ClientType.PC.toString());
		roles = u.getRoles();
	}

	public EnterpriseCAUserDetails(ThinUserWithRole u) {
		super(u.getAccount(), u.getName(), u.getPassword());
		super.setSystemType(SystemType.Enterprise.toString());
		super.setClientType(ClientType.PC.toString());
		roles = u.getRoles();
	}

	/**
	 * 获取权限
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
		// return AuthorityUtils.createAuthorityList("ROLE_ENTERPRISE_CAUSER");
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
