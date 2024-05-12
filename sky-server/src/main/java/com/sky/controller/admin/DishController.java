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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        Dish dish=new Dish();
        dish.setId(id);
        DishVO dishVO=dishService.getById(dish);

        return Result.success(dishVO);
    }
    @ApiOperation("根据分类查询菜品")
    @GetMapping("/list")
    public Result<List<DishVO>> getListByCategoryId(Long categoryId) {
        Dish dish=new Dish();
        dish.setCategoryId(categoryId);
        List<DishVO> dishListVOs=dishService.getListByCategoryId(dish);

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
        cleanCache("dish_*");
        return Result.success();
    }

    @ApiOperation("新增菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        dishService.save(dishDTO);
        String key="dish_"+dishDTO.getCategoryId();
        cleanCache(key);
        return  Result.success();
    }

    @ApiOperation("修改菜品")
    @PutMapping
    public Result  modify(@RequestBody DishDTO dishDTO) {
        dishService.modify(dishDTO);
        cleanCache("dish_*");
        return  Result.success();
    }
    @ApiOperation("批量删除菜品")
    @DeleteMapping
    public Result  deletes(@RequestParam List<Long> ids) {
        dishService.deletes(ids);
        cleanCache("dish_*");
        return  Result.success();
    }

    private  void  cleanCache(String pattern){
        Set keys=redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
