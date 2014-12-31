package com.price.v2ex.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YC on 14-12-30.
 */
public class AdapterHandler {

    public static void notifyDataSetChanged(RecyclerView.Adapter adapter, List data, boolean refresh) {
        if (adapter == null || data == null) {
            return;
        }
        if (adapter instanceof HeaderFooterRecyclerAdapter) {
            List origData = ((HeaderFooterRecyclerAdapter) adapter).getData();
            if (refresh) {
                origData.clear();
            }
            origData.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }
}
