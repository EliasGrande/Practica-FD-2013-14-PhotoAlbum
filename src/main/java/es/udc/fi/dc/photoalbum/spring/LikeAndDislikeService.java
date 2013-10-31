package es.udc.fi.dc.photoalbum.spring;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;

public interface LikeAndDislikeService {
	
	void voteLike(LikeAndDislike likeAndDislike, User user);
	
	void voteDislike(LikeAndDislike likeAndDislike, User user);
}
