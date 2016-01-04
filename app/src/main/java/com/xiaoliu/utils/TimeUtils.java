package com.xiaoliu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/30.
 */
public class TimeUtils {
    public static String getImgName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd_HH:mm:ss");
        Date date = new Date();
        String time = format.format(date)+".jpg";
        //System.out.println(time);
        return time;
    }
    public static String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        String time = format.format(date);
        System.out.println(time);
        return time;
    }
}
