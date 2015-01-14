package com.price.v2ex.util;

import android.content.Context;
import android.text.TextUtils;

import com.price.v2ex.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YC on 14/12/20.
 */
public class TimeUtils {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String timestampToDate(long timestamp) {
        return simpleDateFormat.format(new Date(timestamp * 1000));
    }

    /**
     * 返回 format 格式的时间字符串
     * 时间格式为 yyyy-MM-dd HH:mm:ss
     * yyyy 返回4位年份
     * MM 返回2位月份
     * dd 返回2位日
     * 时间类同
     *
     * @return 相应日期类型的字符串
     */
    public static String getCurrnetDate() {
        return simpleDateFormat.format(new Date()).toString();
    }

    /**
     * 获取时间差
     *
     * @param context
     * @param currentTime
     * @return
     */
    public static String getDateDiffer(Context context, long currentTime) {
        Date date = new Date(currentTime * 1000);
        return getDateDiffer(context, date);
    }

    /**
     * 获取时间差
     *
     * @param date
     * @return
     */
    public static String getDateDiffer(Context context, String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        Date begin = null;
        try {
            begin = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDateDiffer(context, begin);
    }

    private static String getDateDiffer(Context context, Date begin) {
        if (begin == null) {
            return "";
        }
        String date = "";
        try {
            Date end = simpleDateFormat.parse(getCurrnetDate());
            long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒

            long day = between / (24 * 3600);
            long hour = between % (24 * 3600) / 3600;
            long minute = between % 3600 / 60;
            long second = between % 60 / 60;

            if (day >= 1) {
                date = context.getString(R.string.day_ago, day);
            } else if (hour > 0 && day == 0) {
                date = context.getString(R.string.hour_ago, hour);
            } else if (minute > 0 && hour == 0 && day == 0) {
                date = context.getString(R.string.min_ago, minute);
            }/*else if(second>0 && minute==0&&hour==0 && day == 0){
                date = second + "秒前";
            }*/ else {
                date = context.getString(R.string.one_min_ago);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
