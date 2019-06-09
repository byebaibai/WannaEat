package com.homework.getfood.util;

import java.util.Calendar;

/**
 * 获得当前时间
 */
public class TimeFetcher {
    /**
     * 获得当前时间
     * @return 返回当前时间
     */
    public static String getTime(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String s = year + "-" + month + "-" + day + " " + hour + ":" + minute;
        return s;
    }
}
