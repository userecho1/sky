package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@RestController
@RequestMapping("/admin/workspace")
@Api(tags = "工作台相关接口")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @ApiOperation("查询今日数据")
    @GetMapping("/businessData")
    public Result<BusinessDataVO> businessData(){
        LocalDateTime endTime= LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        BusinessDataVO businessDataVO = workspaceService.businessData(startTime,endTime);
        return Result.success(businessDataVO);
    }

    @ApiOperation("查询套餐总览")
    @GetMapping("/overviewSetmeals")
    public Result<SetmealOverViewVO> overviewSetmeals(){

        SetmealOverViewVO setmealOverViewVO =workspaceService.overviewSetmeals();
        return Result.success(setmealOverViewVO);
    }

    @ApiOperation("菜品总览")
    @GetMapping("/overviewDishes")
    public Result<DishOverViewVO> overviewDishes(){

        DishOverViewVO dishOverViewVO =workspaceService.overviewDishes();
        return Result.success(dishOverViewVO);
    }

    @ApiOperation("今日订单管理")
    @GetMapping("/overviewOrders")
    public Result<OrderOverViewVO> overviewOrders(){

        OrderOverViewVO orderOverViewVO =workspaceService.overviewOrders();
        return Result.success(orderOverViewVO);
    }
}
