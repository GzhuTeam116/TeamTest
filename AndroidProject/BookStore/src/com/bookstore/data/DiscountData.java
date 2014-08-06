package com.bookstore.data;

import org.json.JSONException;
import org.json.JSONObject;

public class DiscountData {
	
	public static class Discount{
		public int DiscountID;
		public String DiscountImgUrl;
		public String PromotionIntro;
	}
	
	public static Discount getDiscountDataFromJson(JSONObject json) throws JSONException{
		
		Discount discount = new Discount();
		discount.DiscountID = json.getInt("discount_id");
		discount.DiscountImgUrl = json.getString("discount_img_url");
		discount.PromotionIntro = json.getString("discount_promotion");
		return discount;
	}
	
}
