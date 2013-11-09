package es.udc.fi.dc.photoalbum.spring;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;

public interface LikeAndDislikeService {

    /**
     * Add new like vote to object likeAndDislike.
     * 
     * @param likeAndDislike
     *            Object that will be update with a like.
     * @param user
     *            User that votes.
     */
    LikeAndDislike voteLike(LikeAndDislike likeAndDislike, User user);

    /**
     * Add new dislike vote to object likeAndDislike.
     * 
     * @param likeAndDislike
     *            Object that will be update with a dislike.
     * @param user
     *            User that votes.
     */
    LikeAndDislike voteDislike(LikeAndDislike likeAndDislike,
            User user);

    /**
     * Remove a vote.
     * 
     * @param likeAndDislike
     *            Object that wil be updated.
     * @param user
     *            User that had voted.
     */
    LikeAndDislike unvote(LikeAndDislike likeAndDislike, User user);

    /**
     * Check if the user had voted.
     * 
     * @param likeAndDislike
     * @param user
     * @return True if the user voted, false if the user not voted.
     */
    boolean userHasVoted(LikeAndDislike likeAndDislike, User user);
}
