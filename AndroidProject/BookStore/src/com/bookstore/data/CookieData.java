package com.bookstore.data;

public class CookieData {

	private String cookie_name;
	private String cookie_value;
	private String cookie_domain;
	private String cookie_path;
	private String cookie_expires;
	private String cookie_secure;
	
	public CookieData(){
		
	}
	
	public CookieData(String cookie_value) {
		super();
		this.cookie_value = cookie_value;
	}
	public String getCookie_name() {
		return cookie_name;
	}
	public void setCookie_name(String cookie_name) {
		this.cookie_name = cookie_name;
	}
	public String getCookie_value() {
		return cookie_value;
	}
	public void setCookie_value(String cookie_value) {
		this.cookie_value = cookie_value;
	}
	public String getCookie_domain() {
		return cookie_domain;
	}
	public void setCookie_domain(String cookie_domain) {
		this.cookie_domain = cookie_domain;
	}
	public String getCookie_path() {
		return cookie_path;
	}
	public void setCookie_path(String cookie_path) {
		this.cookie_path = cookie_path;
	}
	public String getCookie_expires() {
		return cookie_expires;
	}
	public void setCookie_expires(String cookie_expires) {
		this.cookie_expires = cookie_expires;
	}
	public String getCookie_secure() {
		return cookie_secure;
	}
	public void setCookie_secure(String cookie_secure) {
		this.cookie_secure = cookie_secure;
	}
	
	
	
}
