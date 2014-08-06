package com.bookstore.data;

public class OrderListInfo {

	private String orderlist_img;
	private String orderlist_num;
	private String orderlist_date;
	private String orderlist_pirce;
	public OrderListInfo(){
		
	}
	public OrderListInfo(String orderlist_img, String orderlist_num,
			String orderlist_date, String orderlist_pirce) {
		super();
		this.orderlist_img = orderlist_img;
		this.orderlist_num = orderlist_num;
		this.orderlist_date = orderlist_date;
		this.orderlist_pirce = orderlist_pirce;
	}
	public String getOrderlist_img() {
		return orderlist_img;
	}
	public void setOrderlist_img(String orderlist_img) {
		this.orderlist_img = orderlist_img;
	}
	public String getOrderlist_num() {
		return orderlist_num;
	}
	public void setOrderlist_num(String orderlist_num) {
		this.orderlist_num = orderlist_num;
	}
	public String getOrderlist_date() {
		return orderlist_date;
	}
	public void setOrderlist_date(String orderlist_date) {
		this.orderlist_date = orderlist_date;
	}
	public String getOrderlist_pirce() {
		return orderlist_pirce;
	}
	public void setOrderlist_pirce(String orderlist_pirce) {
		this.orderlist_pirce = orderlist_pirce;
	}
	
	
 }
