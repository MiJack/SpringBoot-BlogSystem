package com.mijack.sbbs.repository;

import com.mijack.sbbs.model.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
    Tag findTagByName(String name);
}
