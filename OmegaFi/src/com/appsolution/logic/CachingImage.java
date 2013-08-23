package com.appsolution.logic;

import android.graphics.Bitmap;
import android.util.LruCache;

public class CachingImage {

	private 	LruCache<String, Bitmap> memoryCache;
	private static CachingImage cachingImage;
	
	private CachingImage(){
		 final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		    // Use 1/8th of the available memory for this memory cache.
		    final int cacheSize = maxMemory / 8;

		    memoryCache = new LruCache<String, Bitmap>(cacheSize) {
		        @Override
		        protected int sizeOf(String key, Bitmap bitmap) {
		            // The cache size will be measured in kilobytes rather than
		            // number of items.
		            return bitmap.getByteCount() / 1024;
		        }
		    };
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {
	       memoryCache.put(key, bitmap);
	    }
	}

	public Bitmap getBitmapFromMemCache(String key) {
		if(key!=null)
			return memoryCache.get(key);
		else
			return null;
	}
	
	public static CachingImage getCachingImage(){
		if(cachingImage==null)
			cachingImage=new CachingImage();
		return cachingImage;
	}
	
}
