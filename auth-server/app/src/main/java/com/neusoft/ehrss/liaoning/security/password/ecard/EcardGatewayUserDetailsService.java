package com.neusoft.ehrss.liaoning.security.password.ecard;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neusoft.sl.si.authserver.base.domains.person.Person;
import com.neusoft.sl.si.authserver.base.domains.person.PersonNumber;
import com.neusoft.sl.si.authserver.base.domains.person.PersonRepository;

@Service
public class EcardGatewayUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(EcardGatewayUserDetailsService.class);

	@Autowired
	private PersonRepository personRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("================EcardGatewayUserDetailsService获取用户信息username={}========", username);
		PersonNumber number = new PersonNumber(username);
		List<Person> persons = personRepository.findByPersonNumber(number);
		if (null == persons || persons.size() == 0) {
			throw new BadCredentialsException("未查询到参保数据");
		}
		Person person = persons.get(0);
		return new EcardGatewayUserDetails(person);
	}

}