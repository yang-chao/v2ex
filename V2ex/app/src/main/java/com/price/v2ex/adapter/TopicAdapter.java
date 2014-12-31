package com.price.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.price.v2ex.R;
import com.price.v2ex.model.ModelUtils;
import com.price.v2ex.model.Topic;
import com.price.v2ex.volley.VolleyManager;


/**
 * Created by YC on 14-12-30.
 */
public class TopicAdapter extends PageAdapter {

    private ImageLoader mImageLoader;

    public TopicAdapter(Context context) {
        mImageLoader = VolleyManager.getImageLoader();
    }

    @Override
    public boolean useHeader() {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindHeaderView(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.adapter_topic, null);
        TopicHolder holder = new TopicHolder(view);
        return holder;
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        Topic topic = (Topic) mData.get(position);
        if (topic == null) {
            return;
        }

        TopicHolder topicHolder = (TopicHolder) holder;
        topicHolder.avatar.setImageUrl(ModelUtils.getCDNUrl(topic.getMember().getAvatarNormal()), mImageLoader);
        topicHolder.title.setText(topic.getTitle());
        topicHolder.name.setText(topic.getMember().getUsername());
        topicHolder.lastTouchedTime.setText(topic.getLastTouched() + "");
        topicHolder.node.setText(topic.getNode().getTitle());
        topicHolder.reply.setText(topic.getReplies() + "回复");
    }

    class TopicHolder extends RecyclerView.ViewHolder{

        NetworkImageView avatar;
        TextView title;
        TextView name;
        TextView reply;
        TextView lastTouchedTime;
        TextView lastTouchedName;
        TextView node;

        public TopicHolder(View itemView) {
            super(itemView);
            avatar = (NetworkImageView) itemView.findViewById(R.id.avatar);
            title = (TextView) itemView.findViewById(R.id.title);
            name = (TextView) itemView.findViewById(R.id.name);
            reply = (TextView) itemView.findViewById(R.id.reply);
            lastTouchedTime = (TextView) itemView.findViewById(R.id.last_touched_time);
            lastTouchedName = (TextView) itemView.findViewById(R.id.last_touched_name);
            node = (TextView) itemView.findViewById(R.id.node);
        }
    }


}
