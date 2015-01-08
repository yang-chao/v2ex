package com.price.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.price.v2ex.R;
import com.price.v2ex.model.ModelUtils;
import com.price.v2ex.model.Reply;
import com.price.v2ex.model.Topic;
import com.price.v2ex.utils.ImageUtils;
import com.price.v2ex.utils.TimeUtils;

/**
 * Created by YC on 15-1-7.
 */
public class TopicReplyAdapter extends HeaderFooterRecyclerAdapter {

    private static final int BASIC_ITEM_REPLY = 0;
    private static final int BASIC_ITEM_REPLY_HEADER = 1;

    private Topic mTopic;
    private Context mContext;

    public TopicReplyAdapter(Context context) {
        mContext = context;
    }

    public void updateTopic(Topic topic) {
        mTopic = topic;
    }

    @Override
    public boolean useHeader() {
        return true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.adapter_topic_reply, null);
        return new TopicHolder(view);
    }

    @Override
    public void onBindHeaderView(RecyclerView.ViewHolder holder, int position) {
        if (mTopic == null) {
            return;
        }

        final TopicHolder topicHolder = (TopicHolder) holder;
        topicHolder.title.setText(mTopic.getTitle());
        topicHolder.time.setText(TimeUtils.timestampToDate(mTopic.getCreated()));
        topicHolder.content.setText(Html.fromHtml(mTopic.getContentRendered()));
        ImageUtils.loadImage(topicHolder.avatar, ModelUtils.getCDNUrl(mTopic.getMember().getAvatarNormal()));
    }

    @Override
    public boolean useFooter() {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindFooterView(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BASIC_ITEM_REPLY_HEADER) {
            View view = View.inflate(parent.getContext(), R.layout.adapter_reply_header, null);
            return new ReplyHeaderHolder(view);
        } else {
            View view = View.inflate(parent.getContext(), R.layout.adapter_reply, null);
            return new ReplyHolder(view);
        }
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == BASIC_ITEM_REPLY_HEADER) {
            ReplyHeaderHolder headerHolder = (ReplyHeaderHolder) holder;
            headerHolder.totalCount.setText(mContext.getString(R.string.reply_suffix, mData.size()));
        } else {
            position = position - 1;
            ReplyHolder replyHolder = (ReplyHolder) holder;
            Reply reply = (Reply) mData.get(position);
            replyHolder.name.setText(reply.getMember().getUsername());
            replyHolder.time.setText(TimeUtils.getDateDiffer(mContext, reply.getCreated()));
            replyHolder.content.setText(Html.fromHtml(reply.getContentRendered()));
            ImageUtils.loadImage(replyHolder.avatar, ModelUtils.getCDNUrl(reply.getMember().getAvatarNormal()));
        }
    }

    @Override
    public int getBasicItemType(int position) {
        if (position == 1) {
            // 回复列表的头
            return BASIC_ITEM_REPLY_HEADER;
        }
        return BASIC_ITEM_REPLY;
    }

    @Override
    public int getBasicItemCount() {
        return super.getBasicItemCount() + 1;
    }

    class TopicHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView time;
        private TextView content;
        private ImageView avatar;

        public TopicHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
            content = (TextView) itemView.findViewById(R.id.content);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }

    class ReplyHolder extends RecyclerView.ViewHolder {

        private ImageView avatar;
        private TextView time;
        private TextView content;
        private TextView name;

        public ReplyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            content = (TextView) itemView.findViewById(R.id.content);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }

    class ReplyHeaderHolder extends RecyclerView.ViewHolder {

        TextView totalCount;

        public ReplyHeaderHolder(View itemView) {
            super(itemView);
            totalCount = (TextView) itemView.findViewById(R.id.count);
        }
    }
}
