package es.udc.fi.dc.photoalbum.spring;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislikeDao;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.hibernate.Voted;
import es.udc.fi.dc.photoalbum.hibernate.VotedDao;

/**
 * Implementation of the {@link LikeAndDislikeService}
 */
@Transactional
public class LikeAndDislikeServiceImpl implements
        LikeAndDislikeService {
    
    /**
     * Defines a LIKE string variable.
     */
    private static String LIKE = "LIKE";
    
    /**
     * Defines a DISLIKE string variable.
     */
    private static String DISLIKE = "DISLIKE";

    /**
     * @see LikeAndDislikeDao
     */
    private LikeAndDislikeDao likeAndDislikeDao;

    /**
     * Method for get an {@link LikeAndDislikeDao}.
     * 
     * @return An {@link LikeAndDislikeDao}
     */
    public LikeAndDislikeDao getLikeAndDislikeDao() {
        return this.likeAndDislikeDao;
    }

    /**
     * Method that allows to put an {@link LikeAndDislikeDao}.
     * 
     * @param likeAndDislikeDao
     *            LikeAndDislikeDao which will be put.
     */
    public void setLikeAndDislikeDao(
            LikeAndDislikeDao likeAndDislikeDao) {
        this.likeAndDislikeDao = likeAndDislikeDao;
    }

    /**
     * @see VotedDao
     */
    private VotedDao votedDao;

    /**
     * Method for get an {@link VotedDao}.
     * 
     * @return An {@link VotedDao}.
     */
    public VotedDao getVotedDao() {
        return this.votedDao;
    }

    /**
     * Method that allows to put an {@link VotedDao}.
     * 
     * @param votedDao
     *            {@link VotedDao} which will be put.
     */
    public void setVotedDao(VotedDao votedDao) {
        this.votedDao = votedDao;
    }

    /**
     * Add new like vote to object likeAndDislike.
     * 
     * @param likeAndDislike
     *            Object that will be update with a like.
     * @param user
     *            User that votes.
     * @return A LikeAndDislike with the like vote.
     * @see es.udc.fi.dc.photoalbum.spring.LikeAndDislikeService#voteLike(LikeAndDislike,
     *      User)
     */
    public LikeAndDislike voteLike(LikeAndDislike likeAndDislike,
            User user) {
        Voted voted = votedDao.get(likeAndDislike.getId(),
                user.getId());
        LikeAndDislike updatedLikeAndDislike;
        if (voted != null) {
            if (voted.getUserVote().equals(LIKE))
                return voted.getLikeAndDislike();
            voted.setUserVote(LIKE);
            votedDao.update(voted);
            updatedLikeAndDislike = voted.getLikeAndDislike();
            updatedLikeAndDislike.setLike(updatedLikeAndDislike
                    .getLike() + 1);
            updatedLikeAndDislike.setDislike(updatedLikeAndDislike
                    .getDislike() - 1);
        } else {
            updatedLikeAndDislike = likeAndDislikeDao
                    .get(likeAndDislike.getId());
            voted = new Voted(updatedLikeAndDislike, user, LIKE);
            votedDao.create(voted);
            updatedLikeAndDislike.setLike(updatedLikeAndDislike
                    .getLike() + 1);
        }
        return likeAndDislikeDao.update(updatedLikeAndDislike);
    }

    /**
     * Add new dislike vote to object likeAndDislike.
     * 
     * @param likeAndDislike
     *            Object that will be update with a dislike.
     * @param user
     *            User that votes.
     * @return A LikeAndDislike with the dislike vote.
     * @see es.udc.fi.dc.photoalbum.spring.LikeAndDislikeService#voteDislike(LikeAndDislike,
     *      User)
     */
    public LikeAndDislike voteDislike(LikeAndDislike likeAndDislike,
            User user) {
        Voted voted = votedDao.get(likeAndDislike.getId(),
                user.getId());
        LikeAndDislike updatedLikeAndDislike;
        if (voted != null) {
            if (voted.getUserVote().equals(DISLIKE))
                return voted.getLikeAndDislike();
            voted.setUserVote(DISLIKE);
            votedDao.update(voted);
            updatedLikeAndDislike = voted.getLikeAndDislike();
            updatedLikeAndDislike.setLike(updatedLikeAndDislike
                    .getLike() - 1);
            updatedLikeAndDislike.setDislike(updatedLikeAndDislike
                    .getDislike() + 1);
        } else {
            updatedLikeAndDislike = likeAndDislikeDao
                    .get(likeAndDislike.getId());
            voted = new Voted(updatedLikeAndDislike, user, DISLIKE);
            votedDao.create(voted);
            updatedLikeAndDislike.setDislike(updatedLikeAndDislike
                    .getDislike() + 1);
        }
        return likeAndDislikeDao.update(updatedLikeAndDislike);
    }

    /**
     * Remove a vote.
     * 
     * @param likeAndDislike
     *            Object that will be updated.
     * @param user
     *            User that had voted.
     * @return A LikeAndDislike which has the unvote.
     * @see es.udc.fi.dc.photoalbum.spring.LikeAndDislikeService#unvote(LikeAndDislike,
     *      User)
     */
    public LikeAndDislike unvote(LikeAndDislike likeAndDislike,
            User user) {
        Voted voted = votedDao.get(likeAndDislike.getId(),
                user.getId());
        LikeAndDislike updatedLikeAndDislike = likeAndDislikeDao
                .get(likeAndDislike.getId());
        if (voted == null)
            return updatedLikeAndDislike;
        if (voted.getUserVote().equals(LIKE))
            updatedLikeAndDislike.setLike(updatedLikeAndDislike
                    .getLike() - 1);
        else if (voted.getUserVote().equals(DISLIKE))
            updatedLikeAndDislike.setDislike(updatedLikeAndDislike
                    .getDislike() - 1);
        votedDao.delete(voted);
        return likeAndDislikeDao.update(updatedLikeAndDislike);
    }

    /**
     * Check if the user had voted.
     * 
     * @param likeAndDislike
     *            A {@link LikeAndDislike} object.
     * @param user
     *            {@link User} for the check.
     * @return True if the user voted, false if the user not voted.
     * @see es.udc.fi.dc.photoalbum.spring.LikeAndDislikeService#userHasVoted(LikeAndDislike,
     *      User)
     */
    public boolean userHasVoted(LikeAndDislike likeAndDislike,
            User user) {
        Voted voted = votedDao.get(likeAndDislike.getId(),
                user.getId());

        return voted == null ? false : true;
    }

}
