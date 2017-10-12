package com.mijack.sbbs.service;

import com.mijack.sbbs.model.Tag;

import java.util.Set;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
public interface TagService {
    Set<Tag> findTags(String[] blogTagSrc);
}
