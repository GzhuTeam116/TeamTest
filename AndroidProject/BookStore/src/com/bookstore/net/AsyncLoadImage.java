package com.bookstore.net;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

public class AsyncLoadImage {
	private int GetImgSuccessTag = 1;
	private int GetImgFaileTag = -1;
	private int RequestConFaile = 0;
	private final ImageCache cache = ImageCache.getImageCacheInstance();

	private static AsyncLoadImage asyncLoadImage;

	private AsyncLoadImage() {

	}

	public static AsyncLoadImage getAsyncLoadImageInstance() {
		if (asyncLoadImage == null) {
			asyncLoadImage = new AsyncLoadImage();
		}
		return asyncLoadImage;
	}

	public void AsyncLoad(final String ImgUrl,
			final AsyncLoadImageHandler handler) {

		if (!TextUtils.isEmpty(ImgUrl)) {
			ThreadPoolUtils.execute(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Bitmap bitmap = null;
					String ErrorInfo = null;
					bitmap = cache.getBitmapFromCache(ImgUrl);
					if(bitmap != null){
						Message msg = handler.obtainMessage();
						msg.what = GetImgSuccessTag;
						msg.obj = bitmap;
						handler.sendMessage(msg);
						System.out.println("bitmap is has");
					}else{
						HttpClient client = new DefaultHttpClient();
						HttpGet httpGet = new HttpGet(ImgUrl);
						try {
							HttpResponse httpresponse = client.execute(httpGet);
							int repcode = httpresponse.getStatusLine()
									.getStatusCode();
							if (repcode != 200) {
								Message msg = handler.obtainMessage();
								msg.what = GetImgFaileTag;
								ErrorInfo = new String("Get Image Failed");
								msg.obj = ErrorInfo;
								handler.sendMessage(msg);
							} else {
								HttpEntity entity = httpresponse.getEntity();
								if (entity != null) {
									InputStream is = entity.getContent();
									bitmap = BitmapFactory.decodeStream(is);
									cache.addBitmapToCache(ImgUrl, bitmap);
									Message msg = handler.obtainMessage();
									msg.what = GetImgSuccessTag;
									msg.obj = bitmap;
									handler.sendMessage(msg);
									//System.out.println("bitmap is not has");
									Log.v("testload", "bitmap is not has");
									is.close();
									is = null;
								} else {
									Message msg = handler.obtainMessage();
									msg.what = GetImgFaileTag;
									ErrorInfo = new String("Get Image Failed");
									msg.obj = ErrorInfo;
									handler.sendMessage(msg);
								}
							}
						} catch (Exception e) {
							Message msg = handler.obtainMessage();
							msg.what = RequestConFaile;
							ErrorInfo = new String("Get RequestConnection Failed");
							msg.obj = ErrorInfo;
							handler.sendMessage(msg);
							e.printStackTrace();
						}
					}
				}
				
				
			});
		}
	}
	
}
	
	/*new Thread() {
	public void run() {
		Bitmap bitmap = null;
		String ErrorInfo = null;
		bitmap = cache.getBitmapFromCache(ImgUrl);
		if(bitmap != null){
			Message msg = handler.obtainMessage();
			msg.what = GetImgSuccessTag;
			msg.obj = bitmap;
			handler.sendMessage(msg);
			System.out.println("bitmap is has");
		}else{
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(ImgUrl);
			try {
				HttpResponse httpresponse = client.execute(httpGet);
				int repcode = httpresponse.getStatusLine()
						.getStatusCode();
				if (repcode != 200) {
					Message msg = handler.obtainMessage();
					msg.what = GetImgFaileTag;
					ErrorInfo = new String("Get Image Failed");
					msg.obj = ErrorInfo;
					handler.sendMessage(msg);
				} else {
					HttpEntity entity = httpresponse.getEntity();
					if (entity != null) {
						InputStream is = entity.getContent();
						bitmap = BitmapFactory.decodeStream(is);
						cache.addBitmapToCache(ImgUrl, bitmap);
						Message msg = handler.obtainMessage();
						msg.what = GetImgSuccessTag;
						msg.obj = bitmap;
						handler.sendMessage(msg);
						//System.out.println("bitmap is not has");
						Log.v("testload", "bitmap is not has");
						System.out.println(currentThread().getId());
						is.close();
						is = null;
					} else {
						Message msg = handler.obtainMessage();
						msg.what = GetImgFaileTag;
						ErrorInfo = new String("Get Image Failed");
						msg.obj = ErrorInfo;
						handler.sendMessage(msg);
					}
				}
			} catch (Exception e) {
				Message msg = handler.obtainMessage();
				msg.what = RequestConFaile;
				ErrorInfo = new String("Get RequestConnection Failed");
				msg.obj = ErrorInfo;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}
	};
}.start();*/
