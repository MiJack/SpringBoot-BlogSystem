package com.mijack.sbbs.controller.api;

import com.mijack.sbbs.controller.base.ApiBaseController;
import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.Category;
import com.mijack.sbbs.model.Tag;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.service.BlogService;
import com.mijack.sbbs.service.CategoryService;
import com.mijack.sbbs.service.StorageService;
import com.mijack.sbbs.service.TagService;
import com.mijack.sbbs.utils.Utils;
import com.mijack.sbbs.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Set;

/**
 * @author Mr.Yuan
 * @since 2017/10/11
 */
@RestController
@RequestMapping("/api/blog")
public class ApiBlogController extends ApiBaseController {

    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    StorageService storageService;

    @PostMapping
    public Response<Blog> postBlog(
            @RequestParam("blog-title") String blogTitle,
            @RequestParam("blog-markdown") String blogMarkdown,
            @RequestParam("blog-tags") String blogTagSrc,
            @RequestParam("blog-category") long blogCategory,
            @RequestParam("blog-draft") boolean isDraft,
            Authentication authentication) throws NoSuchAlgorithmException {
        if (!Utils.isAuthenticated(authentication)) {
            return apiNoAuthenticatedResponse();
        }
        User user = (User) authentication.getPrincipal();
        Category category = categoryService.findCategory(blogCategory);
        if (category == null) {
            return Response.failed("分类不存在");
        }
        Set<Tag> tags = tagService.findTags(blogTagSrc.split(","));
        Blog blog = blogService.createBlog(user, blogTitle, blogMarkdown, category, tags, isDraft);
        return Response.ok(blog).msg("保存成功");
    }

    @PatchMapping
    public Response<Blog> patchBlog(
            @RequestParam("blog-id") Long blogId,
            @RequestParam("blog-title") String blogTitle,
            @RequestParam("blog-markdown") String blogMarkdown,
            @RequestParam("blog-tags") String blogTagSrc,
            @RequestParam("blog-category") long blogCategory,
            @RequestParam("blog-draft") boolean isDraft,
            Authentication authentication) throws NoSuchAlgorithmException {
        if (!Utils.isAuthenticated(authentication)) {
            return apiNoAuthenticatedResponse();
        }
        Blog blog = blogService.findBlog(blogId);
        if (blog == null) {
            return Response.failed("博客未找到");
        }
        User user = (User) authentication.getPrincipal();
        if (!Utils.isEquals(user, blog.getUser())) {
            return Response.failed("你无权修改该博客");
        }
        Category category = categoryService.findCategory(blogCategory);
        if (category == null) {
            return Response.failed("分类不存在");
        }
        Set<Tag> tags = tagService.findTags(blogTagSrc.split(","));
        blog = blogService.updateBlog(blog, user, blogTitle, blogMarkdown, category, tags, isDraft);

        return Response.ok(blog).msg("保存成功");
    }


    @DeleteMapping("/{blogId}")
    public Response<Blog> deleteBlog(@PathVariable("blogId") Long blogId) {
        Blog blog = blogService.findBlog(blogId);
        if (blog == null) {
            return Response.failed("博客未找到");
        }
        if (blogService.deleteBlog(blog)) {
            return Response.ok(blog).msg("删除成功");
        }
        return Response.failed("删除失败");
    }

}
