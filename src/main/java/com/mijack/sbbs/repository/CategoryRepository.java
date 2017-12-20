package com.mijack.sbbs.repository;

import com.mijack.sbbs.model.Category;
import com.mijack.sbbs.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.Yuan
 */
@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    @Query("SELECT new map(category, COUNT(category)) FROM Blog blog JOIN blog.category category" +
            " where blog.draft = 0  and blog.user = :user group by category")
    List<Map> categoryStatistics(@Param("user") User user);
}
