package com.homework.getfood.bean;

public class FoodBean{

	private int id;
	private String name;//名
	private Integer price;//价格
	private String type;//类
	private String icon;//图片
	private Boolean isSpicy;
	private int typeID;
	private Integer cartNum;
	private String detail;
	private Boolean isGroup;
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

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setGroup(Boolean group) {
		isGroup = group;
	}

	public Boolean getSpicy() {
		return isSpicy;
	}

	public void setSpicy(Boolean spicy) {
		isSpicy = spicy;
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

	public void setIcon(String icon) {
		this.icon = icon;
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

	public void setPrice(Integer price) {
		this.price = price;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

}
