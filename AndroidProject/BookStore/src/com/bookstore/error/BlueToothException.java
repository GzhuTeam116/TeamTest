package com.bookstore.error;

@SuppressWarnings("serial")
public class BlueToothException extends Exception {
	public BlueToothException(){
		System.err.println("BlueTooth is not available");
	}
	public BlueToothException(String s){
		System.err.println(s);
	}
}
