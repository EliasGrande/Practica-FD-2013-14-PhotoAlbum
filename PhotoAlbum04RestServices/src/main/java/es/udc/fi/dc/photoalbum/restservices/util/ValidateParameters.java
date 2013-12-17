package es.udc.fi.dc.photoalbum.restservices.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class ValidateParameters {

    private static String NAME = "name";
    private static String TAG = "tag";
    private static String COMMENT = "comment";

    private static String LIKE = "like";
    private static String DISLIKE = "dislike";
    private static String DATE = "date";

    private final static List<String> listOfOrderBy = Arrays
            .asList(new String[] { LIKE, DISLIKE, DATE });

    public static boolean validateFindByAndKeywords(
            List<String> findBy, String keywords) {
        if ((findBy != null) && (findBy.size() != 0)
                && (keywords != null)) {
            if ((findBy.contains(NAME)) || (findBy.contains(TAG))
                    || (findBy.contains(COMMENT))) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateOrderBy(String orderBy){
        return listOfOrderBy.contains(orderBy);
    }
    
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
        if((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) 
                || (month == 10) || (month == 12)){
            if (day <= 31) {
                return true;
            }
        }else if(month == 2){
            if (year % 4 == 0) {
                if(day <= 29){
                    return true;
                }else{
                    return false;
                }
            }else if(day <= 28){
                return true;
            }
        }else{
            if (day <= 30) {
                return true;
            }
        }
        return false;
    }

    public static Calendar toCalendar(String dateStr){
        StringTokenizer st = new StringTokenizer(dateStr, "/");
        Integer day = Integer.valueOf(st.nextToken());
        Integer month = Integer.valueOf(st.nextToken());
        Integer year = Integer.valueOf(st.nextToken());
        
        Calendar date = Calendar.getInstance();
        date.set(year, month, day);
        
        return date;
    }
    
    public static boolean validateDates(Calendar first, Calendar end){
        if(first.before(end)){
            return true;
        }
        return false;
    }
}
