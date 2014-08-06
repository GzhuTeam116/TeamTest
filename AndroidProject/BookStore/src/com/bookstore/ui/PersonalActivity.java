package com.bookstore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bookstore.activity.R;
import com.bookstore.control.ContextManager;
import com.bookstore.control.CookieDao;

public class PersonalActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout personal_query_order = null;
	private RelativeLayout personal_modifyinfo = null;
	private RelativeLayout personal_discount = null;
	private Button personal_login_btn = null;
	private Button personal_logout_btn = null;
	private TextView personal_welcome = null;
	private static CookieDao cookieDao = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);
		InitView();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ContextManager.getInstance().setActivityContext(this);
		//查询数据库中是否有cookie存在，如果有，则更新UI
		try {
			cookieDao = CookieDao
					.getInstance(getApplicationContext());
			if(cookieDao.getCookies().size() != 0){
				personal_login_btn.setVisibility(8);
				personal_logout_btn.setVisibility(0);
				personal_welcome.setVisibility(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void InitView() {
		personal_query_order = (RelativeLayout) findViewById(R.id.personal_query_order);
		personal_modifyinfo = (RelativeLayout) findViewById(R.id.personal_modifyinfo);
		personal_discount = (RelativeLayout) findViewById(R.id.personal_discount);
		personal_login_btn = (Button) findViewById(R.id.personal_login_btn);
		personal_logout_btn = (Button) findViewById(R.id.personal_logout_btn);
		personal_welcome = (TextView) findViewById(R.id.personal_welcome);

		personal_query_order.setOnClickListener(this);
		personal_modifyinfo.setOnClickListener(this);
		personal_discount.setOnClickListener(this);
		personal_login_btn.setOnClickListener(this);
		personal_logout_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.personal_query_order:
//			Toast.makeText(getApplicationContext(), "personal_query_order",
//					Toast.LENGTH_SHORT).show();
			Intent intent_qr = new Intent();
			intent_qr.setClass(getApplicationContext(), OrderListActivity.class);
			startActivity(intent_qr);
			break;

		case R.id.personal_modifyinfo:
//			Toast.makeText(getApplicationContext(), "personal_modifyinfo",
//					Toast.LENGTH_SHORT).show();
			Intent intent_modify = new Intent();
			intent_modify.setClass(getApplicationContext(), UserInfoModifyActivity.class);
			startActivity(intent_modify);
			break;
		case R.id.personal_discount:
			Toast.makeText(getApplicationContext(), "personal_discount",
					Toast.LENGTH_SHORT).show();
			break;

		case R.id.personal_login_btn:
			Intent intent = new Intent();
			intent.setClass(PersonalActivity.this, LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.personal_logout_btn:
			try {
				cookieDao = CookieDao
						.getInstance(getApplicationContext());
				cookieDao.delCookies();
				personal_login_btn.setVisibility(0);
				personal_logout_btn.setVisibility(8);
				personal_welcome.setVisibility(8);
				Toast.makeText(getApplicationContext(), "已注销登录", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}
}
