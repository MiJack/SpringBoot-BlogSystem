package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.Blog;
import com.mijack.sbbs.model.ESBlog;
import com.mijack.sbbs.repository.EsBlogRepository;
import com.mijack.sbbs.service.BlogService;
import com.mijack.sbbs.service.EsBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Yuan
 * @since 2017/10/25
 */
@Service
public class EsBlogServiceImpl implements EsBlogService {
    @Autowired
    EsBlogRepository esBlogRepository;
    @Autowired
    BlogService blogService;

    @Override
    public Page<Blog> listHotestEsBlogs(String keyword, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        if (pageable.getSort() == null) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        Page<ESBlog> pages = esBlogRepository.findDistinctByTitleContainingOrContentContainingOrCategoryContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
        return pages.map(source -> blogService.findBlog(source.getId()));
    }

    @Override
    public Page<Blog> listNewestEsBlogs(String keyword, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        if (pageable.getSort() == null) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        Page<ESBlog> pages = esBlogRepository.findDistinctByTitleContainingOrContentContainingOrCategoryContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
        return pages.map(source -> blogService.findBlog(source.getId()));
    }


}
