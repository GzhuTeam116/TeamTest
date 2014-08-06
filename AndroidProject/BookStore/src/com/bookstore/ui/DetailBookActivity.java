package com.bookstore.ui;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.StringRequest;
import com.bookstore.activity.R;
import com.bookstore.data.DetailBook_bean;



public class DetailBookActivity extends BaseActivity{ 
	private RequestQueue mQueue;
	private TextView book_price;
	private TextView book_name;
	private TextView book_press;
	private TextView book_author;
	private ImageView book_img;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailbook);
		
		book_price = (TextView)findViewById(R.id.book_price);
		book_name = (TextView)findViewById(R.id.book_name);
		book_press = (TextView)findViewById(R.id.book_press);
		book_author = (TextView)findViewById(R.id.book_author);
		book_img = (ImageView)findViewById(R.id.book_image);
		
		 Intent intent=getIntent();
		 Bundle bundle=intent.getExtras();
		 
		 String detail_book_no = bundle.getString("detail");
		 
}
	private void down_detalbook(String one){
		
		String url = "" + one ;
		 StringRequest stringRequest = new StringRequest(url,  
                new Response.Listener<String>() {  
                    @Override  
                    public void onResponse(String response) {  
                  
                    	try {
                    		String info = new String (response.toString().getBytes("ISO-8859-1"),"utf-8");
                    		DetailBook_bean a = new DetailBook_bean();
                    		JSONObject b= new JSONObject(info);
                    		JSONObject c= b.getJSONObject("date");
                    		
                    		book_price.setText(c.getString("book_price"));
                    		book_name.setText(c.getString("book_name"));
                    		book_press.setText(c.getString("book_press"));
                    		book_author.setText(c.getString("book_author"));
                    		String book_url = c.getString("book_img");
                    		
                    		 final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);  
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
                 	        ImageListener listener = ImageLoader.getImageListener(book_img, R.drawable.ic_launcher,R.drawable.ic_launcher);  
                 	        imageLoader.get( book_url, listener);
                    		
                 	        
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Toast.makeText(DetailBookActivity.this, "系统出错",
									     Toast.LENGTH_SHORT).show();
							}
					
                    }  
                }, new Response.ErrorListener() {  
                    @Override  
                    public void onErrorResponse(VolleyError error) {  
                        Log.e("TAG", error.getMessage(), error); 
                        Toast.makeText(DetailBookActivity.this, "网络连接错误",
								     Toast.LENGTH_SHORT).show();
                        
                    }  
                });  
mQueue.add(stringRequest);
mQueue.start();
	}
	
}