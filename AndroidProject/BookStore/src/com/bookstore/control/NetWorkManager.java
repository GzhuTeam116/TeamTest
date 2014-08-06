package com.bookstore.control;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;

import com.bookstore.error.BlueToothException;
import com.bookstore.error.WIFIException;
import com.bookstore.etc.Config;

public class NetWorkManager {

	private static String tag = "NetWorkManager";

	public static final String ACTION_NET_WORK_STATE_CHANGE = Config.ACTION_NET_WORK_STATE_CHANGE;
	public static final String EXTRA_STATE = Config.NET_WORK_EXTRA_STATE;

	private int SYSTEM_INIT = -1;

	public static final int NET_WORK_STATE_ERROR = 0;
	public static final int NET_WORK_STATE_ENABLE = 1;
	public static final int NET_WORK_STATE_DISABLE = 2;
	public static final int NET_WORK_STATE_LOCAL = 3;
	public static final int NET_WORK_STATE_REMOTE = 4;

	public static final int WIFI_STATE_ERROR = 5;
	public static final int WIFI_STATE_ENABLE = 6;
	public static final int WIFI_STATE_DISABLE = 7;

	public static final int BLUETOOTH_STATE_ERROR = 8;
	public static final int BLUETOOTH_STATE_ENABLE = 9;
	public static final int BLUETOOTH_STATE_DISABLE = 10;

	private static NetWorkManager networkmanager = null;
	private static Context context;

	private int net_work_state, wifi_state, bluetooth_state;
	private Boolean isreg; 
	
	private BluetoothAdapter mBluetoothAdapter;
	private WifiManager mWifiManager;

	private NetWorkManager() {
		context = ContextManager.getInstance().getServiceContext();
		net_work_state = SYSTEM_INIT;
		wifi_state = SYSTEM_INIT;
		bluetooth_state = SYSTEM_INIT;
		mBluetoothAdapter = ((BluetoothManager) context
				.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		isreg = false;
		registerReceiver();
	}

	public static NetWorkManager getInstance() {
		if (networkmanager == null) {
			networkmanager = new NetWorkManager();
		}
		return networkmanager;
	}
	
	public void onDestroy() {
		unregisterReceiver();
		networkmanager = null;
	}
	
	public Boolean openBluetoothAdapter() {
		try {
			if(isBluetoothAvailable()){
				return mBluetoothAdapter.enable();
			}
		} catch (BlueToothException e) {
			Log.v(tag, "BlueTooth is not Available!");
		}
		return false;
	}

	public Boolean closeBluetoothAdapter() {
		try {
			if(isBluetoothAvailable())
			return mBluetoothAdapter.disable();
		} catch (BlueToothException e) {
			Log.v(tag, "BlueTooth is not Available!");
		}
		return false;
	}

	public Boolean openWIFIAdapter() {
		try {
			if(isWIFIAvailable()){
				Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				context.startActivity(intent);
				return true;
			}
		} catch (WIFIException e) {
			Log.v(tag, "WIFI is not Available!");
		}
		return false;
	}

	public Boolean closeWIFIAdapter() {
		try {
			if(isWIFIAvailable()){
				return mWifiManager.setWifiEnabled(false);
			}
		} catch (WIFIException e) {
			Log.v(tag, "WIFI is not Available!");
		}
		return false;
	}

	public void setNet_work_state(int state) {
		net_work_state = state;
		Intent intent = new Intent(ACTION_NET_WORK_STATE_CHANGE);
		intent.putExtra(EXTRA_STATE, net_work_state);
		context.sendBroadcast(intent);
	}

	public int getNet_work_state() {
		return net_work_state;
	}

	public int getWifi_state() {
		return wifi_state;
	}

	public int getBluetooth_state() {
		return bluetooth_state;
	}

	public Boolean isBluetoothAvailable() throws BlueToothException {
		
		if(android.os.Build.VERSION.SDK_INT < 18){
			bluetooth_state = BLUETOOTH_STATE_ERROR;
			throw new BlueToothException("Device is not support bluetooth!");
		}
		if (!context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH)) {
			bluetooth_state = BLUETOOTH_STATE_ERROR;
			throw new BlueToothException("Device is not support bluetooth!");
		}
		if (!context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			bluetooth_state = BLUETOOTH_STATE_ERROR;
			throw new BlueToothException("Device is not support bluetooth_le!");
		}
		if (mBluetoothAdapter.isEnabled()) {
			bluetooth_state = BLUETOOTH_STATE_ENABLE;
		}else{
			bluetooth_state = BLUETOOTH_STATE_DISABLE;
		}
		return true;
	}

