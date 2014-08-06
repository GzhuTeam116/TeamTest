package com.bookstore.data;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationData {
	
	public static class Location{
		public int Area;
		public String Areaname;
		public int Floor;
		public int East;
		public int South;
		public int West;
		public int North;
		public int Upstairs;
		public int Downstairs;
		public Boolean hasDiscountInf;
	}
	
	public static Location getAreaInfFromJson(JSONObject json) throws JSONException{
		
		Location l = new Location();
		l.Area = json.getInt("area_id");
		l.Areaname = json.getString("area_name");
		l.Floor = json.getInt("area_floor");
		l.East = json.getInt("area_east");
		l.West = json.getInt("area_west");
		l.South = json.getInt("area_south");
		l.North = json.getInt("area_north");
		l.Upstairs = json.getInt("area_upstairs");
		l.Downstairs = json.getInt("area_downstairs");
		int isAreaDiscount = json.getInt("area_discount");
		if(isAreaDiscount == 1){
			l.hasDiscountInf = true;
		}else{
			l.hasDiscountInf = false;
		}
		return l;
	}
	
}
