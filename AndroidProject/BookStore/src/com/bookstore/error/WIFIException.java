package com.bookstore.error;

@SuppressWarnings("serial")
public class WIFIException extends Exception {
	public WIFIException(){
		System.err.println("WIFI is not available!");
	}
	public WIFIException(String s){
		System.err.println(s);
	}
}
