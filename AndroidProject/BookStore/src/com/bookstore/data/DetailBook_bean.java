package com.bookstore.data;

public class DetailBook_bean {//获取商品详情信息  然后显示 
	private String book_id;//商品id
	private String book_img;//商品图片
	private String book_price;//商品价格
	private String book_name;//商品名称
	private String book_author;//作者
	private String book_press;//商品出版社
	private String introduction;//商品简介
	private String location_area_num;//区域号
	private String location_bookshelf_num;//书架
	public String getBook_img() {
		return book_img;
	}
	public void setBook_img(String book_img) {
		this.book_img = book_img;
	}
	public String getLocation_bookshelf_num() {
		return location_bookshelf_num;
	}
	public void setLocation_bookshelf_num(String location_bookshelf_num) {
		this.location_bookshelf_num = location_bookshelf_num;
	}
	public String getLocation_area_num() {
		return location_area_num;
	}
	public void setLocation_area_num(String location_area_num) {
		this.location_area_num = location_area_num;
	}
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getBook_price() {
		return book_price;
	}
	public void setBook_price(String book_price) {
		this.book_price = book_price;
	}
	public String getBook_author() {
		return book_author;
	}
	public void setBook_author(String book_author) {
		this.book_author = book_author;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public String getBook_press() {
		return book_press;
	}
	public void setBook_press(String book_press) {
		this.book_press = book_press;
	}
	

}
