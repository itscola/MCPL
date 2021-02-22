package top.whitecola.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String timestampToDateStr(Long timestamp) {
        Date date = new Date(timestamp);
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        String format = dateFormat.format(date);
        return format;
    }

}
