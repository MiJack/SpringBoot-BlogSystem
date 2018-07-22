package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.exceptions.VoteExistException;
import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.model.Vote;
import com.mijack.sbbs.repository.VoteRepository;
import com.mijack.sbbs.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Yuan
 * @since 2017/10/14
 */
@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    VoteRepository voteRepository;

    @Override
    public Vote vote(User user, Blog blog) {
        Vote vote = voteRepository.findByUserAndBlog(user, blog);
        if (vote != null) {
            throw new VoteExistException(String.format("用户%s已经给id为%d的博客点赞", user.getUsername(), blog.getId()));
        }
        vote = new Vote(user, blog);
        return voteRepository.save(vote);
    }

    @Override
    public Vote removeVote(User user, Blog blog) {
        Vote vote = voteRepository.findByUserAndBlog(user, blog);
        if (vote == null) {
            throw new VoteExistException(String.format("用户%s未给id为%d的博客点赞", user.getUsername(), blog.getId()));
        }
        voteRepository.deleteById(vote.getId());
        return vote;
    }

    @Override
    public Page<Vote> listUserVote(User user, Pageable pageable) {
        return voteRepository.findByUser(user, pageable);
    }

    @Override
    public Page<Vote> listBlogVote(Blog blog, Pageable pageable) {
        return voteRepository.findByBlog(blog, pageable);
    }
}
