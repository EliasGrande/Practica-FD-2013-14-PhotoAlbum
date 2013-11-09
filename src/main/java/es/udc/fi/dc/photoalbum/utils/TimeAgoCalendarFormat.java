package es.udc.fi.dc.photoalbum.utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.model.StringResourceModel;

@SuppressWarnings("serial")
public class TimeAgoCalendarFormat implements Serializable {

    private static final long ONE_MINUTE = 60L;
    private static final long ONE_HOUR = 60L * ONE_MINUTE;
    private static final long ONE_DAY = 24L * ONE_HOUR;
    private static final long ONE_WEEK = 7L * ONE_DAY;
    private static final long ONE_YEAR = 31557600L; // atomic year
    private static final long ONE_MONTH = Math.round(ONE_YEAR / 12L); // aprox

    private Component component;

    public TimeAgoCalendarFormat(Component component) {
        this.component = component;
    }

    public String format(Calendar calendar) {
        long secondsAgo = (long) Math
                .floor(((new Date()).getTime() - calendar
                        .getTimeInMillis()) / 1000L);
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

    private String timeAgo(long unitsAgo, String unitName) {
        return (unitsAgo <= 1) ? new StringResourceModel("timeAgo."
                + unitName + ".one", component, null).getString()
                : new StringResourceModel("timeAgo." + unitName
                        + ".many", component, null).getString()
                        .replace("%l", String.valueOf(unitsAgo));
    }
}
