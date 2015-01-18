package com.price.v2ex.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.price.v2ex.R;
import com.price.v2ex.adapter.AdapterHandler;
import com.price.v2ex.adapter.NodesAdapter;
import com.price.v2ex.constants.Urls;
import com.price.v2ex.io.NodesHandler;
import com.price.v2ex.io.model.Node;
import com.price.v2ex.model.NodeModel;
import com.price.v2ex.request.ListDataRequest;

import java.util.List;

/**
 * Created by YC on 15-1-9.
 */
public class NodesFragment extends RequestListFragment<Node> {


    @Override
    protected RecyclerView.Adapter createAdapter(Context context) {
        return new NodesAdapter(getActivity());
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    protected Request newRequest(boolean refresh, Response.Listener listener, Response.ErrorListener errorListener) {
        return new ListDataRequest(getActivity(), new NodesHandler(getActivity()), new NodeModel(getActivity()), Urls.NODES, listener, errorListener);
    }

    @Override
    protected List<Node> getLocalData() {
        return NodeModel.getNodes(getActivity());
    }

    @Override
    public void onResponse(List<Node> response) {
        super.onResponse(response);
        showProgress(false);
        markLastPage(true);
        AdapterHandler.notifyDataSetChanged(getAdapter(), response, true);
    }

}
