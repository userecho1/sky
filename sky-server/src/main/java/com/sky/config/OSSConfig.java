package com.sky.config;


import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConfig {

    @Bean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){

        AliOssUtil aliOssUtil=new AliOssUtil(aliOssProperties.getEndpoint(), aliOssProperties.getBucketName());
        return aliOssUtil;
    }
}
