package com.mijack.sbbs.controller;

import com.mijack.sbbs.component.Pagination;
import com.mijack.sbbs.controller.base.BaseController;
import com.mijack.sbbs.exceptions.CategoryNotFoundException;
import com.mijack.sbbs.exceptions.TagNotFoundException;
import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.Category;
import com.mijack.sbbs.model.Tag;
import com.mijack.sbbs.service.BlogService;
import com.mijack.sbbs.service.CategoryService;
import com.mijack.sbbs.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mr.Yuan
 * @since 2017/10/31
 */
@Controller
public class BlogController extends BaseController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    BlogService blogService;

    @GetMapping("/tag/{tagId}")
    public String blogTag(
            @PathVariable(value = "tagId") long tagId,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "" + DEFAULT_BLOG_SIZE_PRE_PAGE) int pageSize,
            Model model) {
        Tag tag = tagService.findTag(tagId);
        if (tag == null) {
            throw new TagNotFoundException();
        }
        Page<Blog> blogPage = blogService.listBlog(tag, PageRequest.of(pageIndex - 1, pageSize));
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
        // 主页的博客
        model.addAttribute("blogs", blogPage.getContent());
        model.addAttribute("tags", tagService.listTag(PageRequest.of(0, 12, Sort.Direction.DESC, "hotValue")).getContent());
        // 分页数据
        model.addAttribute("pagination", pagination);
        return "blog/blog-tag-list";
    }

    @GetMapping("/category/{categoryId}")
    public String blogCategory(
            @PathVariable(value = "categoryId") long categoryId,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "" + DEFAULT_BLOG_SIZE_PRE_PAGE) int pageSize,
            Model model) {
        Category category = categoryService.findCategory(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        Page<Blog> blogPage = blogService.listBlog(category, PageRequest.of(pageIndex - 1, pageSize));
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
        // 主页的博客
        model.addAttribute("blogs", blogPage.getContent());
        model.addAttribute("categories", categoryService.listCategory());
        // 分页数据
        model.addAttribute("pagination", pagination);
        return "blog/blog-category-list";
    }
}
