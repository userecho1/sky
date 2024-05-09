package com.sky.controller.admin;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @ApiOperation("分页查询分类")
    @GetMapping("/page")
    public Result<PageResult>  page(CategoryPageQueryDTO categoryPageQueryDTO) {

        PageResult p =categoryService.page(categoryPageQueryDTO);

        return  Result.success(p);
    }

    @ApiOperation("启用禁用分类")
    @PostMapping("/status/{status}")
    public Result startOrstop(@PathVariable Integer status, Long id) {

        categoryService.startOrstop(status,id);
        return Result.success();
    }

    @ApiOperation("新增分类")
    @PostMapping
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
        return Result.success();
    }

    @ApiOperation("修改分类")
    @PutMapping
    public  Result updateCategory(@RequestBody CategoryDTO categoryDTO) {

        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }
    @ApiOperation("根据id删除分类")
    @DeleteMapping
    public Result deleteCategory(Long id) {
        categoryService.deleteCategory(id);
    return Result.success();
    }


}
