package com.bookstore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bookstore.activity.R;

public class PersonalActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout personal_query_order = null;
	private RelativeLayout personal_modifyinfo = null;
	private RelativeLayout personal_discount = null;
	private Button personal_lgoin_btn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);
		InitView();
	}

	private void InitView() {
		personal_query_order = (RelativeLayout) findViewById(R.id.personal_query_order);
		personal_modifyinfo = (RelativeLayout) findViewById(R.id.personal_modifyinfo);
		personal_discount = (RelativeLayout) findViewById(R.id.personal_discount);
		personal_lgoin_btn = (Button) findViewById(R.id.personal_lgoin_btn);
		
		personal_query_order.setOnClickListener(this);
		personal_modifyinfo.setOnClickListener(this);
		personal_discount.setOnClickListener(this);
		personal_lgoin_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.personal_query_order:
			Toast.makeText(getApplicationContext(), "personal_query_order", 0).show();
			break;

		case R.id.personal_modifyinfo:
			Toast.makeText(getApplicationContext(), "personal_modifyinfo", 0).show();
			break;
		case R.id.personal_discount:
			Toast.makeText(getApplicationContext(), "personal_discount", 0).show();
			break;
		
		case R.id.personal_lgoin_btn:
			Intent intent = new Intent();
			intent.setClass(PersonalActivity.this, LoginActivity.class);
			startActivity(intent);
			break;
		}
	}
}
