package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
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
}
