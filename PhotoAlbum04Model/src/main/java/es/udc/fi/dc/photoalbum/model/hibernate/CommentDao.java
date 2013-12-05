package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.List;

/**
 * DAO interface for the {@link Comment} entity.
 */
public interface CommentDao extends GenericDao<Comment> {

    /**
     * Gets all the {@link Comments comments} of the given
     * {@link Album}.
     * 
     * @param album
     *            Album
     * @return Comment list
     */
    List<Comment> getComments(Album album);

    /**
     * Gets all the {@link Comments comments} of the given
     * {@link File}.
     * 
     * @param file
     *            File
     * @return Comment list
     */
    List<Comment> getComments(File file);

    /**
     * Same as {@link #getComments(Album)} but paging.
     * 
     * @param album
     *            Album
     * @param first
     *            First index in the database result list
     * @param count
     *            Max number of comments to return
     * @return
     */
    List<Comment> getCommentsPaging(Album album, int first, int count);

    /**
     * Same as {@link #getComments(File)} but paging.
     * 
     * @param file
     *            File
     * @param first
     *            First index in the database result list
     * @param count
     *            Max number of comments to return
     * @return
     */
    List<Comment> getCommentsPaging(File file, int first, int count);
}
