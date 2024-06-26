package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    PageResult Page(DishPageQueryDTO dishPageQueryDTO);

    List<DishVO> getListByCategoryId(Dish dish);


    DishVO getById(Dish dish);

    void enableOrDisable(Integer status, Long id);

    void save(DishDTO dishDTO);

    void modify(DishDTO dishDTO);

    void deletes(List<Long> ids);
}
