package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
public class ShopController {

//    @Autowired
//    private ShopService shopService;
//
//    @ApiOperation("获取营业状态")
//    @GetMapping("/status")
//    public Result<Integer> getStatus(){
//
//        Integer a= shopService.getStatus();
//        return  Result.success(a);
//    }
//
//    @ApiOperation("设置营业状态")
//    @GetMapping("/{status}")
//    public Result<Integer> getStatus(Integer status){
//
//         shopService.setStatus(status);
//        return  Result.success();
//    }
}
