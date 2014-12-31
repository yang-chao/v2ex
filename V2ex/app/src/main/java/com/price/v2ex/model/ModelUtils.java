package com.price.v2ex.model;

import android.text.TextUtils;

/**
 * Created by YC on 14-12-31.
 */
public class ModelUtils {

    public static String getCDNUrl(String url) {
        if (!TextUtils.isEmpty(url) && url.startsWith("//")) {
            url = "http:" + url;
        }
        return url;
    }
}
