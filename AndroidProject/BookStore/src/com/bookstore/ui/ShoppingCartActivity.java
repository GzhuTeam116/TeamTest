package com.bookstore.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bookstore.activity.R;
import com.bookstore.control.ContextManager;
import com.bookstore.data.ShopCartInfo;
import com.bookstore.extra.Alipayentry;
import com.bookstore.extra.Product;
import com.bookstore.net.AsyncHttpRequest;
import com.bookstore.net.AsyncHttpRequestHandler;
import com.bookstore.net.AsyncLoadImage;
import com.bookstore.net.AsyncLoadImageHandler;

public class ShoppingCartActivity extends BaseActivity {

	private ImageView iv_emptycart = null;
	private TextView tv_cartips = null;
	private Button pay_btn = null;
	private PopupWindow pop = null;
	private AsyncHttpRequest request;
	private ListView lv_shopcart = null;
	private List<ShopCartInfo> infoes = null;
	private List<Bitmap> bitmaps = null;
	private ShopcartAdapter adapter = null;
	private int count_temp;
	private String orderform_num = null;
	private String orderform_price = null;
	private String CartInfUrl = "http://172.18.4.27:8080/carttest.html";// 获取购物车信息的测试地址
//	private String PayUrl = "";// 支付的测试地址
	private String DelitemUrl = "";// 删除购物条目的测试地址
	private String EidtcountUrl = "";// 更改购物车数量

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopcart);
		lv_shopcart = (ListView) findViewById(R.id.lv_shopcart);
		iv_emptycart = (ImageView) findViewById(R.id.empty_cart);
		tv_cartips = (TextView) findViewById(R.id.cart_tips);
		pay_btn = (Button) findViewById(R.id.pay_btn);
		request = new AsyncHttpRequest();
		pay_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// pay function code
				showDialog_PAY();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		RequestShopcartInfo();
		System.out.println("onresume");
		ContextManager.getInstance().setActivityContext(this);
	}

	private void RequestShopcartInfo() {
		request.Get(CartInfUrl, ShoppingCartActivity.this,
				new AsyncHttpRequestHandler() {

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						ShopcartInfoAnalysis(content);
					}

					@Override
					public void onFaile(String content) {
						// TODO Auto-generated method stub
						super.onFaile(content);
					}

				});
	}

	private class ShopcartAdapter extends BaseAdapter {

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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = View.inflate(getApplicationContext(),
					R.layout.shopcart_infoitem, null);
			ShopCartInfo info = infoes.get(position);
			ImageView shopcartimg = (ImageView) view
					.findViewById(R.id.list_shopcartimg);
			TextView name = (TextView) view
					.findViewById(R.id.tv_shopcart_bookname);
			TextView price = (TextView) view
					.findViewById(R.id.tv_shopcart_bookprice);
			TextView count = (TextView) view
					.findViewById(R.id.tv_shopcart_bookcount);
			Button del_item = (Button) view.findViewById(R.id.del_cartitem);
			Button edit_cartitem = (Button) view
					.findViewById(R.id.edit_cartitem);
			name.setText(info.getBook_name());
			price.setText("单价:" + info.getBook_price() + "元");
			count.setText("数量:X" + info.getBook_count());
			if (bitmaps.size() > 0 && bitmaps.size() > position) {
				System.out.println("bitmapsize:" + bitmaps.size());
				System.out.println("setbitmap,the position is :" + position);
				shopcartimg.setImageBitmap(bitmaps.get(position));
			}
			del_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (infoes.size() > 0) {
						// 在这之前，要先请求服务器，提交购物车的id，服务器进行删除条目后才更新UI
						// String id = infoes.get(position).getShopcartid();
						AsyncHttpRequest httpRequest = new AsyncHttpRequest();
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("shopcartid", infoes
								.get(position).getShopcartid()));
						httpRequest.Post(DelitemUrl, ShoppingCartActivity.this,
								params, new AsyncHttpRequestHandler() {

									@Override
									public void onSuccess(String content) {
										// TODO Auto-generated method stub
										super.onSuccess(content);
										JSONTokener jsonTokener = new JSONTokener(
												content);
										JSONObject object;
										try {
											object = (JSONObject) jsonTokener
													.nextValue();
											if (0 == object.getInt("code")) {
												String data = object
														.getString("data");
												if ("success".equals(data)) {
													infoes.remove(position);
													adapter.notifyDataSetChanged();
													if (infoes.size() == 0) {
														pay_btn.setVisibility(8);
														tv_cartips
																.setVisibility(0);
														iv_emptycart
																.setVisibility(0);
													}
												} else if ("fail".equals(data)) {
													Toast.makeText(
															getApplicationContext(),
															"删除失败",
															Toast.LENGTH_SHORT)
															.show();
												}
											} else {
												Toast.makeText(
														getApplicationContext(),
														"删除失败",
														Toast.LENGTH_SHORT)
														.show();
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											Toast.makeText(
													getApplicationContext(),
													"删除失败", Toast.LENGTH_SHORT)
													.show();
										}
									}

									@Override
									public void onFaile(String content) {
										// TODO Auto-generated method stub
										super.onFaile(content);
										Toast.makeText(getApplicationContext(),
												"删除失败", Toast.LENGTH_SHORT)
												.show();
									}

								});
					}
				}
			});
			edit_cartitem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					initPopWindows(position);
				}
			});
			return view;
		}

	}

	public void initPopWindows(final int position) {
		LayoutInflater myinflater = LayoutInflater.from(this);
		// Log.v("test", "PopWindows界面加载……");
		View view = myinflater.inflate(R.layout.shopcart_popwnd, null);
		// Log.v("test", "PopWindows界面加载完成");
		pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, false);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(true);
		pop.setFocusable(true);
		pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		TextView bookname = (TextView) view
				.findViewById(R.id.pop_tv_cartitemname);
		final EditText count = (EditText) view
				.findViewById(R.id.pop_ed_itemcount);
		Button del_item = (Button) view.findViewById(R.id.del_cartitemcount);
		Button add_item = (Button) view.findViewById(R.id.add_cartitemcount);
		Button cancel = (Button) view.findViewById(R.id.pop_cancel);
		Button confirm = (Button) view.findViewById(R.id.pop_confirm);

		bookname.setText(infoes.get(position).getBook_name());
		count.setText(infoes.get(position).getBook_count());
		count_temp = Integer.parseInt(count.getText().toString().trim());
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.del_cartitemcount:
					if (count_temp > 1) {
						count_temp--;
						count.setText(count_temp + "");
					}
					break;

				case R.id.add_cartitemcount:
					count_temp++;
					count.setText(count_temp + "");
					break;
				case R.id.pop_cancel:
					pop.dismiss();
					break;
				case R.id.pop_confirm:
					// 在这之前，要先请求服务器，提交购物车id,商品id和该商品数量，后才更新UI，以下为成功的情况
					AsyncHttpRequest request = new AsyncHttpRequest();
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("book_id", infoes.get(
							position).getBook_id()));
					params.add(new BasicNameValuePair("book_count", infoes.get(
							position).getBook_count()));
					request.Post(EidtcountUrl, ShoppingCartActivity.this,
							params, new AsyncHttpRequestHandler() {

								@Override
								public void onSuccess(String content) {
									super.onSuccess(content);
									JSONTokener jsonTokener = new JSONTokener(
											content);
									JSONObject object;
									try {
										object = (JSONObject) jsonTokener
												.nextValue();
										if (0 == object.getInt("code")) {
											String data = object
													.getString("data");
											if ("success".equals(data)) {
												infoes.get(position)
														.setBook_count(
																count_temp + "");
												adapter.notifyDataSetChanged();
												pop.dismiss();
											} else {
												Toast.makeText(
														getApplicationContext(),
														"库存不足",
														Toast.LENGTH_SHORT)
														.show();
											}
										} else {
											Toast.makeText(
													getApplicationContext(),
													"更改数量失败",
													Toast.LENGTH_SHORT).show();
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}

								@Override
								public void onFaile(String content) {
									super.onFaile(content);
									Toast.makeText(getApplicationContext(),
											"无法连接到服务器", Toast.LENGTH_SHORT)
											.show();
								}

							});
					break;
				}
			}
		};
		del_item.setOnClickListener(listener);
		add_item.setOnClickListener(listener);
		cancel.setOnClickListener(listener);
		confirm.setOnClickListener(listener);
	}

	// 支付
	private void VerfyPurchase() {
		Alipayentry alipayentry = new Alipayentry(ShoppingCartActivity.this);
		Product product = new Product(orderform_num, orderform_num + " key",
				orderform_num + " detail", orderform_price);
		alipayentry.Pay(product);
	}

	// 解析购物车数据
	private void ShopcartInfoAnalysis(String content) {
		JSONTokener jsonTokener = new JSONTokener(content);
		infoes = new ArrayList<ShopCartInfo>();
		// Log.v("test", "content-->" + content);
		try {
			JSONObject object = (JSONObject) jsonTokener.nextValue();
			if (0 == object.getInt("code")) {
				JSONObject jsonObject = object.getJSONObject("data");
				orderform_num = jsonObject.getString("orderform_num");
				orderform_price = jsonObject.getString("orderform_price");
				JSONArray array = jsonObject.getJSONArray("shopcart");
				if (array.length() == 0) {
					tv_cartips.setVisibility(0);
					iv_emptycart.setVisibility(0);
					pay_btn.setVisibility(8);
					// Toast.makeText(getApplicationContext(),
					// "购物车为空", Toast.LENGTH_SHORT).show();
				} else {
					iv_emptycart.setVisibility(8);
					tv_cartips.setVisibility(8);
					pay_btn.setVisibility(0);
					for (int i = 0; i < array.length(); i++) {
						JSONObject object2 = (JSONObject) array.opt(i);
						ShopCartInfo info = new ShopCartInfo();
						info.setShopcartid(object2.getString("shopcartid"));
						// Log.v("test",
						// "cartid-->"
						// + object2
						// .getString("shopcartid"));
						info.setBook_id(object2.getString("book_id"));
						info.setBook_count(object2.getString("book_count"));
						info.setBook_img(object2.getString("book_img"));
						info.setBook_name(object2.getString("book_name"));
						info.setBook_price(object2.getString("book_price"));
						infoes.add(info);
					}
					adapter = new ShopcartAdapter();
					lv_shopcart.setAdapter(adapter);
					for (ShopCartInfo test : infoes) {
						String imgurl = test.getBook_img();
						System.out.println("img:" + imgurl);
						AsyncLoadImage loadImage = AsyncLoadImage
								.getAsyncLoadImageInstance();
						bitmaps = new ArrayList<Bitmap>();
						if (bitmaps.size() == 0) {
							System.out.println("bitmaps is null");
						} else {
							System.out.println("bitmaps is not null");
						}
						loadImage.AsyncLoad(imgurl,
								new AsyncLoadImageHandler() {

									@Override
									public void LoadImgSuccess(Bitmap bitmap) {
										// TODO Auto-generated
										// method stub
										super.LoadImgSuccess(bitmap);
										bitmaps.add(bitmap);
										System.out.println("bitmapsize:"
												+ bitmaps.size());
										adapter.notifyDataSetChanged();
										System.out.println("add bitmap");
									}

									@Override
									public void LoadImgFailed(String ErrorInfo) {
										// TODO Auto-generated
										// method stub
										super.LoadImgFailed(ErrorInfo);
										System.out.println(ErrorInfo);
									}

								});
					}
				}
			} else {
				// code 为 1
				tv_cartips.setVisibility(0);
				iv_emptycart.setVisibility(0);
				pay_btn.setVisibility(8);
				Toast.makeText(getApplicationContext(), "获取购物车信息失败",
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showDialog_PAY() {
		AlertDialog.Builder ad = new Builder(this);
		ad.setTitle("支付信息确认");
		ad.setMessage("确定要进行支付？请仔细核对要支付的商品");
		ad.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				VerfyPurchase();
			}
		});
		ad.setNegativeButton("取消", null);
		ad.show();
	}

}
