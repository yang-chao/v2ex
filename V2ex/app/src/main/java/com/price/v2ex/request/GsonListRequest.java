package com.price.v2ex.request;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YC on 14-12-31.
 */
public class GsonListRequest<T> extends Request<List<T>> {
    private final Context mContext;
    private final Gson mGson = new Gson();
    private final Class<T[]> mClazz;
    private String mCharset = "utf-8";
    private Response.Listener<List<T>> mListener;


    public GsonListRequest(Context context, String url, Class<T[]> clazz,
                           Response.Listener<List<T>> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mListener = listener;
        mContext = context;
        mClazz = clazz;
    }


    @Override
    protected Response<List<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = getResponseStr(response);
            T[] array = mGson.fromJson(json, mClazz);
            List<T> object = new ArrayList<T>();
            for (T topic : array) {
                object.add(topic);
            }
            return Response.success(object, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(List<T> response) {
        mListener.onResponse(response);
    }

    protected final String getResponseStr(NetworkResponse response) {
        try {
            String charset = !TextUtils.isEmpty(mCharset) ? mCharset : HttpHeaderParser.parseCharset(response.headers);
            return new String(response.data, charset);
        } catch (Exception e) {
            return null;
        }
    }

    public Context getContext() {
        return mContext;
    }
}
