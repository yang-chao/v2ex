package com.price.v2ex.fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.price.v2ex.volley.VolleyManager;

/**
 * 适用于一个页面只请求一次数据，如文章的详细页
 *
 * Created by YC on 15-1-5.
 */
public abstract class RequestFragment<T> extends BaseFragment implements Response.Listener<T>, Response.ErrorListener {


    protected abstract Request onCreateRequest(Response.Listener<T> listener, Response.ErrorListener errorListener);

    @Override
    protected void requestData() {
        Request request = onCreateRequest(this, this);
        if (request != null) {
            VolleyManager.addRequest(getActivity(), request);
        }
    }

    @Override
    public void onResponse(T response) {
        setLoadFinish(true);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


}
