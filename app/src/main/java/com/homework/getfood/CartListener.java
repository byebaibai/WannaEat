package com.homework.getfood;

import com.homework.getfood.bean.FoodBean;

public interface CartListener {
    //void onUpdateDetailList(FoodBean product, String type);

    void onRemoveFood(FoodBean product);
    void updateFood();
}
