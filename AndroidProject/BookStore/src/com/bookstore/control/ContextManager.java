package com.bookstore.control;

import android.content.Context;

public class ContextManager {
	
	private static ContextManager cm = null;
	private Context mActivity,mService;
	
	private ContextManager (){
		
	}
	
	public static ContextManager getInstance(){
		if(cm == null){
			cm = new ContextManager();
		}
		return cm;
	}
	
	public void setActivityContext(Context c){
		mActivity = c;
	}
	
	public void setServiceContext(Context c){
		mService = c;
	}
	
	public Context getActivityContext(){
		return mActivity;
	}
	
	public Context getServiceContext(){
		return mService;
	}
	
}
