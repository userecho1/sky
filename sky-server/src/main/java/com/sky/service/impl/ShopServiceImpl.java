package com.sky.service.impl;

import com.sky.mapper.ShopMapper;
import com.sky.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public Integer getStatus() {
        Integer a=shopMapper.getStatus();
        return a;
    }

    @Override
    public void setStatus(Integer status) {
        shopMapper.setStatus(status);
    }
}
