package com.bookstore.data;

public class SearchResult_bean {
	private int id;
	private String book_id;
	private String commodity_icon;
	private String commodity_name;
	private String book_price;
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCommodity_icon() {
		return commodity_icon;
	}
	public void setCommodity_icon(String commodity_icon) {
		this.commodity_icon = commodity_icon;
	}
	public String getBook_price() {
		return book_price;
	}
	public void setBook_price(String book_price) {
		this.book_price = book_price;
	}
	public String getCommodity_name() {
		return commodity_name;
	}
	public void setCommodity_name(String commodity_name) {
		this.commodity_name = commodity_name;
	}
	
	
}
