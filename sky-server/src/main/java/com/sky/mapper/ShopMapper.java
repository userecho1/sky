package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ShopMapper {
    //TODO 补全表格
    @Select("select ")
    Integer getStatus();

    @Update("update ")
    void setStatus(Integer status);
}
