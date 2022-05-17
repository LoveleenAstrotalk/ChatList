package com.astrotalk.sdk.api.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AstroCalenderUtils {

    public static String dateTimeFormat = "dd MMM yy, hh:mm a";
    public static String timeFormat = "hh:mm a";

    private static final SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat(dateTimeFormat);
    private static final SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(timeFormat);

    public static String getTimeFormat(long milliSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleTimeFormat.format(calendar.getTime());
    }

    public static String getDateTimeString(long milliSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateTimeFormat.format(calendar.getTime());
    }
}
