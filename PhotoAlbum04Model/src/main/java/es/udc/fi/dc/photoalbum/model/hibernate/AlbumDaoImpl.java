package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

/**
 * {@link AlbumDao} Hibernate implementation.
 */
public class AlbumDaoImpl extends HibernateDaoSupport implements
        AlbumDao {

    /**
     * The search will be sorted by {@link Album} date.
     */
    private final static String DATE = "date";
    
    /**
     * The search will be sorted by {@link Album} likes.
     */
    private final static String LIKE = "like";
    
    /**
     * Restriction for Album: Public albums.
     * <p>
     * Parameters: (String) inheritPrivacyLevel, (String)
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

    /**
     * Restriction for Album: Get public albums.
     */
    private static final String HQL_RESTRICTION_PUBLIC_ALBUMS = "WHERE "
            + "("
            + "a.privacyLevel = :publicPrivacyLevel"
            + ") ";
    
    /**
     * Restriction for Album: Get albums that its album name contains
     * the keyword.
     */
    private static final String HQL_RESTRICTION_KEYWORDS = "AND "
            + "("
            + "a.name LIKE :keywords"
            + ") ";
    
    /**
     * Restriction for Album: Search albums that have the text of a
     * comment containing the specified text.
     */
    private static final String HQL_RESTRICTION_COMMENT = "AND "
            + "("
            + "a.id "
            + "IN "
            + "("
            + "SELECT c.album.id FROM Comment c "
            + "WHERE c.text LIKE :keywords "
            + ")"
            + ") ";
    
    /**
     * Restriction for Album: Get albums whose tag have the specified
     * tag text.
     */
    private static final String HQL_RESTRICTION_TAG = "AND "
            + "("
            + "a.id "
            + "IN "
            + "("
            + "SELECT t.album.id "
            + "FROM AlbumTag t "
            + "WHERE t.tag LIKE :keywords"
            + ")"
            + ") ";
    
    /**
     * Restriction for Album: Get albums which date is between two
     * dates.
     */
    private static final String HQL_RESTRICTION_BETWEEN_DATE = "AND "
            + "("
            + "a.date BETWEEN :fechaMin AND :fechaMax"
            + ") ";
    
    /**
     * Restriction for Album: Sort result by {@link Album} date.
     */
    private static final String HQL_RESTRICTION_ORDER_BY_DATE = "ORDER BY "
            + "a.date DESC ";

    /**
     * Restriction for Album: Sort result by {@link Album} likes.
     */
    private static final String HQL_RESTRICTION_ORDER_BY_LIKE = "ORDER BY "
            + "a.likeAndDislike.like DESC, a.date";

    /**
     * Restriction for Album: Sort result by {@link Album} dislikes.
     */
    private static final String HQL_RESTRICTION_ORDER_BY_DISLIKE = "ORDER BY "
            + "a.likeAndDislike.dislike DESC, a.date";
    
    /**
     * Creates an hql query for the current session.
     * 
     * @param hql
     *            Query string.
     * @return Query
     */
    private Query createQuery(String hql) {
        return getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql);
    }

    @Override
    public void create(Album album) {
        getHibernateTemplate().save(album);
    }

    @Override
    public void delete(Album album) {
        getHibernateTemplate().delete(album);
    }

    @Override
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

    @Override
    public void rename(Album album, String newName) {
        album.setName(newName);
        getHibernateTemplate().update(album);
    }

    @Override
    public Album getById(Integer id) {
        return getHibernateTemplate().get(Album.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Album> getAlbums(Integer id) {
        return (ArrayList<Album>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(Album.class)
                                .createCriteria("user")
                                .add(Restrictions.eq("id", id))
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Album> getPublicAlbums() {

        String hql = "FROM Album WHERE "
                + HQL_RESTRICTION_ALBUMS_PUBLIC;

        return (ArrayList<Album>) createQuery(hql)
                .setParameter("inheritPrivacyLevel",
                        PrivacyLevel.INHERIT_FROM_ALBUM)
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC).list();
    }

    @Override
    public void changePrivacyLevel(Album album, String privacyLevel) {
        album.setPrivacyLevel(privacyLevel);
        getHibernateTemplate().update(album);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Album> getAlbumsSharedWith(Integer userId,
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

    @Override
    @SuppressWarnings("unchecked")
    public Album getSharedAlbum(String albumName, int userSharedToId,
            String userSharedEmail) {
        // get the album
        List<Album> list = (ArrayList<Album>) getHibernateTemplate()
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

    @Override
    @SuppressWarnings("unchecked")
    public List<Album> getAlbumsByTag(int userId, String tag) {
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Album> getAlbums(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy,
            Calendar fechaMin, Calendar fechaMax, int first, int count) {
        String query = "SELECT a FROM Album a ";
        
        query += HQL_RESTRICTION_PUBLIC_ALBUMS; 
        
        if (name) {
            query += HQL_RESTRICTION_KEYWORDS;
        }
        
        if (comment) {
            query += HQL_RESTRICTION_COMMENT;
        }
        
        if (tag) {
            query += HQL_RESTRICTION_TAG;
        }
        
        query += HQL_RESTRICTION_BETWEEN_DATE;
        
        if (orderBy.equals(DATE)) {
            query += HQL_RESTRICTION_ORDER_BY_DATE;
        } else if (orderBy.equals(LIKE)) {
            query += HQL_RESTRICTION_ORDER_BY_LIKE;
        } else {
            query += HQL_RESTRICTION_ORDER_BY_DISLIKE;
        }
        
        return (List<Album>) getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createQuery(query)
                .setParameter("keywords", "%" + keywords + "%")
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC)
                .setParameter("fechaMin", fechaMin)
                .setParameter("fechaMax", fechaMax)
                .setFirstResult(first).setMaxResults(count).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Album> getAlbums(String orderBy, int first, int count) {
        String query = "SELECT a FROM Album a ";
        
        query += HQL_RESTRICTION_PUBLIC_ALBUMS; 
        
        if (orderBy.equals(DATE)) {
            query += HQL_RESTRICTION_ORDER_BY_DATE;
        } else if (orderBy.equals(LIKE)) {
            query += HQL_RESTRICTION_ORDER_BY_LIKE;
        } else {
            query += HQL_RESTRICTION_ORDER_BY_DISLIKE;
        }
        
        return (List<Album>) getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createQuery(query)
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC)
                .setFirstResult(first).setMaxResults(count).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Album> getAlbums(String orderBy, Calendar fechaMin,
            Calendar fechaMax, int first, int count) {
        String query = "SELECT a FROM Album a ";
        
        query += HQL_RESTRICTION_PUBLIC_ALBUMS;
        
        query += HQL_RESTRICTION_BETWEEN_DATE;
        
        if (orderBy.equals(DATE)) {
            query += HQL_RESTRICTION_ORDER_BY_DATE;
        } else if (orderBy.equals(LIKE)) {
            query += HQL_RESTRICTION_ORDER_BY_LIKE;
        } else {
            query += HQL_RESTRICTION_ORDER_BY_DISLIKE;
        }
        
        return (List<Album>) getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createQuery(query)
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC)
                .setParameter("fechaMin", fechaMin)
                .setParameter("fechaMax", fechaMax)
                .setFirstResult(first).setMaxResults(count).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Album> getAlbums(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy, int first,
            int count) {
        String query = "SELECT a FROM Album a ";
        
        query += HQL_RESTRICTION_PUBLIC_ALBUMS;
        
        if (name) {
            query += HQL_RESTRICTION_KEYWORDS;
        }
        
        if (comment) {
            query += HQL_RESTRICTION_COMMENT;
        }
        
        if (tag) {
            query += HQL_RESTRICTION_TAG;
        }
        
        if (orderBy.equals(DATE)) {
            query += HQL_RESTRICTION_ORDER_BY_DATE;
        } else if (orderBy.equals(LIKE)) {
            query += HQL_RESTRICTION_ORDER_BY_LIKE;
        } else {
            query += HQL_RESTRICTION_ORDER_BY_DISLIKE;
        }
        
        return (List<Album>) getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createQuery(query)
                .setParameter("keywords", "%" + keywords + "%")
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC).setFirstResult(first)
                .setMaxResults(count).list();
    }
}