	public Boolean isWIFIAvailable() throws WIFIException {
		if (!context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_WIFI)) {
			wifi_state = WIFI_STATE_ERROR;
			throw new WIFIException("Device is not support wifi!");
		}
		if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
			wifi_state = WIFI_STATE_ENABLE;
		}else{
			wifi_state = WIFI_STATE_DISABLE;
		}
		return true;
	}

	public Boolean isNetWorkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null || !info.isAvailable()) {
			net_work_state = NET_WORK_STATE_DISABLE;
			return false;
		}
		net_work_state = NET_WORK_STATE_ENABLE;
		return true;
	}

	public Boolean isLocalServiceAvailable() throws BlueToothException,
			WIFIException {
		if (isBluetoothAvailable() && isWIFIAvailable()
				&& net_work_state == NET_WORK_STATE_LOCAL) {
			return true;
		}
		return false;
	}

	public Boolean isRemoteServiceAvailable() {
		return (isNetWorkAvailable() && net_work_state == NET_WORK_STATE_REMOTE);
	}

	public void registerReceiver() {
		IntentFilter local_wifi_filter = new IntentFilter(
				WifiManager.WIFI_STATE_CHANGED_ACTION);
		context.registerReceiver(netWorkStateChangeReceiver, local_wifi_filter);
		IntentFilter remote_network_filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		context.registerReceiver(netWorkStateChangeReceiver,
				remote_network_filter);
		IntentFilter local_bluetooth_filter = new IntentFilter(
				BluetoothAdapter.ACTION_STATE_CHANGED);
		context.registerReceiver(netWorkStateChangeReceiver,
				local_bluetooth_filter);
		isreg = true;
	}

	public void unregisterReceiver() {
		if(isreg){
			context.unregisterReceiver(netWorkStateChangeReceiver);
			isreg = false;
		}
	}

	private BroadcastReceiver netWorkStateChangeReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context c, Intent i) {
			// TODO Auto-generated method stub
			String action = i.getAction();
			Intent intent = new Intent(ACTION_NET_WORK_STATE_CHANGE);
			if (net_work_state == NET_WORK_STATE_LOCAL) {
				if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
					int wstate = i.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
					switch (wstate) {
					case WifiManager.WIFI_STATE_ENABLED:
						wifi_state = WIFI_STATE_ENABLE;
						intent.putExtra(EXTRA_STATE, wifi_state);
						context.sendBroadcast(intent);
						break;
					case WifiManager.WIFI_STATE_DISABLED:
						wifi_state = WIFI_STATE_DISABLE;
						intent.putExtra(EXTRA_STATE, wifi_state);
						context.sendBroadcast(intent);
						break;
					}
				}
				if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
					int bstate = i.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
					switch (bstate) {
					case BluetoothAdapter.STATE_ON:
						bluetooth_state = BLUETOOTH_STATE_ENABLE;
						intent.putExtra(EXTRA_STATE, bluetooth_state);
						context.sendBroadcast(intent);
						break;
					case BluetoothAdapter.STATE_OFF:
						bluetooth_state = BLUETOOTH_STATE_DISABLE;
						intent.putExtra(EXTRA_STATE, bluetooth_state);
						context.sendBroadcast(intent);
						break;
					}
				}
			} else {
				if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
					ConnectivityManager cm = (ConnectivityManager) context
							.getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo info = cm.getActiveNetworkInfo();
					if (info == null) {
						if (net_work_state != NET_WORK_STATE_DISABLE) {
							net_work_state = NET_WORK_STATE_DISABLE;
							intent.putExtra(EXTRA_STATE, net_work_state);
							context.sendBroadcast(intent);
						}
					} else {
						if (net_work_state == SYSTEM_INIT
								|| net_work_state == NET_WORK_STATE_DISABLE) {
							net_work_state = NET_WORK_STATE_ENABLE;
							intent.putExtra(EXTRA_STATE, net_work_state);
							context.sendBroadcast(intent);
						}
					}

				}
			}
		}
	};

}
