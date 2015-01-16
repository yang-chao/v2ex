package com.price.v2ex.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 14-12-30.
 */
public class Node {

    public static enum NodeStatus {
        Sub, NonSub
    }

    public String id;
    public String name;
    public String title;
    @SerializedName("title_alternative")
    public String titleAlternative;
    public String url;
    public int topics;
    public String header;
    public String footer;
    public long created;
    @SerializedName("avatar_mini")
    public String avatarMini;
    @SerializedName("avatar_normal")
    public String avatarNormal;
    @SerializedName("avatar_large")
    public String avatarLarge;
    public int status;
    public int stars;

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

    public int getStars() {
        return stars;
    }

    public int getStatus() {
        return status;
    }

    public void setSubscribeStatus(NodeStatus status) {
        if (status == NodeStatus.Sub) {
            this.status = 1;
        } else {
            this.status = 0;
        }
    }

    public NodeStatus getSubscribeStatus() {
        if (status == 1) {
            return NodeStatus.Sub;
        } else {
            return NodeStatus.NonSub;
        }
    }
}
