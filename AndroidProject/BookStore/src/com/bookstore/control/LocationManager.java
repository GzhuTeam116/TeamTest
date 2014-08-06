package com.bookstore.control;

import org.json.JSONException;
import org.json.JSONObject;

import com.bookstore.data.LocationData;
import com.bookstore.data.LocationData.Location;

public class LocationManager {

	private static LocationManager lm = null;
	protected Location mLocation;
	protected LocationListener mLocationListener;
	
	private LocationManager(){
		mLocation = null;
		mLocationListener = null;
	}
	
	public static LocationManager getInstance(){
		if(lm == null){
			lm = new LocationManager();
		}
		return lm;
	}

	public Location getLocation() {
		return mLocation;
	}

	public void setLocation(Location location) {
		mLocation = location;
		mLocationListener.onLocationChange(mLocation);
	}
	
	public void setLocation(JSONObject json) throws JSONException{
		mLocation = LocationData.getAreaInfFromJson(json);
		mLocationListener.onLocationChange(mLocation);
	}
	
	public void setLocationListener(LocationListener locationlistener){
		mLocationListener = locationlistener;
	}
	
	public interface LocationListener {
		public void onLocationChange(Location location);
	}
}
