package com.bookstore.extra;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alipay.android.app.sdk.AliPay;

public class Alipayentry {
	
	private static Context context ;
	public Alipayentry(Context context){
		this.context = context;
	}

	private static final int RQF_PAY = 1;
	private static final int RQF_LOGIN = 2;
//	public static Product product  ;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj);

			switch (msg.what) {
			case RQF_PAY:
			case RQF_LOGIN: {
				Toast.makeText(context, result.getResult(),
						Toast.LENGTH_SHORT).show();
			}
				break;
			default:
				break;
			}
		};
	};
	public  void Pay(Product product){
		try {
			String info = getOrderInfo(product);
			String sign = Rsa.sign(info, Keys.PRIVATE);
			sign = URLEncoder.encode(sign);
			info += "&sign=\"" + sign + "\"&" + getSignType();
			final String orderInfo = info;
			new Thread() {
				public void run() {
					AliPay alipay = new AliPay((Activity) context, mHandler);
					
					//设置为沙箱模式，不设置默认为线上环境
					alipay.setSandBox(true);

					String result = alipay.pay(orderInfo);

					Log.i("test", "result = " + result);
					Message msg = new Message();
					msg.what = RQF_PAY;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			}.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "调用远程服务失败",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	private static String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	private static String getOrderInfo(Product product){
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(product.getOut_trade_no());
		sb.append("\"&subject=\"");
		sb.append(product.getSubject());
		sb.append("\"&body=\"");
		sb.append(product.getBody());
		sb.append("\"&total_fee=\"");
		sb.append(product.getPrice().replace("一口价:", ""));
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
//		sb.append(URLEncoder.encode("http://notify.java.jpxx.org/index.jsp"));
		sb.append(URLEncoder.encode(product.notify_url));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(Keys.DEFAULT_SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"1m");
		sb.append("\"");

		return new String(sb);
	}
}
