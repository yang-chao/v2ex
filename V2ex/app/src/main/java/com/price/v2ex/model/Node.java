package com.price.v2ex.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 14-12-30.
 */
public class Node {
    private String id;
    private String name;
    private String title;
    @SerializedName("title_alternative")
    private String titleAlternative;
    private String url;
    private int topics;
    private String header;
    private String footer;
    private long created;
    @SerializedName("avatar_mini")
    private String avatarMini;
    @SerializedName("avatar_normal")
    private String avatarNormal;
    @SerializedName("avatar_large")
    private String avatarLarge;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public int getTopics() {
        return topics;
    }

    public String getAvatarMini() {
        return avatarMini;
    }

    public String getAvatarNormal() {
        return avatarNormal;
    }

    public String getAvatarLarge() {
        return avatarLarge;
    }

    public String getTitleAlternative() {
        return titleAlternative;
    }

    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }

    public long getCreated() {
        return created;
    }
}
