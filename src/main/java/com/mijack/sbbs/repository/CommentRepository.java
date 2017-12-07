package com.mijack.sbbs.repository;

import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.Comment;
import com.mijack.sbbs.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Yuan
 * @since 2017/10/11
 */
@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findAllByUser(User user, Pageable pageable);


    Page<Comment> findAllByBlog(Blog blog, Pageable pageable);

    int countByBlog(Blog blog);
}
