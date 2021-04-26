
package com.neusoft.sl.si.authserver.base.domains.user;

import com.neusoft.sl.si.authserver.base.domains.role.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User>, UserRepositoryCustom {
    User findByCaKey(String var1);

    User findByAccount(String var1);

    User findByIdNumber(String var1);

    List<User> findByRoles(Role var1);

    List<User> findByOrganizationId(String var1);

    User findByMobile(String var1);

    List<User> findByMainCompanyNum(String var1);

    User findById(String var1);

    User findByEmail(String var1);

    User findByOrgCode(String var1);

    User findByUnitId(Long unitId);

}
