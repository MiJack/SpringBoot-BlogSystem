package com.mijack.sbbs.controller.api;

import com.mijack.sbbs.auth.token.RestfulApiToken;
import com.mijack.sbbs.vo.PageResponse;
import com.mijack.sbbs.controller.base.BaseController;
import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.Comment;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.service.BlogService;
import com.mijack.sbbs.service.CommentService;
import com.mijack.sbbs.service.UserService;
import com.mijack.sbbs.utils.Utils;
import com.mijack.sbbs.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.Yuan
 * @since 2017/10/11
 */
@RestController
public class ApiCommentController extends BaseController {
    @Autowired
    CommentService commentService;
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;

    @GetMapping("/api/user/comments")
    public PageResponse<Comment> listUserComment(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "" + DEFAULT_BLOG_SIZE_PRE_PAGE) int pageSize,
                                                 Authentication authentication) {
        if (!Utils.isAuthenticated(authentication)) {
            return PageResponse.failed("接口未授权");
        }
        RestfulApiToken token = (RestfulApiToken) authentication;
        User user = token.getUser();
        Page<Comment> votePage = commentService.listUserComment(user, new PageRequest(pageIndex - 1, pageSize));
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

    @GetMapping("/api/users/{userId}/comments")
    public PageResponse<Comment> listUserComment(@RequestParam("userId") long userId,
                                                 @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "" + DEFAULT_BLOG_SIZE_PRE_PAGE) int pageSize) {
        User user = userService.findUser(userId);
        if (user == null) {
            return PageResponse.failed("用户不存在");
        }
        Page<Comment> votePage = commentService.listUserComment(user, new PageRequest(pageIndex - 1, pageSize));
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

    @GetMapping("/api/blog/{blogId}/comments")
    public PageResponse<Comment> listBlogComment(@PathVariable("blogId") long blogId,
                                                 @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "" + DEFAULT_BLOG_SIZE_PRE_PAGE) int pageSize) {
        Blog blog = blogService.findBlog(blogId);
        if (blog == null) {
            return PageResponse.failed("博客不存在");
        }
        Page<Comment> votePage = commentService.listBlogComment(blog, new PageRequest(pageIndex - 1, pageSize));
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

    @PostMapping("/api/blog/{blogId}/comments")
    public Response<Comment> commentBlog(@PathVariable("blogId") long blogId,
                                         @RequestParam("comment") String comment,
                                         Authentication authentication) {
        if (!Utils.isAuthenticated(authentication)) {
            return Response.failed("接口未授权");
        }
        Blog blog = blogService.findBlog(blogId);
        if (blog == null) {
            return Response.failed("博客不存在");
        }
        RestfulApiToken token = (RestfulApiToken) authentication;
        User user = token.getUser();
        Comment vote = commentService.comment(new Comment(user, blog, comment));
        return Response.ok(vote).msg("投票成功");
    }

    @DeleteMapping("/api/blog/{blogId}/comments/{commentId}")
    public Response<Comment> removeVoteBlog(@PathVariable("blogId") long blogId,
                                            @PathVariable("commentId") long commentId,
                                            Authentication authentication) {
        if (!Utils.isAuthenticated(authentication)) {
            return Response.failed("接口未授权");
        }
        Blog blog = blogService.findBlog(blogId);
        if (blog == null) {
            return Response.failed("博客不存在");
        }
        RestfulApiToken token = (RestfulApiToken) authentication;
        User user = token.getUser();
        Comment comment = commentService.findComment(commentId);
        if (!Utils.isEquals(user, comment.getUser())) {
            return Response.failed("您无权删除本评论");
        }
        if (!Utils.isEquals(blog, comment.getBlog())) {
            return Response.failed("您无权删除本评论");
        }
        commentService.deleteComment(comment);
        return Response.ok(comment).msg("投票成功");
    }
}
