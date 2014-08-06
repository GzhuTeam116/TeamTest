package com.bookstore.data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bookstore.ui.HomeActivity;

public class down_json {
	private Context context;
	private RequestQueue mQueue;
	public down_json(RequestQueue one,Context context){//���������ʱ����Ҫ ��RequestQueue���
		super();
		this.context=context;
		this.mQueue=one;
	}
	
	public List<Home_gridview_bean> get_HotSrchBook_HotSaleBook(String url){//可以获取 热搜信息 和热销 推荐 信息 一开始的俩个gridview 
		
		//API 2 和 3 
		final List<Home_gridview_bean> list = new ArrayList<Home_gridview_bean>();
		// list 保存数组信息
		/*
		 *    “code”:0，
   “msg”:”获取热销推荐信息成功”，

    "data": [
        {
            "book_id": "书本id 1",
            "book_img": "图书略缩图1",
            "sales_volume": "销量1"
        },
        {
            "book_id": "书本id 1",
            "book_img": "图书略缩图2",
            "sales_volume": "销量2"
        }
		适用于  hobby_gridviewitem 
		 * 
		*/
		//String url = Url_name.url_get_GetDetailBook;
	
		       StringRequest stringRequest = new StringRequest(url,  
		                                new Response.Listener<String>() {  
		                                    @Override  
		                                    public void onResponse(String response) {  
		                                    	
		                                    	try {
													String info = new String (response.toString().getBytes("ISO-8859-1"),"utf-8");
													// 数据转换成 UTF-8
													JSONObject json_info =new JSONObject(info);
													String code = json_info.getString("code");//判断信息获取成功或者失败
													//code1 = code.toString();
													if(code.equals("1")){
														JSONArray data = json_info.getJSONArray("data");
															for(int i = 0 ; i < data.length(); i++){
																JSONObject pic = data.getJSONObject(i);
																Home_gridview_bean h_g_b =new Home_gridview_bean();
																h_g_b.setI(i);
																h_g_b.setBook_id(pic.getString("book_id"));
																h_g_b.setBook_img(pic.getString("book_img"));
																h_g_b.setSales_volume(pic.getString("sales_volume"));
																list.add(h_g_b);
															}
														
													}else {
														Toast.makeText(context, "信息获取失败",
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
	
public List<HobbyBook_bean> get_GetHobbyBook(){//可以获取 热搜信息 和热销 推荐 信息 一开始的俩个gridview 
		
		//API 2 和 3 
		final List<HobbyBook_bean> list = new ArrayList<HobbyBook_bean>();
		// list 保存数组信息
		/*
		 *  {
   		“code”：0，
    		“msg”：“获取喜好信息”，
    		"hobby_result": [
      	 {
            "book_id": "书本id 1",
            "book_img": "图书略缩图1",
            "sales_volume": "销量1"
        	},
        	{
            "book_id": "书本id 2",
            "book_img": "图书略缩图2",
            "sales_volume": "销量2"
        	}
        …………
    	]	
		}

		 * 
		*/
		String url = Url_name.url_get_GetHobbyBook;
		
		//http://192.168.1.110:8080/whatsapp/test.html
		 //String url = "http://192.168.1.101:8080/services/REST/Student/information/get?stuNo=1106100022";
		       StringRequest stringRequest = new StringRequest(url,  
		                                new Response.Listener<String>() {  
		                                    @Override  
		                                    public void onResponse(String response) {  
		                                    	
		                                    	try {
													String info = new String (response.toString().getBytes("ISO-8859-1"),"utf-8");
													// 数据转换成 UTF-8
													JSONObject json_info =new JSONObject(info);
													String code = json_info.getString("code");//判断信息获取成功或者失败
													//code1 = code.toString();
													if(code.equals("1")){
														JSONArray data = json_info.getJSONArray("hobby_result");
															for(int i = 0 ; i < data.length(); i++){
																JSONObject pic = data.getJSONObject(i);
																HobbyBook_bean hbb = new HobbyBook_bean();
																hbb.setId(i);
																hbb.setBook_id(pic.getString("book_id"));
																hbb.setBook_img(pic.getString("book_img"));
																hbb.setSales_volume(pic.getString("sales_volume"));
																list.add(hbb);
															}
														
													}else {
														Toast.makeText(context, "信息获取失败",
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


public List<PromotionsImg_bean> get_PromotionsImg(){//可以获取 热搜信息 和热销 推荐 信息 一开始的俩个gridview 
	//ImageSwitcher
	//
	final List<PromotionsImg_bean> list = new ArrayList<PromotionsImg_bean>();
	// list 保存数组信息
	/*
	 *  "promotions_info_result": [
        {
            "promotions_id": "促销信息id 1",
            "promotions_img1": "促销信息图片1",
            "promotions_tag": "促销分类1"
        },
        {
            "promotions_id": "促销信息id 2",
            "promotions_img1": "促销信息图片2",
            "promotions_tag": "促销分类2"
        }


	 * 
	*/
	String url = Url_name.url_get_GetPromotionsImg;
	
	//http://192.168.1.110:8080/whatsapp/test.html
	 //String url = "http://192.168.1.101:8080/services/REST/Student/information/get?stuNo=1106100022";
	       StringRequest stringRequest = new StringRequest(url,  
	                                new Response.Listener<String>() {  
	                                    @Override  
	                                    public void onResponse(String response) {  
	                                    	
	                                    	try {
												String info = new String (response.toString().getBytes("ISO-8859-1"),"utf-8");
												// 数据转换成 UTF-8
												JSONObject json_info =new JSONObject(info);
											
												//code1 = code.toString();
											
													JSONArray data = json_info.getJSONArray("promotions_info_result");
														for(int i = 0 ; i < data.length(); i++){
															JSONObject pic = data.getJSONObject(i);
															PromotionsImg_bean Pro = new PromotionsImg_bean();
															Pro.setId(i);
															Pro.setPromotions_id(pic.getString("promotions_id"));
															Pro.setPromotions_img1(pic.getString("promotions_img1"));
															Pro.setPromotions_tag(pic.getString("promotions_tag"));
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

// public List get 分类信息

}
