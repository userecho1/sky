package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.DishDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
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

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

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
        Category category= Category.builder().status(status).id(id)
//                .updateTime(LocalDateTime.now())
//                .updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.update(category);

    }

    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setStatus(StatusConstant.DISABLE);

//        category.setUpdateTime(LocalDateTime.now());
//        category.setCreateTime(LocalDateTime.now());
//
//        category.setUpdateUser(BaseContext.getCurrentId());
//        category.setCreateUser(BaseContext.getCurrentId());
        categoryMapper.insert(category);

    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);


//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);
    }

    @Override
    public void deleteCategory(Long id) {
        int count1=dishMapper.getCountByCategoryId(id);
        int count2=setmealMapper.getCountByCategoryId(id);
        if(count1!=0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);

        }
        if(count2!=0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);}
        categoryMapper.delete(id);
    }

    @Override
    public List<Category> qurryByType(Integer id) {
        CategoryPageQueryDTO queryDTO=new CategoryPageQueryDTO();
        queryDTO.setType(id);
        List<Category> categoryList=categoryMapper.list(queryDTO);
        return categoryList;
    }

}
