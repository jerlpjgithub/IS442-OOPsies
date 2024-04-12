package com.oopsies.server.util;

import java.util.Calendar;
import java.util.Date;

 /**
 * Utility class for datetime-related operations.
 */
public class DateUtil {

    public DateUtil() {}
     /**
     * Checks if the given event date is before today's date.
     *
     * @param eventDate The date of the event.
     * @return true if the event date is before today; otherwise, false.
     */
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

    /**
     * Checks if the given event date is more than six months from today's date.
     *
     * @param eventDate The date of the event.
     * @return true if the event date is more than six months from today; otherwise, false.
     */
    public boolean isMoreThanSixMonths(Date eventDate) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, 6); // Add 6 months to the current date

        Calendar event = Calendar.getInstance();
        event.setTime(eventDate);

        return event.after(calendar);
    }

    /**
     * Checks if the time difference between the given event date and today's date is less than 24 hours.
     *
     * @param eventDate The date of the event.
     * @return true if the time difference is less than 24 hours; otherwise, false.
     */
    public boolean isLessThanTwentyFourHours(Date eventDate) {
        Date currentDate = new Date();
        long diffInMilliseconds = eventDate.getTime() - currentDate.getTime();

        // Convert milliseconds to hours
        long diffInHours = diffInMilliseconds / (60 * 60 * 1000);

        return diffInHours < 24;
    }

    /**
     * Checks if date1 is the same date as date2 and is before date2 in terms of time.
     *
     * @param date1 The first date.
     * @param date2 The second date.
     * @return true if date1 is the same date as date2 and before date2 in time; otherwise, false.
     */
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
