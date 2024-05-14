package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.list;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    @Transactional
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {

       ShoppingCart shoppingCart= new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> shoppingCarts =shoppingCartMapper.list(shoppingCart);
        if(shoppingCarts!=null&&shoppingCarts.size()>0){
            ShoppingCart cart=shoppingCarts.get(0);
            cart.setNumber(cart.getNumber()+1);
            shoppingCartMapper.update(cart);
        }else {
            Long dishId=shoppingCartDTO.getDishId();
            if(dishId!=null){
                Dish dish=new Dish();
                dish.setId(dishId);
                Dish dish1=dishMapper.getOne(dish);

                shoppingCart.setName(dish1.getName());
                shoppingCart.setImage(dish1.getImage());
                shoppingCart.setAmount(dish1.getPrice());


            }else {
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal=setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());


            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);


        }
    }

    @Override
    public List<ShoppingCart> showShoppingCart() {
        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    @Override
    public void cleanShoppingCart() {
        Long userId=BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart=new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());


        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if(list==null||list.size()==0){
            return;
        }
        Integer a=list.get(0).getNumber();
        if(a==1){
            shoppingCartMapper.delete(shoppingCart);
        }else {
            ShoppingCart shoppingCart1=list.get(0);
            shoppingCart1.setNumber(a-1);

            shoppingCartMapper.update(shoppingCart1);
        }

    }
}
