package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DishFlavorMapper {

    @Select("select * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> getFlavorsByDishid(Long id);


    void insert(DishFlavor flavor);

    @Select("select * from  dish_flavor where dish_id=#{dishId} and name=#{name}")
   DishFlavor getFlavorByDishidandFlavorName(DishFlavor flavor);

    void update(DishFlavor dishFlavor);

    @Delete("delete from dish_flavor where dish_id=#{id}")
    void delete(Long id);
}
