package com.price.v2ex.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 14-12-30.
 */
public class Node {
    private String id;
    private String name;
    private String title;
    private String url;
    private String topics;
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

    public String getTopics() {
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
}
