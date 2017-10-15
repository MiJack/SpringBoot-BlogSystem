package com.mijack.sbbs.repository;

import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Yuan
 * @since 2017/10/11
 */
@Repository
public interface VoteRepository extends PagingAndSortingRepository<Vote, Long> {
    /**
     * @param user
     * @param blog
     * @return
     */
    Vote findByUserAndBlog(User user, Blog blog);

    /**
     * @param user
     * @param pageable
     * @return
     */
    Page<Vote> findByUser(User user, Pageable pageable);

    /**
     * @param blog
     * @param pageable
     * @return
     */
    Page<Vote> findByBlog(Blog blog, Pageable pageable);
}
