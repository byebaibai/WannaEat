package com.homework.getfood.bean;

import java.util.ArrayList;

/**
 * 订单 类
 */
public class OrderBean {
    private Integer orderID; //订单ID
    private String orderTime; //订单时间
    private Integer orderPrice; //订单未打折价格
    private ArrayList<FoodBean> orderFoodList; //订单商品
    private Integer orderActualPrice; //订单打折后价格
    private Boolean hasGroup; //订单中是否含套餐
    private Integer foodNumber; //订单内食物数目
    public OrderBean(int id, String time, int price, ArrayList<FoodBean> list){
        orderID = id;
        orderTime = time;
        orderPrice = price;
        orderFoodList = list;
        hasGroup = checkList();
        foodNumber = getNumber();
    }
    public OrderBean(){

    }
    private Integer getNumber(){
        Integer n = 0;
        for (FoodBean fb : orderFoodList){
            n += fb.getCartNum();
        }
        return n;
    }
    private Boolean checkList(){
        for (FoodBean fb : orderFoodList){
            if (fb.getGroup()) return true;
        }
        return false;
    }

    public Boolean getHasGroup() {
        return hasGroup;
    }

    public Integer getOrderActualPrice() {
        if (orderActualPrice == null) orderActualPrice = orderPrice;
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

    public ArrayList<FoodBean> getOrderFoodList() {
        return orderFoodList;
    }

    public Integer getFoodNumber() {
        return foodNumber;
    }

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void setFoodNumber(Integer foodNumber) {
        this.foodNumber = foodNumber;
    }

    public void setHasGroup(Boolean hasGroup) {
        this.hasGroup = hasGroup;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public void setOrderFoodList(ArrayList<FoodBean> orderFoodList) {
        this.orderFoodList = orderFoodList;
    }
}
