package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    void startOrstop(Integer status, Long id);

    void save(CategoryDTO categoryDTO);

    void updateCategory(CategoryDTO categoryDTO);
}
