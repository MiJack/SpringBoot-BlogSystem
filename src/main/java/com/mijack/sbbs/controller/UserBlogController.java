package com.mijack.sbbs.controller;

import com.mijack.sbbs.component.Pagination;
import com.mijack.sbbs.controller.base.BaseController;
import com.mijack.sbbs.exceptions.BlogNotFoundException;
import com.mijack.sbbs.exceptions.UserNotFoundException;
import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.Category;
import com.mijack.sbbs.model.Comment;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.service.*;
import com.mijack.sbbs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Mr.Yuan
 * @since 2017/10/9
 */
@Controller
public class UserBlogController extends BaseController {

    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    StorageService storageService;

    @GetMapping("/user/blogs.html")
    public String userBlog(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "" + DEFAULT_BLOG_SIZE_PRE_PAGE) int pageSize,
                           Authentication authentication, Model model) {
        if (!Utils.isAuthenticated(authentication)) {
            return "redirect:/login.html";
        }
        User user = (User) authentication.getPrincipal();
        PageRequest pageRequest = new PageRequest(pageIndex - 1, pageSize,
                Sort.Direction.DESC, "updateTime", "createTime");
        Page<Blog> blogPage = blogService.listBlog(user, pageRequest);
        int firstPage = Math.max(1, pageIndex - DEFAULT_BLOG_PAGE_OFFSET);
        int endPage = Math.max(1, Math.min(blogPage.getTotalPages(), pageIndex + DEFAULT_BLOG_PAGE_OFFSET));
        Pagination pagination = new Pagination(
                pageSize == DEFAULT_BLOG_SIZE_PRE_PAGE ?
                        "index.html?pageIndex={pageIndex}" :
                        ("index.html?pageIndex={pageIndex}&pageSize=" + pageSize)
                , firstPage//首页页码
                , endPage//末页页码
                , pageIndex//当前页页码
                , blogPage.isFirst()//是否为首页
                , blogPage.isLast()//是否为末页
        );
        model.addAttribute("user", user);
        model.addAttribute("pagination", pagination);
        model.addAttribute("blogs", blogPage.getContent());

        return "user/blog-list";
    }

    @GetMapping("/user/{userId}/blogs.html")
    public String userBlog(@PathVariable("userId") long userId,
                           @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "" + DEFAULT_BLOG_SIZE_PRE_PAGE) int pageSize,
                           Model model) {
        PageRequest pageRequest = new PageRequest(pageIndex - 1, pageSize,
                Sort.Direction.DESC, "updateTime", "createTime");
        User user = userService.findUser(userId);
        Page<Blog> blogPage = blogService.listBlog(user, pageRequest);
        int firstPage = Math.max(1, pageIndex - DEFAULT_BLOG_PAGE_OFFSET);
        int endPage = Math.max(1, Math.min(blogPage.getTotalPages(), pageIndex + DEFAULT_BLOG_PAGE_OFFSET));
        Pagination pagination = new Pagination(
                pageSize == DEFAULT_BLOG_SIZE_PRE_PAGE ?
                        "index.html?pageIndex={pageIndex}" :
                        ("index.html?pageIndex={pageIndex}&pageSize=" + pageSize)
                , firstPage//首页页码
                , endPage//末页页码
                , pageIndex//当前页页码
                , blogPage.isFirst()//是否为首页
                , blogPage.isLast()//是否为末页
        );
        model.addAttribute("user", user);
        model.addAttribute("pagination", pagination);
        model.addAttribute("blogs", blogPage.getContent());
        return "user/blog-list";
    }

    @GetMapping("/write-blog.html")
    public String writeBlog(Authentication authentication, Model model) {
        if (!Utils.isAuthenticated(authentication)) {
            return "redirect:/login.html";
        }
        List<Category> categories = categoryService.listCategory();
        model.addAttribute("categories", categories);
        return "blog/blog-write";
    }

    @GetMapping("/user/{userId}/blog/{blogId}")
    public String blogView(@PathVariable("userId") long userId,
                           @PathVariable("blogId") long blogId,
                           Model model) {
        User user = userService.findUser(userId);
        if (user == null) {
            throw new UserNotFoundException("用户未找到");
        }
        Blog blog = blogService.findBlog(blogId);
        if (blog == null || !Utils.isEquals(user, blog.getUser())) {
            throw new BlogNotFoundException("博客未找到");
        }
        Page<Comment> comments = commentService.listBlogComment(blog, new PageRequest(0, 10,
                new Sort(new Sort.Order(Sort.Direction.ASC, "commentNumber"))));
        model.addAttribute("blog", blog);
        model.addAttribute("blogContent", blogService.getBlogContent(blog));
        model.addAttribute("blogComments", comments.getContent());
        return "blog/blog-view";
    }
}
