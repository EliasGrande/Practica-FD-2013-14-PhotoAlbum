package es.udc.fi.dc.photoalbum.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;

/**
 * Utility to generate random data
 */
public class RandomDataGenerator {

    /**
     * Useful static variable with the typical text LOREN_IPSUM.
     */
    private static final String LOREN_IPSUM = "Lorem ipsum dolor sit amet,"
            + " consectetur adipisicing elit, sed do eiusmod tempor incididunt "
            + "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis "
            + "nostrud exercitation ullamco laboris nisi ut aliquip ex ea "
            + "commodo consequat. Duis aute irure dolor in reprehenderit in "
            + "voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
            + "Excepteur sint occaecat cupidatat non proident, sunt in culpa "
            + "qui officia deserunt mollit anim id est laborum. Sed ut "
            + "perspiciatis unde omnis iste natus error sit voluptatem "
            + "accusantium doloremque laudantium, totam rem aperiam, eaque "
            + "ipsa quae ab illo inventore veritatis et quasi architecto "
            + "beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem "
            + "quia voluptas sit aspernatur aut odit aut fugit, sed quia "
            + "consequuntur magni dolores eos qui ratione voluptatem sequi "
            + "nesciunt. Neque porro quisquam est, qui dolorem ipsum quia "
            + "dolor sit amet, consectetur, adipisci velit, sed quia non "
            + "numquam eius modi tempora incidunt ut labore et dolore "
            + "magnam aliquam quaerat voluptatem. Ut enim ad minima "
            + "veniam, quis nostrum exercitationem ullam corporis suscipit "
            + "laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis "
            + "autem vel eum iure reprehenderit qui in ea voluptate velit "
            + "esse quam nihil molestiae consequatur, vel illum qui "
            + "dolorem eum fugiat quo voluptas nulla pariatur?";
    /**
     * Defines a simple version of LOREN_IPSUM.
     */
    private static final String LOREN_IPSUM_SIMPLE = LOREN_IPSUM
            .replaceAll("[^a-z]", "");
    /**
     * Defines a time of beginning.
     */
    private static final long BEGIN_TIME = (new Date()).getTime();
    /**
     * Defines a time of finish.
     */
    private static final long END_TIME = BEGIN_TIME
            - (1000L * 3600L * 24L * 365L * 2L);
    /**
     * Defines an id which value is 0.
     */
    private static int id = 0;

    /**
     * Generates a random integer between two specified numbers.
     * 
     * @param min
     *            Minimum value that will have the random number.
     * @param max
     *            Maximum value that will have the random number.
     * @return A random integer number
     */
    public static int randomInt(int min, int max) {
        return min
                + (int) Math.round(Math.random()
                        * (double) ((max - min) + 1));
    }

    /**
     * Generates a random long between two specified numbers.
     * 
     * @param min
     *            Minimum value that will have the random number
     * @param max
     *            Maximum value that will have the random number.
     * @return A random long number.
     */
    public static long randomLong(long min, long max) {
        return min
                + Math.round(Math.random()
                        * (double) ((max - min) + 1L));
    }

    /**
     * Generates a random date.
     * 
     * @return A random date.
     */
    public static Calendar randomCalendar() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        date.setTime(randomLong(BEGIN_TIME, END_TIME));
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Generates a random file comment.
     * 
     * @param file
     *            {@link File} that will be inserted the random
     *            comment
     * 
     * @return A random file {@link Comment} that has been generated.
     */
    public static Comment randomComment(File file) {
        Comment comment = new Comment(randomLikeAndDislike(),
                randomUser(), randomText(), null, file);
        comment.setId(++id);
        comment.setDate(randomCalendar());
        return comment;
    }

