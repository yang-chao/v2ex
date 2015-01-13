package com.price.v2ex.constants;

/**
 * Created by YC on 14-12-30.
 */
public class Urls {

    public static final String HOST = "http://www.v2ex.com/api/";

    /** 最新话题 */
    public static final String LATEST = HOST + "topics/latest.json";
    /** 热门话题 */
    public static final String HOT = HOST + "topics/hot.json";
    /** 话题详细页 */
    public static final String TOPIC = HOST + "topics/show.json?id=%s";
    /** 话题回复数据 */
    public static final String REPLY = HOST + "replies/show.json?topic_id=%s";
    /** 所有节点 */
    public static final String NODES = HOST + "nodes/all.json";
    /** 节点信息 */
    public static final String NODE = HOST + "nodes/show.json?name=%s";
    /** 节点下的话题 */
    public static final String NODE_TOPICS = HOST + "topics/show.json?node_id=%s";
}
