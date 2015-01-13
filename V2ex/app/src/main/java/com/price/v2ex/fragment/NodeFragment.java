package com.price.v2ex.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.price.v2ex.BuildConfig;
import com.price.v2ex.R;
import com.price.v2ex.adapter.AdapterHandler;
import com.price.v2ex.adapter.NodeAdapter;
import com.price.v2ex.adapter.TopicReplyAdapter;
import com.price.v2ex.common.ActivityHelper;
import com.price.v2ex.constants.Urls;
import com.price.v2ex.model.Node;
import com.price.v2ex.model.Topic;
import com.price.v2ex.request.GsonListRequest;
import com.price.v2ex.request.GsonRequest;

import java.net.URLStreamHandler;
import java.util.List;

/**
 * Created by YC on 15-1-13.
 */
public class NodeFragment extends RequestsFragment {

    private static final String PARAM_NODE_ID = "param_node_ID";
    private static final String PARAM_NODE_NAME = "param_node_name";

    private String mNodeId;
    private String mNodeName;
    private NodeAdapter mAdapter;


    public static Fragment newInstance(String nodeId, String nodeName) {
        NodeFragment fragment = new NodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_NODE_ID, nodeId);
        bundle.putString(PARAM_NODE_NAME, nodeName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mNodeId = getArguments().getString(PARAM_NODE_ID);
            mNodeName = getArguments().getString(PARAM_NODE_NAME);
        }
        requestData();
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(android.R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new NodeAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
        showProgress(true);
    }

    @Override
    protected Request[] onCreateRequests() {
        final Request[] requests = new Request[2];
        String url = String.format(Urls.NODE, mNodeName);
        if (BuildConfig.DEBUG) {
            Log.d("url: ", url);
        }
        requests[0] = new GsonRequest<Node>(getActivity(), url, Node.class, null,
                new Response.Listener<Node>() {
                    @Override
                    public void onResponse(Node response) {
                        if (!isAdded() || response == null) {
                            return;
                        }
                        getActionBar().setTitle(response.getName());
                        showProgress(false);
                        mAdapter.updateNode(response);
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        url = String.format(Urls.NODE_TOPICS, mNodeId);
        if (BuildConfig.DEBUG) {
            Log.d("url: ", url);
        }
        requests[1] = new GsonListRequest<Topic>(getActivity(), url, Topic[].class,
                new Response.Listener<List<Topic>>() {
                    @Override
                    public void onResponse(List<Topic> response) {
                        if (!isAdded()) {
                            return;
                        }
                        AdapterHandler.notifyDataSetChanged(mAdapter, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return requests;
    }
}
