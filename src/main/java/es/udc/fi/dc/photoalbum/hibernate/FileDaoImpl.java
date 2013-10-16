package es.udc.fi.dc.photoalbum.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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
		if ((list.size() == 1)) {
			File file = list.get(0);
			// verify ownership
			if (file.getAlbum().getUser().getId() == userId)
				return file;
		}
		return null;
	}

	public void delete(File file) {
		getHibernateTemplate().delete(file);
	}

	public void changeAlbum(File file, Album album) {
		getHibernateTemplate().update(file);
		file.setAlbum(album);
	}

	private Criteria getAlbumOwnFilesCriteria(int albumId) {
		return getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createCriteria(File.class).createCriteria("album")
				.add(Restrictions.eq("id", albumId)).addOrder(Order.asc("id"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<File> getAlbumFilesOwn(int albumId) {
		return (ArrayList<File>) getAlbumOwnFilesCriteria(albumId).list();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<File> getAlbumFilesOwnPaging(int albumId, int first,
			int count) {
		return (ArrayList<File>) getAlbumOwnFilesCriteria(albumId)
				.setFirstResult(first).setMaxResults(count).list();
	}

	private Criteria getAlbumSharedFilesCriteria(int albumId, int userId) {

		// main query, search by album
		Criteria criteria = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(File.class, "fi")
				.createAlias("fi.album", "fi_al")
				.add(Restrictions.eq("fi_al.id", albumId));

		// OR1: get public files
		Criterion publicFileCr = Restrictions.eq("fi.privacyLevel",
				PrivacyLevel.PUBLIC);

		// OR2: get files shared to userId
		Criterion sharedFileCr = Subqueries.propertyIn(
				"fi.id",
				DetachedCriteria.forClass(FileShareInformation.class, "fis")
						.createAlias("fis.user", "fis_us")
						.createAlias("fis.file", "fis_fi")
						.add(Restrictions.eq("fis_us.id", userId))
						.setProjection(Projections.property("fis_fi.id"))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY));

		// OR3: get inherit files if the album (is public OR is shared to
		// userId)
		Criterion publicAlbumCr = Restrictions.eq("fi_al.privacyLevel",
				PrivacyLevel.PUBLIC);
		Criterion sharedAlbumCr = Subqueries.propertyIn(
				"fi_al.id",
				DetachedCriteria.forClass(AlbumShareInformation.class, "ais")
						.createAlias("ais.user", "ais_us")
						.createAlias("ais.album", "ais_al")
						.add(Restrictions.eq("ais_us.id", userId))
						.add(Restrictions.eq("ais_al.id", albumId))
						.setProjection(Projections.property("ais_al.id")));
		Criterion inheritFileCr = Restrictions
				.conjunction()
				.add(Restrictions.eq("fi.privacyLevel",
						PrivacyLevel.INHERIT_FROM_ALBUM))
				.add(Restrictions.disjunction()
						.add(publicAlbumCr)
						.add(sharedAlbumCr));

		// OR: join OR1, OR2, OR3
		Disjunction or = Restrictions.disjunction();
		or.add(publicFileCr);
		or.add(sharedFileCr);
		or.add(inheritFileCr);

		// add restrictions to the main query
		criteria.add(or).addOrder(Order.asc("fi.id"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<File> getAlbumFilesShared(int albumId, int userId) {
		return (ArrayList<File>) getAlbumSharedFilesCriteria(albumId, userId)
				.list();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<File> getAlbumFilesSharedPaging(int albumId, int userId,
			int first, int count) {
		return (ArrayList<File>) getAlbumSharedFilesCriteria(albumId, userId)
				.setFirstResult(first).setMaxResults(count).list();
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

	public void changePrivacyLevel(File file, String privacyLevel) {
		getHibernateTemplate().update(file);
		file.setPrivacyLevel(privacyLevel);
	}
}
