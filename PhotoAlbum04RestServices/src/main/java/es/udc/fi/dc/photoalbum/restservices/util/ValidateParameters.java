package es.udc.fi.dc.photoalbum.restservices.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import es.udc.fi.dc.photoalbum.restservices.servlets.SearchServlet;

/**
 * Utility class to validate parameters for the {@link SearchServlet}.
 */
public final class ValidateParameters {
    /**
     * The criterion of search NAME.
     */
    private static final String NAME = "name";
    /**
     * The criterion of search TAG.
     */
    private static final String TAG = "tag";
    /**
     * The criterion of search COMMENT.
     */
    private static final String COMMENT = "comment";
    /**
     * The criterion of order LIKE.
     */
    private static final String LIKE = "like";
    /**
     * The criterion of order DISLIKE.
     */
    private static final String DISLIKE = "dislike";
    /**
     * The criterion of order DATE.
     */
    private static final String DATE = "date";

    /**
     * The number of the month.
     */
    private static final Integer JANUARY = 1;
    /**
     * The number of the month.
     */
    private static final Integer FEBRUARY = 2;
    /**
     * The number of the month.
     */
    private static final Integer MARCH = 3;
    /**
     * The number of the month.
     */
    private static final Integer MAY = 5;
    /**
     * The number of the month.
     */
    private static final Integer JULY = 7;
    /**
     * The number of the month.
     */
    private static final Integer AUGUST = 8;
    /**
     * The number of the month.
     */
    private static final Integer OCTOBER = 10;
    /**
     * The number of the month.
     */
    private static final Integer DECEMBER = 12;

    /**
     * Private constructor.
     */
    private ValidateParameters() {
    }

    /**
     * List that contains all the possibles criterion for ordering.
     */
    private static final List<String> listOfOrderBy = Arrays
            .asList(new String[] { LIKE, DISLIKE, DATE });

    /**
     * Method that allows to check if a value of findBy and him
     * keywords are correct.
     * 
     * @param findBy
     *            List of values of findBy parameter.
     * @param keywords
     *            The keywords to search.
     * @return boolean Returns true if are correct.
     */
    public static boolean validateFindByAndKeywords(
            List<String> findBy, String keywords) {
        if (((findBy != null) && (findBy.size() != 0) && (keywords != null))
                && ((findBy.contains(NAME)) || (findBy.contains(TAG)) || (findBy
                        .contains(COMMENT)))) {
            return true;

        }
        return false;
    }

    /**
     * Check if orderBy is contained by the {@link #listOfOrderBy}.
     * 
     * @param orderBy
     *            The orderBy to check.
     * @return boolean True if is contained.
     */
    public static boolean validateOrderBy(String orderBy) {
        return listOfOrderBy.contains(orderBy);
    }

    /**
     * Validates if a date in String format is correct.
     * 
     * @param date
     *            The date to check.
     * @return boolean True if is correct.
     */
    public static boolean validate(String date) {
        if (!(Pattern.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d", date))) {
            return false;
        }

        StringTokenizer st = new StringTokenizer(date, "/");
        Integer day = Integer.valueOf(st.nextToken());
        Integer month = Integer.valueOf(st.nextToken());
        Integer year = Integer.valueOf(st.nextToken());

        if (day <= 0) {
            return false;
        }
        if ((month == JANUARY) || (month == MARCH) || (month == MAY)
                || (month == JULY) || (month == AUGUST)
                || (month == OCTOBER) || (month == DECEMBER)) {
            if (day <= 31) {
                return true;
            }
        } else if (month == FEBRUARY) {
            if (year % 4 == 0) {
                if (day <= 29) {
                    return true;
                }
            } else if (day <= 28) {
                return true;
            }
        } else {
            if (day <= 30) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convert a date in String format (dd/mm/yyyy) in a Calendar.
     * 
     * @param dateStr
     *            The date to convert.
     * @return Calendar
     */
    public static Calendar toCalendar(String dateStr) {
        StringTokenizer st = new StringTokenizer(dateStr, "/");
        Integer day = Integer.valueOf(st.nextToken());
        Integer month = Integer.valueOf(st.nextToken());
        Integer year = Integer.valueOf(st.nextToken());

        Calendar date = Calendar.getInstance();
        date.set(year, month, day);

        return date;
    }

    /**
     * Validates if the Calendar begin is earlier than the end
     * Calendar.
     * 
     * @param first
     *            The begin Calendar.
     * @param end
     *            The end Calendar.
     * @return boolean True if are correct.
     */
    public static boolean validateDates(Calendar first, Calendar end) {
        if (first.before(end)) {
            return true;
        }
        return false;
    }
}
