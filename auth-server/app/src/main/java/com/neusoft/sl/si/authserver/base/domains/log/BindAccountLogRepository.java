package com.neusoft.sl.si.authserver.base.domains.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BindAccountLogRepository extends JpaRepository<BindAccountLog, String>, JpaSpecificationExecutor<BindAccountLog> {
}
