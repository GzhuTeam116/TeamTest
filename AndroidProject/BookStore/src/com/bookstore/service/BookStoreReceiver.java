package com.bookstore.service;

import com.bookstore.control.ContextManager;
import com.bookstore.control.NetWorkManager;
import com.bookstore.control.WarnDialogBuilder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BookStoreReceiver extends BroadcastReceiver {

	private String tag = "BookStoreReceiver";
	
	private NetWorkManager nm;
	private ContextManager cm;
	
	public BookStoreReceiver(){
		Log.v(tag,"BookStoreReceiver�Ѵ���");
		nm = NetWorkManager.getInstance();
		cm = ContextManager.getInstance();
	}
	
	@Override
	public void onReceive(Context c, Intent i) {
		// TODO Auto-generated method stub
		String action = i.getAction();
		if (action.equals(NetWorkManager.ACTION_NET_WORK_STATE_CHANGE)) {
			int net_state = nm.getNet_work_state();
			Log.v(tag, net_state + "");
			int state = i.getIntExtra(NetWorkManager.EXTRA_STATE, 0);
			WarnDialogBuilder b = new WarnDialogBuilder(cm.getActivityContext());
			b.setNetWorkManager(nm);
			if (net_state == NetWorkManager.NET_WORK_STATE_LOCAL) {
				switch (state) {
				case NetWorkManager.BLUETOOTH_STATE_ENABLE:
					Log.v(tag, "���� �ѿ���");
					break;
				case NetWorkManager.BLUETOOTH_STATE_DISABLE:
					b.setWarnDialogType(WarnDialogBuilder.WARN_DIALOG_TYPE_BLUETOOTH);
					b.prepare();
					b.create().show();
					Log.v(tag, "�����ѹر�");
					break;
				case NetWorkManager.WIFI_STATE_ENABLE:
					Log.v(tag, "WIFI�ѿ���");
					break;
				case NetWorkManager.WIFI_STATE_DISABLE:
					b.setWarnDialogType(WarnDialogBuilder.WARN_DIALOG_TYPE_WIFI);
					b.prepare();
					b.create().show();
					Log.v(tag, "WIFI�ѹر�");
					break;
				}
			} else {
				if (state == NetWorkManager.NET_WORK_STATE_DISABLE) {
					Log.v(tag, "�����ѹر�");
				} else {
					Log.v(tag, "�����ѿ���");
				}
			}
		}
	}

}
