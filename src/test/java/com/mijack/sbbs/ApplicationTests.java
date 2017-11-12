package com.mijack.sbbs;

import com.mijack.sbbs.config.WebConfiguration;
import com.mijack.sbbs.config.WebMongoConfiguration;
import com.mijack.sbbs.config.WebSecurityConfig;
import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.Category;
import com.mijack.sbbs.model.Tag;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.service.BlogService;
import com.mijack.sbbs.service.CategoryService;
import com.mijack.sbbs.service.TagService;
import com.mijack.sbbs.service.UserService;
import okio.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest

@Import({WebSecurityConfig.class, WebConfiguration.class, WebMongoConfiguration.class})
public class ApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    TagService tagService;
    @Autowired
    CategoryService categoryService;

    @Test
    public void createBlogs() throws IOException {
        User user = userService.findUser(4);
        System.out.println(user);
        Category category = categoryService.findCategory(10);
        Set<Tag> tags = tagService.findTags(new String[]{"Android", "安全", "逆向工程", "反编译"});
        String[] filePaths = new String[]{
                "F:\\github\\blog\\source\\_posts\\Android-Reverse-Engineering-101-Part-1.md",
                "F:\\github\\blog\\source\\_posts\\Android-Reverse-Engineering-101-Part-2.md",
                "F:\\github\\blog\\source\\_posts\\Android-Reverse-Engineering-101-Part-4.md",
                "F:\\github\\blog\\source\\_posts\\Android-Reverse-Engineering-101-Part-5.md"
        };

        for (String filePath : filePaths) {
            File file =new File(filePath);
            Source source = Okio.source(file);
            BufferedSource buffer = Okio.buffer(source);
            Blog blog = blogService.createBlog(user, file.getName(), buffer.readByteString().utf8(), category, tags, false);
            System.out.println(blog);
        }
    }

}