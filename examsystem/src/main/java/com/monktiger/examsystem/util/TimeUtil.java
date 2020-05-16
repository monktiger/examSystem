package com.monktiger.examsystem.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static int BetweenDay(Date date){
        long mill1=date.getTime();
        long mill2=System.currentTimeMillis();
        int day = (int)((mill2-mill1)/1000/60/60/24);
        return day;
    }
    public static int BetweenHour(Date date){
        long mill1=date.getTime();
        long mill2=System.currentTimeMillis();
        int hour =(int)((mill2-mill1)/1000/60/60);
        return hour;
    }
    public static long BetweenCent(long mill1){
        long mill2=System.currentTimeMillis();
        long cent =(mill2-mill1)/1000;
        return cent;
    }

    public static Date strToDate(String dateTimeStr) throws ParseException {
        return sDateFormat.parse(dateTimeStr);
    }
}
