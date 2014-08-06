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

public class down_DetailBook {
	private Context context;
	private RequestQueue mQueue;
	public down_DetailBook(RequestQueue one ,Context con){
		super();
		this.context=con;
		this.mQueue=one;
	}
	
	public List<DetailBook_bean> get_HotSrchBook_HotSaleBook(String url){//可以获取 热搜信息 和热销 推荐 信息 一开始的俩个gridview 
		
		//API 2 和 3 
		final List<DetailBook_bean> list = new ArrayList<DetailBook_bean>();
		// list 保存数组信息
		/*{
  “code”:0
   “msg”:”获取商品详情成功”
    "date": {
        "book_id": "商品id",
        "book_img": "商品图片",
        "book_price": "商品价格",
        "book_name": "商品名称",
        "book_author": "作者",
        "book_press": "商品出版社",
        "introduction": "商品简介",
        "location": {
            "area_num": "区域号",
            "bookshelf_num": "书架"
        }
    }
}

		 * 
		*/
		
		       StringRequest stringRequest = new StringRequest(url,  
		                                new Response.Listener<String>() {  
		                                    @Override  
		                                    public void onResponse(String response) {  
		                                    	
		                                    	try {
													String info = new String (response.toString().getBytes("ISO-8859-1"),"utf-8");
													// 数据转换成 UTF-8
													JSONObject one = new JSONObject(info);
													String code = one.getString("code");
													if(code.equals("1")){
														JSONObject two =one.getJSONObject("date");
														DetailBook_bean book= new DetailBook_bean();
														book.setBook_id(two.getString("book_id"));
														book.setBook_img(two.getString("book_img"));
														book.setBook_price(two.getString("book_price"));
														book.setBook_name(two.getString("book_name"));
														book.setBook_author(two.getString("book_author"));
														book.setBook_press(two.getString("book_press"));
														book.setIntroduction(two.getString("introduction"));
														JSONObject three = two.getJSONObject("location");
														book.setLocation_area_num(three.getString("area_num"));
														book.setLocation_bookshelf_num(three.getString("bookshelf_num"));
														
														list.add(book);
													}else {
														Toast.makeText(context, "系统出错",
															     Toast.LENGTH_SHORT).show();
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
