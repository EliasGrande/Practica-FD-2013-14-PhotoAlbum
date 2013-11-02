package es.udc.fi.dc.photoalbum.spring;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislikeDao;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.hibernate.Voted;
import es.udc.fi.dc.photoalbum.hibernate.VotedDao;

@Transactional
public class LikeAndDislikeServiceImpl implements LikeAndDislikeService {

	/* LikeAndDislikeDao */
	private LikeAndDislikeDao likeAndDislikeDao;

	public LikeAndDislikeDao getLikeAndDislikeDao() {
		return this.likeAndDislikeDao;
	}

	public void setLikeAndDislikeDao(LikeAndDislikeDao likeAndDislikeDao) {
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

	public void voteLike(LikeAndDislike likeAndDislike, User user) {
		Voted voted = votedDao.get(likeAndDislike.getId(), user.getId());
		if(voted != null){
			voted.setUserVote("LIKE");
			votedDao.update(voted);
			likeAndDislike.setLike(likeAndDislike.getLike() + 1);
			likeAndDislike.setDislike(likeAndDislike.getDislike() - 1);
			likeAndDislikeDao.update(likeAndDislike);
		}else{
			voted = new Voted(likeAndDislike, user,"LIKE");
			votedDao.create(voted);
			likeAndDislike.setLike(likeAndDislike.getLike() + 1);
			likeAndDislikeDao.update(likeAndDislike);
		}
	}

	public void voteDislike(LikeAndDislike likeAndDislike, User user) {
		Voted voted = votedDao.get(likeAndDislike.getId(), user.getId());
		if(voted != null){
			voted.setUserVote("DISLIKE");
			votedDao.update(voted);
			likeAndDislike.setLike(likeAndDislike.getLike() - 1);
			likeAndDislike.setDislike(likeAndDislike.getDislike() + 1);
			likeAndDislikeDao.update(likeAndDislike);
		}else{
			voted = new Voted(likeAndDislike, user,"DISLIKE");
			votedDao.create(voted);
			likeAndDislike.setDislike(likeAndDislike.getDislike() + 1);
			likeAndDislikeDao.update(likeAndDislike);
		}
	}


}
