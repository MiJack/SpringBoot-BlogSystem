package com.mijack.sbbs.service;

import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.Category;
import com.mijack.sbbs.model.Tag;
import com.mijack.sbbs.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * @author Mr.Yuan
 * @since 2017/10/9
 */
public interface BlogService {

    Page<Blog> listBlog(User user, Pageable pageable);

    Page<Blog> listBlog(Tag tag, Pageable pageable);

    Page<Blog> listBlog(Category category, Pageable pageRequest);

    boolean deleteBlog(Blog blog);

    Blog findBlog(long blogId);

    Blog saveBlog(Blog blog);

    String getBlogContent(Blog blog);

    Blog createBlog(User user, String blogTitle, String blogMarkdown, Category category, Set<Tag> tags, boolean isDraft);

    Blog updateBlog(Blog blog, User user, String blogTitle, String blogMarkdown, Category category, Set<Tag> tags, boolean isDraft);

    void deleteAllBlog();
}
