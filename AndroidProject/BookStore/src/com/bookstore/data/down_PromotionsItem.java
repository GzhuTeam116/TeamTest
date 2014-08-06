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

public class down_PromotionsItem {
	private Context context;
	private RequestQueue mQueue;
	public down_PromotionsItem(RequestQueue one ,Context con){
		super();
		this.context=con;
		this.mQueue=one;
	}
	
	public List<PromotionsItem_bean> get_HotSrchBook_HotSaleBook(String url){//可以获取 热搜信息 和热销 推荐 信息 一开始的俩个gridview 
		
		//API 2 和 3 
		final List<PromotionsItem_bean> list = new ArrayList<PromotionsItem_bean>();
		// list 保存数组信息
		/*
   "promotions_item_result": [
        {
            "book_id": "商品id",
            "book_img": "商品图片1",
            "book_name": "商品名称1",
            "book_promotions_price": "商品促销价格1"
        },
        {
            "book_id": "商品id",
            "book_img": "商品图片2",
            "book_name": "商品名称2",
            "book_promotions_price": "商品促销价格2"
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
													
														JSONArray data = one.getJSONArray("promotions_item_result");
															for(int i = 0 ; i < data.length(); i++){
																JSONObject pic = data.getJSONObject(i);
																PromotionsItem_bean Pro =new PromotionsItem_bean();
																Pro.setId(i);
																Pro.setBook_id(pic.getString("book_id"));
																Pro.setBook_img(pic.getString("book_img"));
																Pro.setBook_name(pic.getString("book_name"));
																Pro.setBook_promotions_price(pic.getString("book_promotions_price"));
																list.add(Pro);
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
