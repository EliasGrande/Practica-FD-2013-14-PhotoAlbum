package es.udc.fi.dc.photoalbum.hibernate;

public interface LikeAndDislikeDao extends GenericDao<LikeAndDislike> {
	
	LikeAndDislike update(LikeAndDislike likeAndDislike);
}
