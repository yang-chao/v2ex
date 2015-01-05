package com.price.v2ex.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.price.v2ex.R;
import com.price.v2ex.volley.VolleyManager;

/**
 * Created by YC on 15-1-5.
 */
public abstract class RequestFragment<T> extends BaseFragment implements Response.Listener<T>, Response.ErrorListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        RelativeLayout rootView = (RelativeLayout) view.findViewById(R.id.root);
        RelativeLayout.LayoutParams params;

        // content
        View contentView = onCreateContentView(inflater, container, savedInstanceState);
        if (contentView != null) {
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rootView.addView(contentView, params);
        }

        // progress
        View progressView = inflater.inflate(R.layout.base_progressbar, container, false);
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        rootView.addView(progressView, params);

        return view;
    }

    /**
     * 适用于一个页面只请求一次数据，如文章的详细页
     */
    protected void requestDataOnce() {
        Request request = onCreateRequest(this, this);
        if (request != null) {
            VolleyManager.addRequest(getActivity(), request);
        }
    }

    protected abstract View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract Request onCreateRequest(Response.Listener listener, Response.ErrorListener errorListener);

    @Override
    public void onResponse(T response) {
        setLoadFinish(true);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    public void setLoadFinish(boolean finish) {
        if (!isAdded()) {
            return;
        }
        getView().findViewById(android.R.id.progress).setVisibility(finish ? View.GONE : View.VISIBLE);
    }
}
