package com.homework.getfood.context;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;

import com.alibaba.fastjson.JSON;
import com.homework.getfood.R;
import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.bean.OrderBean;
import com.homework.getfood.util.JsonUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppContext extends Application {
    private static ArrayList<FoodBean> foodData = new ArrayList<FoodBean>();
    private static HashMap<String, FoodBean> foodMap = new HashMap<String, FoodBean>();
    private static HashMap<String, FoodBean> cartMap = new HashMap<String, FoodBean>();
    private static ArrayList<OrderBean> orderBeanArrayList = new ArrayList<OrderBean>();
    private static int typeNum;

    private static AppContext mInstance;

    public static AppContext getContext() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
    public static void setCart(HashMap<String, FoodBean> cmap){
        cartMap = cmap;
    }
    public static HashMap<String, FoodBean> getMap() {
        return foodMap;
    }

    public static int getTypeNum() {
        return typeNum;
    }

    private void initData() {
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
                        String foodName = xrp.nextText();
                        FoodBean fb = new FoodBean(foodID, foodName, price, foodType, foodTypeID, imageURL);
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
        String order = getJson("OrderData.json");
        List<OrderBean> ob = JSON.parseArray(order,OrderBean.class);
        orderBeanArrayList = new ArrayList<OrderBean>(ob);
        System.out.println(orderBeanArrayList.size());
    }

    private String getJson(String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


}
