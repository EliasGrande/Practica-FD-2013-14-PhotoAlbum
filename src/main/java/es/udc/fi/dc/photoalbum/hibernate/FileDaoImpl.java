package es.udc.fi.dc.photoalbum.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

import java.util.ArrayList;

public class FileDaoImpl extends HibernateDaoSupport implements FileDao {

	public void create(File file) {
		getHibernateTemplate().save(file);
	}

	public File getById(Integer id) {
		return getHibernateTemplate().get(File.class, id);
	}

	public File getFileOwn(int id, String name, int userId) {
		@SuppressWarnings("unchecked")
		ArrayList<File> list = (ArrayList<File>) getHibernateTemplate()
				.findByCriteria(
						DetachedCriteria
								.forClass(File.class)
								.add(Restrictions.eq("id", id))
								.createCriteria("album")
								.add(Restrictions.eq("name", name))
								.setResultTransformer(
										Criteria.DISTINCT_ROOT_ENTITY));
		if ((list.size() == 1)
				&& ((list.get(0)).getAlbum().getUser().getId() == userId)) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private File getFileSharedOrPublic(int id, String name, int userId,
			String minPrivacyLevel) {

		DetachedCriteria subquery = DetachedCriteria
				.forClass(ShareInformation.class, "shareinformation")
				.createAlias("shareinformation.album", "sinf_al")
				.createAlias("shareinformation.user", "sinf_us")
				// file is in the album
				.add(Restrictions.eqProperty("sinf_al.id", "file_al.id"))
				// album name is correct
				.add(Restrictions.eq("sinf_al.name", name))
				// album shareable or public
				.add(PrivacyLevel.minPrivacyLevelCriterion(
						"sinf_al.privacyLevel", minPrivacyLevel))
				// album shared with the indicated user
				.add(Restrictions.eq("sinf_us.id", userId))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				// return only album id
				.setProjection(Property.forName("sinf_al.id"));
		
		ArrayList<File> list = (ArrayList<File>) getHibernateTemplate()
				.findByCriteria(
						DetachedCriteria
								.forClass(File.class, "file")
								.createAlias("file.album", "file_al")
								// file id exist
								.add(Restrictions.eq("file.id", id))
								// file shareable or public
								.add(PrivacyLevel
										.minPrivacyLevelCriterion(minPrivacyLevel))
								// album subquery check
								.add(Subqueries.exists(subquery))
								.setResultTransformer(
										Criteria.DISTINCT_ROOT_ENTITY));
		
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public File getFileShared(int id, String name, int userId) {
		return getFileSharedOrPublic(id,name,userId,PrivacyLevel.SHAREABLE);
	}

	public void delete(File file) {
		getHibernateTemplate().delete(file);
	}

	public void changeAlbum(File file, Album album) {
		getHibernateTemplate().update(file);
		file.setAlbum(album);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<File> getAlbumFiles(int albumId) {
		return (ArrayList<File>) getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(File.class).createCriteria("album")
						.add(Restrictions.eq("id", albumId))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY));
	}

	@SuppressWarnings("unchecked")
	public ArrayList<File> getAlbumFiles(int albumId, String minPrivacyLevel) {
		return (ArrayList<File>) getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(File.class)
						.createAlias("album", "al")
						.add(Restrictions.eq("al.id", albumId))
						.add(PrivacyLevel.minPrivacyLevelCriterion(minPrivacyLevel))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY));
	}

	@SuppressWarnings("unchecked")
	public ArrayList<File> getAlbumFilesPaging(int albumId, int first, int count) {
		return (ArrayList<File>) getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(File.class)
				.createCriteria("album").add(Restrictions.eq("id", albumId))
				.addOrder(Order.asc("id")).setFirstResult(first)
				.setMaxResults(count)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<File> getAlbumFilesPaging(int albumId, int first, int count, String minPrivacyLevel) {
		return (ArrayList<File>) getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(File.class)
				.createAlias("album", "al")
				.add(Restrictions.eq("al.id", albumId))
				.add(PrivacyLevel.minPrivacyLevelCriterion(minPrivacyLevel))
				.addOrder(Order.asc("id")).setFirstResult(first)
				.setMaxResults(count)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	public Long getCountAlbumFiles(int albumId) {
		ArrayList<Long> list = (ArrayList<Long>) getHibernateTemplate()
				.findByCriteria(
						DetachedCriteria
								.forClass(File.class)
								.createCriteria("album")
								.add(Restrictions.eq("id", albumId))
								.setProjection(Projections.rowCount())
								.setResultTransformer(
										Criteria.DISTINCT_ROOT_ENTITY));
		return list.get(0);
	}

	public void changePrivacy(File file, String privacyLevel) {
		getHibernateTemplate().update(file);
		file.setPrivacyLevel(privacyLevel);
	}
}
