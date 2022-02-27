package com.sofa.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final String YYYY_MM_DD="YYYY_MM_DD";
    private static final String YYYY_MM_DD_HH_MM="yyyy_MM_dd HH:MM";

    public static String getDate(){
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
        return format.format(new Date());
    }

    public static String getDateTime(){
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
        return format.format(new Date());
    }

    public static Date formatDate(String time){
        SimpleDateFormat format=new SimpleDateFormat();
        try {
            return format.parse(time);
        } catch (ParseException e) {
            return null;
        }
    }

}
