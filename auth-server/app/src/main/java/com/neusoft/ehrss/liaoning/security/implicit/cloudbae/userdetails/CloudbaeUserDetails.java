package com.neusoft.ehrss.liaoning.security.implicit.cloudbae.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.neusoft.ehrss.liaoning.security.userdetails.IdNumberUserDetails;
import com.neusoft.ehrss.liaoning.security.userdetails.redis.PersonRedisUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;

/**
 * 
 * @author mojf
 *
 */
public class CloudbaeUserDetails extends IdNumberUserDetails {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public CloudbaeUserDetails(ThinUser user) {
		super(user.getAccount(), user.getIdNumber(), user.getName(), "", user.getMobile(), SystemType.Person.toString(), "Cloudbae");
	}

	public CloudbaeUserDetails(PersonRedisUser idNumberUser) {
		super(idNumberUser.getIdNumber(), idNumberUser.getIdNumber(), idNumberUser.getName(), "", idNumberUser.getMobile(), SystemType.Person.toString(), "Cloudbae");
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_CLOUDBAE");
	}

}
