package com.neusoft.ehrss.liaoning.security.implicit.alipay.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.neusoft.ehrss.liaoning.security.userdetails.PersonNumberUserDetails;
import com.neusoft.sl.si.authserver.base.domains.person.Person;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;

/**
 * 
 * @author mojf
 *
 */
public class AlipayUserDetails extends PersonNumberUserDetails {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public AlipayUserDetails(Person person, String clientId) {
		super(person.getPersonNumberString(), person.getCertificate().getNumber(), person.getName(), "", "", SystemType.Person.toString(), clientId);
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_ALIPAY");
	}

}
