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

    public static final String CONTENT_AUTHORITY = "com.price.v2ex";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_NODES = "nodes";

    public static final String[] TOP_LEVEL_PATHS = {
            PATH_NODES,
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
}
