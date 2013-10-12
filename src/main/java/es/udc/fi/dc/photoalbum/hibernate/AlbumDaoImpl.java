package es.udc.fi.dc.photoalbum.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

import java.util.ArrayList;

public class AlbumDaoImpl extends HibernateDaoSupport implements AlbumDao {

	public void create(Album album) {
		getHibernateTemplate().save(album);
	}

	public void delete(Album album) {
		getHibernateTemplate().delete(album);
	}

	public Album getAlbum(String name, int userId) {
		@SuppressWarnings("unchecked")
		ArrayList<Album> list = (ArrayList<Album>) getHibernateTemplate()
				.findByCriteria(
						DetachedCriteria
								.forClass(Album.class)
								.add(Restrictions.eq("name", name))
								.createCriteria("user")
								.add(Restrictions.eq("id", userId))
								.setResultTransformer(
										Criteria.DISTINCT_ROOT_ENTITY));
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public void rename(Album album, String newName) {
		album.setName(newName);
		getHibernateTemplate().update(album);
	}

	public Album getById(Integer id) {
		return getHibernateTemplate().get(Album.class, id);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Album> getAlbums(Integer id) {
		return (ArrayList<Album>) getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(Album.class).createCriteria("user")
						.add(Restrictions.eq("id", id))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY));
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Album> getPublicAlbums() {
		
		DetachedCriteria dc = DetachedCriteria.forClass(Album.class, "album")
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		// public albums with inherit files
		DetachedCriteria inheritFilesDc = DetachedCriteria.forClass(File.class, "ih_file")
				.createAlias("ih_file.album", "ih_file_al")
				.add(Restrictions.eq("ih_file.privacyLevel", PrivacyLevel.INHERIT_FROM_ALBUM))
				.setProjection(Projections.property("ih_file_al.id"));
		Criterion inheritFilesCr = Restrictions
				.conjunction()
				.add(Restrictions.eq("album.privacyLevel", PrivacyLevel.PUBLIC))
				.add(Subqueries.propertyIn("album.id", inheritFilesDc));
		
		// albums with public files
		DetachedCriteria publicFilesDc = DetachedCriteria.forClass(File.class, "pu_file")
				.createAlias("pu_file.album", "pu_file_al")
				.add(Restrictions.eq("pu_file.privacyLevel", PrivacyLevel.PUBLIC))
				.setProjection(Projections.property("pu_file_al.id"));
		Criterion publicFilesCr = Subqueries.propertyIn("album.id",publicFilesDc);
		
		return (ArrayList<Album>) getHibernateTemplate().findByCriteria(
				dc.add(Restrictions.disjunction().add(inheritFilesCr).add(publicFilesCr)));
	}
	
	public void changePrivacyLevel(Album album, String privacyLevel) {
		album.setPrivacyLevel(privacyLevel);
		getHibernateTemplate().update(album);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Album> getAlbumsSharedWith(Integer userId, String ownerEmail) {

//		-- THE ACTUAL SQL QUERY --
//
//		-- example data
//		SET @USER_ID=2;
//		SET @OWNER_EMAIL='test@test.test';
//
//		-- the query
//		SELECT * FROM ALBUM
//		WHERE user_id IN (
//			SELECT id FROM usuario
//			WHERE email LIKE @OWNER_EMAIL
//		)
//		AND (
//			id IN (
//				SELECT album_id FROM album_share_information
//				WHERE user_id = @USER_ID
//				AND album_id IN (
//					SELECT album_id FROM archivo
//					WHERE privacy_level LIKE 'INHERIT_FROM_ALBUM'
//				)
//			)
//			OR id IN (
//				SELECT album_id FROM archivo
//				WHERE id IN (
//					SELECT file_id FROM file_share_information
//					WHERE user_id = @USER_ID
//				)
//			)
//		);
		
		String hql = "FROM Album al "
				// albums of ownerEmail
				+ "WHERE al.user.email = :ownerEmail "
				+ "AND ("
						+ "("
							+ "al.id IN ("
								// albums shared with userId and (*1)
								+ "SELECT asi.album.id FROM AlbumShareInformation asi "
								+ "WHERE asi.user.id = :userId "
								+ "AND asi.album.id IN ("
									// (*1) with INHERIT files
									+ "SELECT ifi.album.id FROM File ifi "
									+ "WHERE ifi.privacyLevel = :inheritPrivacyLevel"
								+ ")"
							+ ")"
						+ ")"
						+ "OR"
						+ "("
							+ "al.id IN ("
								// albums (*2)
								+ "SELECT sfi.album.id FROM File sfi "
								+ "WHERE sfi.id IN ("
									// (*2) with files shared with userId
									+ "SELECT fsi.file.id FROM FileShareInformation fsi "
									+ "WHERE fsi.user.id = :userId"
								+ ")"
							+ ")"
						+ ")"
					+ ")"
				+ ")";

		return (ArrayList<Album>) getHibernateTemplate()
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(hql)
				.setParameter("ownerEmail", ownerEmail)
				.setParameter("userId", userId)
				.setParameter("inheritPrivacyLevel",
						PrivacyLevel.INHERIT_FROM_ALBUM).list();
		
//		// I first tried using criterias but the join walker crash
//
//		DetachedCriteria dc = DetachedCriteria.forClass(Album.class, "album");
//		
//		// shared albums with inherit files
//		DetachedCriteria inheritFilesDc = DetachedCriteria.forClass(File.class, "ih_file")
//				.createAlias("ih_file.album", "ih_file_al")
//				.add(Restrictions.eq("ih_file.privacyLevel", PrivacyLevel.INHERIT_FROM_ALBUM))
//				.setProjection(Projections.property("ih_file_al.id"));
//		DetachedCriteria sharedAlbumsDc = DetachedCriteria.forClass(
//				AlbumShareInformation.class, "asi")
//				.createAlias("asi.album", "asi_al")
//				.createAlias("asi.user", "asi_us")
//				.add(Restrictions.eq("asi_us", userId))
//				.add(Subqueries.propertyIn("asi_al.id", inheritFilesDc))
//				.setProjection(Projections.property("asi_al.id"));
//		Criterion inheritFilesCr = Subqueries.propertyIn("album.id", sharedAlbumsDc);
//		
//		// albums with shared files
//		DetachedCriteria sharedFilesDc = DetachedCriteria.forClass(
//				FileShareInformation.class, "fsi")
//				.createAlias("fsi.file", "fsi_fi")
//				.createAlias("fsi_fi.album", "fsi_fi_al")
//				.createAlias("fsi.user", "fsi_us")
//				.add(Restrictions.eq("fsi_us", userId))
//				.setProjection(Projections.property("fsi_fi_al.id"));
//		Criterion sharedFilesCr = Subqueries.propertyIn("album.id", sharedFilesDc);
//
//		return (ArrayList<Album>) getHibernateTemplate().findByCriteria(
//				dc.add(
//						Restrictions.disjunction()
//								.add(inheritFilesCr)
//								.add(sharedFilesCr)
//						).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY));
	}

	
	public Album getSharedAlbum(String albumName, int userSharedToId,
			String userSharedEmail) {
		// TODO: esto hay que hacerlo bien, comprobando compartición y tal
		return (Album) getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(Album.class)
						.createAlias("user", "us")
						.add(Restrictions.eq("name", albumName))
						.add(Restrictions.eq("us.email", userSharedEmail)));
	}
}
