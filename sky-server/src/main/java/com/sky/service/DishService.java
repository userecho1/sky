package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {
    PageResult Page(DishPageQueryDTO dishPageQueryDTO);

    List<Dish> getListByCategoryId(Integer categoryId);


    Dish getById(int id);
}
