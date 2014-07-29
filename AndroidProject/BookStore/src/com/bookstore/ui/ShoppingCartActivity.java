package com.bookstore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bookstore.activity.R;

public class ShoppingCartActivity extends BaseActivity {

	private Button shopcart_login_btn = null;//购物车中的登录按钮
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopcart);
		ShopCartBtn();
	}
	
	private void ShopCartBtn(){
		shopcart_login_btn = (Button) findViewById(R.id.shopcart_login);
		shopcart_login_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ShoppingCartActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
	}
}
