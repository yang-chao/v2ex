package com.price.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.price.v2ex.R;
import com.price.v2ex.activity.TopicActivity;
import com.price.v2ex.io.model.ModelUtils;
import com.price.v2ex.io.model.Topic;
import com.price.v2ex.util.ImageUtils;
import com.price.v2ex.util.TimeUtils;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by YC on 14-12-30.
 */
public class TopicAdapter extends PageAdapter<Topic> {

    private Context mContext;

    public TopicAdapter(Context context) {
        mContext = context;
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
        final Topic topic = getItem(position);
        if (topic == null) {
            return;
        }

        final TopicHolder topicHolder = (TopicHolder) holder;
        ImageUtils.loadImage(topicHolder.avatar, ModelUtils.getImageUrl(topic.getMember().getAvatarNormal()));
        topicHolder.title.setText(topic.getTitle());
        topicHolder.name.setText(topic.getMember().getUsername());
        topicHolder.lastTouchedTime.setText(TimeUtils.timestampToDate(topic.getLastTouched()));
        topicHolder.node.setText(topic.getNode().getTitle());
        topicHolder.reply.setText(topic.getReplies() + "回复");

        topicHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopicActivity.startTopicActivity(mContext, topic.getId(), topicHolder.avatar, "shareAvatar");
            }
        });
    }

    class TopicHolder extends RecyclerView.ViewHolder{

        CircleImageView avatar;
        TextView title;
        TextView name;
        TextView reply;
        TextView lastTouchedTime;
        TextView lastTouchedName;
        TextView node;

        public TopicHolder(View itemView) {
            super(itemView);
            avatar = (CircleImageView) itemView.findViewById(R.id.avatar);
            title = (TextView) itemView.findViewById(R.id.title);
            name = (TextView) itemView.findViewById(R.id.name);
            reply = (TextView) itemView.findViewById(R.id.reply);
            lastTouchedTime = (TextView) itemView.findViewById(R.id.last_touched_time);
            lastTouchedName = (TextView) itemView.findViewById(R.id.last_touched_name);
            node = (TextView) itemView.findViewById(R.id.node);
        }
    }
}
