package com.mijack.sbbs.repository;

import com.mijack.sbbs.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Yuan
 * @since 2017/10/11
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);

    User findByUsernameOrEmail(String username, String email);
}
