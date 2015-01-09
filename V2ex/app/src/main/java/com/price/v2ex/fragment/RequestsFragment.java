package com.price.v2ex.fragment;

import com.android.volley.Request;
import com.price.v2ex.volley.VolleyManager;

/**
 * 适用于一个页面同时发送多个数据请求
 *
 * Created by YC on 15-1-5.
 */
public abstract class RequestsFragment extends BaseFragment {


    protected abstract Request[] onCreateRequests();

    protected void requestData() {
        Request[] requests = onCreateRequests();
        for (Request request : requests) {
            VolleyManager.addRequest(getActivity(), request);
        }
    }

}
