package com.price.v2ex.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.price.v2ex.provider.V2exContract.NodesColumns;

/**
 * Created by YC on 15-1-13.
 */
public class V2exDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "v2ex.db";
    private static final int DATABASE_VERSION = 1;

    interface Tables {
        String NODES = "nodes";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
