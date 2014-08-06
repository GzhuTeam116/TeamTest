package com.bookstore.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class down_SearchResult {
	private Context context;
	private RequestQueue mQueue;
	public down_SearchResult(RequestQueue one ,Context con){
		super();
		this.context=con;
		this.mQueue=one;
	}
	
	public List<SearchResult_bean> get_SearchResult(String what_you_find){//可以获取 热搜信息 和热销 推荐 信息 一开始的俩个gridview 
		
		//API 2 和 3 
		String url = ""+what_you_find;
		final List<SearchResult_bean> list = new ArrayList<SearchResult_bean>();
		// list 保存数组信息
		/*
		 * "search_result": [
        {
            "book_id": "商品id 1",
            "commodity_icon": "图片链接1",
            "commodity_name": "商品名称1",
            "book_price": "商品价格1"
        },
        {
            "book_id": "商品id 2",
            "commodity_icon": "图片链接2",
            "commodity_name": "商品名称2",
            "book_price": "商品价格2"
        }

		*/
		
		       StringRequest stringRequest = new StringRequest(url,  
		                                new Response.Listener<String>() {  
		                                    @Override  
		                                    public void onResponse(String response) {  
		                                    	
		                                    	try {
													String info = new String (response.toString().getBytes("ISO-8859-1"),"utf-8");
													// 数据转换成 UTF-8
													JSONObject one = new JSONObject(info);
													JSONArray search_result = one.getJSONArray("search_result");
													
													for( int i = 0 ; i < search_result.length(); i++ ){
														JSONObject c = search_result.getJSONObject(i);
														SearchResult_bean Sean = new SearchResult_bean();
														Sean.setId(i);
														Sean.setBook_id(c.getString("book_id"));
														Sean.setCommodity_icon(c.getString("commodity_icon"));
														Sean.setCommodity_name(c.getString("commodity_name"));
														Sean.setBook_price(c.getString("book_price"));
														list.add(Sean);
													}
												
													
												} catch (Exception e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
													Toast.makeText(context, "系统出错",
														     Toast.LENGTH_SHORT).show();
												}
										
		                                    }  
		                                }, new Response.ErrorListener() {  
		                                    @Override  
		                                    public void onErrorResponse(VolleyError error) {  
		                                        Log.e("TAG", error.getMessage(), error); 
		                                        Toast.makeText(context, "网络连接错误",
													     Toast.LENGTH_SHORT).show();
		                                        
		                                    }  
		                                });  
		        mQueue.add(stringRequest);
		       // mQueue.start();
		        
		        return list;
	}
}
