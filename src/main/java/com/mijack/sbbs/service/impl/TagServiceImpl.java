package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.Tag;
import com.mijack.sbbs.repository.TagRepository;
import com.mijack.sbbs.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            if (tag == null) {
                tag = tagRepository.save(new Tag(name));
            }
            tags.add(tag);
        }
        return tags;
    }

    @Override
    public Tag findTag(long tagId) {
        return tagRepository.findOne(tagId);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }
}
