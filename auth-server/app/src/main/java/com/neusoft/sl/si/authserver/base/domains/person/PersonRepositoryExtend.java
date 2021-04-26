package com.neusoft.sl.si.authserver.base.domains.person;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.neusoft.sl.si.authserver.base.domains.person.identification.IdentityCard;

/**
 * 人员仓储实现类
 * 
 * @author wuyf
 * 
 */
@Repository
public class PersonRepositoryExtend {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryExtend.class);

	@PersistenceContext
	private EntityManager entityManager;

	private static final String CACHE_PERSON_REDIS_KEY = "CACHE_PERSON";

	@SuppressWarnings("unchecked")
	@Cacheable(value = CACHE_PERSON_REDIS_KEY, key = "'CACHE_PERSON_'+#certificateNumber + '_' + #name", unless = "null==#result")
	public List<Person> findAllByCertificate(String certificateNumber, String name) {
		LOGGER.debug("findAllByCertificate certificateNumber={} name={}", certificateNumber, name);
		String hql = "select distinct a from Person a where ( a.certificate.number = ?1 " + " or  a.certificate.number = ?2 or a.certificate.number = ?3 ) and a.name = ?4 order by a.profileStatus asc";
		Query q = entityManager.createQuery(hql);
		q.setParameter(1, certificateNumber.toUpperCase());
		q.setParameter(2, certificateNumber.toLowerCase());
		q.setParameter(3, IdentityCard.convertEighteenOrFifteen(certificateNumber).toUpperCase());
		q.setParameter(4, name);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Cacheable(value = CACHE_PERSON_REDIS_KEY, key = "'CACHE_PERSON_'+#certificateNumber", unless = "null==#result")
	public List<Person> findByCertificate(String certificateNumber) {
		LOGGER.debug("findAllByCertificate certificateNumber={}", certificateNumber);
		String hql = "select distinct a from Person a where ( a.certificate.number = ?1 " + " or  a.certificate.number = ?2 or a.certificate.number = ?3)  order by a.profileStatus asc";
		Query q = entityManager.createQuery(hql);
		q.setParameter(1, certificateNumber.toUpperCase());
		q.setParameter(2, certificateNumber.toLowerCase());
		q.setParameter(3, IdentityCard.convertEighteenOrFifteen(certificateNumber).toUpperCase());
		return q.getResultList();
	}

}
