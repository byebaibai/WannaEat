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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.util.Log;
import java.io.FileWriter;
public class AppContext extends Application {
    private static ArrayList<FoodBean> foodData = new ArrayList<FoodBean>();
    private static HashMap<String, FoodBean> foodMap = new HashMap<String, FoodBean>();
    private static HashMap<String, FoodBean> cartMap = new HashMap<String, FoodBean>();
    private static ArrayList<OrderBean> orderBeanArrayList = new ArrayList<OrderBean>();
    private static int typeNum;
    private static String JsonString;
    private static AppContext mInstance;
    private static String packageName;
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
        JsonString = getJson("OrderData.json");
        List<OrderBean> ob = JSON.parseArray(JsonString,OrderBean.class);
        orderBeanArrayList = new ArrayList<OrderBean>(ob);
        System.out.println(orderBeanArrayList.size());
    }

    private String getJson(String fileName){
        try {
            File f = new File("/data/data/" + getPackageName() + "/" + fileName);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            return mResponse;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "[]";
        }
    }
    public static void updateOrder(OrderBean ob){
        String s = JsonUtils.parseObjToJson(ob);
        orderBeanArrayList.add(ob);
//        System.out.println(JsonString);
        if(orderBeanArrayList.size() == 0){
            JsonString = "[" + s +  "]";
        }else {
            int last = JsonString.lastIndexOf("]");
            JsonString = JsonString.substring(0, last) + "," + s + "]";
        }
//        System.out.println(JsonString);
//        List<OrderBean> obList = JSON.parseArray(JsonString,OrderBean.class);
//        orderBeanArrayList = new ArrayList<OrderBean>(obList);
//        System.out.println(orderBeanArrayList.size());
//        System.out.println(s);
        try {
            FileWriter file = new FileWriter("/data/data/" +  packageName + "/" + "OrderData.json");
            file.write(JsonString);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updateRemove(){
        String s = JSON.toJSONString(orderBeanArrayList);
        JsonString = s;
        try {
            FileWriter file = new FileWriter("/data/data/" +  packageName + "/" + "OrderData.json");
            file.write(JsonString);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
