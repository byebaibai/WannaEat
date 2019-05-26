package com.homework.getfood.bean;

import java.util.ArrayList;
import com.alibaba.fastjson.JSON;
public class OrderBean {
    private Integer orderID;
    private String orderTime;
    private Integer orderPrice;
    private Integer orderCouponType;
    private ArrayList<FoodBean> orderFoodList;
    private Integer orderActualPrice;
    public OrderBean(int id,String time, int price, int couponType, ArrayList<FoodBean> list){
        orderID = id;
        orderTime = time;
        orderPrice = price;
        orderCouponType = couponType;
        orderFoodList = list;
    }
    public OrderBean(){

    }

    public Integer getOrderActualPrice() {
        return orderActualPrice;
    }

    public void setOrderActualPrice(Integer orderActualPrice) {
        this.orderActualPrice = orderActualPrice;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public Integer getOrderCouponType() {
        return orderCouponType;
    }

    public void setOrderCouponType(Integer orderCouponType) {
        this.orderCouponType = orderCouponType;
    }

    public ArrayList<FoodBean> getOrderFoodList() {
        return orderFoodList;
    }

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderFoodList(ArrayList<FoodBean> orderFoodList) {
        this.orderFoodList = orderFoodList;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
