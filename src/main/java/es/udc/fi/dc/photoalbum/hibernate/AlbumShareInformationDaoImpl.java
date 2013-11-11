package es.udc.fi.dc.photoalbum.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;

public class AlbumShareInformationDaoImpl extends HibernateDaoSupport
        implements AlbumShareInformationDao {

    /**
     * Guarda un ShareInformation en base de datos.
     */
    public void create(AlbumShareInformation shareInformation) {
        getHibernateTemplate().save(shareInformation);
    }

    /**
     * Elimina un ShareInformation de base de datos.
     */
    public void delete(AlbumShareInformation shareInformation) {
        getHibernateTemplate().delete(shareInformation);
    }

    /**
     * ShareInformation de un album concreto. Usado en
     * "Albumes compartidos conmigo > usuario@ejemplo.com > AlbumEjemplo"
     * .
     * 
     * @param albumName
     *            Nombre del album
     * @param userSharedToId
     *            Id de usuario con el que se ha compartido
     * @param userSharedEmail
     *            Email del propietario
     */
    public AlbumShareInformation getShare(String albumName,
            int userSharedToId, String userSharedEmail) {
        @SuppressWarnings("unchecked")
        ArrayList<AlbumShareInformation> list = 
            (ArrayList<AlbumShareInformation>) getHibernateTemplate()
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

    public AlbumShareInformation getShare(int albumId, int userId) {
        @SuppressWarnings("unchecked")
        ArrayList<AlbumShareInformation> list = 
            (ArrayList<AlbumShareInformation>) getHibernateTemplate()
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

    /**
     * Usado en "Mis albumes > AlbumEjemplo > Compartir" para mostrar
     * la lista de usuarios con los que he compartido el album.
     * 
     * @param albumId
     *            Identificador del album.
     */
    @SuppressWarnings("unchecked")
    public ArrayList<AlbumShareInformation> getAlbumShares(int albumId) {
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
