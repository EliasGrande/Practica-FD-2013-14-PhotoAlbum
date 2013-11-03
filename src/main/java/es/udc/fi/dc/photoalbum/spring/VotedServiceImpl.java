package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.hibernate.Voted;
import es.udc.fi.dc.photoalbum.hibernate.VotedDao;

public class VotedServiceImpl implements VotedService{
	
	/* VotedDao */
	private VotedDao votedDao;

	public VotedDao getVotedDao() {
		return this.votedDao;
	}

	public void setVotedDao(VotedDao votedDao) {
		this.votedDao = votedDao;
	}

	public Voted getVoted(LikeAndDislike likeAndDislike, User user) {
		return votedDao.get(likeAndDislike.getId(), user.getId());
	}

	public ArrayList<Voted> getVoted(
			ArrayList<LikeAndDislike> likeAndDislikeList, User user) {
		return votedDao.getVoted(likeAndDislikeList, user.getId());
	}
}
