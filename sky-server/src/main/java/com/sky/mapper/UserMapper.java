
package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("select * from  user where openid=#{openid}")
    User getByOpenid(String openid);

    void insert(User user);

    @Select("select * from user where  id=#{userId}")
    User getById(Long userId);

    Integer sumByMap(Map map);


//
//    List<Map<LocalDate,Integer>> getByBetweenTimeAandB(@Param("begin") LocalDate begin,
//                                                       @Param("end") LocalDate end);
//    @MapKey("date")
//    List<Map<LocalDate, Integer>> getByBetweenTimeAandB(@Param("begin") LocalDate begin, @Param("end") LocalDate end);
//
//    Integer getAllByLessTimeA(LocalDateTime a);
}
