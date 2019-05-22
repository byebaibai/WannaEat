package com.homework.getfood.context;

import android.app.Application;
import android.content.Context;
import android.content.res.XmlResourceParser;

import com.homework.getfood.R;
import com.homework.getfood.bean.FoodBean;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AppContext extends Application {
    private static ArrayList<FoodBean> foodData = new ArrayList<FoodBean>();
    private static HashMap<String, FoodBean> foodMap = new HashMap<String, FoodBean>();
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

    public static ArrayList<FoodBean> getData() {
        return foodData;
    }

    public static HashMap<String, FoodBean> getMap() {
        return foodMap;
    }

    public static int getTypeNum() {
        return typeNum;
    }

    public void addFood(FoodBean fb) {

    }

    private void initData() {
        XmlResourceParser xrp = getResources().getXml(R.xml.food);
        Context ctx = getBaseContext();
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
                        int imageUrl = getResources().getIdentifier(imageURL, "drawable", ctx.getPackageName());
                        FoodBean fb = new FoodBean(foodID, foodName, price, foodType, foodTypeID, imageUrl);
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
}
