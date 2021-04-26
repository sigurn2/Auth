package com.neusoft.ehrss.liaoning.security.password.ecard;

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
public class EcardGatewayUserDetails extends PersonNumberUserDetails {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public EcardGatewayUserDetails(Person person) {
		super(person.getPersonNumberString(), person.getCertificate().getNumber(), person.getName(), "", "", SystemType.Person.toString(), "EcardGateway");
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_ECARD_GATEWAY");
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
