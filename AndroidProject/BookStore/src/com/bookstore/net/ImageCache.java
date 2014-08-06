package com.bookstore.net;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageCache {

	private static ImageCache imageCache;
	private static final int SOFT_CACHE_SIZE = 15;
	private static LruCache<String, Bitmap> mLruCache;
	private static LinkedHashMap<String, SoftReference<Bitmap>> mSoftCache;
	
	public static ImageCache getImageCacheInstance(){
		if (imageCache == null) {
			imageCache = new ImageCache();
		}
		return imageCache;
	}
	
	private ImageCache(){
		int cacheSize = 8*1024*1024;
		mLruCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				if (bitmap != null) {
					return bitmap.getRowBytes() * bitmap.getHeight();
				}
				return 0;
			}

			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				if (oldValue != null)
					mSoftCache.put(key, new SoftReference<Bitmap>(oldValue));
			}
		};

		mSoftCache = new LinkedHashMap<String, SoftReference<Bitmap>>(
				SOFT_CACHE_SIZE, 0.75f, true) {
			private static final long serialVersionUID = 6040103833179403725L;

			@Override
			protected boolean removeEldestEntry(
					Entry<String, SoftReference<Bitmap>> eldest) {
				if (size() > SOFT_CACHE_SIZE) {
					return true;
				}
				return false;
			}
		};
	}
	
	/**
	 * 
	 * �ӻ����л�ȡͼƬ
	 */
	public Bitmap getBitmapFromCache(String url) {
		Bitmap bitmap;
		synchronized (mLruCache) {
			bitmap = mLruCache.get(url);
			if (bitmap != null) {
				mLruCache.remove(url);
				mLruCache.put(url, bitmap);
				return bitmap;
			}
		}
		synchronized (mSoftCache) {
			SoftReference<Bitmap> bitmapReference = mSoftCache.get(url);
			if (bitmapReference != null) {
				bitmap = bitmapReference.get();
				if (bitmap != null) {
					mLruCache.put(url, bitmap);
					mSoftCache.remove(url);
					return bitmap;
				} else {
					mSoftCache.remove(url);
				}
			}
		}
		return null;
	}

	public void addBitmapToCache(String url, Bitmap bitmap) {
		if (bitmap != null) {
			synchronized (mLruCache) {
				mLruCache.put(url, bitmap);
			}
		}
	}

	public void clearCache() {
		mSoftCache.clear();
	}

	
	
}
