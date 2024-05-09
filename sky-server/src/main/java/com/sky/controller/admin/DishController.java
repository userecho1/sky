package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;

//    @ApiOperation("根据id查询菜品")
//    @GetMapping("/{id}")
//    public Result<Dish> getById(@PathVariable int id) {
//        Dish dish=dishService.getById();
//
//        return Result.success(dish);
//    }

    @ApiOperation("分页查询菜品")
    @GetMapping("/page")
    public  Result<PageResult> Page(DishPageQueryDTO dishPageQueryDTO) {
        PageResult p=dishService.Page(dishPageQueryDTO);

        return Result.success(p);
    }
}
