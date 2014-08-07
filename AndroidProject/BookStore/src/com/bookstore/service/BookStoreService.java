package com.bookstore.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.bookstore.control.ContextManager;
import com.bookstore.control.IbeaconManager;
import com.bookstore.control.IbeaconManager.IbeaconInfListener;
import com.bookstore.control.LocationManager;
import com.bookstore.control.NetWorkManager;
import com.bookstore.control.WarnDialogBuilder;
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

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.v(tag, "service onCreate");
		init();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.v(tag, "service onStart");
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
		checkDevice();
		if(mNetWorkManager.getBluetooth_state()!=NetWorkManager.BLUETOOTH_STATE_ERROR){
			mIbeaconManager = IbeaconManager.getInstance();
			mIbeaconManager.setIbeaconInfListener(mIbeaconInfListener);
			mLocationManager = LocationManager.getInstance();
		}
		bookstoreRegisterReceiver();
		
	}

	private void checkDevice() {
		try {
			if (mNetWorkManager.isBluetoothAvailable()) {
				Log.v(tag, "Device support BLE");
			}
			if (mNetWorkManager.isWIFIAvailable()) {
				Log.v(tag, "Device support WIFI");
			}
			if (!mNetWorkManager.isNetWorkAvailable()) {
				Log.v(tag, "Network is not available!");
				Toast.makeText(BookStoreService.this, "当前网络状态不可用，请检测设备",
						Toast.LENGTH_SHORT).show();
			}
		} catch (BlueToothException | WIFIException e) {
			Log.v(tag, "Device is not support BLE or WIFI");
		}
	}

	public void bookstoreRegisterReceiver() {
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
				Log.v(tag, "Ibeacon Information is Available");
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
								super.onSuccess(content);
								try {
									Location ld = LocationData.getAreaInfFromJson(new JSONObject(
											content).getJSONObject("area_info"));
									mLocationManager.setLocation(ld);
								} catch (JSONException e) {
									Log.v(tag,
											"Location infmation is not Available");
								}
							}

							@Override
							public void onFaile(String content) {
								super.onFaile(content);
								Log.v(tag,
										"Cant not get location infmation from server");
							}

						});
			}
		}
	};

	private BroadcastReceiver bookstoreBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (action.equals(NetWorkManager.ACTION_NET_WORK_STATE_CHANGE)) {
				int net_state = mNetWorkManager.getNet_work_state();
				int state = intent.getIntExtra(NetWorkManager.EXTRA_STATE, -1);
				WarnDialogBuilder b = new WarnDialogBuilder(
						mContextManager.getActivityContext());
				b.setNetWorkManager(mNetWorkManager);
				if (net_state == NetWorkManager.NET_WORK_STATE_LOCAL) {
					switch (state) {
					case NetWorkManager.NET_WORK_STATE_LOCAL:
						if (mNetWorkManager.getBluetooth_state() == NetWorkManager.BLUETOOTH_STATE_ENABLE) {
							Log.v(tag,
									"net_work_state change to NET_WORK_STATE_LOCAL, start bluetooth moudle");
							mIbeaconManager.startIbeaconInfScan();
						} else {
							Toast.makeText(BookStoreService.this,
									"检测到您正在使用店内WIFI网络，可以打开蓝牙获取更好的服务",
									Toast.LENGTH_SHORT).show();
						}
						break;
					case NetWorkManager.WIFI_STATE_ENABLE:
						break;
					case NetWorkManager.WIFI_STATE_DISABLE:
						b.setWarnDialogType(WarnDialogBuilder.WARN_DIALOG_TYPE_BLUETOOTH);
						b.prepare();
						b.create().show();
						Log.v(tag, "BluetoothAdapter is disable");
						break;
					case NetWorkManager.BLUETOOTH_STATE_ENABLE:
						mIbeaconManager.startIbeaconInfScan();
						break;
					case NetWorkManager.BLUETOOTH_STATE_DISABLE:
						mIbeaconManager.stopIbeaconInfScan();
						b.setWarnDialogType(WarnDialogBuilder.WARN_DIALOG_TYPE_BLUETOOTH);
						b.prepare();
						b.create().show();
						Log.v(tag, "BluetoothAdapter is disable");
						break;
					}
				} else {
					Log.v(tag,
							"net_work_state change to NET_WORK_STATE_REMOTE, stop bluetooth moudle");
					if(mNetWorkManager.getBluetooth_state() != NetWorkManager.BLUETOOTH_STATE_ERROR){
						mIbeaconManager.stopIbeaconInfScan();
					}
					if (state == NetWorkManager.NET_WORK_STATE_DISABLE) {
						Toast.makeText(BookStoreService.this,
								"检测到网络已关闭，请检查设备状态", Toast.LENGTH_SHORT).show();
						Log.v(tag, "NetWork is not available");
					} else {
						Log.v(tag, "NetWork is available");
					}
				}
			}
		}

	};
}
