package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.Tag;
import com.mijack.sbbs.repository.TagRepository;
import com.mijack.sbbs.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    @Override
    public Set<Tag> findTags(String[] tagNames) {
        Set<Tag> tags = new HashSet<>();
        if (tagNames == null || tagNames.length == 0) {
            return tags;
        }
        for (String name : tagNames) {
            Tag tag = tagRepository.findTagByName(name);
            tags.add(tag);
        }
        return tags;
    }
}
