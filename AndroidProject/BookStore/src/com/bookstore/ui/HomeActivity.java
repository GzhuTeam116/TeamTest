package com.bookstore.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.StringRequest;
import com.bookstore.activity.R;
import com.bookstore.data.Home_gridview_adapter;
import com.bookstore.data.Home_gridview_bean;
import com.bookstore.data.PromotionsImg_bean;
import com.bookstore.data.sort_book_bean;
import com.bookstore.data.sort_book_gridview;

public class HomeActivity extends BaseActivity implements ViewFactory,
		OnTouchListener {

	private ImageSwitcher imageSwicher;
	private int[] arrayPictures = { R.drawable.title1, R.drawable.tilte2,
			R.drawable.title3, R.drawable.title4 };
	private int pictureIndex; // Ҫ��ʾ��ͼƬ��ͼƬ�����е�Index
	private float touchDownX; // ���һ���ʱ��ָ���µ�X���
	private float touchUpX; // ���һ���ʱ��ָ�ɿ���X���
	private GridView gv_hotsale = null;
	private GridView gv_hobby = null;
	private GridView gv_category = null;
	private RequestQueue mQueue;
	private int screenW = 0;
	private List<Home_gridview_bean> ListHotsale_book;
	private List<Home_gridview_bean> ListHobbyBook ;
	private List<sort_book_bean> Listsort;
	private List<PromotionsImg_bean> ListPromotionsImg ;

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
		// ΪImageSwicher����Factory������ΪImageSwicher����ImageView
		imageSwicher.setFactory(HomeActivity.this);
		// ����ImageSwitcher���һ����¼�
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
		//SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
		//		data, R.layout.hotsale_gridviewitem, new String[] { "icon" },
		//		new int[] { R.id.iv_hotsaleicon });
		
		Home_gridview_adapter adapter1 = new Home_gridview_adapter(ListHotsale_book,HomeActivity.this,mQueue);
		gv_hotsale.setAdapter(adapter1);
		Log.v("test", "gridview complete");
		layout.addView(gv_hotsale);
		gv_hotsale.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Intent intent = new Intent();
				intent.putExtra("detail",ListHotsale_book.get(arg2).getBook_id());
				intent.setClass(HomeActivity.this, DetailBookActivity.class);
				startActivity(intent);
				
			}
		});
	
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
		//SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
		//		data, R.layout.hobby_gridviewitem, new String[] { "icon" },
		//		new int[] { R.id.iv_hobbyicon });
	//	gv_hobby.setAdapter(adapter);
		
		Home_gridview_adapter adapter1 = new Home_gridview_adapter(ListHobbyBook,HomeActivity.this,mQueue);
		gv_hobby.setAdapter(adapter1);
		Log.v("test", "gridview complete");
		layout.addView(gv_hobby);
		gv_hobby.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.putExtra("detail",ListHobbyBook.get(arg2).getBook_id());
				intent.setClass(HomeActivity.this, DetailBookActivity.class);
				startActivity(intent);
			}
		});
		
	}

	private void InitGridViewCategory() {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("category_name", "����" + i);
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
	//	SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
		//		data, R.layout.category_gridviewitem,
	//			new String[] { "category_name" },
			//	new int[] { R.id.tv_hotsaleicon });
	//	
		
		sort_book_gridview adapter1 = new sort_book_gridview(Listsort,HomeActivity.this,mQueue);
		gv_category.setAdapter(adapter1);
		
		//gv_category.setAdapter(adapter);
		Log.v("test", "gridview complete");
		layout.addView(gv_category);
		gv_category.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("sort",Listsort.get(arg2).getSort_id());
				intent.setClass(HomeActivity.this, sort_SecCategoryIitem_Activity.class);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public View makeView() {
		
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(arrayPictures[pictureIndex]);
	//	imageView.setImageURI(ListPromotionsImg.get(pictureIndex).getPromotions_img1());
	/*	 final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);  
	        ImageCache imageCache = new ImageCache() {  
	            @Override  
	            public void putBitmap(String key, Bitmap value) {  
	                lruCache.put(key, value);  
	            }  
	  
	            @Override  
	            public Bitmap getBitmap(String key) {  
	                return lruCache.get(key);  
	            }  
	        };  
	        
	        ImageLoader imageLoader = new ImageLoader(mQueue, imageCache);  
	        ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.ic_launcher,R.drawable.ic_launcher);  
	        imageLoader.get(ListPromotionsImg.get(pictureIndex).getPromotions_img1(), listener);
		*/
		
		return imageView;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			touchDownX = event.getX();// ȡ�����һ���ʱ��ָ���µ�X���
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			touchUpX = event.getX();// ȡ�����һ���ʱ��ָ�ɿ���X���
			// �������ң���ǰһ��
			if (touchUpX - touchDownX > 100) {
				// ȡ�õ�ǰҪ����ͼƬ��index
				pictureIndex = pictureIndex == 0 ? arrayPictures.length - 1
						: pictureIndex - 1;
				// ����ͼƬ�л��Ķ���
				imageSwicher.setInAnimation(AnimationUtils.loadAnimation(this,
						android.R.anim.slide_in_left));
				imageSwicher.setOutAnimation(AnimationUtils.loadAnimation(this,
						android.R.anim.slide_out_right));
				// ���õ�ǰҪ����ͼƬ
				
				imageSwicher.setImageResource(arrayPictures[pictureIndex]);
				// �������󣬿���һ��
			} else if (touchDownX - touchUpX > 100) {
				// ȡ�õ�ǰҪ����ͼƬ��index
				pictureIndex = pictureIndex == arrayPictures.length - 1 ? 0
						: pictureIndex + 1;
				// ����ͼƬ�л��Ķ���
				imageSwicher.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.slide_out_left));
				imageSwicher.setOutAnimation(AnimationUtils.loadAnimation(this,
						R.anim.slide_in_right));
				// ���õ�ǰҪ����ͼƬ
				imageSwicher.setImageResource(arrayPictures[pictureIndex]);
				
				
			}
			else {
				/*
				 * 点击后进行数据处理和跳转页面
				 * 
				 */
			}
			return true;
		}
		return false;
	}
	
	private void down_index(){ // 下载主页信息  主页内容请求  API 1  FROM 数据定义格式文档
	 
			String url = "";
		/*
		 * 
		 * 1.获取热销信息（前十）
		2.获取用户喜好信息（十个）
		3.获取商品分类列表信息
		4.获取促销信息（待定）

		 * 
		 * 
		 */
		       StringRequest stringRequest = new StringRequest(url,  
		                                new Response.Listener<String>() {  
		                                    @Override  
		                                    public void onResponse(String response) {  
		                                    	
		                                    	List<Home_gridview_bean> listHotsale_book = new ArrayList<Home_gridview_bean>();
		                                    	List<Home_gridview_bean> listHobbyBook = new ArrayList<Home_gridview_bean>();
		                                    	List<sort_book_bean> listsort = new ArrayList<sort_book_bean>();
		                                    	List<PromotionsImg_bean> listPromotionsImg = new ArrayList<PromotionsImg_bean>();
		                                    	
		                                    	try {
													String info = new String (response.toString().getBytes("ISO-8859-1"),"utf-8");
													JSONObject a = new JSONObject(info);
													String access_method = a.getString("access_method"); //判断内网还是外网
													JSONArray HotSaleBook = a.getJSONArray("HotSaleBook");
													
													
													for ( int i= 0; i< HotSaleBook.length() ; i++){ //
														Home_gridview_bean one = new Home_gridview_bean();
														JSONObject one_json = HotSaleBook.getJSONObject(i);
														one.setI(i);
														one.setBook_id(one_json.getString("book_id"));
														one.setBook_img(one_json.getString("book_img"));
														one.setSales_volume(one_json.getString("sales_volume"));
														listHotsale_book.add(one);
														
													}
													ListHotsale_book = listHotsale_book;
													/*
													 * 上面的是 HotSaleBook 的信息解析
													 */
														
													JSONArray HobbyBook = a.getJSONArray("HobbyBook");
													for( int i = 0 ;i < HobbyBook.length() ; i++){
														Home_gridview_bean one = new Home_gridview_bean();
														JSONObject one_json = HotSaleBook.getJSONObject(i);
														one.setI(i);
														one.setBook_id(one_json.getString("book_id"));
														one.setBook_img(one_json.getString("book_img"));
														one.setSales_volume(one_json.getString("sales_volume"));
														listHobbyBook.add(one);
													}
													ListHobbyBook = listHobbyBook;
													/*
													 *  上面的是 HobbyBook 的信息解析
													 */
													JSONArray sort_book = a.getJSONArray("sort_book");
													for( int i= 0 ; i< sort_book.length() ; i++ ){
														sort_book_bean one = new sort_book_bean();
														JSONObject one_json = sort_book.getJSONObject(i);
														one.setI(i);
														one.setSort_id(one_json.getString("sort_id"));
														one.setSort_name(one_json.getString("sort_name"));
														listsort.add(one);
														
													}
													Listsort = listsort;
													
													/*
													 * 上面的是商品分类信息 
													 */
													
													JSONArray PromotionsImg = a.getJSONArray("PromotionsImg");
													for ( int i = 0; i<PromotionsImg.length() ; i++  ){
														PromotionsImg_bean one =  new PromotionsImg_bean();
														JSONObject one_json = PromotionsImg.getJSONObject(i);
														one.setId(i);
														one.setPromotions_id(one_json.getString("promotions_id"));
														one.setPromotions_img1(one_json.getString("promotions_img1"));
														one.setPromotions_tag(one_json.getString("promotions_tag"));
														listPromotionsImg.add(one);
													}
													ListPromotionsImg = listPromotionsImg ;
													
													
												} catch (Exception e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
													Toast.makeText(HomeActivity.this, "系统出错",
														     Toast.LENGTH_SHORT).show();
												}
										
		                                    }  
		                                }, new Response.ErrorListener() {  
		                                    @Override  
		                                    public void onErrorResponse(VolleyError error) {  
		                                        Log.e("TAG", error.getMessage(), error); 
		                                        Toast.makeText(HomeActivity.this, "网络连接错误",
													     Toast.LENGTH_SHORT).show();
		                                        
		                                    }  
		                                });  
		        mQueue.add(stringRequest);
		        mQueue.start();
	}

}
