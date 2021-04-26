package com.neusoft.sl.si.authserver.base.domains.user.family;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserFamilyEntityRepository extends JpaRepository<UserFamilyEntity, String>{
	
	public UserFamilyEntity findByFamilyId(String familyId);

    public List<UserFamilyEntity> findByUserId(String userId);
    
    @Transactional
    @Query("from UserFamilyEntity u where u.familyId=?1")
    public List<UserFamilyEntity> findByFamilyid(String familyId);

}
