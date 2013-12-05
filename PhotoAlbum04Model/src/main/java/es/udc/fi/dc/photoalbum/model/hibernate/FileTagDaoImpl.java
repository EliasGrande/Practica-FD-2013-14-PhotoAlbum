package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * {@link FileTagDao} Hibernate implementation.
 */
public class FileTagDaoImpl extends HibernateDaoSupport implements
        FileTagDao {

    @Override
    @SuppressWarnings("unchecked")
    public FileTag getTag(int fileId, String tag) {
        ArrayList<FileTag> list = (ArrayList<FileTag>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(FileTag.class)
                                .createAlias("file", "fi")
                                .add(Restrictions.eq("tag", tag))
                                .add(Restrictions.eq("fi.id", fileId))
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
    public List<FileTag> getTags(int fileId) {
        return (ArrayList<FileTag>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(FileTag.class)
                                .createCriteria("file")
                                .add(Restrictions.eq("id", fileId))
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY));
    }

    @Override
    public void create(FileTag fileTag) {
        getHibernateTemplate().save(fileTag);

    }

    @Override
    public void delete(FileTag fileTag) {
        getHibernateTemplate().delete(fileTag);

    }
}
