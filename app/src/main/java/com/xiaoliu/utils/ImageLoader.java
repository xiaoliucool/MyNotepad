package com.xiaoliu.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2016/1/7.
 */
public class ImageLoader {
    private LruCache<String, Bitmap> mLruCache;
    //单例模式
    private static ImageLoader imageLoader;
    //私有的构造方法
    private ImageLoader(){
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }
    public static ImageLoader getInstance(){
        if (imageLoader==null){
            imageLoader = new ImageLoader();
        }
        return imageLoader;
    }
    public void addBitmapToCache(String key, Bitmap bitmap){
        if (getBitmapFromMemoryCache(key)==null){
            mLruCache.put(key, bitmap);
        }
    }
    public Bitmap getBitmapFromMemoryCache(String key){
        return mLruCache.get(key);
    }
}
