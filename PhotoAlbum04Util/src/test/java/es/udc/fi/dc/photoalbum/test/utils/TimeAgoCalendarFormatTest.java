package es.udc.fi.dc.photoalbum.test.utils;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.util.utils.TimeAgoCalendarFormat;

/**
 * The class <code>TimeAgoCalendarFormatTest</code> contains tests for
 * the class {@link <code>TimeAgoCalendarFormat</code>}.
 */
public class TimeAgoCalendarFormatTest {

    private static final long TIME_IN_MILLIS_NOW = (new Date(
            TimeAgoCalendarFormat.ONE_YEAR * 5)).getTime();
    private static final HashMap<String, String> TRANSLATES;

    static {
        TRANSLATES = new HashMap<String, String>();
        TRANSLATES.put("timeAgo.second.one", "1 second ago");
        TRANSLATES.put("timeAgo.second.many", "%l seconds ago");
        TRANSLATES.put("timeAgo.minute.one", "1 minute ago");
        TRANSLATES.put("timeAgo.minute.many", "%l minutes ago");
        TRANSLATES.put("timeAgo.hour.one", "1 hour ago");
        TRANSLATES.put("timeAgo.hour.many", "%l hours ago");
        TRANSLATES.put("timeAgo.day.one", "1 day ago");
        TRANSLATES.put("timeAgo.day.many", "%l days ago");
        TRANSLATES.put("timeAgo.week.one", "1 week ago");
        TRANSLATES.put("timeAgo.week.many", "%l weeks ago");
        TRANSLATES.put("timeAgo.month.one", "1 month ago");
        TRANSLATES.put("timeAgo.month.many", "%l months ago");
        TRANSLATES.put("timeAgo.year.one", "1 year ago");
        TRANSLATES.put("timeAgo.year.many", "%l years ago");
    }

    private TimeAgoCalendarFormat calendarFormat;

    @SuppressWarnings("serial")
    @Before
    public void setUp() {
        calendarFormat = new TimeAgoCalendarFormat(TRANSLATES) {
            @Override
            protected long getTimeInMillisNow() {
                return TIME_IN_MILLIS_NOW;
            }
        };
    }

    /**
     * Run the String format(Calendar) method test for seconds
     */
    @Test
    public void testFormatSeconds() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("1 second ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_SECOND * 5L);
        assertEquals("5 seconds ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_MINUTE
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("1 minute ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_MINUTE * 5L
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("5 minutes ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_HOUR
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("1 hour ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_HOUR * 5L
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("5 hours ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_DAY
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("1 day ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_DAY * 5L
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("5 days ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_WEEK
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("1 week ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_WEEK * 3L
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("3 weeks ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_MONTH
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("1 month ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_MONTH * 5L
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("5 months ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_YEAR
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("1 year ago", calendarFormat.format(calendar));

        calendar.setTimeInMillis(TIME_IN_MILLIS_NOW
                - TimeAgoCalendarFormat.ONE_YEAR * 5L
                - TimeAgoCalendarFormat.ONE_SECOND);
        assertEquals("5 years ago", calendarFormat.format(calendar));
    }
}