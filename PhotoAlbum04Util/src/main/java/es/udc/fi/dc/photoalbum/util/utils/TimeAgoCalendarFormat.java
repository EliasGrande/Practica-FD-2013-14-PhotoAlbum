package es.udc.fi.dc.photoalbum.util.utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Utility to calculate the time passed since an event
 */
@SuppressWarnings("serial")
public class TimeAgoCalendarFormat implements Serializable {

    /**
     * Static variable to facilitate the use of units of time.
     */
    public static final long ONE_SECOND = 1000L;
    /**
     * Static variable to facilitate the use of units of time.
     */
    public static final long ONE_MINUTE = 60L * ONE_SECOND;
    /**
     * Static variable to facilitate the use of units of time.
     */
    public static final long ONE_HOUR = 60L * ONE_MINUTE;
    /**
     * Static variable to facilitate the use of units of time.
     */
    public static final long ONE_DAY = 24L * ONE_HOUR;
    /**
     * Static variable to facilitate the use of units of time.
     */
    public static final long ONE_WEEK = 7L * ONE_DAY;
    /**
     * Static variable to facilitate the use of units of time.
     * Approximately atomic year.
     */
    public static final long ONE_YEAR = 31557600000L;
    /**
     * Static variable to facilitate the use of units of time.
     */
    public static final long ONE_MONTH = Math.round(ONE_YEAR / 12L);

    /**
     * @return Time stamp now.
     */
    protected long getTimeInMillisNow() {
        return (new Date()).getTime();
    }

    /**
     * HashMap of the localized text, using the resourceKey as key and
     * the value as value.
     */
    private Map<String, String> translates;

    /**
     * Constructor for TimeAgoCalendarFormat.
     * 
     * @param translates
     *            Map of the localized text, using the resourceKey as
     *            key and the value as value.
     */
    public TimeAgoCalendarFormat(Map<String, String> translates) {
        this.translates = translates;
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
        long millisAgo = (getTimeInMillisNow() - calendar
                .getTimeInMillis());
        if (millisAgo < ONE_MINUTE) {
            return timeAgo(millisAgo / ONE_SECOND, "second");
        } else if (millisAgo < ONE_HOUR) {
            return timeAgo(millisAgo / ONE_MINUTE, "minute");
        } else if (millisAgo < ONE_DAY) {
            return timeAgo(millisAgo / ONE_HOUR, "hour");
        } else if (millisAgo < ONE_WEEK) {
            return timeAgo(millisAgo / ONE_DAY, "day");
        } else if (millisAgo < ONE_MONTH) {
            return timeAgo(millisAgo / ONE_WEEK, "week");
        } else if (millisAgo < ONE_YEAR) {
            return timeAgo(millisAgo / ONE_MONTH, "month");
        } else {
            return timeAgo(millisAgo / ONE_YEAR, "year");
        }
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
        return (unitsAgo <= 1) ? translates.get("timeAgo." + unitName
                + ".one") : translates.get(
                "timeAgo." + unitName + ".many").replace("%l",
                String.valueOf(unitsAgo));
    }
}
