package com.mijack.sbbs.service;

import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Mr.Yuan
 * @since 2017/10/9
 */
public interface BlogService {

    Page<Blog> listBlog(User user, Pageable pageable);

    boolean deleteBlog(Blog blog);

    Blog findBlog(long blogId);

    Blog saveBlog(Blog blog);
}
