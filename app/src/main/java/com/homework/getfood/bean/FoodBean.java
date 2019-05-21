package com.homework.getfood.bean;


import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;


import java.io.Serializable;
import java.math.BigDecimal;

public class FoodBean implements Serializable{

	private int id;
	private String name;//名
	private String isCommand;//是否推荐
	private BigDecimal price;//价格
	private String cut;//打折
	private String type;//类
	private int icon;//图片
	private long selectCount;

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

	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
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
