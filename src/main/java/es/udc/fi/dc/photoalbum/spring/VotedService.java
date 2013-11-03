package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.hibernate.Voted;

public interface VotedService {
	
	Voted getVoted(LikeAndDislike likeAndDislike, User user);
	
	ArrayList<Voted> getVoted(ArrayList<LikeAndDislike> likeAndDislikeList, User user);
}
