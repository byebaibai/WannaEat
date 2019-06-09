package com.homework.getfood.util;

import com.homework.getfood.bean.FoodBean;

/**
 * 购物车监听器
 */
public interface CartListener {
    void onRemoveFood(FoodBean product);
    void updateFood();
}
