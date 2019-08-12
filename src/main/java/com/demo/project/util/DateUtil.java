package com.demo.project.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat formatter1 = new SimpleDateFormat( "yyyy-MM-dd");


//    public static Date changeDate(Date da) {
//        Date date = new Date();
//        String time= formatter.format(date);
//        ParsePosition pos=new ParsePosition(0);
//        Date d = formatter.parse(time,pos);
//    }

}