package com.price.v2ex.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.price.v2ex.adapter.AdapterHandler;
import com.price.v2ex.adapter.NodesAdapter;
import com.price.v2ex.io.model.Node;
import com.price.v2ex.provider.V2exContract;

import java.util.ArrayList;

/**
 * Created by YC on 15-1-15.
 */
public class NodeModel {

    public static void updateNodes(final Context context, final RecyclerView.Adapter adapter) {
        new AsyncTask<Void, Void, ArrayList<Node>>() {
            @Override
            protected ArrayList<Node> doInBackground(Void... params) {
                Uri uri = V2exContract.Nodes.CONTENT_URI;
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    ArrayList<Node> nodes = new ArrayList<Node>();
                    while (cursor.moveToNext()) {
                        Node node = new Node();
                        node.id = cursor.getString(cursor.getColumnIndex(V2exContract.Nodes.NODE_ID));
                        node.name = cursor.getString(cursor.getColumnIndex(V2exContract.Nodes.NODE_NAME));
                        node.title = cursor.getString(cursor.getColumnIndex(V2exContract.Nodes.NODE_TITLE));
                        node.titleAlternative = cursor.getString(cursor.getColumnIndex(V2exContract.Nodes.NODE_TITLE_ALTERNATIVE));
                        node.url = cursor.getString(cursor.getColumnIndex(V2exContract.Nodes.NODE_URL));
                        node.avatarLarge = cursor.getString(cursor.getColumnIndex(V2exContract.Nodes.NODE_AVATAR_LARGE));
                        node.avatarNormal = cursor.getString(cursor.getColumnIndex(V2exContract.Nodes.NODE_AVATAR_NORMAL));
                        node.avatarMini = cursor.getString(cursor.getColumnIndex(V2exContract.Nodes.NODE_AVATAR_MINI));
                        node.created = Long.valueOf(cursor.getString(cursor.getColumnIndex(V2exContract.Nodes.NODE_CREATED)));
                        node.footer = cursor.getString(cursor.getColumnIndex(V2exContract.Nodes.NODE_FOOTER));
                        node.header = cursor.getString(cursor.getColumnIndex(V2exContract.Nodes.NODE_HEADER));
                        node.stars = cursor.getInt(cursor.getColumnIndex(V2exContract.Nodes.NODE_STARS));
                        node.status = cursor.getInt(cursor.getColumnIndex(V2exContract.Nodes.NODE_STATUS));
                        node.topics = cursor.getInt(cursor.getColumnIndex(V2exContract.Nodes.NODE_TOPICS));
                        nodes.add(node);
                    }
                    cursor.close();
                    return nodes;
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Node> nodes) {
                AdapterHandler.notifyDataSetChanged(adapter, nodes);
            }
        }.execute((Void) null);
    }
}
