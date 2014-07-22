package com.bookstore.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;

import com.bookstore.activity.R;

public class TabContentActivity extends TabActivity implements OnClickListener {
	private TabHost mTabHost;
	private ImageView view1, view2, view3, view4;
	private ImageView[] views = { view1, view2, view3, view4 };
	private String[] name = { "HomeActivity", "SearchActivity",
			"ShoppingCartActivity", "PersonActivity" };
	private Intent intentTag, intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabcontent);
		// ExitApplication.getInstance().addActivity(this);
		views[0] = (ImageView) findViewById(R.id.contentdilplay_LinearLayout_ImageView0);
		views[0].setOnClickListener(this);
		views[1] = (ImageView) findViewById(R.id.contentdilplay_LinearLayout_ImageView1);
		views[1].setOnClickListener(this);
		views[2] = (ImageView) findViewById(R.id.contentdilplay_LinearLayout_ImageView2);
		views[2].setOnClickListener(this);
		views[3] = (ImageView) findViewById(R.id.contentdilplay_LinearLayout_ImageView3);
		views[3].setOnClickListener(this);

		// 初始化TabHost
		initeTabHost();

	}

	/**
	 * 初始化TAbHost
	 */
	private void initeTabHost() {
		// TODO Auto-generated method stub
		mTabHost = getTabHost();
		intentTag = getIntent();
		// ExitApplication.getInstance().exit2();
		mTabHost.addTab(mTabHost.newTabSpec("HomeActivity").setIndicator("首页")
				.setContent(new Intent(this, HomeActivity.class)));
		intent = new Intent(this, SearchActivity.class);
		mTabHost.addTab(mTabHost.newTabSpec("SearchActivity")
				.setIndicator("搜索").setContent(intent));
		mTabHost.addTab(mTabHost.newTabSpec("ShoppingCartActivity")
				.setIndicator("购物车")
				.setContent(new Intent(this, ShoppingCartActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("PersonActivity")
				.setIndicator("个人信息")
				.setContent(new Intent(this, PersonActivity.class)));
		String tag = intentTag.getStringExtra("TAG");

	}

	private void setBackground(int i) {
		for (int j = 0; j < views.length; j++) {
			if (j == i) {
				views[i].setBackgroundResource(R.drawable.tab_focus);
				continue;
			}
			views[j].setBackgroundResource(0);
		}
	}

	public void intentAcitivity(int i) {
		mTabHost.setCurrentTabByTag(name[i]);

		setBackground(i);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.contentdilplay_LinearLayout_ImageView0:
			intentAcitivity(0);
			break;
		case R.id.contentdilplay_LinearLayout_ImageView1:
			intentAcitivity(1);
			break;
		case R.id.contentdilplay_LinearLayout_ImageView2:
			intentAcitivity(2);
			break;
		case R.id.contentdilplay_LinearLayout_ImageView3:
			intentAcitivity(3);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

}
