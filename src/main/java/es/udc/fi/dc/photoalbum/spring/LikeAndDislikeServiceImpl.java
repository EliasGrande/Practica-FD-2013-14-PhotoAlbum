package es.udc.fi.dc.photoalbum.spring;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislikeDao;
import es.udc.fi.dc.photoalbum.hibernate.User;

public class LikeAndDislikeServiceImpl implements LikeAndDislikeService{

	private LikeAndDislikeDao likeAndDislikeDao;

	public LikeAndDislikeDao getLikeAndDislikeDao() {
		return this.likeAndDislikeDao;
	}

	public void setLikeAndDislikeDao(LikeAndDislikeDao likeAndDislikeDao) {
		this.likeAndDislikeDao = likeAndDislikeDao;
	}

	public void voteLike(LikeAndDislike likeAndDislike, User user) {
		// TODO Auto-generated method stub
		
	}
	public void voteDislike(LikeAndDislike likeAndDislike, User user) {
		// TODO Auto-generated method stub
		
	}

}
