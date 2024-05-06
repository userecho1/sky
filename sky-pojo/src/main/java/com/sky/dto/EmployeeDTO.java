package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "添加员工传递的数据模型")
public class EmployeeDTO implements Serializable {

    @ApiModelProperty("自增id")
    private Long id;
    @ApiModelProperty("员工账号名")
    private String username;
    @ApiModelProperty("员工名")
    private String name;
    @ApiModelProperty("员工手机号")
    private String phone;
    @ApiModelProperty("员工性别")
    private String sex;
    @ApiModelProperty("员工身份证")
    private String idNumber;

}
