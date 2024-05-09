package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Override
    public PageResult Page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        List<Dish> dishList=dishMapper.list(dishPageQueryDTO);
        Page<Dish> p=(Page<Dish>) dishList ;
        PageResult pageResult=new PageResult(p.getTotal(),p.getResult());
        return  pageResult;
    }

//    @Override
//    public Dish getById() {
//        Dish dish=dishMapper.
//        return dish;
//    }
}
