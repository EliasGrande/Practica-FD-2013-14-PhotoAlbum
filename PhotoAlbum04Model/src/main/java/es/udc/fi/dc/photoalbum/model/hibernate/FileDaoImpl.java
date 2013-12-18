package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

/**
 * {@link FileDao} Hibernate implementation.
 */
public class FileDaoImpl extends HibernateDaoSupport implements
        FileDao {

    /**
     * The search will be sorted by {@link File} date.
     */
    private static final String DATE = "date";
    
    /**
     * The search will be sorted by {@link File} likes.
     */
    private static final String LIKE = "like";
    
    /**
     * Query for File: Search files by tag returning only the ones
     * viewable by userId.
     * <p>
     * Parameters: (String) tag, (int) userId, (String)
     * inheritPrivacyLevel, (String) publicPrivacyLevel
     */
    private static final String HQL_QUERY_FILES_BY_TAG = "FROM File "
            + "WHERE id IN ("
            + "SELECT file.id FROM FileTag "
            + "WHERE tag = :tag"
            + ")"
            + "AND"
            + "("
            + "album.id IN ("
            + "SELECT id FROM Album "
            + "WHERE user.id = :userId"
            + ")"
            + "OR privacyLevel = :publicPrivacyLevel "
            + "OR id IN ("
            + "SELECT file.id FROM FileShareInformation "
            + "WHERE user.id = :userId"
            + ")"
            + "OR ("
            + "privacyLevel = :inheritPrivacyLevel "
            + "AND ("
            + "album.privacyLevel = :publicPrivacyLevel "
            + "OR album.id IN ("
            + "SELECT album.id FROM AlbumShareInformation "
            + "WHERE user.id = :userId" + ")" + ")" + ")" + ")";

    /**
     * Restriction for File: Get public files or files that inherits
     * of and public album.
     */
    private static final String HQL_RESTRICTION_PUBLIC_FILES = "WHERE (" 
            + "("
            + "(f.privacyLevel = :publicPrivacyLevel) "  
            + ") " 
            + "OR " 
            + "("
            + "(f.privacyLevel = :inheritPrivacyLevel) " 
            + "AND "
            + "(" 
            + "f.album.id " 
            + "IN " 
            + "("
            + "SELECT a.id FROM Album a "
            + "WHERE a.privacyLevel = :publicPrivacyLevel" 
            + "))" 
            + ")" 
            + ") ";
    
    /**
     * Restriction for File: Get files that its file name contains
     * the keyword.
     */
    public static final String HQL_RESTRICTION_NAME = "AND "
            + "("
            + "f.name LIKE :keywords "
            + ") ";
    
    /**
     * Restriction for File: Search files that have the text of a
     * comment containing the specified text.
     */
    public static final String HQL_RESTRICTION_COMMENT = "AND " 
            + "(" 
            + "f.id " 
            + "IN " 
            + "("
            + "SELECT c.file.id " 
            + "FROM Comment c "
            + "WHERE c.text LIKE :keywords" 
            + ")"
            + ") ";
    
    /**
     * Restriction for File: Get files whose tag have the specified
     * tag text.
     */
    public static final String HQL_RESTRICTION_TAG = "AND " 
            + "(" 
            + "f.id " 
            + "IN " 
            + "("
            + "SELECT t.file.id " 
            + "FROM FileTag t "
            + "WHERE t.tag LIKE :keywords" 
            + ")" 
            + ") ";
    
    /**
     * Restriction for File: Get files which date is between two
     * dates.
     */
    public static final String HQL_RESTRICTION_BETWEEN_DATE = "AND "
            + "("
            + "f.date BETWEEN :fechaMin AND :fechaMax"
            + ") ";
  
    /**
     * Restriction for File: Sort result by {@link File} date.
     */
    private static final String HQL_RESTRICTION_ORDER_BY_DATE = "ORDER BY "
            + "f.date DESC ";
            
    /**
     * Restriction for File: Sort result by {@link File} likes.
     */
    private static final String HQL_RESTRICTION_ORDER_BY_LIKE = "ORDER BY "
            + "f.likeAndDislike.like DESC, f.date";
    
    /**
     * Restriction for File: Sort result by {@link File} dislikes.
     */
    private static final String HQL_RESTRICTION_ORDER_BY_DISLIKE = 
            "ORDER BY f.likeAndDislike.dislike DESC, f.date";
    
    @Override
    public void create(File file) {
        getHibernateTemplate().save(file);
    }

    @Override
    public File getById(Integer id) {
        return getHibernateTemplate().get(File.class, id);
    }

    @Override
    public File getFileOwn(int id, String name, int userId) {
        @SuppressWarnings("unchecked")
        List<File> list = (ArrayList<File>) getHibernateTemplate()
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
            if (file.getAlbum().getUser().getId() == userId) {
                return file;
            }
        }
        return null;
    }

    @Override
    public void delete(File file) {
        getHibernateTemplate().delete(file);
    }

    @Override
    public void changeAlbum(File file, Album album) {
        getHibernateTemplate().update(file);
        file.setAlbum(album);
    }

    /**
     * Criteria for retrieving all the files of an album.
     * 
     * @param albumId
     *            Album id
     * @return File list
     */
    private Criteria getAlbumOwnFilesCriteria(int albumId) {
        return getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createCriteria(File.class)
                .createCriteria("album")
                .add(Restrictions.eq("id", albumId))
                .addOrder(Order.asc("id"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<File> getAlbumFilesOwn(int albumId) {
        return (ArrayList<File>) getAlbumOwnFilesCriteria(albumId)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<File> getAlbumFilesOwnPaging(int albumId, int first,
            int count) {
        return (ArrayList<File>) getAlbumOwnFilesCriteria(albumId)
                .setFirstResult(first).setMaxResults(count).list();
    }

    /**
     * Files from the given {@link Album}, that the given {@link User}
     * can see, criteria.
     * <p>
     * This criteria only return files which (1) are public, or (2)
     * are shared with {@code userId}, or (3) inherit their privacy
     * level from the album and the album is public or shared with
     * {@code userId}.
     * 
     * @param albumId
     *            {@link Album} id
     * @param userId
     *            {@link User} id
     * @return Files criteria.
     */
    private Criteria getAlbumSharedFilesCriteria(int albumId,
            int userId) {
        Criteria criteria = getHibernateTemplate()
                .getSessionFactory().getCurrentSession()
                .createCriteria(File.class, "fi")
                .createAlias("fi.album", "fi_al")
                .add(Restrictions.eq("fi_al.id", albumId));

        Criterion publicFileCr = Restrictions.eq("fi.privacyLevel",
                PrivacyLevel.PUBLIC);

        Criterion sharedFileCr = Subqueries.propertyIn(
                "fi.id",
                DetachedCriteria
                        .forClass(FileShareInformation.class, "fis")
                        .createAlias("fis.user", "fis_us")
                        .createAlias("fis.file", "fis_fi")
                        .add(Restrictions.eq("fis_us.id", userId))
                        .setProjection(
                                Projections.property("fis_fi.id"))
                        .setResultTransformer(
                                Criteria.DISTINCT_ROOT_ENTITY));

        Criterion publicAlbumCr = Restrictions.eq(
                "fi_al.privacyLevel", PrivacyLevel.PUBLIC);
        Criterion sharedAlbumCr = Subqueries.propertyIn(
                "fi_al.id",
                DetachedCriteria
                        .forClass(AlbumShareInformation.class, "ais")
                        .createAlias("ais.user", "ais_us")
                        .createAlias("ais.album", "ais_al")
                        .add(Restrictions.eq("ais_us.id", userId))
                        .add(Restrictions.eq("ais_al.id", albumId))
                        .setProjection(
                                Projections.property("ais_al.id")));
        Criterion inheritFileCr = Restrictions
                .conjunction()
                .add(Restrictions.eq("fi.privacyLevel",
                        PrivacyLevel.INHERIT_FROM_ALBUM))
                .add(Restrictions.disjunction().add(publicAlbumCr)
                        .add(sharedAlbumCr));

        Disjunction or = Restrictions.disjunction();
        or.add(publicFileCr);
        or.add(sharedFileCr);
        or.add(inheritFileCr);

        criteria.add(or).addOrder(Order.asc("fi.id"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return criteria;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<File> getAlbumFilesShared(int albumId, int userId) {
        return (ArrayList<File>) getAlbumSharedFilesCriteria(albumId,
                userId).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<File> getAlbumFilesSharedPaging(int albumId,
            int userId, int first, int count) {
        return (ArrayList<File>) getAlbumSharedFilesCriteria(albumId,
                userId).setFirstResult(first).setMaxResults(count)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long getCountAlbumFiles(int albumId) {
        List<Long> list = (ArrayList<Long>) getHibernateTemplate()
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

    @Override
    public void changePrivacyLevel(File file, String privacyLevel) {
        getHibernateTemplate().update(file);
        file.setPrivacyLevel(privacyLevel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<File> getFilesByTag(int userId, String tag) {
        return (ArrayList<File>) getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createQuery(HQL_QUERY_FILES_BY_TAG)
                .setParameter("tag", tag)
                .setParameter("userId", userId)
                .setParameter("inheritPrivacyLevel",
                        PrivacyLevel.INHERIT_FROM_ALBUM)
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<File> getFilesByTagPaging(int userId, String tag,
            int first, int count) {
        return (ArrayList<File>) getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createQuery(HQL_QUERY_FILES_BY_TAG)
                .setParameter("tag", tag)
                .setParameter("userId", userId)
                .setParameter("inheritPrivacyLevel",
                        PrivacyLevel.INHERIT_FROM_ALBUM)
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC).setFirstResult(first)
                .setMaxResults(count).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<File> getFiles(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy,
            Calendar fechaMin, Calendar fechaMax, int first, int count) {

        String query = "SELECT f FROM File f ";
        query += HQL_RESTRICTION_PUBLIC_FILES;
        if (name) {
            query += HQL_RESTRICTION_NAME;
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
        return (List<File>) getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createQuery(query)
                .setParameter("keywords", "%" + keywords + "%")
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC)
                .setParameter("inheritPrivacyLevel",
                        PrivacyLevel.INHERIT_FROM_ALBUM)
                .setParameter("fechaMin", fechaMin)
                .setParameter("fechaMax", fechaMax)
                .setFirstResult(first).setMaxResults(count).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<File> getFiles(String orderBy, int first, int count) {
        String query = "SELECT f FROM File f ";
        query += HQL_RESTRICTION_PUBLIC_FILES;
        if (orderBy.equals(DATE)) {
            query += HQL_RESTRICTION_ORDER_BY_DATE;
        } else if (orderBy.equals(LIKE)) {
            query += HQL_RESTRICTION_ORDER_BY_LIKE;
        } else {
            query += HQL_RESTRICTION_ORDER_BY_DISLIKE;
        }
        return (List<File>) getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createQuery(query)
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC)
                .setParameter("inheritPrivacyLevel",
                        PrivacyLevel.INHERIT_FROM_ALBUM)
                .setFirstResult(first).setMaxResults(count).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<File> getFiles(String orderBy, Calendar fechaMin,
            Calendar fechaMax, int first, int count) {
        String query = "SELECT f FROM File f ";
        query += HQL_RESTRICTION_PUBLIC_FILES;
        query += HQL_RESTRICTION_BETWEEN_DATE;
        if (orderBy.equals(DATE)) {
            query += HQL_RESTRICTION_ORDER_BY_DATE;
        } else if (orderBy.equals(LIKE)) {
            query += HQL_RESTRICTION_ORDER_BY_LIKE;
        } else {
            query += HQL_RESTRICTION_ORDER_BY_DISLIKE;
        }
        return (List<File>) getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createQuery(query)
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC)
                .setParameter("inheritPrivacyLevel",
                        PrivacyLevel.INHERIT_FROM_ALBUM)
                .setParameter("fechaMin", fechaMin)
                .setParameter("fechaMax", fechaMax)
                .setFirstResult(first).setMaxResults(count).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<File> getFiles(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy, int first,
            int count) {
        String query = "SELECT f FROM File f ";
        query += HQL_RESTRICTION_PUBLIC_FILES;
        if (name) {
            query += HQL_RESTRICTION_NAME;
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
        return (List<File>) getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createQuery(query)
                .setParameter("keywords", "%" + keywords + "%")
                .setParameter("publicPrivacyLevel",
                        PrivacyLevel.PUBLIC)
                .setParameter("inheritPrivacyLevel",
                        PrivacyLevel.INHERIT_FROM_ALBUM)
                .setFirstResult(first).setMaxResults(count).list();
    }
}
