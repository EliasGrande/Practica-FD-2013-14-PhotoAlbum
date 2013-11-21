package es.udc.fi.dc.photoalbum.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * {@link LikeAndDislikeDao} Hibernate implementation.
 */
public class LikeAndDislikeDaoImpl extends HibernateDaoSupport
        implements LikeAndDislikeDao {

    public void create(LikeAndDislike likeAndDislike) {
        getHibernateTemplate().save(likeAndDislike);

    }

    public void delete(LikeAndDislike likeAndDislike) {
        getHibernateTemplate().delete(likeAndDislike);

    }

    public LikeAndDislike update(LikeAndDislike likeAndDislike) {
        getHibernateTemplate().update(likeAndDislike);
        return likeAndDislike;
    }

    public LikeAndDislike get(int likeAndDislikeId) {
        return getHibernateTemplate().get(LikeAndDislike.class,
                likeAndDislikeId);
    }

}
