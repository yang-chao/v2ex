package com.price.v2ex.utils;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * Created by YC on 15-1-13.
 */
public class StringUtils {

    public static Spanned getHtmlText(String text) {
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        return Html.fromHtml(text);
    }
}
