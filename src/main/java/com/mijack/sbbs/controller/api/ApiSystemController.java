package com.mijack.sbbs.controller.api;

import com.mijack.sbbs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Yuan
 * @since 2017/10/26
 */
@RestController
public class ApiSystemController {
    @Autowired
    BlogService blogService;

    @DeleteMapping("/api/system/deleteAllBlog")
    public String deleteAllBlog() {
        blogService.deleteAllBlog();
        return "ok";
    }
}
