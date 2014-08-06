package com.bookstore.control;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.cookie.Cookie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bookstore.data.CookieData;
import com.bookstore.etc.CookieSQLite;

public class CookieDao {

	private static final String COOKIES_NAME_COL = "name";
	private static final String COOKIES_VALUE_COL = "value";
	private static final String COOKIES_DOMAIN_COL = "domain";
	private static final String COOKIES_PATH_COL = "path";
	private static String mCookieLock = "asd";
	private static CookieDao cookieDao;
	private static CookieSQLite cookieSQLite;

	private CookieDao(Context context) {
		android.util.Log.v("test", "cookiedao���󱻴���");
		if(cookieSQLite == null){
			cookieSQLite = new CookieSQLite(context);
		}
	}

	public static CookieDao getInstance(Context context){
		if(cookieDao == null){
			cookieDao = new CookieDao(context);
		}
		return cookieDao;
	}
	
	public void  addCookie(Cookie cookie) {
		android.util.Log.v("test", "addcookie");
		SQLiteDatabase db = cookieSQLite.getWritableDatabase();
		if (cookie.getDomain() == null || cookie.getPath() == null
				|| cookie.getName() == null || db == null) {
			return;
		}
		//String mCookieLock = "asd";
		synchronized (mCookieLock) {
			ContentValues cookieVal = new ContentValues();
			cookieVal.put(COOKIES_DOMAIN_COL, cookie.getDomain());
			cookieVal.put(COOKIES_PATH_COL, cookie.getPath());
			cookieVal.put(COOKIES_NAME_COL, cookie.getName());
			cookieVal.put(COOKIES_VALUE_COL, cookie.getValue());

			db.insert("cookies", null, cookieVal);
			System.out.println("sqlite cookievalue:"+cookie.getValue());
			db.close();
		}
	}

	public List<CookieData> getCookies() {
		SQLiteDatabase db = cookieSQLite.getReadableDatabase();
		//SQLiteDatabase db = CookieSQLite.mDatabase;
		//String mCookieLock = "asd";
		synchronized (mCookieLock){
			List<CookieData> cookies= new ArrayList<CookieData>();
			Cursor cs = db.query("cookies", new String[] { COOKIES_VALUE_COL },
					null, null, null, null, null);
			while (cs.moveToNext()) {
				String cookie_value = cs.getString(cs.getColumnIndex(COOKIES_VALUE_COL));
				CookieData cookie = new CookieData(cookie_value);
				cookies.add(cookie);
			}
			cs.close();
			db.close();
			return cookies;
		}
		
	}
	
	public void delCookies(){
		SQLiteDatabase db = cookieSQLite.getWritableDatabase();
		//String mCookieLock = "asd";
		synchronized (mCookieLock){
			db.delete("cookies", null, null);
			db.close();
		}
	}
}
