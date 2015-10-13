package com.price.v2ex.volley;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

import java.io.File;

/**
 * Created by YC on 14/12/20.
 */
public class VolleyManager {

    /**
     * 缓存目录
     */
    public static final String DEFAULT_CACHE_DIR = "netease_merchant_volley";
    private static VolleyManager sVolleyManager;
    private RequestQueue mRequestQueue;
    private ImageLoader.ImageCache mImageCache;
    private ImageLoader mImageLoader;


    private VolleyManager(Context context) {
        init(context.getApplicationContext());
    }

    private static VolleyManager getVolleyManager(Context context) {
        if (sVolleyManager == null) {
            sVolleyManager = new VolleyManager(context);
        }
        return sVolleyManager;
    }

    public static RequestQueue getRequestQueue(Context context) {
        return getVolleyManager(context).mRequestQueue;
    }

    public static ImageLoader.ImageCache getImageCache(Context context) {
        return getVolleyManager(context).mImageCache;
    }

    public static ImageLoader getImageLoader(Context context) {
        return getVolleyManager(context).mImageLoader;
    }

    public static void addRequest(Context context, Request request) {
        getRequestQueue(context).add(request);
    }

    private void init(Context context) {
        mRequestQueue = CustomVolley.newRequestQueue(context);
        mImageCache = new BitmapLruCache(context);
        mImageLoader = new ImageLoader(mRequestQueue, mImageCache);
        mRequestQueue.start();
    }

    private static class CustomVolley {
        /**
         * Default on-disk cache directory.
         */

        public static RequestQueue newRequestQueue(Context context, HttpStack stack) {
            File cacheDir = new File(context.getExternalCacheDir(), DEFAULT_CACHE_DIR);
            String userAgent = "volley/0";
            try {
                String packageName = context.getPackageName();
                PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
                userAgent = packageName + "/" + info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
            }

            if (stack == null) {
                if (Build.VERSION.SDK_INT >= 9) {
                    stack = new HurlStack();
                } else {
                    // Prior to Gingerbread, HttpUrlConnection was unreliable.
                    // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
//                    stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
                }
            }

            Network network = new BasicNetwork(stack);

            // 修改缓存大小
            RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir, 200 * 1024 * 1024), network);
            queue.start();

            return queue;
        }

        /**
         * Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
         *
         * @param context A {@link Context} to use for creating the cache dir.
         * @return A started {@link RequestQueue} instance.
         */
        public static RequestQueue newRequestQueue(Context context) {
            return newRequestQueue(context, null);
        }
    }

}
