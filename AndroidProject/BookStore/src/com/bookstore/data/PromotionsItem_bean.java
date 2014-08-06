package com.bookstore.data;

public class PromotionsItem_bean {//获取促销信息列表 
	private int id;
	private String book_id;//商品id
	private String book_img;////商品图片
	private String book_name;//商品名称
	private String book_promotions_price;//商品促销价格
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getBook_img() {
		return book_img;
	}
	public void setBook_img(String book_img) {
		this.book_img = book_img;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public String getBook_promotions_price() {
		return book_promotions_price;
	}
	public void setBook_promotions_price(String book_promotions_price) {
		this.book_promotions_price = book_promotions_price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
