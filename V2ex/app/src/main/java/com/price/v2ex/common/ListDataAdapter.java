package com.price.v2ex.common;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YC on 15-1-9.
 */
public abstract class ListDataAdapter<T> extends RecyclerView.Adapter {

    public List<T> mData = new ArrayList<T>();

    public List<T> getData() {
        return mData;
    }

    public T getItem(int position) {
        return mData.get(position);
    }
}
