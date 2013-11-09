package es.udc.fi.dc.photoalbum.spring;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislikeDao;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.hibernate.Voted;
import es.udc.fi.dc.photoalbum.hibernate.VotedDao;

@Transactional
public class LikeAndDislikeServiceImpl implements
        LikeAndDislikeService {

    /* LikeAndDislikeDao */
    private LikeAndDislikeDao likeAndDislikeDao;

    public LikeAndDislikeDao getLikeAndDislikeDao() {
        return this.likeAndDislikeDao;
    }

    public void setLikeAndDislikeDao(
            LikeAndDislikeDao likeAndDislikeDao) {
        this.likeAndDislikeDao = likeAndDislikeDao;
    }

    /* VotedDao */
    private VotedDao votedDao;

    public VotedDao getVotedDao() {
        return this.votedDao;
    }

    public void setVotedDao(VotedDao votedDao) {
        this.votedDao = votedDao;
    }

    /* IMPLEMENTATION */

    public LikeAndDislike voteLike(LikeAndDislike likeAndDislike,
            User user) {
        Voted voted = votedDao.get(likeAndDislike.getId(),
                user.getId());
        LikeAndDislike updatedLikeAndDislike;
        if (voted != null) {
            if (voted.getUserVote().equals("LIKE"))
                return voted.getLikeAndDislike();
            voted.setUserVote("LIKE");
            votedDao.update(voted);
            updatedLikeAndDislike = voted.getLikeAndDislike();
            updatedLikeAndDislike.setLike(updatedLikeAndDislike
                    .getLike() + 1);
            updatedLikeAndDislike.setDislike(updatedLikeAndDislike
                    .getDislike() - 1);
        } else {
            updatedLikeAndDislike = likeAndDislikeDao
                    .get(likeAndDislike.getId());
            voted = new Voted(updatedLikeAndDislike, user, "LIKE");
            votedDao.create(voted);
            updatedLikeAndDislike.setLike(updatedLikeAndDislike
                    .getLike() + 1);
        }
        return likeAndDislikeDao.update(updatedLikeAndDislike);
    }

    public LikeAndDislike voteDislike(LikeAndDislike likeAndDislike,
            User user) {
        Voted voted = votedDao.get(likeAndDislike.getId(),
                user.getId());
        LikeAndDislike updatedLikeAndDislike;
        if (voted != null) {
            if (voted.getUserVote().equals("DISLIKE"))
                return voted.getLikeAndDislike();
            voted.setUserVote("DISLIKE");
            votedDao.update(voted);
            updatedLikeAndDislike = voted.getLikeAndDislike();
            updatedLikeAndDislike.setLike(updatedLikeAndDislike
                    .getLike() - 1);
            updatedLikeAndDislike.setDislike(updatedLikeAndDislike
                    .getDislike() + 1);
        } else {
            updatedLikeAndDislike = likeAndDislikeDao
                    .get(likeAndDislike.getId());
            voted = new Voted(updatedLikeAndDislike, user, "DISLIKE");
            votedDao.create(voted);
            updatedLikeAndDislike.setDislike(updatedLikeAndDislike
                    .getDislike() + 1);
        }
        return likeAndDislikeDao.update(updatedLikeAndDislike);
    }

    public LikeAndDislike unvote(LikeAndDislike likeAndDislike,
            User user) {
        Voted voted = votedDao.get(likeAndDislike.getId(),
                user.getId());
        LikeAndDislike updatedLikeAndDislike = likeAndDislikeDao
                .get(likeAndDislike.getId());
        if (voted == null)
            return updatedLikeAndDislike;
        if (voted.getUserVote().equals("LIKE"))
            updatedLikeAndDislike.setLike(updatedLikeAndDislike
                    .getLike() - 1);
        else if (voted.getUserVote().equals("DISLIKE"))
            updatedLikeAndDislike.setDislike(updatedLikeAndDislike
                    .getDislike() - 1);
        votedDao.delete(voted);
        return likeAndDislikeDao.update(updatedLikeAndDislike);
    }

    public boolean userHasVoted(LikeAndDislike likeAndDislike,
            User user) {
        Voted voted = votedDao.get(likeAndDislike.getId(),
                user.getId());

        return voted == null ? false : true;
    }

}
