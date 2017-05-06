package com.abel.hwes.util;

/**
 * Finished the string validate
 * @author Abel.li
 *
 */
public class StringUtil {

    /**
     *
     * @param str
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.equals("")) {
            return true;
        }
        return false;
    }

    public static String transSpecialChar(String str) {
        if (!isEmpty(str)) {
            str = str.replace("\\", "\\\\");
            str = str.replace("_", "\\_");
            str = str.replace("%", "\\%");
            str = str.replace("'", "\\'");
            return str;
        }
        return "";
    }

    public static String moveSpecialChar(String str) {
        if (!isEmpty(str)) {
            str = str.replaceAll("\"", "");
            str = str.replaceAll("\\s", "");
            return str;
        }
        return "";
    }

    public static String trimString(String str)
    {
        if (!isEmpty(str)) {
            int start = 0,end = str.length()-1;
            while(start<=end && str.charAt(start)==' ')
                start++;
            while(start<=end && str.charAt(end)==' ')
                end--;
            return str.substring(start,end+1);
        }
        return "";
    }
}
