package com.oopsies.server.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private final Date currentDate = new Date();
    public DateUtil() {}
    public boolean isBeforeToday(Date eventDate) {
        Calendar eventCalendar = Calendar.getInstance();
        eventCalendar.setTime(eventDate);
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentDate);

        // Compare year, month, and day components
        return eventCalendar.get(Calendar.YEAR) <= currentCalendar.get(Calendar.YEAR)
                && eventCalendar.get(Calendar.MONTH) <= currentCalendar.get(Calendar.MONTH)
                && eventCalendar.get(Calendar.DAY_OF_MONTH) <= currentCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public boolean isMoreThanSixMonths(Date eventDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, 6); // Add 6 months to the current date

        Calendar event = Calendar.getInstance();
        event.setTime(eventDate);

        return event.after(calendar);
    }

    public boolean isLessThanTwentyFourHours(Date eventDate) {
        long diffInMilliseconds = eventDate.getTime() - currentDate.getTime();

        // Convert milliseconds to hours
        long diffInHours = diffInMilliseconds / (60 * 60 * 1000);

        return diffInHours < 24;
    }
}
