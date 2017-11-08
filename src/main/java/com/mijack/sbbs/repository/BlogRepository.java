package com.mijack.sbbs.repository;

import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.Category;
import com.mijack.sbbs.model.Tag;
import com.mijack.sbbs.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface BlogRepository extends PagingAndSortingRepository<Blog, Long> {
    Page<Blog> findAllByUser(User user, Pageable pageable);

    Page<Blog> findAllByDraft(boolean draft, Pageable pageable);

    Page<Blog> findAllByTagsContainingAndDraft(Tag tag, boolean draft, Pageable pageable);

    Page<Blog> findAllByCategoryAndDraft(Category category, boolean draft, Pageable pageable);

    @Query("SELECT count(b)  FROM Blog b WHERE b.user=:user  and draft =0")
    int blogCount(@Param("user") User user);
}
