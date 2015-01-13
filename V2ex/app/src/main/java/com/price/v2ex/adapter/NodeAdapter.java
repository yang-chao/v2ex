package com.price.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.price.v2ex.R;
import com.price.v2ex.activity.TopicActivity;
import com.price.v2ex.common.HeaderFooterRecyclerAdapter;
import com.price.v2ex.model.ModelUtils;
import com.price.v2ex.model.Node;
import com.price.v2ex.model.Topic;
import com.price.v2ex.utils.ImageUtils;
import com.price.v2ex.utils.StringUtils;
import com.price.v2ex.utils.TimeUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by YC on 15-1-13.
 */
public class NodeAdapter extends HeaderFooterRecyclerAdapter<Topic> {

    private Context mContext;
    private Node mNode;

    public NodeAdapter(Context context) {
        mContext = context;
    }

    public void updateNode(Node node) {
        mNode = node;
    }

    @Override
    public boolean useHeader() {
        return true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.adapter_node_header, null);
        return new NodeHolder(view);
    }

    @Override
    public void onBindHeaderView(RecyclerView.ViewHolder holder, int position) {
        if (mNode == null) {
            return;
        }

        NodeHolder nodeHolder = (NodeHolder) holder;
        nodeHolder.name.setText(mNode.getTitle());
        nodeHolder.desc.setText(StringUtils.getHtmlText(mNode.getHeader()));
        ImageUtils.loadImage(nodeHolder.avatar, ModelUtils.getImageUrl(mNode.getAvatarLarge()));
    }

    @Override
    public boolean useFooter() {
        return false;
    }

    @Override
    public void onBindFooterView(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
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

    @Override
    public int getBasicItemType(int position) {
        return 0;
    }

    class NodeHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView avatar;
        TextView desc;

        public NodeHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
        }
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
