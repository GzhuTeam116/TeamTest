package com.bookstore.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.bookstore.control.ContextManager;
import com.bookstore.data.SearchResult_bean;


public class ListItemActivity extends Activity implements OnScrollListener {
	private RequestQueue mQueue;
	private listViewAdapter adapter;
	private ListView listView = null;
	private View moreview = null;
//	private Thread mThread = null;
	private List<SearchResult_bean> ListSearch_book;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listitem);
		 Intent intent=getIntent();
		 Bundle bundle=intent.getExtras();
		 String search = bundle.getString("search");
		 
		 down(search);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ContextManager.getInstance().setActivityContext(this);
	}

	private void init() { // 执行完下载后 加载
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		moreview = inflater.inflate(R.layout.listitem_load_more, null);
		listView = (ListView) findViewById(R.id.lv_listitem);
		listView.addFooterView(moreview);
		adapter = new listViewAdapter(ListSearch_book, ListItemActivity.this, mQueue);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {// 点击详细信息

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "点击进入"+arg2+"本书",
					     Toast.LENGTH_SHORT).show();
				String one = ListSearch_book.get(arg2).getBook_id();
				
				Intent intent = new Intent();
				intent.putExtra("result", one);
				intent.setClass(ListItemActivity.this, ResultActivity.class);
				startActivity(intent);
				
				
			}
		});
	}
// 缺乏数据 和 listview 需要重新做 layout 也需要 
	class listViewAdapter extends BaseAdapter {
		private LayoutInflater inflater; 
	    private List<SearchResult_bean> pictures; 
	    private RequestQueue mQueue;
	    
	    public  listViewAdapter (List<SearchResult_bean> pictures, Context context,RequestQueue one) 
	    { 
	        super(); 
	        this.pictures=pictures;
	        this.mQueue=one;
	        inflater = LayoutInflater.from(context);
	    
	    } 
		int count = 10;

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
	            convertView = inflater.inflate(R.layout.listview_search_result_item, null); 
	            viewHolder = new ViewHolder(); 
	          
	            viewHolder.image = (ImageView) convertView.findViewById(R.id.search_view);
	            viewHolder.textname = (TextView)convertView.findViewById(R.id.textview_name);
	            viewHolder.text_price = (TextView)convertView.findViewById(R.id.text_price);
	            convertView.setTag(viewHolder); 
	            viewHolder.textname.setText(pictures.get(position).getCommodity_name());//商品名称
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
		        imageLoader.get(pictures.get(position).getCommodity_icon(), listener);
		        
		      
	            
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
		/*public View getView(int pos, View v, ViewGroup p) {
			TextView view;
			if (v == null) {
				view = new TextView(ListItemActivity.this);
			} else {
				view = (TextView) v;
			}
			
			view.setText("ListItem " + pos);
			view.setTextSize(20f);
			view.setGravity(Gravity.CENTER);
			view.setHeight(60);
			return view;
		}*/
	//}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem + visibleItemCount == totalItemCount) {
			if (adapter.count <= 41) {
				adapter.count += 10;
				int currentPage = adapter.count / 10;
				Toast.makeText(getApplicationContext(),
						"��" + currentPage + "ҳ", Toast.LENGTH_LONG).show();
			} else {
				listView.removeFooterView(moreview);
			}
			adapter.notifyDataSetChanged();
//		
//			
		}
	}
	
	private void down(String one  ){
		String url = "" + one ;
		
		 StringRequest stringRequest = new StringRequest(url,  
                 new Response.Listener<String>() {  
                     @Override  
                     public void onResponse(String response) {  
                    	List<SearchResult_bean> listsearch_book = new ArrayList<SearchResult_bean>();
   
                     	
                     	try {
                     		String info = new String (response.toString().getBytes("ISO-8859-1"),"utf-8");
                     		
                     		
                     		init();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Toast.makeText(ListItemActivity.this, "系统出错",
									     Toast.LENGTH_SHORT).show();
							}
					
                     }  
                 }, new Response.ErrorListener() {  
                     @Override  
                     public void onErrorResponse(VolleyError error) {  
                         Log.e("TAG", error.getMessage(), error); 
                         Toast.makeText(ListItemActivity.this, "网络连接错误",
								     Toast.LENGTH_SHORT).show();
                         
                     }  
                 });  
mQueue.add(stringRequest);
mQueue.start();
		
	}
}
