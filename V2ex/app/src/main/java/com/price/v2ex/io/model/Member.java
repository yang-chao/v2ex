package com.price.v2ex.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 14-12-30.
 */
public class Member {

    public String id;
    public String username;
    public String tagline;
    @SerializedName("avatar_mini")
    public String avatarMini;
    @SerializedName("avatar_normal")
    public String avatarNormal;
    @SerializedName("avatar_large")
    public String avatarLarge;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTagline() {
        return tagline;
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
