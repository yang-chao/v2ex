package com.price.v2ex.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;

import com.price.v2ex.util.SelectionBuilder;
import static com.price.v2ex.util.LogUtils.LOGV;
import com.price.v2ex.provider.V2exDataBase.Tables;
import com.price.v2ex.provider.V2exContract.Nodes;
import com.price.v2ex.provider.V2exContract.Topics;
import com.price.v2ex.provider.V2exContract.Members;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by YC on 15-1-13.
 */
public class V2exContentProvider extends ContentProvider {
    private static final String TAG = V2exContentProvider.class.getSimpleName();

    private V2exDataBase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int NODES = 100;
    private static final int NODES_ID = 101;

    private static final int TOPICS = 200;
    private static final int TOPICS_ID = 201;
    private static final int TOPICS_COLUMN = 202;

    private static final int MEMBERS = 300;
    private static final int MEMBERS_ID = 301;

    /**
     * Build and return a {@link UriMatcher} that catches all {@link Uri}
     * variations supported by this {@link ContentProvider}.
     */
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = V2exContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, "nodes", NODES);
        matcher.addURI(authority, "nodes/*", NODES_ID);

        matcher.addURI(authority, "topics", TOPICS);
        matcher.addURI(authority, "topics/*", TOPICS_ID);
        matcher.addURI(authority, "topics/column/*", TOPICS_COLUMN);

        matcher.addURI(authority, "members", MEMBERS);
        matcher.addURI(authority, "members/*", MEMBERS_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new V2exDataBase(getContext());
        return true;
    }

    private void deleteDatabase() {
        // TODO: wait for content provider operations to finish, then tear down
        mOpenHelper.close();
        Context context = getContext();
        V2exDataBase.deleteDatabase(context);
        mOpenHelper = new V2exDataBase(getContext());
    }


    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NODES:
                return V2exContract.Nodes.CONTENT_TYPE;
            case NODES_ID:
                return V2exContract.Nodes.CONTENT_ITEM_TYPE;
            case TOPICS:
                return V2exContract.Topics.CONTENT_TYPE;
            case TOPICS_ID:
                return V2exContract.Topics.CONTENT_ITEM_TYPE;
            case TOPICS_COLUMN:
                return V2exContract.Topics.CONTENT_TYPE;
            case MEMBERS:
                return V2exContract.Members.CONTENT_TYPE;
            case MEMBERS_ID:
                return V2exContract.Members.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);

        // avoid the expensive string concatenation below if not loggable
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            LOGV(TAG, "uri=" + uri + " match=" + match + " proj=" + Arrays.toString(projection) +
                    " selection=" + selection + " args=" + Arrays.toString(selectionArgs) + ")");
        }

        switch (match) {
            default: {
                // Most cases are handled with simple SelectionBuilder
                final SelectionBuilder builder = buildExpandedSelection(uri, match);

                boolean distinct = !TextUtils.isEmpty(
                        uri.getQueryParameter(V2exContract.QUERY_PARAMETER_DISTINCT));

                Cursor cursor = builder
                        .where(selection, selectionArgs)
                        .query(db, distinct, projection, sortOrder, null);
                Context context = getContext();
                if (null != context) {
                    cursor.setNotificationUri(context.getContentResolver(), uri);
                }
                return cursor;
            }
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case NODES: {
                db.insertOrThrow(Tables.NODES, null, values);
                notifyChange(uri);
                return Nodes.buildNodeUri(values.getAsString(Nodes.NODE_ID));
            }
            case TOPICS: {
                db.insertOrThrow(Tables.TOPICS, null, values);
                notifyChange(uri);
                return Topics.buildTopicUri(values.getAsString(Topics.TOPIC_ID));
            }
            case MEMBERS: {
                db.insertOrThrow(Tables.MEMBERS, null, values);
                notifyChange(uri);
                return Members.buildMemberUri(values.getAsString(Members.MEMBER_ID));
            }
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (uri == V2exContract.BASE_CONTENT_URI) {
            // Handle whole database deletes (e.g. when signing out)
            deleteDatabase();
            notifyChange(uri);
            return 1;
        }
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);
        int retVal = builder.where(selection, selectionArgs).delete(db);
        notifyChange(uri);
        return retVal;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);
        int retVal = builder.where(selection, selectionArgs).update(db, values);
        notifyChange(uri);
        return retVal;
    }

    /**
     * Apply the given set of {@link android.content.ContentProviderOperation}, executing inside
     * a {@link SQLiteDatabase} transaction. All changes will be rolled back if
     * any single one fails.
     */
    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    /**
     * Build an advanced {@link SelectionBuilder} to match the requested
     * {@link Uri}. This is usually only used by {@link #query}, since it
     * performs table joins useful for {@link Cursor} data.
     */
    private SelectionBuilder buildExpandedSelection(Uri uri, int match) {
        final SelectionBuilder builder = new SelectionBuilder();
        switch (match) {
            case NODES: {
                return builder.table(Tables.NODES);
            }
            case NODES_ID: {
                final String nodeId = Nodes.getNodeId(uri);
                return builder.table(Tables.NODES)
                        .where(Nodes.NODE_ID + "=?", nodeId);
            }
            case TOPICS: {
                return builder.table(Tables.TOPICS);
            }
            case TOPICS_ID: {
                final String topicId = Topics.getTopicId(uri);
                return builder.table(Tables.TOPICS)
                        .where(Topics.TOPIC_ID + "=?", topicId);
            }
            case MEMBERS: {
                return builder.table(Tables.MEMBERS);
            }
            case MEMBERS_ID: {
                final String memberId = Members.getMemberId(uri);
                return builder.table(Tables.MEMBERS)
                        .where(Members.MEMBER_ID + "=?", memberId);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    /**
     * Build a simple {@link SelectionBuilder} to match the requested
     * {@link Uri}. This is usually enough to support {@link #insert},
     * {@link #update}, and {@link #delete} operations.
     */
    private SelectionBuilder buildSimpleSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NODES: {
                return builder.table(Tables.NODES);
            }
            case NODES_ID: {
                final String nodeId = Nodes.getNodeId(uri);
                return builder.table(Tables.NODES)
                        .where(Nodes.NODE_ID + "=?", nodeId);
            }
            case TOPICS: {
                return builder.table(Tables.TOPICS);
            }
            case TOPICS_ID: {
                final String topicId = Topics.getTopicId(uri);
                return builder.table(Tables.TOPICS)
                        .where(Topics.TOPIC_ID + "=?", topicId);
            }
            case TOPICS_COLUMN: {
                String columnId = Topics.getTopicColumnId(uri);
                return builder.table(Tables.TOPICS).where(Topics.TOPIC_COLUMN_ID + "=?", columnId);
            }
            case MEMBERS: {
                return builder.table(Tables.MEMBERS);
            }
            case MEMBERS_ID: {
                final String memberId = Members.getMemberId(uri);
                return builder.table(Tables.MEMBERS)
                        .where(Members.MEMBER_ID + "=?", memberId);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri for " + match + ": " + uri);
            }
        }
    }

    private void notifyChange(Uri uri) {
        Context context = getContext();
        context.getContentResolver().notifyChange(uri, null);
    }
}
