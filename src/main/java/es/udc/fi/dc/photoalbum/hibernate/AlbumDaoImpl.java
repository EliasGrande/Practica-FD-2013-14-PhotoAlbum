package es.udc.fi.dc.photoalbum.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

import java.util.ArrayList;

public class AlbumDaoImpl extends HibernateDaoSupport implements
        AlbumDao {

    /**
     * Restriction for Album: Public albums.
     * 
     * Parameters: - (String) inheritPrivacyLevel - (String)
     * publicPrivacyLevel
     */
    private static final String HQL_RESTRICTION_ALBUMS_PUBLIC = "("
            + "("
            // public albums (*1)
            + "privacyLevel = :publicPrivacyLevel "
            + "AND id IN ("
            // (*1) with inherit files
            + "SELECT album.id FROM File "
            + "WHERE privacyLevel = :inheritPrivacyLevel" + ")"
            + ")"
            + "OR"
            + "("
            // albums (*2)
            + "id IN ("
            // (*2) with public files
            + "SELECT album.id FROM File "
            + "WHERE privacyLevel = :publicPrivacyLevel" + ")" + ")"
            + ")";

    /**
     * Restriction for Album: Albums shared with userId.
     * 
     * Parameters: - (int) userId - (String) inheritPrivacyLevel -
     * (String) publicPrivacyLevel
     */
    private static final String HQL_RESTRICTION_ALBUMS_SHARED_WITH = "("
            + "("
            + "id IN ("
            // albums shared with userId and (*1)
            + "SELECT album.id FROM AlbumShareInformation "
            + "WHERE user.id = :userId "
            + "AND album.id IN ("
            // (*1) with INHERIT or PUBLIC files
            + "SELECT album.id FROM File "
            + "WHERE privacyLevel = :inheritPrivacyLevel "
            + "OR privacyLevel = :publicPrivacyLevel"
            + ")"
            + ")"
            + ")"
            + "OR"
            + "("
            + "id IN ("
            // albums (*2)
            + "SELECT album.id FROM File "
            + "WHERE id IN ("
            // (*2) with files shared with userId
            + "SELECT file.id FROM FileShareInformation "
            + "WHERE user.id = :userId" + ")" + ")" + ")" + ")";

    /**
     * Restriction for Album: The owner is userId.
     * 
     * Parameters: - (int) userId
     */
    private static final String HQL_RESTRICTION_IM_THE_OWNER = "(user.id = :userId)";

    private Query createQuery(String hql) {
        return getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql);
    }

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
        return (ArrayList<Album>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(Album.class)
                                .createCriteria("user")
                                .add(Restrictions.eq("id", id))
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY));
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Album> getPublicAlbums() {

        String hql = "FROM Album WHERE "
                + HQL_RESTRICTION_ALBUMS_PUBLIC;

        return (ArrayList<Album>) createQuery(hql)
                .setParameter("inheritPrivacyLevel",
                        PrivacyLevel.INHERIT_FROM_ALBUM)
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC).list();
    }

    public void changePrivacyLevel(Album album, String privacyLevel) {
        album.setPrivacyLevel(privacyLevel);
        getHibernateTemplate().update(album);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Album> getAlbumsSharedWith(Integer userId,
            String ownerEmail) {

        String hql = "FROM Album "
                + "WHERE user.email = :ownerEmail " + "AND"
                + HQL_RESTRICTION_ALBUMS_SHARED_WITH;

        return (ArrayList<Album>) createQuery(hql)
                .setParameter("ownerEmail", ownerEmail)
                .setParameter("userId", userId)
                .setParameter("inheritPrivacyLevel",
                        PrivacyLevel.INHERIT_FROM_ALBUM)
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC).list();
    }

    @SuppressWarnings("unchecked")
    public Album getSharedAlbum(String albumName, int userSharedToId,
            String userSharedEmail) {
        // get the album
        ArrayList<Album> list = (ArrayList<Album>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(Album.class)
                                .createAlias("user", "us")
                                .add(Restrictions.eq("name",
                                        albumName))
                                .add(Restrictions.eq("us.email",
                                        userSharedEmail)));

        // the query should return one result
        if (list.size() == 1) {
            Album album = list.get(0);
            // check if the album is shared with "userSharedToId"
            list = getAlbumsSharedWith(userSharedToId,
                    userSharedEmail);
            if (list.contains(album))
                return album;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Album> getAlbumsByTag(int userId, String tag) {
        String hql = "FROM Album "
                // albums with the tag
                + "WHERE id IN (" + "SELECT album.id FROM AlbumTag "
                + "WHERE tag = :tag"
                + ")"
                // albums viewable by the user
                + "AND (" + HQL_RESTRICTION_IM_THE_OWNER + " OR "
                + HQL_RESTRICTION_ALBUMS_SHARED_WITH + " OR "
                + HQL_RESTRICTION_ALBUMS_PUBLIC + ")" + ")";

        return (ArrayList<Album>) getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createQuery(hql)
                .setParameter("userId", userId)
                .setParameter("tag", tag)
                .setParameter("inheritPrivacyLevel",
                        PrivacyLevel.INHERIT_FROM_ALBUM)
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC).list();
    }
}
