package com.price.v2ex.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.DateUtils;

/**
 * Created by YC on 15-1-13.
 */
public class V2exContract {

    /**
     * Query parameter to create a distinct query.
     */
    public static final String QUERY_PARAMETER_DISTINCT = "distinct";

    interface NodesColumns {
        String NODE_ID = "node_id";
        String NODE_NAME = "node_name";
        String NODE_URL = "node_url";
        String NODE_TITLE = "node_title";
        String NODE_TITLE_ALTERNATIVE = "node_title_alternative";
        String NODE_TOPICS = "node_topics";
        String NODE_STARS = "node_stars";
        String NODE_HEADER = "node_header";
        String NODE_FOOTER = "node_footer";
        String NODE_CREATED = "node_created";
        String NODE_AVATAR_MINI = "node_avatar_mini";
        String NODE_AVATAR_NORMAL = "node_avatar_normal";
        String NODE_AVATAR_LARGE = "node_avatar_large";
        String NODE_STATUS = "node_status";
    }

    interface TopicsColumns {
        String TOPIC_ID = "topic_id";
        String TOPIC_TITLE = "topic_title";
        String TOPIC_CONTENT = "topic_content";
        String TOPIC_CONTENT_RENDERED = "topic_content_rendered";
        String TOPIC_REPLIES = "topic_replied";
        String TOPIC_CREATED = "topic_created";
        String TOPIC_LAST_MODIFIED = "topic_last_modified";
        String TOPIC_LAST_TOUCHED = "topic_last_touched";
        String TOPIC_MEMBER_ID = "topic_member_id";
        String TOPIC_NODE_ID = "topic_node_id";
        String TOPIC_COLUMN_ID = "topic_column_id";
    }

    interface MemberColumns {
        String MEMBER_ID = "member_id";
        String MEMBER_USERNAME = "member_username";
        String MEMBER_TAGLINE = "member_tagline";
        String MEMBER_AVATAR_MINI = "member_mini";
        String MEMBER_AVATAR_NORMAL = "member_normal";
        String MEMBER_AVATAR_LARGE = "member_avatar_large";
    }

    public static final String CONTENT_AUTHORITY = "com.price.v2ex";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_NODES = "nodes";
    private static final String PATH_TOPICS = "topics";
    private static final String PATH_MEMBERS = "members";

    public static final String[] TOP_LEVEL_PATHS = {
            PATH_NODES,
            PATH_TOPICS,
            PATH_MEMBERS
    };

    public static class Nodes implements NodesColumns, BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NODES).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.v2ex.node";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.v2ex.node";

        /** "ORDER BY" clauses. */
        public static final String DEFAULT_SORT = NodesColumns.NODE_ID + " ASC";

        /** Build {@link Uri} for requested {@link #NODE_ID}. */
        public static Uri buildNodeUri(String nodeId) {
            return CONTENT_URI.buildUpon().appendPath(nodeId).build();
        }

        /** Read {@link #NODE_ID} from {@link Nodes} {@link Uri}. */
        public static String getNodeId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static class Topics implements TopicsColumns, BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOPICS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.v2ex.topic";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.v2ex.topic";

        public static final String DEFAULT_SORT = TopicsColumns.TOPIC_ID + " ASC";

        public static Uri buildTopicUri(String topicId) {
            return CONTENT_URI.buildUpon().appendPath(topicId).build();
        }

        public static String getTopicId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildTopicColumnUri(String columnId) {
            return CONTENT_URI.buildUpon().appendPath(columnId).build();
        }

        public static String getTopicColumnId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Members implements MemberColumns, BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEMBERS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.v2ex.member";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.v2ex.member";

        public static final String DEFAULT_SORT = MemberColumns.MEMBER_ID + " ASC";

        public static Uri buildMemberUri(String memberId) {
            return CONTENT_URI.buildUpon().appendPath(memberId).build();
        }

        public static String getMemberId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
