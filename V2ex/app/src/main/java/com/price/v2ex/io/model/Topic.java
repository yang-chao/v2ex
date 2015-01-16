package com.price.v2ex.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 14-12-30.
 */
public class Topic {
    public String id;
    public String title;
    public String content;
    @SerializedName("content_rendered")
    public String contentRendered;
    public int replies;
    public long created;
    @SerializedName("last_modified")
    public long lastModified;
    @SerializedName("last_touched")
    public long lastTouched;
    public Member member;
    public Node node;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getContentRendered() {
        return contentRendered;
    }

    public int getReplies() {
        return replies;
    }

    public long getCreated() {
        return created;
    }

    public long getLastModified() {
        return lastModified;
    }

    public long getLastTouched() {
        return lastTouched;
    }

    public Member getMember() {
        return member;
    }

    public Node getNode() {
        return node;
    }
}
