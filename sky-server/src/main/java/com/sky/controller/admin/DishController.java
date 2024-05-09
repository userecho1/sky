package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;

    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        DishVO dishVO=dishService.getById(id);

        return Result.success(dishVO);
    }
    @ApiOperation("根据分类查询菜品")
    @GetMapping("/list")
    public Result<List<DishVO>> getListByCategoryId(Long categoryId) {
        List<DishVO> dishListVOs=dishService.getListByCategoryId(categoryId);

        return Result.success(dishListVOs);
    }

    @ApiOperation("分页查询菜品")
    @GetMapping("/page")
    public  Result<PageResult> Page(DishPageQueryDTO dishPageQueryDTO) {
        PageResult p=dishService.Page(dishPageQueryDTO);

        return Result.success(p);
    }

    @ApiOperation("菜品起售停售")
    @PostMapping("status/{status}")
    public Result enableOrDisable(@PathVariable Integer status, Long id) {
        dishService.enableOrDisable(status,id);
        return Result.success();
    }
}
