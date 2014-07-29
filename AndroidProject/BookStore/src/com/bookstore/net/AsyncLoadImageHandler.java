package com.bookstore.net;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class AsyncLoadImageHandler extends Handler {

	public void LoadImgSuccess(Bitmap bitmap) {

	}

	public void LoadImgFailed(String ErrorInfo) {

	}

	@Override
	public void handleMessage(Message msg) {

		switch (msg.what) {
		case 1:
			Bitmap bitmap = (Bitmap) msg.obj;
			LoadImgSuccess(bitmap);
			break;
		default:
			String ErrorInfo = (String) msg.obj;
			LoadImgFailed(ErrorInfo);
			break;
		}
		super.handleMessage(msg);
	}

}
