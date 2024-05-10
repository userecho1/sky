package com.sky.config;


import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConfig {


    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){

        AliOssUtil aliOssUtil=new AliOssUtil();

    }
}
