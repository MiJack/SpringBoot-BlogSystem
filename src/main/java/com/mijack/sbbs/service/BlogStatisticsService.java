package com.mijack.sbbs.service;

import com.mijack.sbbs.model.BlogStatistics;
import com.mijack.sbbs.model.Category;
import com.mijack.sbbs.model.Tag;
import com.mijack.sbbs.model.User;

import java.util.Map;

/**
 * @author Mr.Yuan
 * @since 2017/11/6
 */
public interface BlogStatisticsService {
    BlogStatistics blogStatistics(User user);

     Map<Tag,Long> tagStatistics(User user) ;

     Map<Category, Long> categoryStatistics(User user);

     int blogCount(User user);
}