    /**
     * Generates a random album comment.
     * 
     * @param album
     *            {@link Album} that will be inserted the random
     *            comment
     * @return A random album {@link Comment} that has been generated.
     */
    public static Comment randomComment(Album album) {
        Comment comment = new Comment(randomLikeAndDislike(),
                randomUser(), randomText(), album, null);
        comment.setId(++id);
        comment.setDate(randomCalendar());
        return comment;
    }

    /**
     * Generates a random {@link LikeAndDislike}
     * 
     * @return The {@link LikeAndDislike} that has been generated.
     */
    public static LikeAndDislike randomLikeAndDislike() {
        int min = 5;
        int max = 500;
        LikeAndDislike likeAndDislike = new LikeAndDislike();
        likeAndDislike.setLike(randomInt(min, max));
        likeAndDislike.setDislike(randomInt(min, max));
        return likeAndDislike;
    }

    /**
     * Generates a random {@link User}.
     * 
     * @return The {@link User} that has been generated.
     */
    public static User randomUser() {
        return new User(randomInt(1, 5000), randomEmail(), "");
    }

    /**
     * Generates a random text using the {@code LOREN_IPSUM} variable.
     * 
     * @return The random text that has been generated.
     */
    public static String randomText() {
        int beginIndex = randomInt(0, LOREN_IPSUM.length() / 2 - 1);
        int endIndex = randomInt(beginIndex + 5,
                LOREN_IPSUM.length() - 1);
        return LOREN_IPSUM.substring(beginIndex, endIndex);
    }

    /**
     * Generates a random text using the {@code LOREN_IPSUM} variable
     * between a minimum and a maximum length.
     * 
     * @param minLen
     *            The minimum length of the random text that will be
     *            generated.
     * @param maxLen
     *            The maximum length of the random text that will be
     *            generated.
     * @return The random text that has been generated.
     */
    public static String randomText(int minLen, int maxLen) {
        return randomText(LOREN_IPSUM, minLen, maxLen);
    }

    /**
     * Generates a random text using the {@code LOREN_IPSUM} variable
     * between a minimum and a maximum length employing a base of text
     * for the text that will be generated.
     * 
     * @param baseText
     *            The base of text.
     * @param minLen
     *            The minimum length of the random text that will be
     *            generated.
     * @param maxLen
     *            The maximum length of the random text that will be
     *            generated.
     * @return The random text that has been generated.
     */
    public static String randomText(String baseText, int minLen,
            int maxLen) {
        if (maxLen > baseText.length())
            return randomText(baseText + " " + baseText, minLen,
                    maxLen);
        int beginIndex = randomInt(0, baseText.length() - maxLen);
        int endIndex = randomInt(beginIndex + minLen, beginIndex
                + maxLen);
        return baseText.substring(beginIndex, endIndex);
    }

    /**
     * Generates a random email.
     * 
     * @return An email that has been generated.
     */
    public static String randomEmail() {
        return randomText(LOREN_IPSUM_SIMPLE, 5, 15) + "@"
                + randomText(LOREN_IPSUM_SIMPLE, 5, 15) + "."
                + randomText(LOREN_IPSUM_SIMPLE, 2, 3);
    }

    /**
     * Generates a list of random file comment.
     * 
     * @param file
     *            {@link File} that will be inserted the random
     *            comment.
     * @param count
     *            The number of comments that will be generated.
     * @return A list with the file comments generated.
     */
    public static List<Comment> randomComments(File file, int count) {
        ArrayList<Comment> list = new ArrayList<Comment>();
        for (int i = 0; i < count; i++)
            list.add(randomComment(file));
        return list;
    }

    /**
     * Generates a list of random album comment.
     * 
     * @param album
     *            {@link Album} that will be inserted the random
     *            comment
     * @param count
     *            The number of comments that will be generated.
     * @return A list with the album comments generated.
     */
    public static List<Comment> randomComments(Album album, int count) {
        ArrayList<Comment> list = new ArrayList<Comment>();
        for (int i = 0; i < count; i++)
            list.add(randomComment(album));
        return list;
    }
}
