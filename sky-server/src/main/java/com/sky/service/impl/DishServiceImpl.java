package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public PageResult Page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        List<Dish> dishList=dishMapper.list(dishPageQueryDTO);
        Page<Dish> p = (Page<Dish>) dishList;
        long total = p.getTotal();

        List<DishVO> dishVOList=new ArrayList<>();
        for (Dish dish : dishList) {
            DishVO dishVO=new DishVO();
            BeanUtils.copyProperties(dish,dishVO);

            String categoryname= categoryMapper.getCategoryNameByCategoryid(dish.getCategoryId());
            dishVO.setCategoryName(categoryname);

            List<DishFlavor> flavors=dishFlavorMapper.getFlavorsByDishid(dish.getId());
            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }



        PageResult pageResult=new PageResult(total,dishVOList);
        return  pageResult;
    }

    @Override
    public List<DishVO> getListByCategoryId(Long categoryId) {
       List<Dish> dishList= dishMapper.getByCategoryId(categoryId);
        List<DishVO> dishVOList=new ArrayList<>();
        for (Dish dish : dishList) {
            DishVO dishVO=new DishVO();
            BeanUtils.copyProperties(dish,dishVO);

            String categoryname= categoryMapper.getCategoryNameByCategoryid(dish.getCategoryId());
            dishVO.setCategoryName(categoryname);

            List<DishFlavor> flavors=dishFlavorMapper.getFlavorsByDishid(dish.getId());
            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }
        return  dishVOList;
    }

    @Override
    public DishVO getById(Long id) {
        Dish dish=dishMapper.getById(id);
        DishVO dishVO=new DishVO();
        BeanUtils.copyProperties(dish,dishVO);

        String categoryname= categoryMapper.getCategoryNameByCategoryid(dish.getCategoryId());
        dishVO.setCategoryName(categoryname);

        List<DishFlavor> flavors=dishFlavorMapper.getFlavorsByDishid(id);
        dishVO.setFlavors(flavors);

        return dishVO;
    }

    @AutoFill(OperationType.UPDATE)
    @Override
    public void enableOrDisable(Integer status, Long id) {
        Dish dish=new Dish();
        dish.setStatus(status);
        dish.setId(id);
        dishMapper.update(dish);
    }


}
