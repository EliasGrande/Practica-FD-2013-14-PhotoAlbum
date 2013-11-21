package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;

/**
 * Interface for the {@link CommentService}
 */
public interface CommentService {

    /**
     * Create a comment.
     * 
     * @param userThatComment
     *            User thar create the comment.
     * @param album
     *            Album that owns the comment.
     * @param text
     *            The text that contains the comment.
     */
    void create(User userThatComment, Album album, String text);

    /**
     * Create a comment.
     * 
     * @param userThatComment
     *            User thar create the comment.
     * @param file
     *            File that owns the comment.
     * @param text
     *            The text that contains the comment.
     */
    void create(User userThatComment, File file, String text);

    /**
     * Delete a comment.
     * 
     * @param comment
     *            Comment that will be deleted.
     */
    void delete(Comment comment);

    /**
     * Obtains all the comments for an album.
     * 
     * @param album
     *            It's the album they belong comments.
     * 
     * @return ArrayList<Comment> A list of album comments.
     */
    List<Comment> getComments(Album album);

    /**
     * Obtains all the comments for a file.
     * 
     * @param file
     *            It's the file they belong comments.
     * 
     * @return ArrayList<Comment> A list of file comments.
     */
    List<Comment> getComments(File file);

    /**
     * Obtains the comments for an album paginated.
     * 
     * @param album
     *            It's the album they belong comments.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtained
     * 
     * @return ArrayList<Comment> A list of album comments.
     */
    List<Comment> getCommentsPaging(Album album, int first, int count);

    /**
     * Obtains the comments for an file paginated.
     * 
     * @param file
     *            It's the file they belong comments.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtains.
     * 
     * @return ArrayList<Comment> A list of file comments.
     */
    List<Comment> getCommentsPaging(File file, int first, int count);

}
