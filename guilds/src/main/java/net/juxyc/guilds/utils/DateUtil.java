package net.juxyc.guilds.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
  public static String getCurrentHour() {
    Calendar cal = Calendar.getInstance();
    String HOUR_FORMAT = "HH:mm";
    SimpleDateFormat sdfHour = new SimpleDateFormat(HOUR_FORMAT);
    return sdfHour.format(cal.getTime());
  }
  
  public static boolean isHourInInterval(String target, String start, String end) {
    return (target.compareTo(start) >= 0 && target
      .compareTo(end) <= 0);
  }
  
  public static boolean isNowInInterval(String start, String end) {
    return 
      isHourInInterval(getCurrentHour(), start, end);
  }
}
