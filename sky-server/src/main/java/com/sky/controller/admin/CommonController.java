package com.sky.controller.admin;


import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用方法接口")
public class CommonController {

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result<String> fileUpload(@RequestBody MultipartFile file) {

        return Result.error("ss");
    }
}
