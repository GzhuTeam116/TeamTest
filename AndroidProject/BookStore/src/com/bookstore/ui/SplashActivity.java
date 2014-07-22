package com.bookstore.ui;

import com.bookstore.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity {

	public static	final int TAG_MESSAGE_DELAYED=100;
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case TAG_MESSAGE_DELAYED:
				startActivity(new Intent(SplashActivity.this, TabContentActivity.class));
				overridePendingTransition( R.anim.push_up_in,R.anim.push_up_out);
				SplashActivity.this.finish();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		animationMethod();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void animationMethod() {
		// TODO Auto-generated method stub
		Message msg=mHandler.obtainMessage();
		msg.what=TAG_MESSAGE_DELAYED;
		mHandler.sendMessageDelayed(msg, 1000);
	}


}
