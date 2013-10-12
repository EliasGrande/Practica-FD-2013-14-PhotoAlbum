package es.udc.fi.dc.photoalbum.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;

public class FileShareInformationDaoImpl extends HibernateDaoSupport implements
		FileShareInformationDao {

	public void create(FileShareInformation shareInformation) {
		getHibernateTemplate().save(shareInformation);
	}

	public void delete(FileShareInformation shareInformation) {
		getHibernateTemplate().delete(shareInformation);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<FileShareInformation> getFileShares(int fileId) {
		return (ArrayList<FileShareInformation>) getHibernateTemplate()
				.findByCriteria(
						DetachedCriteria
								.forClass(FileShareInformation.class)
								.createCriteria("file")
								.add(Restrictions.eq("id", fileId))
								.setResultTransformer(
										Criteria.DISTINCT_ROOT_ENTITY));
	}
}
