package com.bookstore.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bookstore.activity.R;
import com.bookstore.control.ContextManager;
import com.bookstore.net.AsyncHttpRequest;
import com.bookstore.net.AsyncHttpRequestHandler;

public class UserInfoModifyActivity extends Activity {

	private Button modify_btn = null;
	private EditText ed_modify_account = null;
	private EditText ed_modify_phone = null;
	private EditText ed_modify_weiboaccount = null;
	private EditText ed_modify_cardnum = null;

	private String modify_account = null;
	private String modify_phone = null;
	private String modify_weiboaccount = null;
	private String modify_cardnum = null;

	private String userinfoUrl = "";
	// private boolean flag = false;
	private JSONObject data = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountinfo_modify);
		InitView();
		modify_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GetEditView();
				try {
					if(data!=null){
						if (modify_account.equals(data.getString("account"))
								&& modify_phone.equals(data.getString("phone"))
								&& modify_weiboaccount.equals(data
										.getString("weibo_account"))
								&& modify_cardnum.equals(data.getString("cardnum"))) {
							Toast.makeText(getApplicationContext(), "信息未被修改",
									Toast.LENGTH_SHORT).show();
						} else {
							PostModifiedInfo();
						}
					}else{
						PostModifiedInfo();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		GetUserInfo();
		ContextManager.getInstance().setActivityContext(this);
	}

	private void InitView() {
		ed_modify_account = (EditText) findViewById(R.id.ed_modify_account);
		ed_modify_phone = (EditText) findViewById(R.id.ed_modify_phone);
		ed_modify_weiboaccount = (EditText) findViewById(R.id.ed_modify_weiboaccount);
		ed_modify_cardnum = (EditText) findViewById(R.id.ed_modify_cardnum);
		modify_btn = (Button) findViewById(R.id.modify_btn);
	}

	private void GetEditView() {
		modify_account = ed_modify_account.toString().trim();
		modify_phone = ed_modify_phone.toString().trim();
		modify_weiboaccount = ed_modify_weiboaccount.toString().trim();
		modify_cardnum = ed_modify_cardnum.toString().trim();
	}

	private void GetUserInfo() {
		AsyncHttpRequest request = new AsyncHttpRequest();
		request.Get(userinfoUrl, getApplicationContext(),
				new AsyncHttpRequestHandler() {

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						SetDefaultImfo(content);
					}

					@Override
					public void onFaile(String content) {
						// TODO Auto-generated method stub
						super.onFaile(content);
						Toast.makeText(getApplicationContext(), "网络无法连接",
								Toast.LENGTH_SHORT).show();
					}

				});
	}

	private void SetDefaultImfo(String content) {
		JSONTokener jsonTokener = new JSONTokener(content);
		try {
			JSONObject object = (JSONObject) jsonTokener.nextValue();
			if (0 == object.getInt("code")) {
				data = object.getJSONObject("data");
				ed_modify_account.setText(data.getString("account"));
				ed_modify_phone.setText(data.getString("phone"));
				ed_modify_weiboaccount.setText(data.getString("weibo_account"));
				ed_modify_cardnum.setText(data.getString("cardnum"));
			} else {
				Toast.makeText(getApplicationContext(), "个人信息获取失败",
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

//	private void Setnofocus() {
//		ed_modify_account.setFocusable(false);
//		ed_modify_account.setFocusableInTouchMode(false);
//
//		ed_modify_phone.setFocusable(false);
//		ed_modify_phone.setFocusableInTouchMode(false);
//
//		ed_modify_weiboaccount.setFocusable(false);
//		ed_modify_weiboaccount.setFocusableInTouchMode(false);
//
//		ed_modify_cardnum.setFocusable(false);
//		ed_modify_cardnum.setFocusableInTouchMode(false);
//
//		modify_btn.setFocusable(false);
//		modify_btn.setFocusableInTouchMode(false);
//	}
//	
//	private void Setfocus() {
//		ed_modify_account.setFocusable(true);
//		ed_modify_account.setFocusableInTouchMode(true);
//
//		ed_modify_phone.setFocusable(true);
//		ed_modify_phone.setFocusableInTouchMode(true);
//
//		ed_modify_weiboaccount.setFocusable(true);
//		ed_modify_weiboaccount.setFocusableInTouchMode(true);
//
//		ed_modify_cardnum.setFocusable(true);
//		ed_modify_cardnum.setFocusableInTouchMode(true);
//
//		modify_btn.setFocusable(true);
//		modify_btn.setFocusableInTouchMode(true);
//	}


	private void PostModifiedInfo() {
		AsyncHttpRequest httpRequest = new AsyncHttpRequest();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("account", modify_account));
		params.add(new BasicNameValuePair("phone", modify_phone));
		params.add(new BasicNameValuePair("weibo_account", modify_weiboaccount));
		params.add(new BasicNameValuePair("cardnum", modify_cardnum));
		httpRequest.Post(userinfoUrl, UserInfoModifyActivity.this, params,
				new AsyncHttpRequestHandler() {

					@Override
					public void onSuccess(String content) {
						super.onSuccess(content);
						JSONTokener jsonTokener = new JSONTokener(
								content);
						try {
							JSONObject object = (JSONObject) jsonTokener
									.nextValue();
							if (0 == object.getInt("code")){
								String data = object.getString("data");
								if ("success".equals(data)) {
									Toast.makeText(
											getApplicationContext(),
											"修改成功",
											Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(
											getApplicationContext(),
											"修改失败",
											Toast.LENGTH_SHORT).show();
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(
									getApplicationContext(),
									"修改失败",
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFaile(String content) {
						super.onFaile(content);
						Toast.makeText(getApplicationContext(), "网络无法连接",
								Toast.LENGTH_SHORT).show();
					}
			
				});
	}
}
