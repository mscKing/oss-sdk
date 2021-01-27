package com.wecarry.oss.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author miaosc
 * @date 2020/9/12 0012
 */
public class DateUtil {

    /**
     * 格式化日期为格林威治格式
     *
     * @param date
     * @return
     */
    public static String formatGMTString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }
}
