package com.bookstore.ui;

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

public class LoginActivity extends Activity implements OnClickListener{

	private Button login_btn = null;//登录按钮
	private Button register_btn_bylogin = null;//注册按钮
	private EditText ed_login_account = null;
	private EditText ed_login_password = null;
	private String login_account = null;//存放用户名
	private String login_password = null;//存放用户密码
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		InitEditText();
		InitButton();
	}
	
	private void InitButton(){
		login_btn = (Button) findViewById(R.id.login_btn);
		register_btn_bylogin = (Button) findViewById(R.id.register_btn_bylogin);
		login_btn.setOnClickListener(this);
		register_btn_bylogin.setOnClickListener(this);
	}
	
	private void InitEditText(){
		ed_login_account = (EditText) findViewById(R.id.ed_login_account);
		ed_login_password = (EditText) findViewById(R.id.ed_login_password);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			login_account =  ed_login_account.getText().toString().trim();
			login_password = ed_login_password.getText().toString().trim();
			//验证登录
			if(TextUtils.isEmpty(login_account) || TextUtils.isEmpty(login_password) ){
				Toast.makeText(getApplicationContext(), "用户名或密码不能为空", 0).show();
			}else{
				//请求服务器验证用户身份
			}
			//Toast.makeText(getApplicationContext(), "login", 0).show();
			break;

		case R.id.register_btn_bylogin:
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	
}
