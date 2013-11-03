package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class VotedDaoImpl extends HibernateDaoSupport implements VotedDao {

	public void create(Voted voted) {
		getHibernateTemplate().save(voted);
	}

	public void delete(Voted voted) {
		getHibernateTemplate().delete(voted);
	}

	public void update(Voted voted){
		getHibernateTemplate().update(voted);
	}
	
	public Voted get(int likeAndDislikeId, int userId) {
		String hql = "SELECT v "
				+ "FROM Voted v "
				+ "WHERE v.likeAndDislike.id = :likeAndDislikeId "
				+ "AND v.user.id = :userId";
		
		return (Voted) getHibernateTemplate()
		.getSessionFactory()
		.getCurrentSession()
		.createQuery(hql)
		.setParameter("likeAndDislikeId", likeAndDislikeId)
		.setParameter("userId", userId)
		.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Voted> getVoted(
			ArrayList<LikeAndDislike> likeAndDislikeList, int userId) {
		String queryString = "Select v "
				+ "FROM Voted v "
				+ "WHERE v.user.id = :userId "
				+ "AND v.likeAndDislike.id IN (:lalIds) "
				+ "ORDER BY v.likeAndDislike.id DESC";
		
		return (ArrayList<Voted>) getHibernateTemplate()
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(queryString)
				.setParameter("userId", userId)
				.setParameterList("lalIds", likeAndDislikeList)
				.list();
	}

}
