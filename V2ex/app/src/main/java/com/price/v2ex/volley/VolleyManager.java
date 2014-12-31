package com.price.v2ex.volley;

import android.content.Context;

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

    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mImageCache = new BitmapLruCache(context);
        mImageLoader = new ImageLoader(mRequestQueue, mImageCache);
    }

    public static RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
