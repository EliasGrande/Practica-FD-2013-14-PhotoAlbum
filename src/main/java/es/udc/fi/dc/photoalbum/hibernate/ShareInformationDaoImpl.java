package es.udc.fi.dc.photoalbum.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

import java.util.ArrayList;
import java.util.List;

public class ShareInformationDaoImpl extends HibernateDaoSupport implements
		ShareInformationDao {
	
//	private void debug(String method) {
//		System.err.println("Debug: ShareInformationDaoImp[instance="+this.hashCode()+"]." + method);
//	}

	/**
	 * Guarda un ShareInformation en base de datos.
	 */
	public void create(ShareInformation shareInformation) {
		getHibernateTemplate().save(shareInformation);
	}

	/**
	 * Elimina un ShareInformation de base de datos.
	 */
	public void delete(ShareInformation shareInformation) {
		getHibernateTemplate().delete(shareInformation);
	}

	/**
	 * ShareInformation de los albumes compartidos por un usuario a otro. Los
	 * albumes privados son filtrados y no aparecen en el resultado. Usado en
	 * "Albumes compartidos conmigo > usuario@ejemplo.com".
	 * 
	 * @param userShared
	 *            Propietario
	 * @param userSharedTo
	 *            Otro usuario
	 */
	@SuppressWarnings("unchecked")
	public List<ShareInformation> getShares(User userShared, User userSharedTo) {
//		debug("getShares(User userShared, User userSharedTo)");
		return (List<ShareInformation>) getHibernateTemplate().findByCriteria(
				DetachedCriteria
						.forClass(ShareInformation.class)
						.createAlias("album", "al")
						.createAlias("al.user", "alus")
						.createAlias("user", "us")
						.add(Restrictions.eq("alus.id", userShared.getId()))
						.add(Restrictions.eq("us.id", userSharedTo.getId()))
						.add(Restrictions
								.disjunction()
								.add(Restrictions
										.like("al.privacyLevel",
												PrivacyLevel.SHAREABLE,
												MatchMode.EXACT))
								.add(Restrictions.like("al.privacyLevel",
										PrivacyLevel.PUBLIC, MatchMode.EXACT)))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY));
	}

	/**
	 * ShareInformation de un album concreto. Usado en
	 * "Albumes compartidos conmigo > usuario@ejemplo.com > AlbumEjemplo".
	 * 
	 * @param albumName
	 *            Nombre del album
	 * @param userSharedToId
	 *            Id de usuario con el que se ha compartido
	 * @param userSharedEmail
	 *            Email del propietario
	 */
	public ShareInformation getShare(String albumName, int userSharedToId,
			String userSharedEmail) {
//		debug("getShare(String albumName, int userSharedToId, String userSharedEmail)");
		@SuppressWarnings("unchecked")
		ArrayList<ShareInformation> list = (ArrayList<ShareInformation>) getHibernateTemplate()
				.findByCriteria(
						DetachedCriteria
								.forClass(ShareInformation.class)
								.createAlias("album", "al")
								.createAlias("al.user", "alus")
								.createAlias("user", "us")
								.add(Restrictions.eq("al.name", albumName))
								.add(Restrictions.eq("us.id", userSharedToId))
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

	/**
	 * Usado en "Mis albumes > AlbumEjemplo > Compartir" para mostrar la lista
	 * de usuarios con los que he compartido el album.
	 * 
	 * @param albumId
	 *            Identificador del album.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ShareInformation> getAlbumShares(int albumId) {
//		debug("getAlbumShares(int albumId)");
		return (ArrayList<ShareInformation>) getHibernateTemplate()
				.findByCriteria(
						DetachedCriteria
								.forClass(ShareInformation.class)
								.createCriteria("album")
								.add(Restrictions.eq("id", albumId))
								.setResultTransformer(
										Criteria.DISTINCT_ROOT_ENTITY));
	}

	/**
	 * Usado en "Albumes compartidos conmigo" para mostrar la lista de usuarios
	 * que me comparten algo.
	 * 
	 * @param userId
	 *            Id del usuario con el que se han compartido albumes.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ShareInformation> getUserShares(int userId) {
//		debug("getUserShares(int userId)");
		return (ArrayList<ShareInformation>) getHibernateTemplate()
				.findByCriteria(
						DetachedCriteria
								.forClass(ShareInformation.class)
								.createCriteria("user")
								.add(Restrictions.eq("id", userId))
								.setResultTransformer(
										Criteria.DISTINCT_ROOT_ENTITY));
	}
}
