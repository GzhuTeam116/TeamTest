package com.bookstore.data;

import android.util.Log;


public class ShopCartInfo {

	private String shopcartid;
	private String book_id;
	private String book_img;
	private String book_name;
	private String book_price;
	private String book_count;
	
	@Override
	public String toString() {
		Log.v("test", "tostring");
		return "ShopCartInfo [shopcartid=" + shopcartid + ", book_id="
				+ book_id + ", book_img=" + book_img + ", book_name="
				+ book_name + ", book_price=" + book_price + ", book_count="
				+ book_count + "]";
	}
	public String getShopcartid() {
		return shopcartid;
	}
	public void setShopcartid(String shopcartid) {
		this.shopcartid = shopcartid;
	}
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
	public String getBook_price() {
		return book_price;
	}
	public void setBook_price(String book_price) {
		this.book_price = book_price;
	}
	public String getBook_count() {
		return book_count;
	}
	public void setBook_count(String book_count) {
		this.book_count = book_count;
	}
	
}
