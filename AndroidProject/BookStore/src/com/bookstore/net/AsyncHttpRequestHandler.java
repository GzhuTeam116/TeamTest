package com.bookstore.net;

import android.os.Handler;
import android.os.Message;

public class AsyncHttpRequestHandler extends Handler {
	private static final int RequestSuccess = 1;
	private static final int RequestFaile = 2;
	
	public void onSuccess(String content) {

	}

	public void onFaile(String content) {

	}
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		String content = (String) msg.obj;
		switch (msg.what) {
		case RequestSuccess:
			onSuccess(content);
			break;

		case RequestFaile:
			onFaile(content);
			break;
		}
	}

	
}
