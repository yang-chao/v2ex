package com.price.v2ex;

import android.app.Application;

import com.price.v2ex.volley.VolleyManager;

/**
 * Created by YC on 14-12-31.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.init(this);
    }
}
