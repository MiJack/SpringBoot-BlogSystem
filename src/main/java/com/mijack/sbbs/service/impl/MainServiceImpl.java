package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.Tag;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.repository.BlogRepository;
import com.mijack.sbbs.repository.CategoryRepository;
import com.mijack.sbbs.repository.TagRepository;
import com.mijack.sbbs.repository.UserRepository;
import com.mijack.sbbs.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements MainService {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Page<Blog> listHotBlog(Pageable pageable) {
        PageRequest hotValue = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                new Sort(new Sort.Order(Sort.Direction.DESC, "hotValue")));
        return blogRepository.findAllByDraft(false, hotValue);
    }

    @Override
    public Page<Blog> listNewBlog(Pageable pageable) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.DESC, "updateTime", "createTime");
        return blogRepository.findAllByDraft(false, pageRequest);
    }

    @Override
    public Page<Tag> listHotTag(Pageable pageable) {
        PageRequest hotValue = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.DESC, "hotValue");
        return tagRepository.findAll(hotValue);
    }

    @Override
    public Page<User> listHotUser(Pageable pageable) {
        PageRequest hotValue = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.DESC, "hotValue");
        return userRepository.findAll(hotValue);
    }
}
