package com.mijack.sbbs.controller;

import com.mijack.sbbs.component.Pagination;
import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.service.BlogService;
import com.mijack.sbbs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mr.Yuan
 * @since 2017/10/9
 */
@Controller
public class BlogController extends BaseController {

    @Autowired
    BlogService blogService;

    @GetMapping("/user/blogs.html")
    public String userBlog(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "" + DEFAULT_BLOG_SIZE_PRE_PAGE) int pageSize,
                           Authentication authentication, Model model) {
        if (!Utils.isAuthenticated(authentication)) {
            return "redirect:/login.html";
        }
        PageRequest pageRequest = new PageRequest(pageIndex - 1, pageSize,
                Sort.Direction.DESC, "updateTime", "createTime");
        Page<Blog> blogPage = blogService.listBlog(pageRequest);
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
        model.addAttribute("pagination", pagination);
        model.addAttribute("blogs", blogPage.getContent());

        return "user/blog-list";
    }
}
