package com.bookstore.ui;

import com.bookstore.activity.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {

	private EditText ed_register_account = null;
	private EditText ed_register_password = null;
	private EditText ed_phone = null;
	private EditText ed_register_weibo_account = null;
	private EditText ed_register_creaditcard = null;
	private Button register_btn = null;
	private String register_account = null, register_password = null,
			phone = null, weibo_account = null, creaditcard = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		InitView();
		register_btn = (Button) findViewById(R.id.register_btn);
		register_btn.setOnClickListener(this);
	}

	private void InitView() {
		ed_register_account = (EditText) findViewById(R.id.ed_register_account);
		ed_register_password = (EditText) findViewById(R.id.ed_register_password);
		ed_phone = (EditText) findViewById(R.id.ed_phone);
		ed_register_weibo_account = (EditText) findViewById(R.id.ed_register_weibo_account);
		ed_register_creaditcard = (EditText) findViewById(R.id.ed_register_creaditcard);
		
	}

	private void EdToString(){
		register_account = ed_register_account.getText().toString().trim();
		register_password = ed_register_password.getText().toString().trim();
		phone = ed_phone.getText().toString().trim();
		weibo_account = ed_register_weibo_account.getText().toString().trim();
		creaditcard = ed_register_creaditcard.getText().toString().trim();
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.register_btn) {
			EdToString();
			if (TextUtils.isEmpty(register_account)
					|| TextUtils.isEmpty(register_password)
					|| TextUtils.isEmpty(phone)) {
				Toast.makeText(getApplicationContext(), "请检查必填信息内容是否为空", 0)
						.show();
			} else {

			}
		}

	}
}
