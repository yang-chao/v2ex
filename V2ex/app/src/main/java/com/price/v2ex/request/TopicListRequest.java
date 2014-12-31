package com.price.v2ex.request;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.price.v2ex.model.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YC on 14-12-31.
 */
public class TopicListRequest extends Request<List<Topic>> {
    private final Context mContext;
    private final Gson gson = new Gson();
    private final Class<Topic[]> clazz;
    private final Response.Listener<List<Topic>> listener;
    private String mCharset = "utf-8";


    public TopicListRequest(Context context, String url, Class<Topic[]> clazz,
                       Response.Listener<List<Topic>> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mContext = context;
        this.clazz = clazz;
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(List<Topic> response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<List<Topic>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = getResponseStr(response);
            Topic[] topicArray = gson.fromJson(json, clazz);
            List<Topic> topics = new ArrayList<Topic>();
            for (Topic topic : topicArray) {
                topics.add(topic);
            }
            return Response.success(topics, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
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
