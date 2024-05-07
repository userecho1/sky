package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {

        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

        List<Category> categoryList=categoryMapper.list(categoryPageQueryDTO);
        Page<Category> p=(Page<Category>)categoryList;

        PageResult pageResult=new PageResult(p.getTotal(),p.getResult());
        return  pageResult;
    }

    @Override
    public void startOrstop(Integer status, Long id) {
        Category category= Category.builder().status(status).id(id).updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId()).build();
        categoryMapper.update(category);

    }

    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setStatus(StatusConstant.DISABLE);

        category.setUpdateTime(LocalDateTime.now());
        category.setCreateTime(LocalDateTime.now());

        category.setUpdateUser(BaseContext.getCurrentId());
        category.setCreateUser(BaseContext.getCurrentId());
        categoryMapper.insert(category);

    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);


        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);
    }
}
