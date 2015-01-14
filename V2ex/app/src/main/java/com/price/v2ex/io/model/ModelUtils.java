package com.price.v2ex.io.model;

import android.text.TextUtils;

/**
 * Created by YC on 14-12-31.
 */
public class ModelUtils {

    public static String getImageUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if (url.startsWith("//")) {
            url = "http:" + url;
        } else if (url.startsWith("/static")) {
            url = "http://www.v2ex.com" + url;
        }
        return url;
    }
}
