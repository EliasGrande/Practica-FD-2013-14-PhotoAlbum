package es.udc.fi.dc.photoalbum.utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.model.StringResourceModel;

/**
 * Utility to calculate the time passed since an event
 */
@SuppressWarnings("serial")
public class TimeAgoCalendarFormat implements Serializable {

    /**
     * Static variable to facilitate the use of units of time.
     */
    private static final long ONE_MINUTE = 60L;
    /**
     * Static variable to facilitate the use of units of time.
     */
    private static final long ONE_HOUR = 60L * ONE_MINUTE;
    /**
     * Static variable to facilitate the use of units of time.
     */
    private static final long ONE_DAY = 24L * ONE_HOUR;
    /**
     * Static variable to facilitate the use of units of time.
     */
    private static final long ONE_WEEK = 7L * ONE_DAY;
    /**
     * Static variable to facilitate the use of units of time.
     */
    private static final long ONE_YEAR = 31557600L; // atomic year
    /**
     * Static variable to facilitate the use of units of time.
     */
    private static final long ONE_MONTH = Math.round(ONE_YEAR / 12L); // aprox

    /**
     * @see Component
     */
    private Component component;

    /**
     * Constructor for TimeAgoCalendarFormat.
     * 
     * @param component
     *            Component of the calendar.
     */
    public TimeAgoCalendarFormat(Component component) {
        this.component = component;
    }

    /**
     * Returns the format of a calendar. Depending of secondsAgo
     * variable returns a format or another
     * 
     * @param calendar
     *            Calendar for make its format.
     * @return The format which has the calendar.
     */
    public String format(Calendar calendar) {
        long secondsAgo = ((new Date()).getTime() - calendar
                .getTimeInMillis()) / 1000L;
        if (secondsAgo < ONE_MINUTE)
            return timeAgo(secondsAgo, "second");
        else if (secondsAgo < ONE_HOUR)
            return timeAgo(secondsAgo / ONE_MINUTE, "minute");
        else if (secondsAgo < ONE_DAY)
            return timeAgo(secondsAgo / ONE_HOUR, "hour");
        else if (secondsAgo < ONE_WEEK)
            return timeAgo(secondsAgo / ONE_DAY, "day");
        else if (secondsAgo < ONE_MONTH)
            return timeAgo(secondsAgo / ONE_WEEK, "week");
        else if (secondsAgo < ONE_YEAR)
            return timeAgo(secondsAgo / ONE_MONTH, "month");
        else
            return timeAgo(secondsAgo / ONE_YEAR, "year");
    }

    /**
     * Give how much time since an event happened.
     * 
     * @param unitsAgo
     *            An unit of time
     * @param unitName
     *            The name of the unit of time.
     * @return Time in string format of the time since an event
     *         happened.
     */
    private String timeAgo(long unitsAgo, String unitName) {
        return (unitsAgo <= 1) ? new StringResourceModel("timeAgo."
                + unitName + ".one", component, null).getString()
                : new StringResourceModel("timeAgo." + unitName
                        + ".many", component, null).getString()
                        .replace("%l", String.valueOf(unitsAgo));
    }
}
