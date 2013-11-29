package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;
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

import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

/**
 * {@link FileDao} Hibernate implementation.
 */
public class FileDaoImpl extends HibernateDaoSupport implements
        FileDao {

    /**
     * Query for File: Search files by tag returning only the ones
     * viewable by userId.
     * <p>
     * Parameters: (String) tag, (int) userId, (String)
     * inheritPrivacyLevel, (String) publicPrivacyLevel
     */
    private static final String HQL_QUERY_FILES_BY_TAG = "FROM File "
            // search files by tag
            + "WHERE id IN ("
            + "SELECT file.id FROM FileTag "
            + "WHERE tag = :tag"
            + ")"
            // files viewable by userId
            + "AND"
            + "("
            // files owned by userId
            + "album.id IN ("
            + "SELECT id FROM Album "
            + "WHERE user.id = :userId"
            + ")"
            // public files
            + "OR privacyLevel = :publicPrivacyLevel "
            // files shared with userId
            + "OR id IN ("
            + "SELECT file.id FROM FileShareInformation "
            + "WHERE user.id = :userId"
            + ")"
            // inherit files
            + "OR ("
            + "privacyLevel = :inheritPrivacyLevel "
            + "AND ("
            // from public albums
            + "album.privacyLevel = :publicPrivacyLevel "
            // from albums shared with userId
            + "OR album.id IN ("
            + "SELECT album.id FROM AlbumShareInformation "
            + "WHERE user.id = :userId" + ")" + ")" + ")" + ")";

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

        // main query, search by album
        Criteria criteria = getHibernateTemplate()
                .getSessionFactory().getCurrentSession()
                .createCriteria(File.class, "fi")
                .createAlias("fi.album", "fi_al")
                .add(Restrictions.eq("fi_al.id", albumId));

        // OR1: get public files
        Criterion publicFileCr = Restrictions.eq("fi.privacyLevel",
                PrivacyLevel.PUBLIC);

        // OR2: get files shared to userId
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

        // OR3: get inherit files if the album (is public OR is shared
        // to
        // userId)
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
}
