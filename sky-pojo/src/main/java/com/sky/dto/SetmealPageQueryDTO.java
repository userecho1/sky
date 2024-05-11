package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "分页查询套餐的数据模型")
public class SetmealPageQueryDTO implements Serializable {

    @ApiModelProperty("查询套餐页码")
    private int page;

    @ApiModelProperty("查询套餐页内数据条数")
    private int pageSize;

    @ApiModelProperty("查询套餐名")
    private String name;

    //分类id
    @ApiModelProperty("查询套餐所属分类id")
    private Integer categoryId;

    //状态 0表示禁用 1表示启用
    @ApiModelProperty("所需查询套餐状态")
    private Integer status;

}
