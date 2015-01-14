package com.price.v2ex.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 15-1-6.
 */
public class Reply {

    private String id;
    private int thanks;
    private String content;
    @SerializedName("content_rendered")
    private String contentRendered;
    private long created;
    @SerializedName("last_modified")
    private long lastModified;
    private Member member;

    public String getId() {
        return id;
    }

    public int getThanks() {
        return thanks;
    }

    public String getContent() {
        return content;
    }

    public String getContentRendered() {
        return contentRendered;
    }

    public long getCreated() {
        return created;
    }

    public long getLastModified() {
        return lastModified;
    }

    public Member getMember() {
        return member;
    }
}
