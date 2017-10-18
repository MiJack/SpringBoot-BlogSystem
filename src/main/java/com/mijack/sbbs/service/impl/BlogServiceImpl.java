package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.repository.BlogRepository;
import com.mijack.sbbs.service.BlogService;
import com.mijack.sbbs.utils.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Yuan
 * @since 2017/10/9
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;

    @Override
    public Page<Blog> listBlog(User user, Pageable pageable) {
        AssertUtils.notNoll(user, UsernameNotFoundException.class,"用户未找到");
        return blogRepository.findAllByUser(user,pageable);
    }

    @Override
    public boolean deleteBlog(Blog blog) {
        blogRepository.delete(blog);
        return true;
    }

    @Override
    public Blog findBlog(long blogId) {
        return blogRepository.findOne(blogId);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }
}
