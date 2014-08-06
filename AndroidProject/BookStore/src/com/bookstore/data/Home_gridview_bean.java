package com.bookstore.data;

public class Home_gridview_bean { // 在GetHotSaleBook 和 GetHobbyBook 中使用
	private int i;//gridview位置id
	private String book_id;//书本 id
	private String book_img;//图书略缩图
	private String sales_volume; //销量
	public String getSales_volume() {
		return sales_volume;
	}
	public void setSales_volume(String sales_volume) {
		this.sales_volume = sales_volume;
	}
	public String getBook_img() {
		return book_img;
	}
	public void setBook_img(String book_img) {
		this.book_img = book_img;
	}
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
}
