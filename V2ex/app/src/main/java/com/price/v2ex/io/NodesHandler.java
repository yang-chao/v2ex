package com.price.v2ex.io;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.price.v2ex.provider.V2exContract;
import com.price.v2ex.io.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YC on 15-1-14.
 */
public class NodesHandler extends JSONHandler {

    private List<Node> mNodes = new ArrayList<Node>();

    public NodesHandler(Context context) {
        super(context);
    }

    @Override
    public void makeContentProviderOperations(ArrayList<ContentProviderOperation> list) {
        final Uri uri = V2exContract.Nodes.CONTENT_URI;
        list.add(ContentProviderOperation.newDelete(uri).build());
        for (Node node : mNodes) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(uri);
            builder.withValue(V2exContract.Nodes.NODE_ID, node.getId());
            builder.withValue(V2exContract.Nodes.NODE_NAME, node.getName());
            builder.withValue(V2exContract.Nodes.NODE_TITLE, node.getTitle());
            builder.withValue(V2exContract.Nodes.NODE_TITLE_ALTERNATIVE, node.getTitleAlternative());
            builder.withValue(V2exContract.Nodes.NODE_HEADER, node.getHeader());
            builder.withValue(V2exContract.Nodes.NODE_FOOTER, node.getFooter());
            builder.withValue(V2exContract.Nodes.NODE_CREATED, node.getCreated());
            builder.withValue(V2exContract.Nodes.NODE_AVATAR_MINI, node.getAvatarMini());
            builder.withValue(V2exContract.Nodes.NODE_AVATAR_NORMAL, node.getAvatarNormal());
            builder.withValue(V2exContract.Nodes.NODE_AVATAR_LARGE, node.getAvatarLarge());
            builder.withValue(V2exContract.Nodes.NODE_TOPICS, node.getTopics());
            builder.withValue(V2exContract.Nodes.NODE_STARS, node.getStars());
            builder.withValue(V2exContract.Nodes.NODE_STATUS, node.getStatus());
            builder.withValue(V2exContract.Nodes.NODE_URL, node.getUrl());
            list.add(builder.build());
        }
    }

    @Override
    public void process(JsonElement element) {
        for (Node node : new Gson().fromJson(element, Node[].class)) {
            mNodes.add(node);
        }
    }

}
