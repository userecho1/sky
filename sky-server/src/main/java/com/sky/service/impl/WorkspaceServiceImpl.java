package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private DishMapper dishMapper;

    @Override
    public BusinessDataVO businessData() {
        LocalDateTime endTime= LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Map map=new HashMap();
        map.put("begintime", startTime);
        map.put("endtime", endTime);



        Map map1=new HashMap();
        map1.put("begintime", startTime);
        map1.put("endtime", endTime);
        map1.put("status", Orders.COMPLETED);
        Integer newUsers=userMapper.sumByMap(map);
        if(newUsers==null){
            newUsers=0;
        }
        Double  turnover=orderMapper.sumByMap(map1);
        if(turnover==null){
            turnover=0.0;
        }
        Integer totalOrderCount=orderMapper.sumNumberByMap(map);
        Integer validOrderCount=orderMapper.sumNumberByMap(map1);


        if(totalOrderCount==null){

            Double unitPrice=0.0;
            validOrderCount=0;
            Double orderCompletionRate=0.0;
            return BusinessDataVO.builder()
                    .newUsers(newUsers)
                    .validOrderCount(validOrderCount)
                    .turnover(turnover)
                    .orderCompletionRate(orderCompletionRate)
                    .unitPrice(unitPrice)
                    .build();
        }else {
            if (validOrderCount == null) {
                validOrderCount = 0;
                Double orderCompletionRate=0.0;
                Double unitPrice=0.0;
                return BusinessDataVO.builder()
                        .newUsers(newUsers)
                        .validOrderCount(validOrderCount)
                        .turnover(turnover)
                        .orderCompletionRate(orderCompletionRate)
                        .unitPrice(unitPrice)
                        .build();
            }else {
                Double  orderCompletionRate=validOrderCount.doubleValue()/totalOrderCount.doubleValue();
                Double  unitPrice=turnover.doubleValue()/validOrderCount.doubleValue();


                return BusinessDataVO.builder()
                        .newUsers(newUsers)
                        .validOrderCount(validOrderCount)
                        .turnover(turnover)
                        .orderCompletionRate(orderCompletionRate)
                        .unitPrice(unitPrice)
                        .build();
            }



        }


    }

    @Override
    public SetmealOverViewVO overviewSetmeals() {
        Integer  disable= StatusConstant.DISABLE;
        Integer  enable=StatusConstant.ENABLE;
        Integer discontinued=setmealMapper.getCountByStatus(disable);
        Integer sold=setmealMapper.getCountByStatus(enable);


        return SetmealOverViewVO.builder()
                .discontinued(discontinued)
                .sold(sold)
                .build();
    }

    @Override
    public DishOverViewVO overviewDishes() {
        Integer  disable= StatusConstant.DISABLE;
        Integer  enable=StatusConstant.ENABLE;
        Integer discontinued=dishMapper.getCountByStatus(disable);
        Integer sold=dishMapper.getCountByStatus(enable);
        return DishOverViewVO.builder()
                .discontinued(discontinued)
                .sold(sold)
                .build();
    }

    @Override
    public OrderOverViewVO overviewOrders() {
        LocalDateTime endTime= LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Map map=new HashMap();
        map.put("begintime", startTime);
        map.put("endtime", endTime);
        Integer allOrders= orderMapper.sumNumberByMap(map);
        if(allOrders==null){
            allOrders=0;
        }
        map.put("status", Orders.CANCELLED);
        Integer  cancelledOrders=orderMapper.sumNumberByMap(map);
        if(cancelledOrders==null){
            cancelledOrders=0;
        }
        map.put("status", Orders.COMPLETED);
        Integer  completedOrders=orderMapper.sumNumberByMap(map);
        if(completedOrders==null){
            completedOrders=0;
        }
        map.put("status", Orders.DELIVERY_IN_PROGRESS);
        Integer deliveredOrders=orderMapper.sumNumberByMap(map);
        if(deliveredOrders==null){
            deliveredOrders=0;
        }
        map.put("status",Orders.TO_BE_CONFIRMED);
        Integer  waitingOrders=orderMapper.sumNumberByMap(map);
        if(waitingOrders==null){
            waitingOrders=0;
        }


        return OrderOverViewVO.builder()
                .allOrders(allOrders)
                .cancelledOrders(cancelledOrders)
                .completedOrders(completedOrders)
                .deliveredOrders(deliveredOrders)
                .waitingOrders(waitingOrders)
                .build();
    }
}
