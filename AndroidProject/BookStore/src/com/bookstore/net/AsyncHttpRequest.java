package com.bookstore.net;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.bookstore.control.CookieDao;
import com.bookstore.data.CookieData;

public class AsyncHttpRequest {
	private static final int RequestSuccess = 1;
	private static final int RequestFaile = 2;
	private static CookieDao cookieDao = null;

	public void Get(final String httpUrl, final Context context,
			final AsyncHttpRequestHandler handler) {
		if (!TextUtils.isEmpty(httpUrl)) {
			ThreadPoolUtils.execute(new Runnable() {
				@Override
				public void run() {
					HttpClient httpClient = HttpclientInstance
							.gethttpclientinstance();
					HttpGet httpGet = new HttpGet(httpUrl);
					cookieDao = CookieDao.getInstance(context);
					HttpResponse response = null;
					try {
						List<CookieData> cookiesdata = cookieDao.getCookies();
						for (CookieData cookie : cookiesdata) {
							String cookie_value = cookie.getCookie_value();
							httpGet.addHeader("Cookie", cookie_value);
							Log.v("test", "httpget方式addheader...getcookie:" + cookie_value);
						}
						response = httpClient.execute(httpGet);
						int repcode = response.getStatusLine().getStatusCode();
						if(repcode == 200){
							HttpEntity httpEntity = response.getEntity();
							String content = EntityUtils.toString(httpEntity);
							content = content.trim().toLowerCase();
							Message msg = handler.obtainMessage();
							msg.what = RequestSuccess;
							msg.obj = content;
							handler.sendMessage(msg);
						}else{
							Message msg = handler.obtainMessage();
							msg.what = RequestFaile;
							msg.obj = "request falied";
							handler.sendMessage(msg);
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Message msg = handler.obtainMessage();
						msg.what = RequestFaile;
						msg.obj = "request falied";
						handler.sendMessage(msg);
						e.printStackTrace();
					}
				}
			});
		}
	}

	public void Post(final String httpUrl,final Context context, final List<NameValuePair> params,
			final AsyncHttpRequestHandler handler) {
		System.out.println(context.toString().trim());
		ThreadPoolUtils.execute(new Runnable() {

			@Override
			public void run() {
				HttpClient client = HttpclientInstance
						.gethttpclientinstance();
				HttpPost httppost = new HttpPost(httpUrl);
				try {
					httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
					String activity_name = context.toString().trim();
					System.out.println(activity_name);
					HttpResponse rep = null;
					cookieDao = CookieDao.getInstance(context);
					//�û������¼���cookie�洢����ݿ�
					if(activity_name.contains("LoginActivity")){
						Log.v("test", "loginstart");
						rep = client.execute(httppost);
						Log.v("test", "login");
						List<Cookie> cookiesdata = ((AbstractHttpClient) client).getCookieStore().getCookies();
						cookieDao.addCookie(cookiesdata.get(0));
					}else if(activity_name.contains("RegisterActivity")){
						Log.v("test", "register");
						rep = client.execute(httppost);
					}else{
						Log.v("test", "other");
						List<CookieData> cookiesdata = cookieDao.getCookies();
						for (CookieData cookie : cookiesdata) {
							String cookie_value = cookie.getCookie_value();
							httppost.addHeader("Cookie", cookie_value);
							Log.v("test", "httppost方式addheader...getcookie:" + cookie_value);
						}
						rep = client.execute(httppost);
					}
					int repcode = rep.getStatusLine().getStatusCode();
					if(repcode == 200){
						HttpEntity entity = rep.getEntity();
						String content = EntityUtils.toString(entity);
						content = content.trim().toLowerCase();
						Message msg = handler.obtainMessage();
						msg.what = RequestSuccess;
						msg.obj = content;
						handler.sendMessage(msg);
					}else{
						Message msg = handler.obtainMessage();
						msg.what = RequestFaile;
						msg.obj = "request falied";
						handler.sendMessage(msg);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message msg = handler.obtainMessage();
					msg.what = RequestFaile;
					msg.obj = "request falied";
					handler.sendMessage(msg);
				}
				
			}

		});
	}
	
//	public String Post(final String url ,final List<NameValuePair> params,final CookieDao cookieDao){
//		final String content = null ;
//		ThreadPoolUtils.execute(new Runnable() {
//			@Override
//			public void run() {
//				HttpClient client = HttpclientInstance
//						.gethttpclientinstance();
//				HttpPost httppost = new HttpPost(url);
//				try {
//					httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//					HttpResponse rep = null;
//					List<CookieData> cookiesdata = cookieDao.getCookies();
//					if(cookiesdata.size() != 0){
//						for (CookieData cookie : cookiesdata) {
//							String cookie_value = cookie.getCookie_value();
//							httppost.addHeader("Cookie", cookie_value);
//							Log.v("test", "httppost方式addheader...getcookie:" + cookie_value);
//						}
//					}
//					rep = client.execute(httppost);
//					HttpEntity entity = rep.getEntity();
//					content = EntityUtils.toString(entity);
////					content = content.trim().toLowerCase();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
//		return content;
//	}

}


