package com.price.v2ex.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by YC on 14/12/20.
 */
public class VolleyManager {

    private static RequestQueue mRequestQueue;
    private static ImageLoader.ImageCache mImageCache;
    private static ImageLoader mImageLoader;


    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    private static ImageLoader.ImageCache getImageCache(Context context) {
        if (mImageCache == null) {
            mImageCache = new BitmapLruCache(context.getApplicationContext());
        }
        return mImageCache;
    }

    public static ImageLoader getImageLoader(Context context) {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(getRequestQueue(context), getImageCache(context));
        }
        return mImageLoader;
    }

    public static void addRequest(Context context, Request request) {
        getRequestQueue(context).add(request);
    }

}
