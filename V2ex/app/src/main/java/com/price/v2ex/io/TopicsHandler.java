package com.price.v2ex.io;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.price.v2ex.io.model.Topic;
import com.price.v2ex.provider.V2exContract;
import com.price.v2ex.provider.V2exContract.Topics;

import java.util.ArrayList;

/**
 * Created by YC on 15-1-15.
 */
public class TopicsHandler extends JSONHandler<Topic> {

    public TopicsHandler(Context context) {
        super(context);
    }

    @Override
    public void makeContentProviderOperations(ArrayList<ContentProviderOperation> list) {
        final Uri uri = Topics.CONTENT_URI;
        list.add(ContentProviderOperation.newDelete(uri).build());

        NodesHandler nodesHandler = new NodesHandler(mContext);
        MembersHandler membersHandler = new MembersHandler(mContext);

        for (Topic topic : mData) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(uri);
            builder.withValue(Topics.TOPIC_ID, topic.getId());
            builder.withValue(Topics.TOPIC_TITLE, topic.getTitle());
            builder.withValue(Topics.TOPIC_CONTENT, topic.getContent());
            builder.withValue(Topics.TOPIC_CONTENT_RENDERED, topic.getContentRendered());
            builder.withValue(Topics.TOPIC_REPLIES, topic.getReplies());
            builder.withValue(Topics.TOPIC_CREATED, topic.getCreated());
            builder.withValue(Topics.TOPIC_LAST_MODIFIED, topic.getLastModified());
            builder.withValue(Topics.TOPIC_LAST_TOUCHED, topic.getLastTouched());
            builder.withValue(Topics.TOPIC_MEMBER_ID, topic.getMember().getId());
            builder.withValue(Topics.TOPIC_NODE_ID, topic.getNode().getId());
            list.add(builder.build());

            list.add(membersHandler.makeInsertOperation(V2exContract.Members.CONTENT_URI, topic.getMember()));
            list.add(nodesHandler.makeInsertOperation(V2exContract.Nodes.CONTENT_URI, topic.getNode()));
        }
    }

    @Override
    public void process(JsonElement element) {
        Topic[] topics = new Gson().fromJson(element, Topic[].class);
        for (Topic topic : topics) {
            mData.add(topic);
        }
    }
}
