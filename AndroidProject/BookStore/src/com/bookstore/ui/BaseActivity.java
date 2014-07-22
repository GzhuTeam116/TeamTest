package com.bookstore.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem m = menu.add(1, 1, 1, "�˳�");
		// m.setIcon(R.drawable.main_menu_exit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		showDialog();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.KEYCODE_BACK == keyCode) {
			showDialog();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void showDialog() {
		AlertDialog.Builder ad = new Builder(this);
		ad.setTitle("�˳�");
		ad.setMessage("ȷ��Ҫ�˳���");
		ad.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// ExitApplication.getInstance().exit();
				finish();
			}
		});
		ad.setNegativeButton("ȡ��", null);
		ad.show();
	}
}
