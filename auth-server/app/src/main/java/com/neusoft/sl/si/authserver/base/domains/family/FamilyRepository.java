package com.neusoft.sl.si.authserver.base.domains.family;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Family Repository
 */
public interface FamilyRepository extends JpaRepository<Family, String>, JpaSpecificationExecutor<Family> {

	public Family findByFamilyId(String familyId);

	public Long deleteByFamilyId(String familyId);

	public Family findByMobile(String mobile);

	public Family findByIdNumber(String idNumber);

	public Family findByCardNumber(String cardNumber);

}
