package es.udc.fi.dc.photoalbum.model.spring;

import es.udc.fi.dc.photoalbum.model.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.model.hibernate.User;

/**
 * Interface for the {@link LikeAndDislikeService}
 */
public interface LikeAndDislikeService {

    /**
     * Add new like vote to object likeAndDislike.
     * 
     * @param likeAndDislike
     *            Object that will be update with a like.
     * @param user
     *            User that votes.
     * @return A LikeAndDislike with the like vote.
     */
    LikeAndDislike voteLike(LikeAndDislike likeAndDislike, User user);

    /**
     * Add new dislike vote to object likeAndDislike.
     * 
     * @param likeAndDislike
     *            Object that will be update with a dislike.
     * @param user
     *            User that votes.
     * @return A LikeAndDislike with the dislike vote.
     */
    LikeAndDislike voteDislike(LikeAndDislike likeAndDislike,
            User user);

    /**
     * Remove a vote.
     * 
     * @param likeAndDislike
     *            Object that will be updated.
     * @param user
     *            User that had voted.
     * @return A LikeAndDislike which has the unvote.
     */
    LikeAndDislike unvote(LikeAndDislike likeAndDislike, User user);

    /**
     * Check if the user had voted.
     * 
     * @param likeAndDislike
     *            A {@link LikeAndDislike} object.
     * @param user
     *            {@link User} for the check.
     * 
     * @return True if the user voted, false if the user not voted.
     */
    boolean userHasVoted(LikeAndDislike likeAndDislike, User user);
}
