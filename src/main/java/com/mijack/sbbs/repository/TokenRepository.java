package com.mijack.sbbs.repository;

import com.mijack.sbbs.model.Token;
import com.mijack.sbbs.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Yuan
 */
@Repository
public interface TokenRepository extends PagingAndSortingRepository<Token,Long>{
Token findByUser(User user);

    Token findByToken(String token);
}
