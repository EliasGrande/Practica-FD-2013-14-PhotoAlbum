package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.Voted;
import es.udc.fi.dc.photoalbum.hibernate.VotedDao;

// Parece tontería poner esto transaccional pero como el dao se usa en un entorno transaccional
// en el LikeAndDislikeService, si no hago este también transaccional hibernate peta diciéndome
// cosas nazis de que no existe una sesion y no puede hacer una no-transaccional blablabla...
@Transactional
public class VotedServiceImpl implements VotedService{
	
	/* VotedDao */
	private VotedDao votedDao;

	public VotedDao getVotedDao() {
		return this.votedDao;
	}

	public void setVotedDao(VotedDao votedDao) {
		this.votedDao = votedDao;
	}

	public Voted getVoted(int likeAndDislikeId, int userId) {
		return votedDao.get(likeAndDislikeId, userId);
	}

	public ArrayList<Voted> getVoted(
			ArrayList<LikeAndDislike> likeAndDislikeList, int userId) {
		return votedDao.getVoted(likeAndDislikeList, userId);
	}
}
