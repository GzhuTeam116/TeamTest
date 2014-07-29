package com.bookstore.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bookstore.activity.R;

public class ListItemActivity extends Activity implements OnScrollListener {

	private listViewAdapter adapter = new listViewAdapter();
	private ListView listView = null;
	private View moreview = null;
//	private Thread mThread = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listitem);
		init();
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		moreview = inflater.inflate(R.layout.listitem_load_more, null);
		listView = (ListView) findViewById(R.id.lv_listitem);
		listView.addFooterView(moreview);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(this);
	}

	class listViewAdapter extends BaseAdapter {
		int count = 10;

		public int getCount() {
			return count;
		}

		public Object getItem(int pos) {
			return pos;
		}

		public long getItemId(int pos) {
			return pos;
		}

		public View getView(int pos, View v, ViewGroup p) {
			TextView view;
			if (v == null) {
				view = new TextView(ListItemActivity.this);
			} else {
				view = (TextView) v;
			}
			view.setText("ListItem " + pos);
			view.setTextSize(20f);
			view.setGravity(Gravity.CENTER);
			view.setHeight(60);
			return view;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem + visibleItemCount == totalItemCount) {
			if (adapter.count <= 41) {
				adapter.count += 10;
				int currentPage = adapter.count / 10;
				Toast.makeText(getApplicationContext(),
						"第" + currentPage + "页", Toast.LENGTH_LONG).show();
			} else {
				listView.removeFooterView(moreview);
			}
			adapter.notifyDataSetChanged();
//			// 开线程去下载网络数据
//			if (mThread == null || !mThread.isAlive()) {
//				mThread = new Thread() {
//					@Override
//					public void run() {
//						try {
//							// 这里放你网络数据请求的方法，我在这里用线程休眠5秒方法来处理
//							Thread.sleep(5000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						Message message = new Message();
//						message.what = 1;
//						handler.sendMessage(message);
//					}
//				};
//				mThread.start();
//			}
		}
	}

//	private Handler handler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			switch (msg.what) {
//			case 1:
//				if (adapter.count <= 41) {
//					adapter.count += 10;
//					int currentPage = adapter.count / 10;
//					Toast.makeText(getApplicationContext(),
//							"第" + currentPage + "页", Toast.LENGTH_LONG).show();
//				} else {
//					listView.removeFooterView(moreview);
//				}
//				// 重新刷新Listview的adapter里面数据
//				adapter.notifyDataSetChanged();
//				break;
//			default:
//				break;
//			}
//		}
//
//	};
}
