package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * {@link VotedDao} Hibernate implementation.
 */
public class VotedDaoImpl extends HibernateDaoSupport implements
        VotedDao {

    @Override
    public void create(Voted voted) {
        getHibernateTemplate().save(voted);
    }

    @Override
    public void delete(Voted voted) {
        getHibernateTemplate().delete(voted);
    }

    @Override
    public void update(Voted voted) {
        getHibernateTemplate().update(voted);
    }

    @Override
    public Voted get(int likeAndDislikeId, int userId) {
        String hql = "SELECT v " + "FROM Voted v "
                + "WHERE v.likeAndDislike.id = :likeAndDislikeId "
                + "AND v.user.id = :userId";

        return (Voted) getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql)
                .setParameter("likeAndDislikeId", likeAndDislikeId)
                .setParameter("userId", userId).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Voted> getVoted(
            List<Integer> likeAndDislikeIdList, int userId) {
        if (likeAndDislikeIdList.size() == 0) {
            return new ArrayList<Voted>();
        }
        String queryString = "Select v " + "FROM Voted v "
                + "WHERE v.user.id = :userId "
                + "AND v.likeAndDislike.id IN (:lalIds) "
                + "ORDER BY v.id DESC";

        return (ArrayList<Voted>) getHibernateTemplate()
                .getSessionFactory().getCurrentSession()
                .createQuery(queryString)
                .setParameter("userId", userId)
                .setParameterList("lalIds", likeAndDislikeIdList)
                .list();
    }
}
