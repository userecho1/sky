package com.sky.mapper;


import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {



    public List<Category> list(CategoryPageQueryDTO categoryPageQueryDTO) ;
    @AutoFill(OperationType.UPDATE)
    void update(Category category);
    @AutoFill(OperationType.INSERT)
    void insert(Category category);

    @Delete("delete from category where id=#{id}")
    void delete(Long id);



    @Select("select  name  from  category where id=#{categoryId}")
    String getCategoryNameByCategoryid(Long categoryId);


    List<Category> listAndStatus(Category query);
}
