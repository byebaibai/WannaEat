package com.homework.getfood.context;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.XmlResourceParser;

import com.alibaba.fastjson.JSON;
import com.homework.getfood.R;
import com.homework.getfood.bean.CouponBean;
import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.bean.OrderBean;
import com.homework.getfood.util.JsonUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 全局变量|函数
 */
public class AppContext extends Application {
    private static ArrayList<FoodBean> foodData = new ArrayList<FoodBean>();  //食品数据
    private static HashMap<String, FoodBean> foodMap = new HashMap<String, FoodBean>(); //食品数据
    private static HashMap<String, FoodBean> cartMap = new HashMap<String, FoodBean>(); //购物车数据
    private static ArrayList<OrderBean> orderBeanArrayList = new ArrayList<OrderBean>(); //本地订单数据
    private static int typeNum;
    private static String JsonString;
    private static AppContext mInstance;
    private static String packageName;
    private static ArrayList<CouponBean> CouponeList; //优惠券数据
    public static AppContext getContext() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        packageName = getApplicationContext().getPackageName();
        mInstance = this;
        initData();
    }

    public static ArrayList<OrderBean> getOrderBeanArrayList() {
        return orderBeanArrayList;
    }

    public static ArrayList<FoodBean> getData() {
        return foodData;
    }

    public static HashMap<String, FoodBean> getCart(){
        return cartMap;
    }

    public static HashMap<String, FoodBean> getMap() {
        return foodMap;
    }

    public static int getTypeNum() {
        return typeNum;
    }

    private void initData() {
        CouponeList = new ArrayList<CouponBean>();
        CouponeList.add(new CouponBean(0));
        CouponeList.add(new CouponBean(1));
        CouponeList.add(new CouponBean(2)); //添加三种优惠券
        getFoodData();
        JsonString = getJson();
        List<OrderBean> ob = JSON.parseArray(JsonString,OrderBean.class); //获得本地订单列表
        orderBeanArrayList = new ArrayList<OrderBean>(ob);
    }

    /**
     * 获得本地食品数据
     */
    private void getFoodData(){
        XmlResourceParser xrp = getResources().getXml(R.xml.food);
        try {
            //还没有到XML文档的结尾处
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                //如果遇到了开始标签
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    //获取该标签的标签名
                    String tagName = xrp.getName();
                    if (tagName.equals("food")) {
                        //根据属性名获取属性值
                        String foodID = xrp.getAttributeValue(null, "id");
                        String foodTypeID = xrp.getAttributeValue(null, "typeID");
                        String foodType = xrp.getAttributeValue(null, "type");
                        String price = xrp.getAttributeValue(null, "price");
                        String imageURL = xrp.getAttributeValue(null, "imageURL");
                        Integer canspicy = Integer.parseInt(xrp.getAttributeValue(null, "spicy"));
                        Integer isGroup = Integer.parseInt(xrp.getAttributeValue(null, "group"));
                        String detail = xrp.getAttributeValue(null, "detail");
                        Boolean groupFlag = false;
                        if (isGroup == 1) groupFlag = true;
                        Boolean spicyFlag = false;
                        if (canspicy == 1) spicyFlag = true;
                        String foodName = xrp.nextText();
                        FoodBean fb = new FoodBean(foodID, foodName, price, foodType, foodTypeID, imageURL, spicyFlag,groupFlag,detail);
                        foodData.add(fb);
                        foodMap.put(fb.getName(), fb);
                        if(fb.getTypeID() > typeNum) typeNum = fb.getTypeID();
                    }
                }
                //获取解析器的下一个事件
                xrp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取本地订单数据
     * @return 本地订单数据
     */
    private String getJson(){
        try {
            @SuppressLint("SdCardPath") File f = new File("/data/data/" + getPackageName() + "/" + "OrderData.json");
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            return mResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    /**
     * 加入新的订单数据，同时保存在本地文件中
     * @param ob 新的订单数据
     */
    public static void updateOrder(OrderBean ob){
        String s = JsonUtils.parseObjToJson(ob); //将订单资料转化为Json字符串
        orderBeanArrayList.add(ob);
        if(orderBeanArrayList.size() == 0){
            JsonString = "[" + s +  "]";
        }else {
            int last = JsonString.lastIndexOf("]");
            JsonString = JsonString.substring(0, last) + "," + s + "]";
        }
        try {
            @SuppressLint("SdCardPath") FileWriter file = new FileWriter("/data/data/" +  packageName + "/" + "OrderData.json");
            file.write(JsonString);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    /**
     * 删除某订单后，更新本地订单数据
     */
    public static void updateRemove(){
        JsonString = JSON.toJSONString(orderBeanArrayList); //将订单资料转化为Json字符串
        try {
            @SuppressLint("SdCardPath") FileWriter file = new FileWriter("/data/data/" +  packageName + "/" + "OrderData.json");
            file.write(JsonString);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    /**
     * 根据商品总价格和商品中是否含有套餐返回商品可用的优惠券
     * @param price 商品总价格
     * @param isGroup 商品中是否含套餐
     * @return 可以使用的优惠券
     */
    public static ArrayList<CouponBean> getCouponeList(Integer price,Boolean isGroup) {
        ArrayList<CouponBean> cp = new ArrayList<CouponBean>();
        if (isGroup) return cp;
        if(price < 100) return cp;
        else if(price < 200){
            cp.add(CouponeList.get(0));
            return cp;
        }else if(price < 300){
            cp.add(CouponeList.get(0));
            cp.add(CouponeList.get(1));
            return cp;
        }else {
            cp.add(CouponeList.get(0));
            cp.add(CouponeList.get(2));
            return cp;
        }
    }

}
