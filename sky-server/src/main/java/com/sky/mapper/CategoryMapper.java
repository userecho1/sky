package com.sky.mapper;


import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {



    public List<Category> list(CategoryPageQueryDTO categoryPageQueryDTO) ;

    void update(Category category);

    void insert(Category category);

    @Delete("delete from category where id=#{id}")
    void delete(Long id);
}
