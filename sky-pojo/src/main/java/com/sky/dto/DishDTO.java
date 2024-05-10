package com.sky.dto;

import com.sky.entity.DishFlavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "新增菜品传输模型")
public class DishDTO implements Serializable {

    private Long id;
    //菜品名称
    @ApiModelProperty("菜品名称")
    private String name;
    //菜品分类id
    @ApiModelProperty("菜品所属分类")
    private Long categoryId;
    //菜品价格
    @ApiModelProperty("菜品价格")
    private BigDecimal price;
    //图片
    @ApiModelProperty("菜品图片链接")
    private String image;
    //描述信息
    @ApiModelProperty("菜品描述")
    private String description;
    //0 停售 1 起售
    @ApiModelProperty("菜品状态")
    private Integer status;
    //口味
    @ApiModelProperty("菜品风味")
    private List<DishFlavor> flavors = new ArrayList<>();

}
