package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.Comment;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.repository.CommentRepository;
import com.mijack.sbbs.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Yuan
 * @since 2017/10/15
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public Page<Comment> listUserComment(User user, PageRequest pageRequest) {
        return commentRepository.findAllByUser(user, pageRequest);
    }

    @Override
    public Page<Comment> listBlogComment(Blog blog, PageRequest pageRequest) {
        return commentRepository.findAllByBlog(blog, pageRequest);
    }

    @Override
    public Comment comment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comment findComment(long commentId) {
        return commentRepository.findOne(commentId);
    }
}
