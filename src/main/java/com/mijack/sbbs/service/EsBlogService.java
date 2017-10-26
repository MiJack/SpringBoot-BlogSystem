package com.mijack.sbbs.service;

import com.mijack.sbbs.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; /**
 * @author Mr.Yuan
 * @since 2017/10/25
 */
public interface EsBlogService {
    Page<Blog> listHotestEsBlogs(String keyword, Pageable pageable);

    Page<Blog> listNewestEsBlogs(String keyword, Pageable pageable);
}
