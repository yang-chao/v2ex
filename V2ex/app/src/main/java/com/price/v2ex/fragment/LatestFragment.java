package com.price.v2ex.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.price.v2ex.adapter.AdapterHandler;
import com.price.v2ex.adapter.TopicAdapter;
import com.price.v2ex.constants.Urls;
import com.price.v2ex.io.TopicsHandler;
import com.price.v2ex.io.model.Topic;
import com.price.v2ex.model.TopicModel;
import com.price.v2ex.request.GsonListRequest;
import com.price.v2ex.request.ListDataRequest;

import java.util.List;


public class LatestFragment extends RequestListFragment<List<Topic>> {

    public static LatestFragment newInstance() {
        LatestFragment fragment = new LatestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Request newRequest(boolean refresh, Response.Listener<List<Topic>> listener, Response.ErrorListener errorListener) {
        return new ListDataRequest<Topic>(getActivity(), new TopicsHandler(getActivity()), new TopicModel(getActivity()),
                Urls.LATEST, listener, errorListener);
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
