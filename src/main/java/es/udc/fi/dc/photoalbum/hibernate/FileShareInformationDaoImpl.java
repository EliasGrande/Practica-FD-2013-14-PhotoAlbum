package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class FileShareInformationDaoImpl extends HibernateDaoSupport
        implements FileShareInformationDao {

    public void create(FileShareInformation shareInformation) {
        getHibernateTemplate().save(shareInformation);
    }

    public void delete(FileShareInformation shareInformation) {
        getHibernateTemplate().delete(shareInformation);
    }

    @SuppressWarnings("unchecked")
    public List<FileShareInformation> getFileShares(int fileId) {
        return (ArrayList<FileShareInformation>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(FileShareInformation.class)
                                .createCriteria("file")
                                .add(Restrictions.eq("id", fileId))
                                .addOrder(Order.asc("id"))
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY));
    }

    @SuppressWarnings("unchecked")
    public FileShareInformation getShare(int fileId, int userId) {
        ArrayList<FileShareInformation> list = 
                (ArrayList<FileShareInformation>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(FileShareInformation.class)
                                .createAlias("file", "fi")
                                .createAlias("user", "us")
                                .add(Restrictions.eq("fi.id", fileId))
                                .add(Restrictions.eq("us.id", userId))
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY));
        if (list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void deleteShares(int fileId) {
        String hql = "delete from FileShareInformation where file.id = :fileId";
        getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql)
                .setParameter("fileId", fileId).executeUpdate();
    }
}
