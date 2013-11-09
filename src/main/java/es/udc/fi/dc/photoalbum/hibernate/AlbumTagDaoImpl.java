package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AlbumTagDaoImpl extends HibernateDaoSupport implements
        AlbumTagDao {

    @SuppressWarnings("unchecked")
    public AlbumTag getTag(int albumId, String tag) {
        ArrayList<AlbumTag> list = (ArrayList<AlbumTag>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(AlbumTag.class)
                                .createAlias("album", "al")
                                .add(Restrictions.eq("tag", tag))
                                .add(Restrictions
                                        .eq("al.id", albumId))
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY));

        if (list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<AlbumTag> getTags(int albumId) {
        return (ArrayList<AlbumTag>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(AlbumTag.class)
                                .createCriteria("album")
                                .add(Restrictions.eq("id", albumId))
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY));
    }

    public void create(AlbumTag albumTag) {
        getHibernateTemplate().save(albumTag);

    }

    public void delete(AlbumTag albumTag) {
        getHibernateTemplate().delete(albumTag);

    }

}
