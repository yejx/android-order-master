package com.yjx.order.bean;

public class Dish {
	private String name;  //菜名
	private String type;  //菜品类型
	private String price; //菜品价格
	private String currency; //货币类型
	private String unit;  //菜品单位
	private String photo; //图片

	public Dish() {
	}

	public Dish(String name, String type, String price, String currency, String unit, String photo) {
		this.name = name;
		this.type = type;
		this.price = price;
		this.currency = currency;
		this.unit = unit;
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
} 