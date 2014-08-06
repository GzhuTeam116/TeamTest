package com.bookstore.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookstore.activity.R;

public class SearchActivity extends BaseActivity {

	private ViewPager viewPager = null;
	private List<View> views;
	private TextView tv1, tv2;
	private View view_srchistory, view_srchot;
	private ImageView imageView;
	private int offset = 0;
	private int currIndex = 0;
	private int cursor_imgwidth;
	private ImageButton search_btn = null;
	private ImageButton scan_btn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		InitImageButton();
		InitImageView();//��ʼ����ǩ������ͼƬ
		InitTextView(); //��ʼ����ǩtitle
		InitViewPager();
		
	}

	private void InitImageButton(){
		search_btn =(ImageButton) findViewById(R.id.search_button);
		scan_btn = (ImageButton) findViewById(R.id.scan_button);
		AutoCompleteTextView textview =(AutoCompleteTextView)findViewById(R.id.search_content);
		final String one = textview.getText().toString();
		search_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*
				 * �����������Ĵ���
				 */
				//һ���ǲ��Դ��룬��Ϊ����
				Intent intent = new Intent();
				intent.putExtra("search", one);
				intent.setClass(SearchActivity.this, ListItemActivity.class);
				startActivity(intent);
			}
		});
		scan_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*
				 * �����ת��������ɨ�����Ĵ���
				 */
				Intent intent = new Intent();
				intent.setClass(SearchActivity.this, MipcaActivityCapture.class);
				startActivity(intent);
			}
		});
	}

	private void InitImageView() {
		imageView = (ImageView) findViewById(R.id.cursor);
		// ��ȡ��ǩͼƬ
		cursor_imgwidth = BitmapFactory.decodeResource(getResources(),
				R.drawable.cursor).getWidth();
		// ��ȡ��Ļ���
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / 2 - cursor_imgwidth) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);
	}

	private void InitTextView() {
		tv1 = (TextView) findViewById(R.id.text1);
		tv2 = (TextView) findViewById(R.id.text2);
		tv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(0);
			}
		});
		tv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(1);
			}
		});
	}

	private void InitViewPager() {
		viewPager = (ViewPager) findViewById(R.id.vp);
		views = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		view_srchistory = inflater.inflate(R.layout.layout_search_history, null);
		view_srchot = inflater.inflate(R.layout.layout_search_hot, null);
		views.add(view_srchistory);
		views.add(view_srchot);
		viewPager.setAdapter(new MyViewPagerAdapter());
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	public class MyViewPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position));
			return views.get(position);
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			/*
			 * �˴��������ķ���
			 */
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			/*
			 * ���ñ�ǩͼƬ���ƶ�����
			 */
			int one = offset * 2 + cursor_imgwidth;
			System.out.println("agro:" + arg0);
			Animation animation = new TranslateAnimation(one * currIndex, one
					* arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);
			imageView.startAnimation(animation);
		}

	}

}
