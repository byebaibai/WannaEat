package com.homework.getfood.bean;

/**
 * 食物 类
 */
public class FoodBean{
	private int id; //食物ID
	private String name;//食物名称
	private Integer price;//食物价格
	private String type;//食物类别
	private String icon;//食物图片
	private Boolean isSpicy; //食物是否辣
	private int typeID; //种类ID
	private Integer cartNum; //购物车内数目
	private String detail; //食物描述
	private Boolean isGroup; //是否为套餐
	public FoodBean(){

	}
	public FoodBean(String ID, String name, String price, String type, String typeID, String imageID,Boolean spicy,Boolean isGroup, String detail){
		cartNum = 0;
		isSpicy = spicy;
		id = Integer.parseInt(ID);
		this.name = name;
		this.price = Integer.parseInt(price);
		this.type = type;
		this.detail = detail;
		this.isGroup = isGroup;
		this.typeID = Integer.parseInt(typeID);
		icon = imageID;
	}
	public FoodBean(int ID,int typeId,String type,String name,Integer price, String imageID, int cartNum, Boolean isGroup, String detail){
		id = ID;
		typeID = typeId;
		this.name = name;
		icon = imageID;
		this.cartNum = cartNum;
		this.type = type;
		this.price = price;
		this.detail = detail;
		this.isGroup = isGroup;
 	}

	public Boolean getGroup() {
		return isGroup;
	}

	public String getDetail() {
		return detail;
	}

	public Boolean getSpicy() {
		return isSpicy;
	}


	public Integer getCartNum() {
		return cartNum;
	}

	public void setCartNum(Integer cartNum) {
		this.cartNum = cartNum;
	}

	public void updateCartNum(int t){
		cartNum += t;
	}


	public String getIcon() {
		return icon;
	}

	public int getId() {
		return id;
	}

	public Integer getPrice() {
		return price;
	}

	public int getTypeID() {
		return typeID;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGroup(Boolean group) {
		isGroup = group;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSpicy(Boolean spicy) {
		isSpicy = spicy;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
