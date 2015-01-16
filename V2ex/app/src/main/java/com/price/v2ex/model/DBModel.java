package com.price.v2ex.model;

import android.content.Context;

import java.util.List;

/**
 * Created by YC on 15-1-15.
 */
public abstract class DBModel<T> {

    protected Context mContext;

    public DBModel(Context context) {
        mContext = context;
    }

    public abstract List<T> getListData();
}
