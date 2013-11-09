package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

public interface CommentDao extends GenericDao<Comment> {

    ArrayList<Comment> getComments(Album album);

    ArrayList<Comment> getComments(File file);

    ArrayList<Comment> getCommentsPaging(Album album, int first,
            int count);

    ArrayList<Comment> getCommentsPaging(File file, int first,
            int count);
}
