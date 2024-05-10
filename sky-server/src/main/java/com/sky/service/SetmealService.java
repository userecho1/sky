package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    void saveSetmeal(SetmealDTO setmealDTO);

    void startOrstop(Integer status, Long id);

    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);


    SetmealVO getById(Long id);

    void modify(SetmealDTO setmealDTO);

    void deleteByIds(List<Long> ids);
}
