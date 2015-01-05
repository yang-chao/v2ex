package com.price.v2ex.fragment;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.price.v2ex.BuildConfig;
import com.price.v2ex.R;
import com.price.v2ex.constants.Urls;
import com.price.v2ex.model.ModelUtils;
import com.price.v2ex.model.Topic;
import com.price.v2ex.request.TopicListRequest;
import com.price.v2ex.utils.ImageUtils;
import com.price.v2ex.utils.TimeUtils;

import java.util.List;

public class TopicFragment extends RequestFragment<List<Topic>> {

    private static final String PARAM_ID = "param_id";

    private String mTopicId;


    public static TopicFragment newInstance(String topicId) {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_ID, topicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTopicId = getArguments().getString(PARAM_ID);
        }
        requestDataOnce();
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

    @Override
    protected Request onCreateRequest(Response.Listener listener, Response.ErrorListener errorListener) {
        String url = String.format(Urls.TOPIC, mTopicId);
        if (BuildConfig.DEBUG) {
            Log.d("TopicFragment url: ", url);
        }
        return new TopicListRequest(getActivity(), url, Topic[].class, listener, errorListener);
    }

    @Override
    public void onResponse(List<Topic> response) {
        super.onResponse(response);
        if (response == null || response.size() < 1) {
            return;
        }
        bindView(response.get(0));
    }

    private void bindView(Topic topic) {
        View view = getView();
        if (view == null) {
            return;
        }

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(topic.getTitle());
        TextView time = (TextView) view.findViewById(R.id.time);
        time.setText(TimeUtils.timestampToDate(topic.getCreated()));
        TextView content = (TextView) view.findViewById(R.id.content);
        content.setText(Html.fromHtml(topic.getContentRendered()));
        ImageView avatar = (ImageView) view.findViewById(R.id.avatar);
        ImageUtils.loadImage(avatar, ModelUtils.getCDNUrl(topic.getMember().getAvatarNormal()));
    }

}
