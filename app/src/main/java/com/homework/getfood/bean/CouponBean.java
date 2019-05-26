package com.homework.getfood.bean;

public class CouponBean {
    private int type;
    private int minCost;
    private String name;
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
    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
