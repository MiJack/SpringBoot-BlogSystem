package com.mijack.sbbs.repository;

import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends PagingAndSortingRepository<Blog, Long> {
    Page<Blog> findAllByUser(User user, Pageable pageable);

    Page<Blog> findAllByDraft(boolean draft, Pageable pageable);
}
