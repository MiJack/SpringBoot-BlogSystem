package com.mijack.sbbs.service;

import com.mijack.sbbs.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
public interface TagService {
    Set<Tag> findTags(String[] blogTagSrc);

    Tag findTag(long tagId);

    Page<Tag> listTag(Pageable pageable);
}
