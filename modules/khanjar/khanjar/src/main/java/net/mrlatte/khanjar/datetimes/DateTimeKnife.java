package net.mrlatte.khanjar.datetimes;

import android.content.Context;
import android.text.format.DateUtils;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jongha on 8/25/15.
 */
public class DateTimeKnife {
    public static String getString(Context context, Date date) {
        if (date != null) {
            return DateUtils.getRelativeTimeSpanString(context, new DateTime(date).getMillis()).toString();
        }

        return "";
    }

    public static String getMonthDayString(Date date) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        return dateFormat.format(date);
    }

    public static String getDateWithTime(Date date) {
        if (date != null) {
            return DateFormat.getDateInstance().format(date) + " " +
                    DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        }

        return "";
    }

    public static String getHoursWithElapsedTime(Context context, Date date) {
        return DateUtils.getRelativeDateTimeString(context, new DateTime(date).getMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.HOUR_IN_MILLIS, DateUtils.FORMAT_12HOUR).toString();
    }

    public static String getHoursString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(date);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Get duration string from seconds
     *
     * @param duration duration seconds
     * @return
     */
    public static String getDuration(long duration) {
        if (duration / 3600 > 0) {
            return String.format("%d:%02d:%02d", duration / 3600, (duration % 3600) / 60, (duration % 60));
        } else {
            return String.format("%02d:%02d", (duration % 3600) / 60, (duration % 60));
        }
    }

    public static Date fromString(String dateString, String dateFormat, String timezone) {
        Date date = null;

        DateFormat format = new SimpleDateFormat(dateFormat);
        format.setTimeZone(TimeZone.getTimeZone(timezone));

        try {
            date = format.parse(dateString);
        } catch (ParseException ignored) {
        }

        return date;
    }
}