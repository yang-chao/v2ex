package com.price.v2ex.utils;

import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.price.v2ex.volley.VolleyManager;

/**
 * Created by YC on 15-1-4.
 */
public class ImageUtils {

    public static void loadImage(ImageView view, String url) {
        loadImage(view, url, 0, 0);
    }
    public static void loadImage(ImageView view, String url, final int defaultImageResId, final int errorImageResId) {
        ImageLoader imageLoader = VolleyManager.getImageLoader(view.getContext());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(view, defaultImageResId, errorImageResId);
        imageLoader.get(url, imageListener);
    }

}
