package com.bookstore.etc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author User
 *
 */
public class CookieSQLite extends SQLiteOpenHelper {
	private static final String DATABASE_FILE = "cookies.db";
	private static final String COOKIES_NAME_COL = "name";
	private static final String COOKIES_VALUE_COL = "value";
	private static final String COOKIES_DOMAIN_COL = "domain";
	private static final String COOKIES_PATH_COL = "path";
	private static final String COOKIES_EXPIRES_COL = "expires";
	private static final String COOKIES_SECURE_COL = "secure";

	public CookieSQLite(Context context) {
		super(context, DATABASE_FILE, null, 1);
		android.util.Log.v("test", "cookieSQLite对象被创建");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS cookies "
				+ " (_id INTEGER PRIMARY KEY, " + COOKIES_NAME_COL + " TEXT, "
				+ COOKIES_VALUE_COL + " TEXT, " + COOKIES_DOMAIN_COL
				+ " TEXT, " + COOKIES_PATH_COL + " TEXT, "
				+ COOKIES_EXPIRES_COL + " INTEGER, " + COOKIES_SECURE_COL
				+ " INTEGER" + ");");
		/*db.execSQL("CREATE INDEX IF NOT EXISTS cookiesIndex ON " + "cookies"
				+ " (path)");*/
		Log.v("test", "数据库被创建了。。。。");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}