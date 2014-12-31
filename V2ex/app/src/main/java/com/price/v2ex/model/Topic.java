package com.price.v2ex.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 14-12-30.
 */
public class Topic {
    private String id;
    private String title;
    private String content;
    @SerializedName("content_rendered")
    private String contentRendered;
    private int replies;
    private String created;
    @SerializedName("last_modified")
    private String lastModified;
    @SerializedName("last_touched")
    private String lastTouched;
    private Member member;
    private Node node;

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

    public String getCreated() {
        return created;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getLastTouched() {
        return lastTouched;
    }

    public Member getMember() {
        return member;
    }

    public Node getNode() {
        return node;
    }
}
