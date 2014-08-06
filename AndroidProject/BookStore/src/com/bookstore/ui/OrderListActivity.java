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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bookstore.activity.R;
import com.bookstore.control.ContextManager;
import com.bookstore.data.OrderListInfo;
import com.bookstore.net.AsyncHttpRequest;
import com.bookstore.net.AsyncHttpRequestHandler;
import com.bookstore.net.AsyncLoadImage;
import com.bookstore.net.AsyncLoadImageHandler;

public class OrderListActivity extends Activity {

	private ListView lv_orderlist = null;
	private String orderlistUrl = "http://172.18.4.27:8080/jsontest.html";
	private List<OrderListInfo> infoes = null;
	private List<Bitmap> bitmaps = null;
	private OrderlistAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderlist);
		lv_orderlist = (ListView) findViewById(R.id.lv_orderlist);
		requestInfo();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ContextManager.getInstance().setActivityContext(this);
	}

	private void requestInfo() {
		AsyncHttpRequest httpRequest = new AsyncHttpRequest();
		httpRequest.Get(orderlistUrl, getApplicationContext(),
				new AsyncHttpRequestHandler() {

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						orderlistInfo(content);
						lv_orderlist
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										Intent intent = new Intent();
										intent.putExtra("orderform_num", infoes
												.get(position)
												.getOrderlist_num());
										;
										intent.setClass(
												getApplicationContext(),
												OrderFormActivity.class);
										startActivity(intent);
									}
								});
					}

					@Override
					public void onFaile(String content) {
						// TODO Auto-generated method stub
						super.onFaile(content);
						Toast.makeText(getApplicationContext(), "获取订单列表失败",
								Toast.LENGTH_SHORT).show();
					}

				});
	}

	private void orderlistInfo(String content) {
		JSONTokener jsonTokener = new JSONTokener(content);
		infoes = new ArrayList<OrderListInfo>();
		try {
			JSONObject object = (JSONObject) jsonTokener.nextValue();
			if (0 == object.getInt("code")) {
				JSONArray data = object.getJSONArray("data");
				for (int i = 0; i < data.length(); i++) {
					JSONObject object2 = (JSONObject) data.opt(i);
					String form_num = object2.getString("form_num");
					String form_count = object2.getString("form_count");
					String form_date = object2.getString("form_date");
					String book_img = object2.getString("book_img");
					OrderListInfo info = new OrderListInfo(book_img, form_num,
							form_date, form_count);
					infoes.add(info);
				}
				adapter = new OrderlistAdapter();
				lv_orderlist.setAdapter(adapter);
				for (OrderListInfo test : infoes) {
					String iconUrl = test.getOrderlist_img();
					AsyncLoadImage loadImage = AsyncLoadImage
							.getAsyncLoadImageInstance();
					bitmaps = new ArrayList<Bitmap>();
					loadImage.AsyncLoad(iconUrl, new AsyncLoadImageHandler() {

						@Override
						public void LoadImgSuccess(Bitmap bitmap) {
							super.LoadImgSuccess(bitmap);
							bitmaps.add(bitmap);
							System.out.println("bitmapsize:" + bitmaps.size());
							adapter.notifyDataSetChanged();
							System.out.println("add bitmap");
						}

						@Override
						public void LoadImgFailed(String ErrorInfo) {
							super.LoadImgFailed(ErrorInfo);
							System.out.println(ErrorInfo);
						}

					});
				}
			} else {
				Toast.makeText(getApplicationContext(), "获取订单列表失败",
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "获取订单列表失败",
					Toast.LENGTH_SHORT).show();
		}
	}

	private class OrderlistAdapter extends BaseAdapter {

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
			View view = View.inflate(getApplicationContext(),
					R.layout.order_list, null);
			OrderListInfo info = infoes.get(position);
			ImageView list_orderlistimg = (ImageView) view
					.findViewById(R.id.list_orderlistimg);
			TextView tv_orderlist_ordernum = (TextView) view
					.findViewById(R.id.tv_orderlist_ordernum);
			TextView tv_orderlist_orderdate = (TextView) view
					.findViewById(R.id.tv_orderlist_orderdate);
			TextView tv_orderlist_orderprice = (TextView) view
					.findViewById(R.id.tv_orderlist_orderprice);
			tv_orderlist_ordernum.setText("订单号:" + info.getOrderlist_num());
			tv_orderlist_orderdate.setText("日期" + info.getOrderlist_date());
			tv_orderlist_orderprice.setText("总价:" + info.getOrderlist_pirce()
					+ "元");
			if (bitmaps.size() > 0 && bitmaps.size() > position) {
				System.out.println("bitmapsize:" + bitmaps.size());
				System.out.println("setbitmap,the position is :" + position);
				list_orderlistimg.setImageBitmap(bitmaps.get(position));
			}
			return view;
		}

	}
}
