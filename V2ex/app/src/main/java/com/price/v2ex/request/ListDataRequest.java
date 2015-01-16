package com.price.v2ex.request;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.price.v2ex.io.JSONHandler;
import com.price.v2ex.model.DBModel;
import com.price.v2ex.provider.V2exContract;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.price.v2ex.util.LogUtils.LOGD;
import static com.price.v2ex.util.LogUtils.LOGE;

/**
 * Created by YC on 14-1-14.
 */
public class ListDataRequest<T> extends Request<List<T>> {
    private static final String TAG = ListDataRequest.class.getSimpleName();

    private final Context mContext;
    private String mCharset = "utf-8";
    private Response.Listener<List<T>> mListener;

    private JSONHandler<T> mJSONHandler;
    private DBModel<T> mDBModel;

    public ListDataRequest(Context context, JSONHandler jsonHandler, DBModel dbModel, String url,
                           Response.Listener<List<T>> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mListener = listener;
        mContext = context;
        mJSONHandler = jsonHandler;
        mDBModel = dbModel;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    protected Response<List<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = getResponseStr(response);
            JsonReader reader = new JsonReader(new StringReader(json));
            JsonParser parser = new JsonParser();
            mJSONHandler.process(parser.parse(reader));
            ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
            mJSONHandler.makeContentProviderOperations(batch);
            try {
                int operations = batch.size();
                if (operations > 0) {
                    mContext.getContentResolver().applyBatch(V2exContract.CONTENT_AUTHORITY, batch);
                }
                LOGD(TAG, "Successfully applied " + operations + " content provider operations.");
            } catch (RemoteException ex) {
                LOGE(TAG, "RemoteException while applying content provider operations.");
                throw new RuntimeException("Error executing content provider batch operation", ex);
            } catch (OperationApplicationException ex) {
                LOGE(TAG, "OperationApplicationException while applying content provider operations.");
                throw new RuntimeException("Error executing content provider batch operation", ex);
            }

            return Response.success(mDBModel.getListData(), HttpHeaderParser.parseCacheHeaders(response));
//            return Response.success(mJSONHandler.getListData(), HttpHeaderParser.parseCacheHeaders(response));
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
}
