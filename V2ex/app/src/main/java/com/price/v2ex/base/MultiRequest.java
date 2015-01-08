package com.price.v2ex.base;

import com.android.volley.Request;
import com.android.volley.Response;

/**
 * Created by YC on 15-1-7.
 */
public abstract class MultiRequest<T> extends Request<T> {

    private static final int REQUEST_ID_SINGLE = -1;

    private int mRequestId = REQUEST_ID_SINGLE;
    private Response.Listener<ResponseWrapper<T>> mMultiListener;
    private Response.Listener<T> mListener;

    public MultiRequest(int method, String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     *
     * @param requestId requestId不能等于-1
     * @param method
     * @param url
     * @param listener
     * @param errorListener
     */
    public MultiRequest(int requestId, int method, String url, Response.Listener<ResponseWrapper<T>> listener,
                        Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        if (requestId == REQUEST_ID_SINGLE) {
            throw new IllegalArgumentException("requestId can't be -1, case -1 means this request is a single request.");
        }
        mRequestId = requestId;
        mMultiListener = listener;
    }

    @Override
    protected void deliverResponse(T response) {
        if (mMultiListener != null) {
            mMultiListener.onResponse(ResponseWrapper.wrapResponse(mRequestId, response));
        } else if (mListener != null) {
            mListener.onResponse(response);
        }
    }

}
