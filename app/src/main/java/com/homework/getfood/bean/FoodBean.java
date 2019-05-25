package com.homework.getfood.bean;

public class FoodBean{

	private int id;
	private String name;//名
	private Integer price;//价格
	private String type;//类
	private String icon;//图片
	private int typeID;
	private Integer cartNum;
	public FoodBean(){

	}
	public FoodBean(String ID, String name, String price, String type, String typeID, String imageID){
		cartNum = 0;
		id = Integer.parseInt(ID);
		this.name = name;
		this.price = Integer.parseInt(price);
		this.type = type;
		this.typeID = Integer.parseInt(typeID);
		icon = imageID;
	}
	public FoodBean(int ID,int typeId,String type,String name,Integer price, String imageID, int cartNum){
		id = ID;
		typeID = typeId;
		this.name = name;
		icon = imageID;
		this.cartNum = cartNum;
		this.type = type;
		this.price = price;
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
