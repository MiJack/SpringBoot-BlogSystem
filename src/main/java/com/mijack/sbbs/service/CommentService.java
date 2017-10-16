package com.mijack.sbbs.service;

import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.Comment;
import com.mijack.sbbs.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author Mr.Yuan
 * @since 2017/10/15
 */
public interface CommentService {
    Page<Comment> listUserComment(User user, PageRequest pageRequest);

    Page<Comment> listBlogComment(Blog blog, PageRequest pageRequest);

    Comment comment(Comment comment);

    void deleteComment(Comment comment);

    Comment findComment(long commentId);
}
