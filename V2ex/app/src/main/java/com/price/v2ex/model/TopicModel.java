package com.price.v2ex.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.price.v2ex.adapter.AdapterHandler;
import com.price.v2ex.io.model.Topic;
import com.price.v2ex.provider.V2exContract.Topics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YC on 15-1-15.
 */
public class TopicModel extends DBModel<Topic> {

    private String mColumnId;

    public TopicModel(Context context, String columnId) {
        super(context);
        mColumnId = columnId;
    }

    @Override
    public List<Topic> getListData() {
        return getTopics(mContext, mColumnId);
    }

    public static List<Topic> getTopics(Context context, String columnId) {
        Uri uri = Topics.CONTENT_URI;
        String selection = Topics.TOPIC_COLUMN_ID + "=?";
        String[] selectionArgs = {columnId};
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        if (cursor != null) {
            ArrayList<Topic> topics = new ArrayList<Topic>();
            while (cursor.moveToNext()) {
                Topic topic = new Topic();
                topic.id = cursor.getString(cursor.getColumnIndex(Topics.TOPIC_ID));
                topic.nodeId = cursor.getString(cursor.getColumnIndex(Topics.TOPIC_NODE_ID));
                topic.memberId = cursor.getString(cursor.getColumnIndex(Topics.TOPIC_MEMBER_ID));
                topic.columnId = cursor.getString(cursor.getColumnIndex(Topics.TOPIC_COLUMN_ID));
                topic.title = cursor.getString(cursor.getColumnIndex(Topics.TOPIC_TITLE));
                topic.content = cursor.getString(cursor.getColumnIndex(Topics.TOPIC_CONTENT));
                topic.contentRendered = cursor.getString(cursor.getColumnIndex(Topics.TOPIC_CONTENT_RENDERED));
                topic.created = Long.valueOf(cursor.getString(cursor.getColumnIndex(Topics.TOPIC_CREATED)));
                topic.lastModified = Long.valueOf(cursor.getString(cursor.getColumnIndex(Topics.TOPIC_LAST_MODIFIED)));
                topic.lastTouched = Long.valueOf(cursor.getString(cursor.getColumnIndex(Topics.TOPIC_LAST_TOUCHED)));
                topic.replies = cursor.getInt(cursor.getColumnIndex(Topics.TOPIC_REPLIES));
                topic.node = NodeModel.getNode(context, cursor.getString(cursor.getColumnIndex(Topics.TOPIC_NODE_ID)));
                topic.member = MemberModel.getMember(context, cursor.getString(cursor.getColumnIndex(Topics.TOPIC_MEMBER_ID)));
                topics.add(topic);
            }
            cursor.close();
            return topics;
        }
        return null;
    }
}
