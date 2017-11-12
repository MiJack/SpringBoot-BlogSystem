package com.mijack.sbbs.controller;

import com.mijack.sbbs.component.Pagination;
import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.service.EsBlogService;
import com.mijack.sbbs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.mijack.sbbs.controller.base.BaseController.DEFAULT_BLOG_PAGE_OFFSET;
import static com.mijack.sbbs.controller.base.BaseController.DEFAULT_BLOG_SIZE_PRE_PAGE;

/**
 * @author Mr.Yuan
 * @since 2017/10/25
 */
@Controller
public class SearchController {
    @Autowired
    EsBlogService esBlogService;

    @GetMapping({"/search.html"})
    public String search(@RequestParam(value = "order", required = false, defaultValue = "new") String order,
                         @RequestParam(value = "q", required = false) String keyword,
                         @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                         Model model) {
        if (Utils.isEmpty(keyword)) {
            return "search";
        }
        Page<Blog> page = null;
        if (order.equals("hot")) { // 最热查询
            Sort sort = new Sort(Sort.Direction.DESC, "readSize", "commentSize", "voteSize", "createTime");
            Pageable pageable = new PageRequest(pageIndex-1, pageSize, sort);
            page = esBlogService.listHotestEsBlogs(keyword, pageable);
        } else if (order.equals("new")) { // 最新查询
            Sort sort = new Sort(Sort.Direction.DESC, "createTime");
            Pageable pageable = new PageRequest(pageIndex-1, pageSize, sort);
            page = esBlogService.listNewestEsBlogs(keyword, pageable);
        }


        List<Blog> list = page.getContent();   // 当前所在页面数据列表
        int firstPage = Math.max(1, pageIndex - DEFAULT_BLOG_PAGE_OFFSET);
        int endPage = Math.max(1, Math.min(page.getTotalPages(), pageIndex + DEFAULT_BLOG_PAGE_OFFSET));

        Pagination pagination = new Pagination(
                pageSize == DEFAULT_BLOG_SIZE_PRE_PAGE ?
                        "search.html?q=" + keyword + "&pageIndex={pageIndex}" :
                        ("search.html?q=" + keyword + "&pageIndex={pageIndex}&pageSize=" + pageSize)
                , firstPage//首页页码
                , endPage//末页页码
                , pageIndex//当前页页码
                , page.isFirst()//是否为首页
                , page.isLast()//是否为末页
        );
        model.addAttribute("pagination", pagination);
        model.addAttribute("keyword", keyword);
        model.addAttribute("blogs", list);
        return "search-result";
    }
}
