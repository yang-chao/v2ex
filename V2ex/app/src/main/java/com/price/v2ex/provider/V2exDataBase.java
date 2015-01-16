package com.price.v2ex.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.price.v2ex.provider.V2exContract.NodesColumns;
import com.price.v2ex.provider.V2exContract.TopicsColumns;
import com.price.v2ex.provider.V2exContract.MemberColumns;

/**
 * Created by YC on 15-1-13.
 */
public class V2exDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "v2ex.db";
    private static final int DATABASE_VERSION = 1;

    interface Tables {
        String NODES = "nodes";
        String TOPICS = "topics";
        String MEMBERS = "members";
    }

    public V2exDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.NODES + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NodesColumns.NODE_ID + " TEXT NOT NULL,"
                + NodesColumns.NODE_NAME + " TEXT NOT NULL,"
                + NodesColumns.NODE_TITLE + " TEXT NOT NULL,"
                + NodesColumns.NODE_TITLE_ALTERNATIVE + " TEXT,"
                + NodesColumns.NODE_HEADER + " TEXT,"
                + NodesColumns.NODE_FOOTER + " TEXT,"
                + NodesColumns.NODE_CREATED + " TEXT,"
                + NodesColumns.NODE_URL + " TEXT,"
                + NodesColumns.NODE_AVATAR_MINI + " TEXT,"
                + NodesColumns.NODE_AVATAR_NORMAL + " TEXT,"
                + NodesColumns.NODE_AVATAR_LARGE + " TEXT,"
                + NodesColumns.NODE_TOPICS + " INTEGER,"
                + NodesColumns.NODE_STARS + " INTEGER,"
                + NodesColumns.NODE_STATUS + " INTEGER NOT NULL,"
                + "UNIQUE (" + NodesColumns.NODE_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE " + Tables.TOPICS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TopicsColumns.TOPIC_ID + " TEXT NOT NULL,"
                + TopicsColumns.TOPIC_TITLE + " TEXT NOT NULL,"
                + TopicsColumns.TOPIC_CONTENT + " TEXT,"
                + TopicsColumns.TOPIC_CONTENT_RENDERED + " TEXT,"
                + TopicsColumns.TOPIC_CREATED + " TEXT,"
                + TopicsColumns.TOPIC_LAST_MODIFIED + " TEXT,"
                + TopicsColumns.TOPIC_LAST_TOUCHED + " TEXT,"
                + TopicsColumns.TOPIC_REPLIES + " INTEGER,"
                + TopicsColumns.TOPIC_MEMBER_ID + " TEXT,"
                + TopicsColumns.TOPIC_NODE_ID + " TEXT,"
                + TopicsColumns.TOPIC_COLUMN_ID + " TEXT,"
                + "UNIQUE (" + TopicsColumns.TOPIC_ID + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE " + Tables.MEMBERS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MemberColumns.MEMBER_ID + " TEXT NOT NULL,"
                + MemberColumns.MEMBER_USERNAME + " TEXT NOT NULL,"
                + MemberColumns.MEMBER_TAGLINE + " TEXT,"
                + MemberColumns.MEMBER_AVATAR_MINI + " TEXT,"
                + MemberColumns.MEMBER_AVATAR_NORMAL + " TEXT,"
                + MemberColumns.MEMBER_AVATAR_LARGE + " TEXT,"
                + "UNIQUE (" + MemberColumns.MEMBER_ID + ") ON CONFLICT REPLACE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
