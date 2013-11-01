package es.udc.fi.dc.photoalbum.spring;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;

public interface LikeAndDislikeService {

	/**
	 * Add new like vote to object likeAndDislike, but if the user 
	 * had voted like before, remove the vote.
	 * 
	 * @param likeAndDislike
	 *            Object that will be update with a like.
	 * @param user
	 *            User that votes.
	 */
	void voteLike(LikeAndDislike likeAndDislike, User user);

	/**
	 * Add new dislike vote to object likeAndDislike, but if the user
	 * had voted dislike before, remove the vote.
	 * 
	 * @param likeAndDislike
	 *            Object that will be update with a dislike.
	 * @param user
	 *            User that votes.
	 */
	void voteDislike(LikeAndDislike likeAndDislike, User user);

}
