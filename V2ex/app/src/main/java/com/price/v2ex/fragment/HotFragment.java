package com.price.v2ex.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.price.v2ex.adapter.AdapterHandler;
import com.price.v2ex.adapter.TopicAdapter;
import com.price.v2ex.constants.Urls;
import com.price.v2ex.model.Topic;
import com.price.v2ex.request.GsonListRequest;

import java.util.List;


public class HotFragment extends RequestListFragment<List<Topic>> {

    public static HotFragment newInstance() {
        HotFragment fragment = new HotFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Request newRequest(boolean refresh, Response.Listener<List<Topic>> listener, Response.ErrorListener errorListener) {
        return new GsonListRequest(getActivity(), Urls.HOT, Topic[].class, listener, errorListener);
    }

    @Override
    public void onResponse(List<Topic> response) {
        super.onResponse(response);
        markLastPage(true);
        AdapterHandler.notifyDataSetChanged(getAdapter(), (List) response, true);
    }

    @Override
    protected RecyclerView.Adapter onCreateAdapter(Context context) {
        return new TopicAdapter(getActivity());
    }

}
