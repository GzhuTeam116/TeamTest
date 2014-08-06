package com.bookstore.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bookstore.activity.R;
import com.bookstore.control.ContextManager;
import com.bookstore.control.LocationManager;
import com.bookstore.control.LocationManager.LocationListener;
import com.bookstore.data.LocationData.Location;
import com.bookstore.etc.Config;
import com.bookstore.net.AsyncHttpRequest;
import com.bookstore.net.AsyncHttpRequestHandler;


public class NavigationActivity extends Activity {

	private TextView navigation_path, current_position;
	private String book_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_layout);
		init();
	}

	private void init() {
		navigation_path = (TextView) findViewById(R.id.navigation_path);
		current_position = (TextView) findViewById(R.id.current_position);
		book_id = this.getIntent().getStringExtra("book_id");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getNavigationPath();
		LocationManager.getInstance().setLocationListener(
				new LocationListener() {

					@Override
					public void onLocationChange(Location location) {
						// TODO Auto-generated method stub
						current_position.setText(location.Areaname);
					}
				});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ContextManager.getInstance().setActivityContext(this);
	}

	private void getNavigationPath() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("book_id", book_id));
		params.add(new BasicNameValuePair("current_pos", Integer
				.toString(LocationManager.getInstance().getLocation().Area)));
		new AsyncHttpRequest().Post(Config.NAVIGATION_URL, this, params,
				new AsyncHttpRequestHandler() {

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						Log.v("λ����Ϣ", "λ����Ϣ��ȡ�ɹ�");
						navigation_path.setText("======");
					}

					@Override
					public void onFaile(String content) {
						// TODO Auto-generated method stub
						super.onFaile(content);
						Log.v("λ����Ϣ", "λ����Ϣ��ȡ����");
					}

				});

	}
}
