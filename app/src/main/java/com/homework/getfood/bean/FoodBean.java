package com.homework.getfood.bean;


import android.app.Application;
import android.content.Context;

import com.homework.getfood.R;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class FoodBean extends Application implements Serializable {

	private int id;
	private String name;//名
	private String isCommand;//是否推荐
	private Integer price;//价格
	private String cut;//打折
	private String type;//类
	private int icon;//图片
	private int typeID;
	private long selectCount;

	public FoodBean(){

	}
	@Override
	public void onCreate() {
		super.onCreate();
	}
	public FoodBean(String ID, String name, String price, String type, String typeID, int imageID){
		id = Integer.parseInt(ID);
		this.name = name;
		this.price = Integer.parseInt(price);
		this.type = type;
		this.typeID = Integer.parseInt(typeID);
		icon = imageID;
	}
	public long getSelectCount() {
		return selectCount;
	}

	public void setSelectCount(long selectCount) {
		this.selectCount = selectCount;
	}

	public int getIcon() {
		return icon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsCommand() {
		return isCommand;
	}

	public void setIsCommand(String isCommand) {
		this.isCommand = isCommand;
	}

	public Integer getPrice() {
		return price;
	}

	public void setTypeID(int id) {
		this.typeID = id;
	}

	public int getTypeID() { return typeID;}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getCut() {
		return cut;
	}

	public void setCut(String cut) {
		this.cut = cut;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
