package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.Category;
import com.mijack.sbbs.repository.CategoryRepository;
import com.mijack.sbbs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category findCategory(long category) {
        return categoryRepository.findOne(category);
    }
}
