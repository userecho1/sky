package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "分页查询员工传递的数据模型")
public class EmployeePageQueryDTO implements Serializable {

    //员工姓名
    @ApiModelProperty("查询的员工姓名")
    private String name;

    //页码
    @ApiModelProperty("查询员工所用的页码")
    private int page;

    //每页显示记录数
    @ApiModelProperty("查询员工每页显示的条数")
    private int pageSize;

}
