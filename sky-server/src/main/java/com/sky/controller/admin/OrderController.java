package com.sky.controller.admin;


import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Api(tags = "管理端订单相关接口")
public class OrderController {


    @Autowired
   private OrderService orderService;

    @GetMapping("/conditionSearch")
    @ApiOperation("订单搜索")
    public Result<PageResult> ordersSerch( OrdersPageQueryDTO ordersPageQueryDTO) {

        PageResult p=orderService.page(ordersPageQueryDTO);
        return Result.success(p);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    @ApiOperation("查看订单详情")
    public Result<OrderVO> qurryById(@PathVariable Long id) {

        OrderVO vo = orderService.qurryById(id);
        return Result.success(vo);
    }

    @GetMapping("/statistics")
    @ApiOperation("各个订单数量统计")
    public Result<OrderStatisticsVO> statistics() {

        OrderStatisticsVO orderStatisticsVO=orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {


        orderService.rejection(ordersRejectionDTO);
        return Result.success();
    }
    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) {


        orderService.cancel(ordersCancelDTO);
        return Result.success();
    }
    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {


        orderService.confirm(ordersConfirmDTO);
        return Result.success();
    }
    @PutMapping("/delivery/{id}")
    @ApiOperation("派单")
    public Result delivery(@PathVariable Long id) {

        orderService.delivery(id);
        return Result.success();
    }
    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result complete(@PathVariable Long id) {

        orderService.complete(id);
        return Result.success();
    }
    //TODO service逻辑里没有完成相关判断逻辑


}
