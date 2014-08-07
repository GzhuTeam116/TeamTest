package com.bookstore.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bookstore.activity.R;
import com.bookstore.control.ContextManager;
import com.bookstore.control.LocationManager;
import com.bookstore.control.LocationManager.LocationListener;
import com.bookstore.data.DiscountData.Discount;
import com.bookstore.data.LocationData.Location;
import com.bookstore.etc.Config;
import com.bookstore.net.AsyncHttpRequest;
import com.bookstore.net.AsyncHttpRequestHandler;
import com.bookstore.net.AsyncLoadImage;
import com.bookstore.net.AsyncLoadImageHandler;


public class NavigationActivity extends Activity {

	private String tag = "NavigationActivity";
	private TextView navigation_path, current_position;
	private String book_id;
	private View mView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater myInflater = LayoutInflater.from(this);
		mView = myInflater.inflate(R.layout.navigation_layout, null);
		setContentView(mView);
		init();
	}

	private void init() {
		navigation_path = (TextView) findViewById(R.id.navigation_path);
		current_position = (TextView) findViewById(R.id.current_position);
		book_id = this.getIntent().getStringExtra("book_id");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getNavigationPath();
		LocationManager.getInstance().setLocationListener(
				new LocationListener() {

					@Override
					public void onLocationChange(Location location) {
						// TODO Auto-generated method stub
						current_position.setText("您当前的位置为：" + location.Areaname);
						if(location.hasDiscountInf){
							getDiscountInf(location.Area);
						}
					}
				});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ContextManager.getInstance().setActivityContext(this);
	}

	private void getNavigationPath() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("book_id", book_id));
		params.add(new BasicNameValuePair("area_id", Integer
				.toString(LocationManager.getInstance().getLocation().Area)));
		new AsyncHttpRequest().Post(Config.NAVIGATION_URL, this, params,
				new AsyncHttpRequestHandler() {

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						Log.v(tag, "获取导航路径成功");
						StringBuilder sb = new StringBuilder();
						sb.append("出发位置:");
						try {
							JSONArray json_arr = new JSONObject(content).getJSONArray("Navigation_Info");
							for (int i = 0; i < json_arr.length(); i++) {
								sb.append(json_arr.getJSONObject(i).getString("area_name") + '\n');
								if((i+1)<json_arr.length()){
									sb.append("下一步方向:");
									sb.append(json_arr.getJSONObject(i).getString("direction") + '\n');
									sb.append("到达位置:");
								}
							}
						} catch (JSONException e) {
							Log.v(tag, "Fail to parse path information!");
							e.printStackTrace();
						}
						navigation_path.setText(sb.toString());
					}

					@Override
					public void onFaile(String content) {
						super.onFaile(content);
						Log.v(tag, "Fail to get path information!");
					}

				});

	}
	
	private void getDiscountInf(int area){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("area_id",Integer.toString(area)));
		new AsyncHttpRequest().Post(Config.DISCOUNT_URL, this, params, new AsyncHttpRequestHandler(){

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				Discount discount = new Discount();
				showDiscountDialog(discount);
			}

			@Override
			public void onFaile(String content) {
				// TODO Auto-generated method stub
				super.onFaile(content);
				Log.v(tag, "Fail to get Area Discount information!");
			}
			
		});
	}
	
	private void showDiscountDialog(final Discount discount){
		
		LayoutInflater myinflater = LayoutInflater.from(NavigationActivity.this);
		View v = myinflater.inflate(R.layout.discount_dialog_layout, null);
		PopupWindow pop = new PopupWindow(v, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, false);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(true);
		pop.setFocusable(true);
		
		final ImageView iv = (ImageView)v.findViewById(R.id.discount_img);
		final TextView tv = (TextView)v.findViewById(R.id.discount_tv);
		tv.setText(discount.PromotionIntro);
		
		AsyncLoadImage asyncLoadImage = AsyncLoadImage.getAsyncLoadImageInstance();
		asyncLoadImage.AsyncLoad(discount.DiscountImgUrl, new AsyncLoadImageHandler() {

			@Override
			public void LoadImgSuccess(Bitmap bitmap) {
				// TODO Auto-generated method stub
				super.LoadImgSuccess(bitmap);
				iv.setImageBitmap(bitmap);
			}

			@Override
			public void LoadImgFailed(String ErrorInfo) {
				// TODO Auto-generated method stub
				super.LoadImgFailed(ErrorInfo);
			}
			
		});
		
		pop.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
		
		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//跳转到促销信息显示界面
				Intent intent = new Intent();
				intent.putExtra("discount_id", discount.DiscountID);
				startActivity(intent);
			}
		});
	}
}
