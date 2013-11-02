package es.udc.fi.dc.photoalbum.hibernate;

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

}
