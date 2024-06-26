package com.sky.controller.admin;


import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
public class ShopController {

    public  static final String KEY ="SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("获取营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus(){

        Integer a= (Integer) redisTemplate.opsForValue().get(KEY);
        return  Result.success(a);
    }

    @ApiOperation("设置营业状态")
    @PutMapping("/{status}")
    public Result<Integer> getStatus(@PathVariable Integer status){
        log.info("设置店铺营业状态：{}",status== StatusConstant.ENABLE ? "营业中": "打烊中");
        redisTemplate.opsForValue().set(KEY,status);

        return  Result.success();
    }
}
