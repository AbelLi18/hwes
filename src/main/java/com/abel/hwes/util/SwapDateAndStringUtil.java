package com.abel.hwes.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class SwapDateAndStringUtil {

    private static Logger log = Logger.getLogger(SwapDateAndStringUtil.class);

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     * @exception String
     *                format fail, the method will return a new Date(Thu Jan 01
     *                08:00:00 CST 1970).
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            log.error(e.getMessage());
            date = new Date(0);
        }
        return date;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     * @exception String
     *                format fail, the method will return a new Date() 2006-08-01.
     */
    public static Date StrToStartDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            log.error(e.getMessage());
            date = StrToEndDate("2006-08-01");
        }
        return date;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     * @exception String
     *                format fail, the method will return a new Date() 2006-08-31.
     */
    public static Date StrToEndDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            log.error(e.getMessage());
            date = StrToEndDate("2006-08-31");
        }
        return date;
    }
}
