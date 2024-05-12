package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

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

    @Override
    public void enableOrDisable(Integer status, Long id) {
        Dish dish=new Dish();
        dish.setStatus(status);
        dish.setId(id);
        dishMapper.update(dish);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(DishDTO dishDTO) {
        //默认不起售
        dishDTO.setStatus(StatusConstant.DISABLE);
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //插入新增菜品并获得菜品id
        dishMapper.insert(dish);
        Dish dish1=dishMapper.getByDishName(dish.getName());

        //风味循环插入菜品id，并插入dishFlavor表
        List<DishFlavor> flavors=dishDTO.getFlavors();
        if(flavors!=null&&flavors.size()>0) {
            for (DishFlavor dishFlavor : flavors) {
                dishFlavor.setDishId(dish1.getId());
                dishFlavorMapper.insert(dishFlavor);
            }
        }


    }

    @Override
    @Transactional
    public void modify(DishDTO dishDTO) {

        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //更新菜品
        dishMapper.update(dish);

        Long dishId=dish.getId();
        dishFlavorMapper.delete(dishId);
        //风味循环插入菜品id，并插入dishFlavor表
        List<DishFlavor> flavors=dishDTO.getFlavors();

        for (DishFlavor dishFlavor : flavors) {
            dishFlavor.setDishId(dishId);

                dishFlavorMapper.insert(dishFlavor);

        }
    }

    @Override
    @Transactional
    public void deletes(List<Long> ids) {
        for (Long id : ids) {
            if(dishMapper.getById(id).getStatus()==StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }else if(setmealDishMapper.getSetmealIdsByDishId(id)!=null) {
                    throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }else {
                dishMapper.delete(id);
                dishFlavorMapper.delete(id);
            }
        }
    }


}
