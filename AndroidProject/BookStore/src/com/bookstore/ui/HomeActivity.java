package com.bookstore.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.ViewSwitcher.ViewFactory;

import com.bookstore.activity.R;

public class HomeActivity extends BaseActivity implements ViewFactory,
		OnTouchListener {

	private ImageSwitcher imageSwicher;
	private int[] arrayPictures = { R.drawable.title1, R.drawable.tilte2,
			R.drawable.title3, R.drawable.title4 };
	private int pictureIndex; // 要显示的图片在图片数组中的Index
	private float touchDownX; // 左右滑动时手指按下的X坐标
	private float touchUpX; // 左右滑动时手指松开的X坐标
	private GridView gv_hotsale = null;
	private GridView gv_hobby = null;
	private GridView gv_category = null;
	private int screenW = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		screenW = outMetrics.widthPixels;
		InitImgSwitcher();
		InitHotSaleGridview();
		InitHobbyGridview();
		//InitGridViewCategory();
	}

	private void InitImgSwitcher() {
		imageSwicher = (ImageSwitcher) findViewById(R.id.imageSwicher);
		// 为ImageSwicher设置Factory，用来为ImageSwicher制造ImageView
		imageSwicher.setFactory(HomeActivity.this);
		// 设置ImageSwitcher左右滑动事件
		imageSwicher.setOnTouchListener(HomeActivity.this);
	}

	private void InitHotSaleGridview() {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("icon", R.drawable.ic_launcher);
			data.add(map);
		}
		gv_hotsale = new GridView(getApplicationContext());
		gv_hotsale.setColumnWidth(screenW / 6);
		gv_hotsale.setNumColumns(GridView.AUTO_FIT);
		gv_hotsale.setGravity(Gravity.CENTER);
		LayoutParams params = new LayoutParams((screenW / 6) * data.size(),
				LayoutParams.WRAP_CONTENT);
		gv_hotsale.setLayoutParams(params);
		LinearLayout layout = (LinearLayout) findViewById(R.id.hotsale_gv_layout);
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				data, R.layout.hotsale_gridviewitem, new String[] { "icon" },
				new int[] { R.id.iv_hotsaleicon });
		gv_hotsale.setAdapter(adapter);
		Log.v("test", "gridview complete");
		layout.addView(gv_hotsale);
		// gv_hotsale.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), ""+position,
		// Toast.LENGTH_SHORT).show();
		// }
		// });
	}

	private void InitHobbyGridview() {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("icon", R.drawable.ic_launcher);
			data.add(map);
		}
		gv_hobby = new GridView(getApplicationContext());
		gv_hobby.setColumnWidth(screenW / 6);
		gv_hobby.setNumColumns(GridView.AUTO_FIT);
		gv_hobby.setGravity(Gravity.CENTER);
		LayoutParams params = new LayoutParams((screenW / 6) * data.size(),
				LayoutParams.WRAP_CONTENT);
		gv_hobby.setLayoutParams(params);
		LinearLayout layout = (LinearLayout) findViewById(R.id.hobby_gv_layout);
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				data, R.layout.hobby_gridviewitem, new String[] { "icon" },
				new int[] { R.id.iv_hobbyicon });
		gv_hobby.setAdapter(adapter);
		Log.v("test", "gridview complete");
		layout.addView(gv_hobby);
		// gv_hobby.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), ""+position,
		// Toast.LENGTH_SHORT).show();
		// }
		// });
	}

	private void InitGridViewCategory() {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("category_name", "分类" + i);
			data.add(map);
		}
		gv_category = new GridView(getApplicationContext());
		gv_category.setColumnWidth(screenW / 6);
		gv_category.setNumColumns(2);
		gv_category.setGravity(Gravity.CENTER);
		// LayoutParams params = new LayoutParams(screenW,
		// LayoutParams.WRAP_CONTENT);
		// gv_category.setLayoutParams(params);
		LinearLayout layout = (LinearLayout) findViewById(R.id.category_gv_layout);
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				data, R.layout.category_gridviewitem,
				new String[] { "category_name" },
				new int[] { R.id.tv_hotsaleicon });
		gv_category.setAdapter(adapter);
		Log.v("test", "gridview complete");
		layout.addView(gv_category);
		// gv_category.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), ""+position,
		// Toast.LENGTH_SHORT).show();
		// }
		// });
	}

	@Override
	public View makeView() {
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(arrayPictures[pictureIndex]);
		return imageView;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			touchDownX = event.getX();// 取得左右滑动时手指按下的X坐标
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			touchUpX = event.getX();// 取得左右滑动时手指松开的X坐标
			// 从左往右，看前一张
			if (touchUpX - touchDownX > 100) {
				// 取得当前要看的图片的index
				pictureIndex = pictureIndex == 0 ? arrayPictures.length - 1
						: pictureIndex - 1;
				// 设置图片切换的动画
				imageSwicher.setInAnimation(AnimationUtils.loadAnimation(this,
						android.R.anim.slide_in_left));
				imageSwicher.setOutAnimation(AnimationUtils.loadAnimation(this,
						android.R.anim.slide_out_right));
				// 设置当前要看的图片
				imageSwicher.setImageResource(arrayPictures[pictureIndex]);
				// 从右往左，看下一张
			} else if (touchDownX - touchUpX > 100) {
				// 取得当前要看的图片的index
				pictureIndex = pictureIndex == arrayPictures.length - 1 ? 0
						: pictureIndex + 1;
				// 设置图片切换的动画
				imageSwicher.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.slide_out_left));
				imageSwicher.setOutAnimation(AnimationUtils.loadAnimation(this,
						R.anim.slide_in_right));
				// 设置当前要看的图片
				imageSwicher.setImageResource(arrayPictures[pictureIndex]);
			}
			return true;
		}
		return false;
	}

}
