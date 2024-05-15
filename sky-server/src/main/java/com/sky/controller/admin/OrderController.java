package com.sky.controller.admin;


import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
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


}
