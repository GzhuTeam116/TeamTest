package com.bookstore.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bookstore.activity.R;
import com.bookstore.control.ContextManager;
import com.bookstore.data.OrderFormDetailInfo;
import com.bookstore.net.AsyncHttpRequest;
import com.bookstore.net.AsyncHttpRequestHandler;
import com.bookstore.net.AsyncLoadImage;
import com.bookstore.net.AsyncLoadImageHandler;

public class OrderFormActivity extends Activity {

	private TextView tv_orderform_num = null;
	private TextView tv_orderform_price = null;
	private TextView tv_orderform_date = null;
	private ListView lv_orderform = null;
	private List<OrderFormDetailInfo> infoes = null;
	private List<Bitmap> bitmaps = null;
	private OrderFormAdapter adapter = null;
	private String orderformUlr = ""; // 获取订单详情的测试地址

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderform);
		InitView();
		GetOrderFormDetail();
		// lv_orderform.setAdapter(new OrderFormAdapter());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ContextManager.getInstance().setActivityContext(this);
	}
	private void InitView() {
		tv_orderform_num = (TextView) findViewById(R.id.tv_orderform_num);
		tv_orderform_price = (TextView) findViewById(R.id.tv_orderform_price);
		tv_orderform_date = (TextView) findViewById(R.id.tv_orderform_date);
		lv_orderform = (ListView) findViewById(R.id.lv_orderform);
	}

	private void GetOrderFormDetail() {
		Intent intent = getIntent();
		String orderform_num = intent.getStringExtra("orderform_num");
		orderformUlr = orderformUlr + "?orderform_num=" + orderform_num;
		AsyncHttpRequest httpRequest = new AsyncHttpRequest();
		httpRequest.Get(orderformUlr, getApplicationContext(),
				new AsyncHttpRequestHandler() {

					@Override
					public void onSuccess(String content) {
						super.onSuccess(content);
						JSONTokener jsonTokener = new JSONTokener(content);
						infoes = new ArrayList<OrderFormDetailInfo>();
						try {
							JSONObject order_object = (JSONObject) jsonTokener
									.nextValue();
							if (0 == order_object.getInt("code")) {
								JSONObject data = order_object
										.getJSONObject("data");
								String form_num = data.getString("form_num");
								String form_price = data
										.getString("form_price");
								String form_date = data.getString("form_date");

								tv_orderform_num.setText("订单号:" + form_num);
								tv_orderform_price.setText("总价:" + form_price+"元");
								tv_orderform_date.setText("订单日期：" + form_date);

								JSONArray form_item = data
										.getJSONArray("form_item");
								OrderFormDetailInfo info = new OrderFormDetailInfo();
								for (int i = 0; i < form_item.length(); i++) {
									JSONObject object2 = (JSONObject) form_item
											.opt(i);
									info.setOrderformdetail_img(object2
											.getString("book_img"));
									info.setOrderform_bookname(object2
											.getString("book_name"));
									info.setOrderform_bookcount(object2
											.getString("book_count"));
									info.setOrderform_bookprice(object2
											.getString("book_price"));
									info.setOrderform_booktotal(object2
											.getString("book_cost"));
									infoes.add(info);
								}
								adapter = new OrderFormAdapter();
								lv_orderform.setAdapter(adapter);
								for(OrderFormDetailInfo test : infoes){
									String imgUrl = test.getOrderformdetail_img();
									AsyncLoadImage loadImage = AsyncLoadImage
											.getAsyncLoadImageInstance();
									bitmaps = new ArrayList<Bitmap>();
									loadImage.AsyncLoad(imgUrl,
											new AsyncLoadImageHandler() {

												@Override
												public void LoadImgSuccess(
														Bitmap bitmap) {
													// TODO Auto-generated
													// method stub
													super.LoadImgSuccess(bitmap);
													bitmaps.add(bitmap);
													System.out.println("bitmapsize:"
															+ bitmaps
																	.size());
													adapter.notifyDataSetChanged();
													System.out
															.println("add bitmap");
												}

												@Override
												public void LoadImgFailed(
														String ErrorInfo) {
													// TODO Auto-generated
													// method stub
													super.LoadImgFailed(ErrorInfo);
													System.out
															.println(ErrorInfo);
												}

											});
								}
							} else {

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFaile(String content) {
						super.onFaile(content);
					}

				});
	}

	private class OrderFormAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infoes.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			OrderFormDetailInfo info = infoes.get(position);
			LayoutInflater inflater = LayoutInflater
					.from(getApplicationContext());
			View view = inflater.inflate(R.layout.orderform_detailitem, null);
			ImageView list_orderformdetail_img = (ImageView) view
					.findViewById(R.id.list_orderformdetail_img);
			TextView tv_orderform_bookname = (TextView) view
					.findViewById(R.id.tv_orderform_bookname);
			TextView tv_orderform_bookprice = (TextView) view
					.findViewById(R.id.tv_orderform_bookprice);
			TextView tv_orderform_bookcount = (TextView) view
					.findViewById(R.id.tv_orderform_bookcount);
			TextView tv_orderform_booktotal = (TextView) view
					.findViewById(R.id.tv_orderform_booktotal);
			tv_orderform_bookname.setText(info.getOrderform_bookname());
			tv_orderform_bookprice.setText(info.getOrderform_bookprice());
			tv_orderform_bookcount.setText(info.getOrderform_bookcount());
			tv_orderform_booktotal.setText(info.getOrderform_booktotal());
			if (bitmaps.size() > 0 && bitmaps.size() > position) {
				System.out.println("bitmapsize:" + bitmaps.size());
				System.out.println("setbitmap,the position is :" + position);
				list_orderformdetail_img.setImageBitmap(bitmaps.get(position));
			}
			return view;
		}

	}
}
