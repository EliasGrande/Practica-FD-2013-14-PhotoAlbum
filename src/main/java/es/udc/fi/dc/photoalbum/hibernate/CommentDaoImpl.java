package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * {@link CommentDao} Hibernate implementation.
 */
public class CommentDaoImpl extends HibernateDaoSupport implements
        CommentDao {

    public void create(Comment comment) {
        getHibernateTemplate().save(comment);
    }

    public void delete(Comment comment) {
        getHibernateTemplate().delete(comment);

    }

    @SuppressWarnings("unchecked")
    public List<Comment> getComments(Album album) {
        String hql = "SELECT c FROM Comment c "
                + "WHERE c.album.id = :albumId "
                + "ORDER BY c.date DESC";

        return (ArrayList<Comment>) getHibernateTemplate()
                .getSessionFactory().getCurrentSession()
                .createQuery(hql)
                .setParameter("albumId", album.getId()).list();
    }

    @SuppressWarnings("unchecked")
    public List<Comment> getComments(File file) {
        String hql = "SELECT c FROM Comment c "
                + "WHERE c.file.id = :fileId "
                + "ORDER BY c.date DESC";

        return (ArrayList<Comment>) getHibernateTemplate()
                .getSessionFactory().getCurrentSession()
                .createQuery(hql)
                .setParameter("fileId", file.getId()).list();
    }

    @SuppressWarnings("unchecked")
    public List<Comment> getCommentsPaging(Album album,
            int first, int count) {
        String hql = "SELECT c FROM Comment c "
                + "WHERE c.album.id = :albumId "
                + "ORDER BY c.date DESC";

        return (ArrayList<Comment>) getHibernateTemplate()
                .getSessionFactory().getCurrentSession()
                .createQuery(hql)
                .setParameter("albumId", album.getId())
                .setFirstResult(first).setMaxResults(count).list();
    }

    @SuppressWarnings("unchecked")
    public List<Comment> getCommentsPaging(File file, int first,
            int count) {
        String hql = "SELECT c FROM Comment c "
                + "WHERE c.file.id = :fileId "
                + "ORDER BY c.date DESC";

        return (ArrayList<Comment>) getHibernateTemplate()
                .getSessionFactory().getCurrentSession()
                .createQuery(hql)
                .setParameter("fileId", file.getId())
                .setFirstResult(first).setMaxResults(count).list();
    }

}
