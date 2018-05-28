package com.guming.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtil {

    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dateFormatDetail = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final String  TIME_ZONE_TIME_FORMAT="yyyy-MM-dd XX";


    /**
     * 统一时间格式转换器
     * @param date      时间
     * @param str       时间格式format
     * @return
     */
    public static String format(Date date,String str){
        SimpleDateFormat timeFormat = new SimpleDateFormat(str);
        return timeFormat.format(date);
    }

    // 格式化日期与时间
    public static String formatDatetime(Date date) {
        return datetimeFormat.format(date);
    }

    // 格式化日期
    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String formateDateDetail(Date date) {
        return dateFormatDetail.format(date);
    }

    // 格式化字符串日期与时间
    public static Date parseDatetime(String date) {
        try {
            return datetimeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 格式化字符串日期
    public static Date parseDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到系统时间的前三个月
     *
     * @return String eg：2015-09-24 currentDate:2015-12-24
     */
    public static String getBeforeMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //获取当前时间的前6个月
        calendar.add(Calendar.MONTH, -3);
        date = calendar.getTime();
        String beforeDate = formatDate(date);
        return beforeDate;
    }

    /**
     * 获取某年某月的第一天(本月的第一天)
     *
     * @throws
     * @Title:getFisrtDayOfMonth
     * @Description:
     * @param:@return
     * @return:String eg：2015-12-01 currentDate:2015-12-24
     */
    public static String getFisrtDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //获取某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        String firstDayOfMonth = formatDate(calendar.getTime());
        return firstDayOfMonth;
    }

    /**
     * 得到系统时间的前一天
     *
     * @param date eg：2015-12-23 currentDate:2015-12-24
     * @return
     */
    public static Date getBeforeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取当前时间的前后时间
     *
     * @param date
     * @param day  間隔時間
     * @return
     */
    public static Date getWarpDay(Date date, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 获取上个月的第一天
     *
     * @throws
     * @Title:getFisrtDayOfMonth
     * @Description:
     * @param:@return
     * @return:String eg：2015-11-01 00:00:00 currentDate:2015-12-24
     */
    public static String getFisrtDayOfPreMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();
        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = formatDate(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();
        return day_first;
    }

    /**
     * 获取上个月的最后一天
     *
     * @throws
     * @Title:getFisrtDayOfMonth
     * @Description:
     * @param:@return
     * @return:String eg：2015-11-30 23:59:59 currentDate:2015-12-24
     */
    public static String getLstDayOfPreMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = formatDate(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();
        return day_last;
    }

    /**
     * 得到系统时间的前两个月的字符串
     *
     * @return eg：2015-10
     */
    public static String getBeforeTwoMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //获取当前时间的前6个月
        calendar.add(Calendar.MONTH, -2);
        String beforeDate = formatDate(calendar.getTime());
        beforeDate = beforeDate.substring(0, 7);
        return beforeDate;
    }

    /**
     * 日期转星期Int
     *
     * @param datetime
     * @return
     */
    public static Integer dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        int[] weekDays = {6, 0, 1, 2, 3, 4, 5};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 日期转星期String
     *
     * @param dateStr
     * @return
     */
    public static String stringToWeek(String dateStr) {
        Date date = DateUtil.parseDate(dateStr);
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }


    /**
     * 日期转月份的第几天
     *
     * @param datetime
     * @return
     */
    public static Integer dateToDay(Date datetime) {
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        cal.setTime(datetime);
        return cal.get(Calendar.DAY_OF_MONTH); // 指示一个月中的某天。
    }

    /**
     * 当前月份第几周
     *
     * @param datetime
     * @return
     */
    public static Integer weekOfMonth(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date datet = null;
        try {
            datet = f.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        cal.setTime(datet);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 当前月第一天
     *
     * @param datetime
     * @return
     */
    public static String getFirstDay(String datetime) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date datet = null;
        datet = f.parse(datetime);
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.setTime(datet);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = f.format(c.getTime());
        return first;
    }

    /**
     * 当前月最后一天
     *
     * @param datetime
     * @return
     */
    public static String getLastDay(String datetime) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date datet = null;
        datet = f.parse(datetime);
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.setTime(datet);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = f.format(ca.getTime());
        return last;
    }

    /**
     * 获取当天初始时间
     * @return
     */
    public static Date getCurrentStartDay(){
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取当天结束时间
     * @return
     */
    public static Date getCurrentEndDay(){
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

}
