package com.neusoft.ehrss.liaoning.security.password.atm;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.neusoft.ehrss.liaoning.security.userdetails.IdNumberUserDetails;
import com.neusoft.sl.si.authserver.base.domains.person.Person;
import com.neusoft.sl.si.authserver.uaa.log.enums.ClientType;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;

/**
 * 
 * @author mojf
 *
 */
public class AtmUserDetails extends IdNumberUserDetails {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public AtmUserDetails(Person person) {
		super(person.getSocialSecurityCardNumber()+"@@"+person.getName(), person.getCertificate().getNumber(), person.getName(), "", "", SystemType.Person.toString(), ClientType.ATM.toString());
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_ATM");
	}

}
