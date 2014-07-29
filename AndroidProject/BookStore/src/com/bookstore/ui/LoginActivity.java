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

	private Button login_btn = null;//��¼��ť
	private Button register_btn_bylogin = null;//ע�ᰴť
	private EditText ed_login_account = null;
	private EditText ed_login_password = null;
	private String login_account = null;//����û���
	private String login_password = null;//����û�����
	
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
			//��֤��¼
			if(TextUtils.isEmpty(login_account) || TextUtils.isEmpty(login_password) ){
				Toast.makeText(getApplicationContext(), "�û��������벻��Ϊ��", 0).show();
			}else{
				//�����������֤�û����
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
