package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        return null;

    }

    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        LocalDate currentDate = begin;
        while (!currentDate.isAfter(end)) {
            dateList.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        List<Double> turnoverList=new ArrayList<>();
        for (LocalDate date : dateList) {

            LocalDateTime begintime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endtime = LocalDateTime.of(date, LocalTime.MAX);

            Map map=new HashMap();
            map.put("begintime", begintime);
            map.put("endtime", endtime);
            map.put("status", Orders.COMPLETED);
            Double turnover=orderMapper.sumByMap(map);

            turnover=turnover==null?0:turnover;
            turnoverList.add(turnover);
        }




        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();


    }
}
//List<LocalDate> dateList = new ArrayList<>();
//        LocalDate currentDate = begin;
//        while (!currentDate.isAfter(end)) {
//            dateList.add(currentDate);
//            currentDate = currentDate.plusDays(1);
//        }
//        UserReportVO userReportVO = new UserReportVO();
//        userReportVO.setDateList(StringUtils.join(dateList,","));
////       LocalDateTime a= LocalDateTime.of(dateList.get(0), LocalTime.MIN);
////       LocalDateTime b= LocalDateTime.of(dateList.get(dateList.size()), LocalTime.MAX);
//
//
//        List<Map<LocalDate,Integer>> newUserlistAndLocaldate=userMapper.getByBetweenTimeAandB(begin,end);
//        System.out.println(newUserlistAndLocaldate);
//        Integer i=0;
//        List<Integer> newUserlist =new ArrayList<>();
//        LocalDate f=begin;
//        while (!f.isAfter(end)&&i<newUserlistAndLocaldate.size()) {
//            System.out.println(newUserlistAndLocaldate.get(i).get(f).toString());
//            if (newUserlistAndLocaldate.get(i).get("date").toString()==f.toString()){
//                newUserlist.add(0);
//            } else {
//                newUserlist.add(newUserlistAndLocaldate.get(i).get("count"));
//                i++;
//            }
//            f=f.plusDays(1);
//
//        }
//
//
//        userReportVO.setNewUserList(StringUtils.join(newUserlist,","));
//
//        LocalDateTime a=LocalDateTime.of(begin,LocalTime.MIN);
//        Integer all=userMapper.getAllByLessTimeA(a);
//        if(all==null){
//            all=0;
//        }
//
//        List<Integer> AllUserlist=new ArrayList<>();
//        Integer j=0;
//        while (!f.isAfter(end)&&j<newUserlistAndLocaldate.size()) {
//            if (newUserlistAndLocaldate.get(i).get(f) == null) {
//                AllUserlist.add(all);
//            } else {
//                AllUserlist.add(newUserlistAndLocaldate.get(i).get(f));
//                all=all+newUserlistAndLocaldate.get(i).get(f);
//                j++;
//            }
//            f=f.plusDays(1);
//
//        }
//        userReportVO.setTotalUserList(StringUtils.join(AllUserlist,","));
//
//
//        return userReportVO;