package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * {@link CommentDao} Hibernate implementation.
 */
public class CommentDaoImpl extends HibernateDaoSupport implements
        CommentDao {

    @Override
    public void create(Comment comment) {
        getHibernateTemplate().save(comment);
    }

    @Override
    public void delete(Comment comment) {
        getHibernateTemplate().delete(comment);

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Comment> getComments(Album album) {
        String hql = "SELECT c FROM Comment c "
                + "WHERE c.album.id = :albumId "
                + "ORDER BY c.id DESC";

        return (ArrayList<Comment>) getHibernateTemplate()
                .getSessionFactory().getCurrentSession()
                .createQuery(hql)
                .setParameter("albumId", album.getId()).list();
    }

    @Override
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

    @Override
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

    @Override
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
