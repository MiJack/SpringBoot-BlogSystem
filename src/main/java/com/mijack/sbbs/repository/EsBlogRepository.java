package com.mijack.sbbs.repository;

import com.mijack.sbbs.model.ESBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Yuan
 * @since 2017/10/25
 */
@Repository
public interface EsBlogRepository extends ElasticsearchRepository<ESBlog, Long> {
    Page<ESBlog> findDistinctByTitleContainingOrContentContainingOrCategoryContainingOrTagsContaining(
            String title, String content, String category, String tags, Pageable pageable);
}