package com.price.v2ex.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.price.v2ex.adapter.AdapterHandler;
import com.price.v2ex.adapter.TopicAdapter;
import com.price.v2ex.io.TopicsHandler;
import com.price.v2ex.io.model.Topic;
import com.price.v2ex.model.TopicModel;
import com.price.v2ex.request.ListDataRequest;

import java.util.List;

/**
 * Created by YC on 15-1-13.
 */
public class TopicListFragment extends RequestListFragment<Topic> {

    private static final String PARAM_URL = "param_url";
    private static final String PARAM_COLUMN = "param_column";

    private String mUrl;
    private String mColumnId;

    public static Fragment newInstance(String url, String columnId) {
        TopicListFragment fragment = new TopicListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_URL, url);
        bundle.putString(PARAM_COLUMN, columnId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mUrl = getArguments().getString(PARAM_URL);
            mColumnId = getArguments().getString(PARAM_COLUMN);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Request newRequest(boolean refresh, Response.Listener<List<Topic>> listener, Response.ErrorListener errorListener) {
        return new ListDataRequest<>(getActivity(), new TopicsHandler(getActivity(), mColumnId), new TopicModel(getActivity(), mColumnId),
                mUrl, listener, errorListener);
    }

    @Override
    public void onResponse(List<Topic> response) {
        super.onResponse(response);
        markLastPage(true);
        AdapterHandler.notifyDataSetChanged(getAdapter(), response, true);
    }

    @Override
    protected RecyclerView.Adapter createAdapter(Context context) {
        return new TopicAdapter(getActivity());
    }

    @Override
    public Loader<List<Topic>> onCreateLoader(int id, Bundle args) {
        return new TopicLoader(getActivity(), mColumnId);
    }

    private static class TopicLoader extends LocalLoader<List<Topic>> {

        private String mColumnId;

        public TopicLoader(Context context, String columnId) {
            super(context);
            mColumnId = columnId;
        }

        @Override
        public List<Topic> loadInBackground() {
            return TopicModel.getTopics(getContext(), mColumnId);
        }
    }
}
