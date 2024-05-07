package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "分类分页查询的数据模型")
@Data
public class CategoryPageQueryDTO implements Serializable {

    //页码
    @ApiModelProperty("分类查询第几页")
    private int page;

    //每页记录数
    @ApiModelProperty("分类每页查询的记录数")
    private int pageSize;

    //分类名称
    @ApiModelProperty("分类的名称")
    private String name;

    //分类类型 1菜品分类  2套餐分类
    @ApiModelProperty("分类的类型")
    private Integer type;

}
