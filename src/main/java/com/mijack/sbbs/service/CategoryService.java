package com.mijack.sbbs.service;

import com.mijack.sbbs.model.Category;

import java.util.List;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
public interface CategoryService {
    Category findCategory(long category);

    List<Category> listCategory();
}
