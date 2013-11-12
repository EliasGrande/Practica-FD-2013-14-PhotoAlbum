package es.udc.fi.dc.photoalbum.hibernate;

import java.util.List;

public interface CommentDao extends GenericDao<Comment> {

    List<Comment> getComments(Album album);

    List<Comment> getComments(File file);

    List<Comment> getCommentsPaging(Album album, int first,
            int count);

    List<Comment> getCommentsPaging(File file, int first,
            int count);
}
