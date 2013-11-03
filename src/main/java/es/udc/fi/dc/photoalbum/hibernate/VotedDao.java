package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

public interface VotedDao extends GenericDao<Voted> {
	
	void update(Voted voted);
	
	Voted get(int likeAndDislikeId, int userId);
	
	ArrayList<Voted> getVoted(ArrayList<LikeAndDislike> likeAndDislikeList, int userId);
}
