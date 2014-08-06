package com.bookstore.extra;

public class Product {
	public static final String notify_url = "";//支付宝服务器通知商户网站地址
	private String out_trade_no;//订单号
	private String subject;//商品名称
	private String body;//商品详情
	private String price;//订单总金额
	
	
	public Product(String out_trade_no, String subject, String body,
			String price) {
		super();
		this.out_trade_no = out_trade_no;
		this.subject = subject;
		this.body = body;
		this.price = price;
	}
	public Product(String subject, String body, String price) {
		super();
		this.subject = subject;
		this.body = body;
		this.price = price;
	}
	
	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}
