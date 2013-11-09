package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class FileTagDaoImpl extends HibernateDaoSupport implements
        FileTagDao {

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

    @SuppressWarnings("unchecked")
    public ArrayList<FileTag> getTags(int fileId) {
        return (ArrayList<FileTag>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(FileTag.class)
                                .createCriteria("file")
                                .add(Restrictions.eq("id", fileId))
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY));
    }

    public void create(FileTag fileTag) {
        getHibernateTemplate().save(fileTag);

    }

    public void delete(FileTag fileTag) {
        getHibernateTemplate().delete(fileTag);

    }

}
