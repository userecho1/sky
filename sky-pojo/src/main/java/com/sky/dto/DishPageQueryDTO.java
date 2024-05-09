package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "分页查询菜品数据模型")
@Data
public class DishPageQueryDTO implements Serializable {

    @ApiModelProperty("菜品分页查询页数")
    private int page;
    @ApiModelProperty("菜品分页查询每页数")
    private int pageSize;
    @ApiModelProperty("菜品名")
    private String name;

    //分类id
    @ApiModelProperty("菜品所属分类")
    private Integer categoryId;

    //状态 0表示禁用 1表示启用
    @ApiModelProperty("菜品状态")
    private Integer status;

}
