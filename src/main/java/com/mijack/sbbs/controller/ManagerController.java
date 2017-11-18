package com.mijack.sbbs.controller;

import com.mijack.sbbs.component.Pagination;
import com.mijack.sbbs.controller.base.BaseController;
import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mr.Yuan
 * @since 2017/11/12
 */
@Controller
public class ManagerController extends BaseController {
    @Autowired
    MainService mainService;

    @GetMapping("/manager/blogs.html")
    public String manageBlog(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "" + DEFAULT_BLOG_SIZE_PRE_PAGE) int pageSize,
                             Model model) {
        Page<Blog> blogPage = mainService.listNewBlog(new PageRequest(pageIndex - 1, pageSize));
        int firstPage = Math.max(1, pageIndex - DEFAULT_BLOG_PAGE_OFFSET);
        int endPage = Math.max(1, Math.min(blogPage.getTotalPages(), pageIndex + DEFAULT_BLOG_PAGE_OFFSET));
        Pagination pagination = new Pagination(
                pageSize == DEFAULT_BLOG_SIZE_PRE_PAGE ?
                        "/manager/blogs.html?pageIndex={pageIndex}" :
                        ("/manager/blogs.html?pageIndex={pageIndex}&pageSize=" + pageSize)
                , firstPage//首页页码
                , endPage//末页页码
                , pageIndex//当前页页码
                , blogPage.isFirst()//是否为首页
                , blogPage.isLast()//是否为末页
        );
        // 主页的博客
        model.addAttribute("blogs", blogPage.getContent());
        // 分页数据
        model.addAttribute("pagination", pagination);
        return "manager/blogs";
    }
}
