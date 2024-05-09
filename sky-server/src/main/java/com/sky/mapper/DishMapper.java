package com.sky.mapper;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    @Select("select count(0) from dish where category_id=#{id}")
    int getCountByCategoryId(Long id);


    List<Dish> list(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where category_id=#{categoryId}")
    List<Dish> getByCategoryId(Long categoryId);

    @Select("select *from dish where id=#{id}")
    Dish getById(Long id);

    void update(Dish dish);
}
