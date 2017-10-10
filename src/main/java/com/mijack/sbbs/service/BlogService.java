package com.mijack.sbbs.service;

import com.mijack.sbbs.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Mr.Yuan
 * @since 2017/10/9
 */
public interface BlogService {
    Page<Blog> listBlog(Pageable pageable);
}
