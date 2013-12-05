package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * {@link AlbumShareInformationDao} Hibernate implementation.
 */
public class AlbumShareInformationDaoImpl extends HibernateDaoSupport
        implements AlbumShareInformationDao {

    @Override
    public void create(AlbumShareInformation shareInformation) {
        getHibernateTemplate().save(shareInformation);
    }

    @Override
    public void delete(AlbumShareInformation shareInformation) {
        getHibernateTemplate().delete(shareInformation);
    }

    @Override
    public AlbumShareInformation getShare(String albumName,
            int userSharedToId, String userSharedEmail) {
        @SuppressWarnings("unchecked")
        ArrayList<AlbumShareInformation> list = (ArrayList<AlbumShareInformation>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(AlbumShareInformation.class)
                                .createAlias("album", "al")
                                .createAlias("al.user", "alus")
                                .createAlias("user", "us")
                                .add(Restrictions.eq("al.name",
                                        albumName))
                                .add(Restrictions.eq("us.id",
                                        userSharedToId))
                                .add(Restrictions.eq("alus.email",
                                        userSharedEmail))
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY));
        if (list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public AlbumShareInformation getShare(int albumId, int userId) {
        @SuppressWarnings("unchecked")
        ArrayList<AlbumShareInformation> list = (ArrayList<AlbumShareInformation>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(AlbumShareInformation.class)
                                .createAlias("album", "al")
                                .createAlias("user", "us")
                                .add(Restrictions
                                        .eq("al.id", albumId))
                                .add(Restrictions.eq("us.id", userId))
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY));
        if (list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AlbumShareInformation> getAlbumShares(int albumId) {
        return (ArrayList<AlbumShareInformation>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(AlbumShareInformation.class)
                                .createCriteria("album")
                                .add(Restrictions.eq("id", albumId))
                                .addOrder(Order.asc("id"))
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY));
    }
}
