package com.bookstore.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.StringRequest;
import com.bookstore.activity.R;
import com.bookstore.data.SecCategoryIitem_bean;


public class sort_SecCategoryIitem_Activity extends BaseActivity {
	
	private RequestQueue mQueue;
	private List<SecCategoryIitem_bean> list_sort;
	private int page_num=0;
	private ListView listview;
	private listViewAdapter adapter;
	private Button button_next;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sort);
		
		 Intent intent=getIntent();
		 Bundle bundle=intent.getExtras();
		 final String sort_id = bundle.getString("sort");
		 button_next = (Button)findViewById(R.id.button_next);
		 button_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				page_num = page_num + 1;
				
				down(sort_id,""+page_num);
			}
		});
		}
	
	class listViewAdapter extends BaseAdapter {
		private LayoutInflater inflater; 
	    private List<SecCategoryIitem_bean> pictures; 
	    private RequestQueue mQueue;
	    
	    public  listViewAdapter (List<SecCategoryIitem_bean> pictures, Context context,RequestQueue one) 
	    { 
	        super(); 
	        this.pictures=pictures;
	        this.mQueue=one;
	        inflater = LayoutInflater.from(context);
	    
	    } 
		
		public int getCount() {
	        if (null != pictures) 
	        { 
	            return pictures.size(); 
	        } else
	        { 
	            return 0; 
	        } 
		}

		public Object getItem(int pos) {
			   return pictures.get(pos); 
		}

		public long getItemId(int pos) {
			
			return pos;
		}
		public View getView(int position, View convertView, ViewGroup parent ) 
	    { 
	        ViewHolder viewHolder; 
	        if (convertView == null) 
	        { 
	            convertView = inflater.inflate(R.layout.listview_sort_item, null); 
	            viewHolder = new ViewHolder(); 
	          
	            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView1);
	            viewHolder.textname = (TextView)convertView.findViewById(R.id.text_name);
	            viewHolder.text_price = (TextView)convertView.findViewById(R.id.text_price);
	            convertView.setTag(viewHolder); 
	            viewHolder.textname.setText(pictures.get(position).getBook_name());//商品名称
	            viewHolder.text_price.setText(pictures.get(position).getBook_price());//商品价钱
	            
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
		        ImageListener listener = ImageLoader.getImageListener(viewHolder.image, R.drawable.ic_launcher,R.drawable.ic_launcher);  
		        imageLoader.get(pictures.get(position).getBook_img(), listener);
		        
		      
	            
	        } else
	        { 
	            viewHolder = (ViewHolder) convertView.getTag(); 
	        } 
	 
	        return convertView; 
	    }
	    

	    
	 
	} 

	class ViewHolder 
	{ 

	    public ImageView image; 
	    public TextView textname;
	    public TextView text_price;

	}
	
	private void down(final String one,final String two  ){
		String url = "" ;
		
		 StringRequest stringRequest = new StringRequest(Method.POST,url,  
                 new Response.Listener<String>() {  
                     @Override  
                     public void onResponse(String response) {  
                    	List<SecCategoryIitem_bean> List_sort = new ArrayList<SecCategoryIitem_bean>();
                     	try {
                     		
                     		String info = new String (response.toString().getBytes("ISO-8859-1"),"utf-8");
                     		JSONObject a = new JSONObject(info);
                     		JSONArray array = a.getJSONArray("SecCategoryIitem");
                     		for( int i = 0 ;i < array.length() ; i++){
                     			JSONObject b = array.getJSONObject(i);
                     			SecCategoryIitem_bean c = new SecCategoryIitem_bean();
                     			c.setId(i);
                     			c.setBook_id(b.getString("book_id"));
                     			c.setBook_img(b.getString("book_img"));
                     			c.setBook_name(b.getString("book_name"));
                     			c.setBook_price(b.getString("book_price"));
                     			List_sort.add(c);
                     			
                     		}
                     		list_sort=List_sort;
                     		init();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Toast.makeText(sort_SecCategoryIitem_Activity.this, "系统出错",
									     Toast.LENGTH_SHORT).show();
							}
					
                     }  
                 }, new Response.ErrorListener() {  
                     @Override  
                     public void onErrorResponse(VolleyError error) {  
                         Log.e("TAG", error.getMessage(), error); 
                         Toast.makeText(sort_SecCategoryIitem_Activity.this, "网络连接错误",
								     Toast.LENGTH_SHORT).show();
                         
                     }  
                     
                 })  {
			  @Override  
			  protected Map<String, String> getParams() throws AuthFailureError {  
			  Map<String, String> map = new HashMap<String, String>();  
			  map.put("id",one );  
			  map.put("page_num", two);  
			  return map;  
			  } } ;  
mQueue.add(stringRequest);
mQueue.start();
		
	}
	
	private void init(){
		adapter = new listViewAdapter(list_sort, sort_SecCategoryIitem_Activity.this, mQueue);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				
			}
		});
	}
}
