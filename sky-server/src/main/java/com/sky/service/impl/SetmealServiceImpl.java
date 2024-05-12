package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;


    @Override
    @Transactional
    public void saveSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);


        setmeal.setStatus(StatusConstant.DISABLE);
        setmealMapper.insert(setmeal);


        List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();
        Long setmealId = setmeal.getId();


        for (SetmealDish setmealDish : setmealDishList) {
            setmealDish.setSetmealId(setmealId);

        }
        if (setmealDishList.size() > 0 && setmealDishList != null) {
            setmealDishMapper.insert(setmealDishList);
        }

    }

    @Override
    @Transactional
    public void startOrstop(Integer status, Long id) {
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        List<SetmealDish> setmealDishList=setmealDishMapper.getBySetmealId(id);
        for (SetmealDish setmealDish : setmealDishList) {
            Dish dish = new Dish();
            dish.setId(setmealDish.getDishId());
            if(dishMapper.getOne(dish).getStatus()==StatusConstant.DISABLE){
                throw  new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        }
        setmealMapper.update(setmeal);

    }

    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        List<Setmeal> setmealList = setmealMapper.getPage(setmealPageQueryDTO);
        Page<Setmeal> p = (Page<Setmeal>) setmealList;
        Long total = p.getTotal();

        List<SetmealVO> setmealVOList = new ArrayList<>();
        for (Setmeal setmeal : setmealList) {
            SetmealVO setmealVO = new SetmealVO();
            BeanUtils.copyProperties(setmeal, setmealVO);

            String categoryName = categoryMapper.getCategoryNameByCategoryid(setmeal.getCategoryId());
            setmealVO.setCategoryName(categoryName);

            List<SetmealDish> setmealDishList = setmealDishMapper.getBySetmealId(setmeal.getId());
            setmealVO.setSetmealDishes(setmealDishList);


            setmealVOList.add(setmealVO);
        }

        PageResult pageResult = new PageResult(total, setmealVOList);
        return pageResult;
    }

    @Override
    public SetmealVO getById(Long id) {
        Setmeal setmeal=setmealMapper.getById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);

        String categoryName = categoryMapper.getCategoryNameByCategoryid(setmeal.getCategoryId());
        setmealVO.setCategoryName(categoryName);

        List<SetmealDish> setmealDishList = setmealDishMapper.getBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishList);
        return  setmealVO;
    }

    @Override
    @Transactional
    public void modify(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);


        setmealDishMapper.deleteBySetmealId(setmeal.getId());

        List<SetmealDish> setmealDishes= setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmeal.getId());
        }
        if(setmealDishes.size()>0&&setmealDishes!=null){
                setmealDishMapper.insert(setmealDishes);
        }

    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            if(setmealMapper.getById(id).getStatus()==StatusConstant.DISABLE) {
                setmealMapper.delete(id);
                setmealDishMapper.deleteBySetmealId(id);
            }else {
                throw  new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }
}
