package com.price.v2ex.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.price.v2ex.BuildConfig;
import com.price.v2ex.R;
import com.price.v2ex.adapter.AdapterHandler;
import com.price.v2ex.adapter.TopicReplyAdapter;
import com.price.v2ex.constants.Urls;
import com.price.v2ex.model.ModelUtils;
import com.price.v2ex.model.Reply;
import com.price.v2ex.model.Topic;
import com.price.v2ex.request.GsonListRequest;
import com.price.v2ex.utils.ImageUtils;
import com.price.v2ex.utils.TimeUtils;

import java.util.List;

public class TopicFragment extends RequestsFragment {

    private static final String PARAM_ID = "param_id";

    private String mTopicId;

    private TopicReplyAdapter mAdapter;


    public static TopicFragment newInstance(String topicId) {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_ID, topicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTopicId = getArguments().getString(PARAM_ID);
        }
        requestData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(android.R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TopicReplyAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
        showProgress(true);
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

    @Override
    protected Request[] onCreateRequests() {
        String topicUrl = String.format(Urls.TOPIC, mTopicId);
        if (BuildConfig.DEBUG) {
            Log.d("TopicFragment url: ", topicUrl);
        }
        String replyUrl = String.format(Urls.REPLY, mTopicId);
        if (BuildConfig.DEBUG) {
            Log.d("TopicFragment url: ", replyUrl);
        }
        return new Request[]{
                new GsonListRequest<Topic>(getActivity(), topicUrl, Topic[].class,
                        new Response.Listener<List<Topic>>() {
                            @Override
                            public void onResponse(List<Topic> response) {
                                if (!isAdded() || response == null || response.size() < 1) {
                                    return;
                                }
//                                bindView(response.get(0));
                                showProgress(false);
                                mAdapter.updateTopic(response.get(0));
                                mAdapter.notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }),
                new GsonListRequest<Reply>(getActivity(), replyUrl, Reply[].class,
                        new Response.Listener<List<Reply>>() {
                            @Override
                            public void onResponse(List<Reply> response) {
                                if (!isAdded() || mAdapter == null) {
                                    return;
                                }
                                AdapterHandler.notifyDataSetChanged(mAdapter, response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        })};
    }

}
