package com.bookstore.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.bookstore.activity.R;
import com.bookstore.net.AsyncHttpRequest;
import com.bookstore.net.AsyncHttpRequestHandler;

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
	private String RegisterUrl = "";

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

	private void EdToString() {
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
				Toast.makeText(getApplicationContext(), "请检查必要信息是否填写完整", Toast.LENGTH_SHORT)
						.show();
			} else {
				EdToString();
				AsyncHttpRequest httpRequest = new AsyncHttpRequest();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				;
				params.add(new BasicNameValuePair("account", register_account));
				params.add(new BasicNameValuePair("password", register_password));
				params.add(new BasicNameValuePair("phone", phone));
				params.add(new BasicNameValuePair("card_num", weibo_account));
				params.add(new BasicNameValuePair("weibo_account", creaditcard));
				httpRequest.Post(RegisterUrl, RegisterActivity.this, params,
						new AsyncHttpRequestHandler() {

							@Override
							public void onSuccess(String content) {
								// TODO Auto-generated method stub
								super.onSuccess(content);
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
													"注册成功", Toast.LENGTH_SHORT)
													.show();
											SetEditViewEmpty();
										} else if ("faile".equals(data)) {
											Toast.makeText(
													getApplicationContext(),
													"注册失败", Toast.LENGTH_SHORT)
													.show();
										}
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Toast.makeText(
											getApplicationContext(),
											"注册失败", Toast.LENGTH_SHORT)
											.show();
								}
							}

							@Override
							public void onFaile(String content) {
								// TODO Auto-generated method stub
								super.onFaile(content);
								Toast.makeText(
										getApplicationContext(),
										"注册失败", Toast.LENGTH_SHORT)
										.show();
							}

						});
			}
		}

	}
	
	private void SetEditViewEmpty(){
		ed_register_account.setText(null);
		ed_register_password.setText(null);
		ed_phone.setText(null); 
		ed_register_weibo_account.setText(null);
		ed_register_creaditcard .setText(null);
	}
}
