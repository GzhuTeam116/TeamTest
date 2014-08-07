package com.bookstore.control;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;

import com.bookstore.data.IbeaconData;
import com.bookstore.data.IbeaconData.Ibeacon;

public class IbeaconManager {

	public static final int SCAN_PERIOD = 5000;

	private static IbeaconManager mIbeaconManager = null;
	private static IbeaconInfListener mIbeaconInfListener;
	private Context mContext;
	private BookSotoreLeScanCallback mBookSotoreLeScanCallback;
	private BluetoothAdapter mBluetoothAdapter;
	private Handler mHandler;
	private Boolean isScanLoop;

	private Boolean isScan;

	private IbeaconManager() {
		mIbeaconInfListener = null;
		mContext = ContextManager.getInstance().getServiceContext();
		mBookSotoreLeScanCallback = new BookSotoreLeScanCallback();
		mBluetoothAdapter = ((BluetoothManager) mContext
				.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
		mHandler = new Handler();
	}

	public static IbeaconManager getInstance() {
		if (mIbeaconManager == null) {
			mIbeaconManager = new IbeaconManager();
		}
		return mIbeaconManager;
	}

	public void startIbeaconInfScan() {
		if(!isScan){
			isScanLoop = true;
			mHandler.postDelayed(r, SCAN_PERIOD);
			mBluetoothAdapter.startLeScan(mBookSotoreLeScanCallback);
			isScan = true;
		}else{
			isScanLoop = true;
		}
	}

	public void stopIbeaconInfScan() {
		isScanLoop = false;
	}

	private Runnable r = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			mBluetoothAdapter.stopLeScan(mBookSotoreLeScanCallback);
			if (NetWorkManager.getInstance().getBluetooth_state() == NetWorkManager.BLUETOOTH_STATE_DISABLE) {
				isScanLoop = false;
			}
			if (isScanLoop) {
				mHandler.postDelayed(r, SCAN_PERIOD);
				mBluetoothAdapter.startLeScan(mBookSotoreLeScanCallback);
			} else {
				isScan = false;
			}
		}
	};

	public Boolean isBluetoothScan() {
		return isScan;
	}

	public void setIbeaconInfListener(IbeaconInfListener listener) {
		mIbeaconInfListener = listener;
	}

	public interface IbeaconInfListener {
		public void OnIbeaconInfChange(Ibeacon ibeacon);
	}

	private class BookSotoreLeScanCallback implements LeScanCallback {

		@Override
		public void onLeScan(BluetoothDevice device, int rssi, byte[] scanData) {
			// TODO Auto-generated method stub
			Ibeacon ibeacon = IbeaconData
					.getIbeaconData(device, rssi, scanData);
			if (ibeacon.isAvailable) {
				mIbeaconInfListener.OnIbeaconInfChange(ibeacon);
			}
		}

	}

}
