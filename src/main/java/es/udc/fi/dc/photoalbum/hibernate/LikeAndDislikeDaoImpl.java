package es.udc.fi.dc.photoalbum.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * {@link LikeAndDislikeDao} Hibernate implementation.
 */
public class LikeAndDislikeDaoImpl extends HibernateDaoSupport
        implements LikeAndDislikeDao {

    @Override
    public void create(LikeAndDislike likeAndDislike) {
        getHibernateTemplate().save(likeAndDislike);

    }

    @Override
    public void delete(LikeAndDislike likeAndDislike) {
        getHibernateTemplate().delete(likeAndDislike);

    }

    @Override
    public LikeAndDislike update(LikeAndDislike likeAndDislike) {
        getHibernateTemplate().update(likeAndDislike);
        return likeAndDislike;
    }

    @Override
    public LikeAndDislike get(int likeAndDislikeId) {
        return getHibernateTemplate().get(LikeAndDislike.class,
                likeAndDislikeId);
    }
}
