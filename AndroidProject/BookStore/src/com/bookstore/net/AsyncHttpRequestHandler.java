package com.bookstore.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.bookstore.control.NetWorkManager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AsyncHttpRequestHandler extends Handler {
	private String tag = "AsyncHttpRequestHandler";
	private static final int RequestSuccess = 1;
	private static final int RequestFaile = 2;
	
	public void onSuccess(String content) {
		String access_method;
		try {
			access_method = new JSONObject(content).getString("access_method");
			if(access_method.equals("remote")){
				NetWorkManager.getInstance().setNet_work_state(NetWorkManager.NET_WORK_STATE_REMOTE);
			}else{
				NetWorkManager.getInstance().setNet_work_state(NetWorkManager.NET_WORK_STATE_LOCAL);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.v(tag,"get net data error");
		}
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
