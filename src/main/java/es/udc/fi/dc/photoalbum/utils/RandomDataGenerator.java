package es.udc.fi.dc.photoalbum.utils;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;

public class RandomDataGenerator {
	
	private static final String LOREN_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";
	private static final String LOREN_IPSUM_SIMPLE = LOREN_IPSUM.replaceAll("[^a-z]","");
	
	private static int id = 0;

	public static int randomInt(int min, int max) {
		return min + (int)Math.round(Math.random() * ((max - min) + 1));
	}
	
	public static Comment randomComment(File file) {
		Comment comment = new Comment(randomLikeAndDislike(), randomUser(), randomText(), null, file);
		comment.setId(++id);
		return comment;
	}
	
	public static Comment randomComment(Album album) {
		Comment comment = new Comment(randomLikeAndDislike(), randomUser(), randomText(), album, null);
		comment.setId(++id);
		return comment;
	}
	
	public static LikeAndDislike randomLikeAndDislike() {
		int min = 5;
		int max = 500;
		LikeAndDislike likeAndDislike =  new LikeAndDislike();
		likeAndDislike.setLike(randomInt(min, max));
		likeAndDislike.setDislike(randomInt(min, max));
		return likeAndDislike;
	}
	
	public static User randomUser() {
		return new User(randomInt(1,5000),randomEmail(),"");
	}
	
	public static String randomText() {
		int beginIndex = randomInt(0, Math.round(LOREN_IPSUM.length() / 2) - 1);
		int endIndex = randomInt(beginIndex + 5, LOREN_IPSUM.length() - 1);
		return LOREN_IPSUM.substring(beginIndex, endIndex);
	}
	
	public static String randomText(int minLen, int maxLen) {
		return randomText(LOREN_IPSUM, minLen, maxLen);
	}
	
	public static String randomText(String baseText, int minLen, int maxLen) {
		if (maxLen > baseText.length())
			return randomText(baseText + " " + baseText, minLen, maxLen);
		int beginIndex = randomInt(0, baseText.length() - maxLen);
		int endIndex = randomInt(beginIndex + minLen, beginIndex + maxLen);
		return baseText.substring(beginIndex, endIndex);
	}
	
	public static String randomEmail() {
		return randomText(LOREN_IPSUM_SIMPLE,5,15)
				+ "@" + randomText(LOREN_IPSUM_SIMPLE,5,15)
				+ "." + randomText(LOREN_IPSUM_SIMPLE,2,3);
	}
	
	public static ArrayList<Comment> randomComments(File file, int count) {
		ArrayList<Comment> list = new ArrayList<Comment>();
		for (int i=0; i<count; i++)
			list.add(randomComment(file));
		return list;
	}
	
	public static ArrayList<Comment> randomComments(Album album, int count) {
		ArrayList<Comment> list = new ArrayList<Comment>();
		for (int i=0; i<count; i++)
			list.add(randomComment(album));
		return list;
	}
}
