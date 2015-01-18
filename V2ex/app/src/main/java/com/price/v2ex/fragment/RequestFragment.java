package com.price.v2ex.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.price.v2ex.util.NetUtils;
import com.price.v2ex.volley.VolleyManager;

/**
 * 适用于一个页面只请求一次数据，如文章的详细页
 *
 * Created by YC on 15-1-5.
 */
public abstract class RequestFragment<T> extends BaseFragment implements Response.Listener<T>, Response.ErrorListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (requestNetImmediately(getActivity())) {
            requestNetData(true);
        } else {
            requestLocalData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * 进入页面后是否立即加载网络数据
     *
     * @return True立即加载网络数据，False加载本地
     */
    protected boolean requestNetImmediately(Context context) {
        return NetUtils.checkNetwork(context);
    }

    /**
     * 加载网络数据
     * @param refresh true表示刷新，false表示加载更多
     */
    protected void requestNetData(boolean refresh) {
        Request request = createRequest(this, this);
        if (request != null) {
            VolleyManager.addRequest(getActivity(), request);
        }
    }

    /**
     * 加载本地数据
     */
    protected void requestLocalData() {

    }

    @Override
    public void onResponse(T response) {
        showProgress(false);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    protected abstract Request createRequest(Response.Listener<T> listener, Response.ErrorListener errorListener);

}
