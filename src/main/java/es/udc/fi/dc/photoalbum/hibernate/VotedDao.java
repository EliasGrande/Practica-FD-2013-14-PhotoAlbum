package es.udc.fi.dc.photoalbum.hibernate;

public interface VotedDao extends GenericDao<Voted> {
	
	Voted get(int likeAndDislikeId, int userId);
	
}
