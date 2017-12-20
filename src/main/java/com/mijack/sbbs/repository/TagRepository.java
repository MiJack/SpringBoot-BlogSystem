package com.mijack.sbbs.repository;

import com.mijack.sbbs.model.Tag;
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
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
    Tag findTagByName(String name);

    @Query("SELECT new map(tag, COUNT(tag)) FROM Blog  blog JOIN blog.tags tag" +
            " where blog.draft = 0  and blog.user = :user group by tag")
    List<Map> tagStatistics(@Param("user") User user);


}
