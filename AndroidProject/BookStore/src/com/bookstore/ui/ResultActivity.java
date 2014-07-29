package com.bookstore.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bookstore.activity.R;

public class ResultActivity extends Activity {

	private TextView tv_result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_item);
		tv_result = (TextView) findViewById(R.id.tv_result);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String resstr =bundle.getString("result");
		Toast.makeText(getApplicationContext(), resstr, 0).show();
		tv_result.setText(resstr);
	}

}
