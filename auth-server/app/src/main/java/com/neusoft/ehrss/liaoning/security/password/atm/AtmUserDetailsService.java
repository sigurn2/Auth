package com.neusoft.ehrss.liaoning.security.password.atm;

import java.util.List;

import com.neusoft.sl.si.authserver.base.domains.person.PersonNumber;
import com.neusoft.sl.si.authserver.base.domains.person.identification.Certificate;
import com.neusoft.sl.si.authserver.simis.SimisWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neusoft.sl.si.authserver.base.domains.person.Person;
import com.neusoft.sl.si.authserver.base.domains.person.PersonRepository;
import com.neusoft.sl.si.authserver.base.domains.person.PersonRepositoryExtend;

@Service
public class AtmUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(AtmUserDetailsService.class);

	@Autowired
	private PersonRepositoryExtend personRepositoryExtend;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private SimisWebService simisWebService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("================AtmUserDetailsService获取用户信息username={}========", username);
		String[] array = username.split("@@");

		List<Person> persons = null;

		// 身份证号
//		if (array[0].length() >= 15) {
//			if (array.length <= 1) {
//				throw new BadCredentialsException("姓名为空");
//			}
//			persons = personRepositoryExtend.findAllByCertificate(array[0], array[1]);
//		} else {
//			// 社保卡号
//			persons = personRepository.findBySocialSecurityCardNumber(array[0]);
//		}
//		if (null == persons || persons.size() == 0) {
//			throw new BadCredentialsException("未查询到参保数据");
//		}
		//Person person = persons.get(0);
		Person person = new Person(new PersonNumber(array[0]), array[1]);
		person.setCertificate(new Certificate());
		person.setSocialSecurityCardNumber(array[0]);
		return new AtmUserDetails(person);
	}

}