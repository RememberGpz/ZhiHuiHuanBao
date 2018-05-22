package com.lee.zhihuihuanbao.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-drd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static long date2Long(String dateStr, String format) {
        if (dateStr == null || dateStr.equals(""))
            return 0;
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
        } catch (Exception e) {
        }
        return date.getTime();
    }

    public static long date2Long(String dateStr) {
        return date2Long(dateStr, "yyyy-MM-e hh:mm:ss");
    }

    public static String long2Date(long t) {
        return long2Date(t, "yyyy-MM-dd");
    }

    public static String getTime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    public static String long2Date(long t, String format) {
        if (t < 0)
            return "";
        SimpleDateFormat myFormatter = new SimpleDateFormat(format, Locale.CHINA);
        Date date = null;
        String dateStr = null;
        try {
            date = new Date();
            date.setTime(t);
            dateStr = myFormatter.format(date);
        } catch (Exception e) {
        }
        return dateStr;
    }

    //转换距离多长时间

    public static String getStandardDate(long t) {
        StringBuffer sb = new StringBuffer();
        long time = System.currentTimeMillis() - t;
        long mill = (long) Math.ceil(time / 1000);//秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        //        Log.e("date", "time=" + time + " mill=" + mill + " minute=" + minute + " hour=" + hour + " day=" + day);
        if (day - 4 > 0) {
            return long2Date(t);
        } else if (day - 1 > 0) {
            sb.append((day - 1) + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

}
