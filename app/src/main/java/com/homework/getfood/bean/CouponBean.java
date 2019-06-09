package com.homework.getfood.bean;

/**
 * 优惠券 类
 */
public class CouponBean {
    private int type; //优惠券种类
    private int minCost; //优惠券使用的最低价格
    private String name; //优惠券名称
    public CouponBean(int type){
        this.type = type;
        if (type == 0){
            name = "满 100 减 3 元";
            minCost = 100;
        }else if (type == 1){
            name = "满 200 打 9.5 折";
            minCost = 200;
        }else {
            name = "满 300 打 9 折";
            minCost = 300;
        }
    }

    /**
     * 根据输入价格算出优惠价格
     * @param tot 输入价格
     * @return 优惠价格
     */
    public Integer getCost(int tot){
        if (type == 0) return 3;
        else if (type == 1){
            Double t = tot * 0.05;
            return t.intValue();
        }else if (type == 2){
            Double t = tot * 0.1;
            return t.intValue();
        }else return -1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public int getMinCost() {
        return minCost;
    }

    public int getType() {
        return type;
    }
}
