package com.mijack.sbbs.service;

import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Mr.Yuan
 * @since 2017/10/14
 */
public interface VoteService {
    Vote vote(User user, Blog blog);

    Vote removeVote(User user, Blog blog);

    Page<Vote> listUserVote(User user, Pageable pageable);

    Page<Vote> listBlogVote(Blog blog, Pageable pageable);
}
