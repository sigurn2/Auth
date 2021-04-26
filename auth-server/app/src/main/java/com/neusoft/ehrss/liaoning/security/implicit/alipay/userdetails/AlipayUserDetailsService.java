package com.neusoft.ehrss.liaoning.security.implicit.alipay.userdetails;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.AuthorizeCodeInRedisManager;
import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto.AuthorizeCodeUser;
import com.neusoft.ehrss.liaoning.security.userdetails.redis.PersonRedisUser;
import com.neusoft.ehrss.liaoning.security.userdetails.redis.PersonRedisUserManager;
import com.neusoft.sl.si.authserver.base.domains.person.Person;
import com.neusoft.sl.si.authserver.base.domains.person.PersonNumber;
import com.neusoft.sl.si.authserver.base.domains.person.PersonRepository;
import com.neusoft.sl.si.authserver.base.domains.user.enums.UserType;

@Service
public class AlipayUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(AlipayUserDetailsService.class);

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PersonRedisUserManager personRedisUserManager;

	@Autowired
	private AuthorizeCodeInRedisManager authorizeCodeInRedisManager;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("================AlipayUserDetailsService获取用户信息username={}========", username);
		AuthorizeCodeUser user = authorizeCodeInRedisManager.load(username);
		if (user == null) {
			log.error("Invalid authorization code: {}", username);
			throw new BadCredentialsException("请求已过期，请重新操作");
		} else {
			authorizeCodeInRedisManager.remove(username);
		}

		PersonNumber number = new PersonNumber(user.getPersonNumber());
		List<Person> persons = personRepository.findByPersonNumber(number);
		if (persons == null || persons.size() == 0) {
			throw new UsernameNotFoundException("未获取到人员的参保数据");
		}
		Person person = persons.get(0);

		PersonRedisUser redisUser = new PersonRedisUser();
		redisUser.setAccount(user.getPersonNumber());
		redisUser.setPersonNumber(user.getPersonNumber());
		redisUser.setIdNumber(person.getCertificate().getNumber());
		redisUser.setName(person.getName());
		redisUser.setUserType(UserType.USER_PERSON);
		personRedisUserManager.putByPersonNumber(redisUser);

		return new AlipayUserDetails(person, user.getClientId());
	}

}