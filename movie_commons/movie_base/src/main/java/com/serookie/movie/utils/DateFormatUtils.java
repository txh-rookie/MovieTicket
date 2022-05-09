package com.serookie.movie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/4
 */
public class DateFormatUtils {
    public static String format(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = format.format(date);
        return format1;
    }
    public static Date dateUtils(String date){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date parse=null;
        try {
            parse = format.parse(date);
        } catch ( ParseException e ) {
            e.printStackTrace();
        }
        return parse;
    }
}
