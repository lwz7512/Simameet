package com.runbytech.simameet.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liwenzhi on 14-10-14.
 */
public class TimeUtil {

    private static final DateFormat MON_DAY_FORMATTER = new SimpleDateFormat("MM-dd");
    private static final DateFormat HOUR_MIN_FORMATTER = new SimpleDateFormat("HH:mm");
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("MM-dd HH:mm");

    public static Date timestampToDate(int second){
        long dtl = Long.valueOf(second + "000") ;
        return new Date(dtl);//millisecond
    }

    public static String secondToMonDay(int second) {
        Date dt = timestampToDate(second);
        return MON_DAY_FORMATTER.format(dt);
    }

    public static String secondToHourMinute(int second) {
        Date dt = timestampToDate(second);
        return HOUR_MIN_FORMATTER.format(dt);
    }

    public static String secondToDate(int second) {
        Date dt = timestampToDate(second);
        return DATE_FORMATTER.format(dt);
    }

}
