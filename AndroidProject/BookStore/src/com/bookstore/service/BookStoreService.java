package com.bookstore.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.bookstore.control.ContextManager;
import com.bookstore.control.IbeaconManager;
import com.bookstore.control.IbeaconManager.IbeaconInfListener;
import com.bookstore.control.LocationManager;
import com.bookstore.control.LocationManager.LocationListener;
import com.bookstore.control.NetWorkManager;
import com.bookstore.data.IbeaconData.Ibeacon;
import com.bookstore.data.LocationData;
import com.bookstore.data.LocationData.Location;
import com.bookstore.error.BlueToothException;
import com.bookstore.error.WIFIException;
import com.bookstore.etc.Config;
import com.bookstore.net.AsyncHttpRequest;
import com.bookstore.net.AsyncHttpRequestHandler;

public class BookStoreService extends Service {

	private String tag = "BookStoreService";

	protected LocationManager mLocationManager;
	protected IbeaconManager mIbeaconManager;
	protected NetWorkManager mNetWorkManager;
	protected ContextManager mContextManager;
	protected BookStoreReceiver bookstoreBroadcastReceiver;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
		Log.v(tag, "service onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(tag, "service Start");
		startTask();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(tag, "service destroy");
		bookstoreUnregisterReceiver();
		mNetWorkManager.onDestroy();
	}

	private void init() {
		mContextManager = ContextManager.getInstance();
		mContextManager.setServiceContext(BookStoreService.this);
		mNetWorkManager = NetWorkManager.getInstance();
		// mNetWorkManager.setNet_work_state(NetWorkManager.NET_WORK_STATE_LOCAL);
		mIbeaconManager = IbeaconManager.getInstance();
		mIbeaconManager.setIbeaconInfListener(mIbeaconInfListener);
		mLocationManager = LocationManager.getInstance();
		bookstoreRegisterReceiver();
	}

	public void startTask() {
		Log.v(tag, "startTask");
		try {
			if(mNetWorkManager.isLocalServiceAvailable()){
				if(mNetWorkManager.getBluetooth_state() == NetWorkManager.BLUETOOTH_STATE_ENABLE){
					mIbeaconManager.startIbeaconInfScan();
				}else{
					if(mIbeaconManager.isBluetoothScan()){
						mIbeaconManager.stopIbeaconInfScan();
					}
					Toast.makeText(BookStoreService.this, "请打开蓝牙", Toast.LENGTH_SHORT).show();
				}
			}
		} catch (BlueToothException | WIFIException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Toast.makeText(BookStoreService.this, "设备不支持蓝牙或WiFi", Toast.LENGTH_SHORT).show();
		}
	}

	public void bookstoreRegisterReceiver() {
		bookstoreBroadcastReceiver = new BookStoreReceiver();
		IntentFilter net_filter = new IntentFilter(
				NetWorkManager.ACTION_NET_WORK_STATE_CHANGE);
		registerReceiver(bookstoreBroadcastReceiver, net_filter);
	}

	public void bookstoreUnregisterReceiver() {
		unregisterReceiver(bookstoreBroadcastReceiver);
	}

	private IbeaconInfListener mIbeaconInfListener = new IbeaconInfListener() {

		@Override
		public void OnIbeaconInfChange(Ibeacon ibeacon) {
			// TODO Auto-generated method stub
			if (ibeacon.isAvailable) {
				Log.v(tag, "�������󣬸���Ibeacon��Ӧ��Location��Ϣ");
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("uuid", ibeacon.uuid));
				params.add(new BasicNameValuePair("major", Integer
						.toString(ibeacon.major)));
				params.add(new BasicNameValuePair("minor", Integer
						.toString(ibeacon.minor)));
				new AsyncHttpRequest().Post(Config.LOCATION_URL,
						BookStoreService.this, params,
						new AsyncHttpRequestHandler() {

							@Override
							public void onSuccess(String content) {
								// TODO Auto-generated method stub
								super.onSuccess(content);
								try {
									Location ld = LocationData.getAreaInfFromJson(new JSONObject(
											content).getJSONObject("area_info"));
									mLocationManager.setLocation(ld);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									Toast.makeText(BookStoreService.this, "��Ϣ��ȡʧ��", Toast.LENGTH_SHORT).show();
									//e.printStackTrace();
								}
							}

							@Override
							public void onFaile(String content) {
								// TODO Auto-generated method stub
								super.onFaile(content);
							}

						});
			}
		}
	};
}
