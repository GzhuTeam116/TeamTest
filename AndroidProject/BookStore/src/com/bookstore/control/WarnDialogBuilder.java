package com.bookstore.control;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

public class WarnDialogBuilder extends Builder {
	
	public static final int WARN_DIALOG_TYPE_WIFI = 0;
	public static final int WARN_DIALOG_TYPE_BLUETOOTH = 1;
	
	private NetWorkManager nm;
	private int WarnDialogType;
	
	public WarnDialogBuilder(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public WarnDialogBuilder(Context context,NetWorkManager manager,int type) {
		super(context);
		nm = manager;
		WarnDialogType = type;
	}
	
	public void setNetWorkManager(NetWorkManager manager){
		nm = manager;
	}
	
	public void setWarnDialogType(int type){
		WarnDialogType = type;
	}
	
	public void prepare(){
		setPositiveButton("����", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				switch(WarnDialogType){
				case WARN_DIALOG_TYPE_WIFI:
					if(nm.openWIFIAdapter()){
						//Toast.makeText(getContext(), "WIFI�Ѿ�����", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getContext(), "WIFI����ʧ��", Toast.LENGTH_SHORT).show();
					}
					break;
				case WARN_DIALOG_TYPE_BLUETOOTH:
					if(nm.openBluetoothAdapter()){
						Toast.makeText(getContext(), "�����Ѿ�����", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getContext(), "��������ʧ��", Toast.LENGTH_SHORT).show();
					}
					break;
				}
			}
		});
		
		setNegativeButton("ȡ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(getContext(), "�Ѿ�ȡ������", Toast.LENGTH_SHORT).show();
			}
		});
		
		setTitle("������Ϣ");

		if(WarnDialogType == WARN_DIALOG_TYPE_WIFI){
			setMessage("WIFI�����Ѿ��رգ��Ƿ����ڿ�����");
		}
		if(WarnDialogType == WARN_DIALOG_TYPE_BLUETOOTH){
			setMessage("���������Ѿ��رգ��Ƿ����ڿ�����");
		}
	}
}
