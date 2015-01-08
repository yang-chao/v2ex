package com.price.v2ex.constants;

/**
 * Created by YC on 14-12-30.
 */
public class Urls {

    public static final String HOST = "http://www.v2ex.com/api/";

    /** 最新主题 */
    public static final String LATEST = HOST + "topics/latest.json";
    /** 热门主题 */
    public static final String HOT = HOST + "topics/hot.json";
    /** 主题详细页 */
    public static final String TOPIC = HOST + "topics/show.json?id=%s";
    /** 主题回复数据 */
    public static final String REPLY = HOST + "replies/show.json?topic_id=%s";



}
