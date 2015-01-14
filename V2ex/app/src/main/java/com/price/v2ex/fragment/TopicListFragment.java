package com.price.v2ex.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.price.v2ex.adapter.AdapterHandler;
import com.price.v2ex.adapter.TopicAdapter;
import com.price.v2ex.io.model.Topic;
import com.price.v2ex.request.GsonListRequest;

import java.util.List;

/**
 * Created by YC on 15-1-13.
 */
public class TopicListFragment extends RequestListFragment<List<Topic>> {

    private static final String PARAM_URL = "param_url";

    private String mUrl;


    public static Fragment newInstance(String url) {
        TopicListFragment fragment = new TopicListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_URL, url);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUrl = getArguments().getString(PARAM_URL);
        }
    }

    @Override
    protected Request newRequest(boolean refresh, Response.Listener<List<Topic>> listener, Response.ErrorListener errorListener) {
        return new GsonListRequest(getActivity(), mUrl, Topic[].class, listener, errorListener);
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
