package com.bookstore.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bookstore.activity.R;
import com.bookstore.control.CookieDao;
import com.bookstore.net.AsyncHttpRequest;
import com.bookstore.net.AsyncHttpRequestHandler;

public class LoginActivity extends Activity implements OnClickListener {

	private Button login_btn = null;
	private Button register_btn_bylogin = null;
	private EditText ed_login_account = null;
	private EditText ed_login_password = null;
	private String login_account = null;
	private String login_password = null;
	private static CookieDao cookieDao = null;
	private String loginUrl = "http://172.22.71.238:9000/login";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		InitEditText();
		InitButton();
	}

	private void InitButton() {
		login_btn = (Button) findViewById(R.id.login_btn);
		register_btn_bylogin = (Button) findViewById(R.id.register_btn_bylogin);
		login_btn.setOnClickListener(this);
		register_btn_bylogin.setOnClickListener(this);
	}

	private void InitEditText() {
		ed_login_account = (EditText) findViewById(R.id.ed_login_account);
		ed_login_password = (EditText) findViewById(R.id.ed_login_password);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			login_account = ed_login_account.getText().toString().trim();
			login_password = ed_login_password.getText().toString().trim();
			if (TextUtils.isEmpty(login_account)
					|| TextUtils.isEmpty(login_password)) {
				Toast.makeText(getApplicationContext(), "用户名或密码不能为空",
						Toast.LENGTH_SHORT).show();
			} else {
				AsyncHttpRequest httpRequest = new AsyncHttpRequest();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userName", login_account));
				params.add(new BasicNameValuePair("password", login_password));
				httpRequest.Post(loginUrl, LoginActivity.this, params,
						new AsyncHttpRequestHandler() {

							@Override
							public void onSuccess(String content) {
								super.onSuccess(content);
								System.out.println(content);
//								Toast.makeText(
//										getApplicationContext(),
//										"测试",
//										Toast.LENGTH_SHORT).show();
								JSONTokener jsonTokener = new JSONTokener(
										content);
								try {
									JSONObject object = (JSONObject) jsonTokener
											.nextValue();
									if (0 == object.getInt("code")) {
										String data = object.getString("data");
										if ("success".equals(data)) {
											Toast.makeText(
													getApplicationContext(),
													"登录成功",
													Toast.LENGTH_SHORT).show();
											finish();
											// 验证成功,跳转回个人主页
//											Intent intent = new Intent();
//											intent.setClass(
//													getApplicationContext(),
//													PersonalActivity.class);
//											startActivity(intent);
										} else if ("faile".equals(data)) {
											// 验证失败
											try {
												delcookie();
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											Toast.makeText(
													getApplicationContext(),
													"用户名或密码错误1",
													Toast.LENGTH_SHORT).show();
											delcookie();
										}
									} else {
										// 验证失败
										Toast.makeText(getApplicationContext(),
												"用户名或密码错误", Toast.LENGTH_SHORT)
												.show();
										delcookie();
									}
								} catch (JSONException e) {
									Toast.makeText(getApplicationContext(),
											"无法连接到服务器，验证用户身份失败1",
											Toast.LENGTH_SHORT).show();
									e.printStackTrace();
									delcookie();
								}
							}

							@Override
							public void onFaile(String content) {
								super.onFaile(content);
								Toast.makeText(getApplicationContext(),
										"无法连接到服务器，验证用户身份失败",
										Toast.LENGTH_SHORT).show();
								delcookie();
							}

						});
			}
			// Toast.makeText(getApplicationContext(), "login", 0).show();
			break;

		case R.id.register_btn_bylogin:
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	private void delcookie(){
		cookieDao = CookieDao
				.getInstance(getApplicationContext());
		cookieDao.delCookies();
	}

}
