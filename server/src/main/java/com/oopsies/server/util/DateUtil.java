package com.oopsies.server.util;

import java.util.Calendar;
import java.util.Date;

/**
 * DateUtil is a helper class to perform datetime comparisons
 */
public class DateUtil {

    public DateUtil() {}
    public boolean isBeforeToday(Date eventDate) {
        Date currentDate = new Date();
        Calendar eventCalendar = Calendar.getInstance();
        eventCalendar.setTime(eventDate);
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentDate);

        // Compare year, month, and day components
        return eventCalendar.get(Calendar.YEAR) <= currentCalendar.get(Calendar.YEAR)
                && eventCalendar.get(Calendar.MONTH) <= currentCalendar.get(Calendar.MONTH)
                && eventCalendar.get(Calendar.DAY_OF_MONTH) <= currentCalendar.get(Calendar.DAY_OF_MONTH)
                && eventCalendar.get(Calendar.DAY_OF_YEAR) <= currentCalendar.get(Calendar.DAY_OF_YEAR);
    }

    public boolean isMoreThanSixMonths(Date eventDate) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, 6); // Add 6 months to the current date

        Calendar event = Calendar.getInstance();
        event.setTime(eventDate);

        return event.after(calendar);
    }

    public boolean isLessThanTwentyFourHours(Date eventDate) {
        Date currentDate = new Date();
        long diffInMilliseconds = eventDate.getTime() - currentDate.getTime();

        // Convert milliseconds to hours
        long diffInHours = diffInMilliseconds / (60 * 60 * 1000);

        return diffInHours < 24;
    }

    public boolean isSameDateAndBeforeTime(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean sameDate = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        if (sameDate) {
            return cal1.before(cal2);
        }

        return false;
    }
}
