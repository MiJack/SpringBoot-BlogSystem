package com.mijack.sbbs.repository;

import com.mijack.sbbs.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Yuan
 * @since 2017/10/26
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    String ROLE_ROOT = "ROLE_ROOT";
    String ROLE_ADMIN = "ROLE_ADMIN";
    String ROLE_USER = "ROLE_USER";

    Role findByName(String name);
}
