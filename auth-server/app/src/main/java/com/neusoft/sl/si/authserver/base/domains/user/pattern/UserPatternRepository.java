package com.neusoft.sl.si.authserver.base.domains.user.pattern;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * T_USER_PATTERN Repository
 */
public interface UserPatternRepository extends JpaRepository<UserPattern, String>, JpaSpecificationExecutor<UserPattern> {


}
