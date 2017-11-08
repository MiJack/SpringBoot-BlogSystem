package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.BlogStatistics;
import com.mijack.sbbs.model.Category;
import com.mijack.sbbs.model.Tag;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.repository.BlogRepository;
import com.mijack.sbbs.repository.CategoryRepository;
import com.mijack.sbbs.repository.TagRepository;
import com.mijack.sbbs.service.BlogStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * @author Mr.Yuan
 * @since 2017/11/6
 */
@Service
public class BlogStatisticsServiceImpl implements BlogStatisticsService {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public BlogStatistics blogStatistics(User user) {
        BlogStatistics blogStatistics = new BlogStatistics();
        blogStatistics.setBlogCount(blogCount(user));
        blogStatistics.setCategoryStatistics(categoryStatistics(user));
        blogStatistics.setTagStatistics(tagStatistics(user));
        return blogStatistics;
    }

    @Override
    public Map<Tag, Long> tagStatistics(User user) {
        return tagRepository.tagStatistics(user)
                .stream()
                .collect(
                        Collectors.toMap(map -> (Tag) map.get("0"),//keyMapper
                                map -> (Long) map.get("1"),//valueMapper
                                (a, b) -> b)
                );
    }

    @Override
    public Map<Category, Long> categoryStatistics(User user) {
        return categoryRepository.categoryStatistics(user)
                .stream()
                .collect(
                        Collectors.toMap(map -> (Category) map.get("0"),//keyMapper
                                map -> (Long) map.get("1"),//valueMapper
                                (a, b) -> b)
                );
    }

    @Override
    public int blogCount(User user) {
        return blogRepository.blogCount(user);
    }

}
