package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.List;

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

    @SuppressWarnings("unchecked")
    @Override
    public List<LikeAndDislike> getLikesAndDislikes(boolean like) {
        String query = "SELECT l FROM LikeAndDislike l ";
        
        if (like) {
            query += "ORDER BY l.like DESC ";
        } else {
            query += "ORDER BY l.dislike DESC ";
        }
        
        return (List<LikeAndDislike>) getHibernateTemplate()
                .getSessionFactory().getCurrentSession()
                .createQuery(query).list();
    }
}
