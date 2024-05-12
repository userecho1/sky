package com.sky.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String wx_Login="https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {

        String openId=getOpenid(userLoginDTO.getCode());

        if(openId==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user=userMapper.getByOpenid(openId);

        if(user==null){
            user=User.builder()
                    .openid(openId)
                    .createTime(LocalDateTime.now())//不用aop是因为这一项没有更新时间
                    .build();
            userMapper.insert(user);
        }
        return user;
    }
//调用微信接口服务获取openid
    private String getOpenid(String code){
        HashMap<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("grant_type","authorization_code");
        map.put("js_code",code);
        String json= HttpClientUtil.doGet((wx_Login),map);

        JSONObject jsonObject = JSON.parseObject(json);
        String openId=jsonObject.getString("openid");
        return openId;
    }
}
