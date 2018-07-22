package com.mijack.sbbs.controller.api;

import com.mijack.sbbs.auth.token.RestfulApiToken;
import com.mijack.sbbs.vo.PageResponse;
import com.mijack.sbbs.controller.base.BaseController;
import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.model.Vote;
import com.mijack.sbbs.service.BlogService;
import com.mijack.sbbs.service.UserService;
import com.mijack.sbbs.service.VoteService;
import com.mijack.sbbs.utils.Utils;
import com.mijack.sbbs.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 点赞接口
 *
 * @author Mr.Yuan
 * @since 2017/10/11
 */
@RestController
public class ApiVoteController extends BaseController {
    @Autowired
    VoteService voteService;
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;

    @GetMapping("/api/user/votes")
    public PageResponse<Vote> listUserVote(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "" + DEFAULT_BLOG_SIZE_PRE_PAGE) int pageSize,
                                           Authentication authentication) {
        if (!Utils.isAuthenticated(authentication)) {
            return PageResponse.failed("接口未授权");
        }
        RestfulApiToken token = (RestfulApiToken) authentication;
        User user = token.getUser();
        Page<Vote> votePage = voteService.listUserVote(user, PageRequest.of(pageIndex - 1, pageSize));
        int firstPage = Math.max(1, pageIndex - DEFAULT_BLOG_PAGE_OFFSET);
        int endPage = Math.max(1, Math.min(votePage.getTotalPages(), pageIndex + DEFAULT_BLOG_PAGE_OFFSET));
        return PageResponse
                .ok(votePage.getContent())
                .currentPage(pageIndex)
                .isFirstPage(pageIndex == firstPage)
                .isLastPage(pageIndex == endPage)
                .firstPage(firstPage)
                .lastPage(endPage)
                .msg("获取成功");
    }

    @GetMapping("/api/users/{userId}/votes")
    public PageResponse<Vote> listUserVote(@RequestParam("userId") long userId,
                                           @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "" + DEFAULT_BLOG_SIZE_PRE_PAGE) int pageSize) {
        User user = userService.findUser(userId);
        if (user == null) {
            return PageResponse.failed("用户不存在");
        }
        Page<Vote> votePage = voteService.listUserVote(user, PageRequest.of(pageIndex - 1, pageSize));
        int firstPage = Math.max(1, pageIndex - DEFAULT_BLOG_PAGE_OFFSET);
        int endPage = Math.max(1, Math.min(votePage.getTotalPages(), pageIndex + DEFAULT_BLOG_PAGE_OFFSET));
        return PageResponse
                .ok(votePage.getContent())
                .currentPage(pageIndex)
                .isFirstPage(pageIndex == firstPage)
                .isLastPage(pageIndex == endPage)
                .firstPage(firstPage)
                .lastPage(endPage)
                .msg("获取成功");
    }

    @GetMapping("/api/blog/{blogId}/votes")
    public PageResponse<Vote> listVoteBlog(@PathVariable("blogId") long blogId,
                                           @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "" + DEFAULT_BLOG_SIZE_PRE_PAGE) int pageSize) {
        Blog blog = blogService.findBlog(blogId);
        if (blog == null) {
            return PageResponse.failed("博客不存在");
        }
        Page<Vote> votePage = voteService.listBlogVote(blog, PageRequest.of(pageIndex - 1, pageSize));
        int firstPage = Math.max(1, pageIndex - DEFAULT_BLOG_PAGE_OFFSET);
        int endPage = Math.max(1, Math.min(votePage.getTotalPages(), pageIndex + DEFAULT_BLOG_PAGE_OFFSET));
        return PageResponse
                .ok(votePage.getContent())
                .currentPage(pageIndex)
                .isFirstPage(pageIndex == firstPage)
                .isLastPage(pageIndex == endPage)
                .firstPage(firstPage)
                .lastPage(endPage)
                .msg("获取成功");
    }

    @PostMapping("/api/blog/{blogId}/vote")
    public Response<Vote> voteBlog(@PathVariable("blogId") long blogId, Authentication authentication) {
        if (!Utils.isAuthenticated(authentication)) {
            return Response.failed("接口未授权");
        }
        Blog blog = blogService.findBlog(blogId);
        if (blog == null) {
            return Response.failed("博客不存在");
        }
        RestfulApiToken token = (RestfulApiToken) authentication;
        User user = token.getUser();
        Vote vote = voteService.vote(user, blog);
        return Response.ok(vote).msg("投票成功");
    }

    @DeleteMapping("/api/blog/{blogId}/vote")
    public Response<Vote> removeVoteBlog(@PathVariable("blogId") long blogId, Authentication authentication) {
        if (!Utils.isAuthenticated(authentication)) {
            return Response.failed("接口未授权");
        }
        Blog blog = blogService.findBlog(blogId);
        if (blog == null) {
            return Response.failed("博客不存在");
        }
        RestfulApiToken token = (RestfulApiToken) authentication;
        User user = token.getUser();
        Vote vote = voteService.removeVote(user, blog);
        return Response.ok(vote).msg("投票成功");
    }
}
