package es.udc.fi.dc.photoalbum.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class LikeAndDislikeDaoImpl extends HibernateDaoSupport implements LikeAndDislikeDao {

	@Override
	public void create(LikeAndDislike likeAndDislike) {
		getHibernateTemplate().save(likeAndDislike);
		
	}

	@Override
	public void delete(LikeAndDislike t) {
		// TODO Auto-generated method stub
		
	}
	
}
