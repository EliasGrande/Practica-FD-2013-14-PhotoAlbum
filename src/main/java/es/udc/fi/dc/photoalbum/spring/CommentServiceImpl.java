package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumTagDao;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.CommentDao;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislikeDao;
import es.udc.fi.dc.photoalbum.hibernate.User;

/**
 * Implementation of the {@link CommentService}
 */
@Transactional
public class CommentServiceImpl implements CommentService {
    /**
     * {@link #getCommentDao()}
     */
    private LikeAndDislikeDao likeAndDislikeDao;

    /**
     * Method for get an {@link LikeAndDislikeDao}.
     * 
     * @return LikeAndDislikeDao
     * @see AlbumTagDao
     */
    public LikeAndDislikeDao getLikeAndDislikeDao() {
        return this.likeAndDislikeDao;
    }

    /**
     * Method that allows to put an {@link LikeAndDislikeDao}.
     * 
     * @param likeAndDislikeDao
     *            {@link LikeAndDislikeDao} which will be put.
     */
    public void setLikeAndDislikeDao(
            LikeAndDislikeDao likeAndDislikeDao) {
        this.likeAndDislikeDao = likeAndDislikeDao;
    }

    /**
     * @see CommentDao
     */
    private CommentDao commentDao;

    /**
     * Method for get an {@link CommentDao}.
     * 
     * @return CommentDao
     * @see CommentDao
     */
    public CommentDao getCommentDao() {
        return this.commentDao;
    }

    /**
     * Method that allows to put an {@link CommentDao}.
     * 
     * @param commentDao
     *            {@link CommentDao} which will be put.
     */
    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    /**
     * Create a {@link Album} {@link Comment}.
     * 
     * @param userThatComment
     *            {@link User} thar create the {@link Comment}.
     * @param album
     *            {@link Album} that owns the {@link Comment}.
     * @param text
     *            The text that contains the {@link Comment}.
     * 
     * @see es.udc.fi.dc.photoalbum.spring.CommentService#create(User,
     *      Album, String)
     */
    public void create(User userThatComment, Album album, String text) {
        LikeAndDislike likeAndDislike = new LikeAndDislike();
        likeAndDislikeDao.create(likeAndDislike);
        Comment comment = new Comment(likeAndDislike,
                userThatComment, text, album, null);
        commentDao.create(comment);
    }

    /**
     * Create a {@link File} {@link Comment}.
     * 
     * @param userThatComment
     *            {@link User} that create the {@link Comment}.
     * @param file
     *            {@link File} that owns the {@link Comment}.
     * @param text
     *            The text that contains the {@link Comment}.
     * 
     * @see es.udc.fi.dc.photoalbum.spring.CommentService#create(User,
     *      File, String)
     */
    public void create(User userThatComment, File file, String text) {
        LikeAndDislike likeAndDislike = new LikeAndDislike();
        likeAndDislikeDao.create(likeAndDislike);
        Comment comment = new Comment(likeAndDislike,
                userThatComment, text, null, file);
        commentDao.create(comment);
    }

    /**
     * Delete a {@link Comment}.
     * 
     * @param comment
     *            {@link Comment} that will be deleted.
     * 
     * @see es.udc.fi.dc.photoalbum.spring.CommentService#delete(Comment)
     */
    public void delete(Comment comment) {
        LikeAndDislike lad = comment.getLikeAndDislike();
        commentDao.delete(comment);
        likeAndDislikeDao.delete(lad);

    }

    /**
     * Obtains all the {@link Comment} for an {@link Album}
     * 
     * @param album
     *            It's the {@link Album} they belong {@link Comment}.
     * 
     * @return List<Comment> A list of {@link Album} {@link Comment}.
     * 
     * @see es.udc.fi.dc.photoalbum.spring.CommentService#
     *      getComments(Album)
     */
    public List<Comment> getComments(Album album) {
        return commentDao.getComments(album);
    }

    /**
     * Obtains all the {@link Comment} for an {@link File}
     * 
     * @param file
     *            It's the {@link File} they belong {@link Comment}.
     * 
     * @return List<Comment> A list of {@link File} {@link Comment}.
     * @see es.udc.fi.dc.photoalbum.spring.CommentService#
     *      getComments(File)
     */
    public List<Comment> getComments(File file) {
        return commentDao.getComments(file);
    }

    /**
     * Obtains the comments for an {@link Album} paginated.
     * 
     * @param album
     *            It's the {@link Album} they belong {@link Comment}.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtained
     * 
     * @return List<Comment> A list of {@link Album} {@link Comment}
     * @see es.udc.fi.dc.photoalbum.spring.CommentService#
     *      getCommentsPaging(Album, int, int)
     */
    public List<Comment> getCommentsPaging(Album album, int first,
            int count) {
        return commentDao.getCommentsPaging(album, first, count);
    }

    /**
     * Obtains the comments for an {@link File} paginated.
     * 
     * @param file
     *            It's the {@link File} they belong {@link Comment}.
     * @param first
     *            The first element that obtains.
     * @param count
     *            The number of elements that will be obtains.
     * 
     * @return List<Comment>
     * @see es.udc.fi.dc.photoalbum.spring.CommentService
     *      #getCommentsPaging(File, int, int)
     */
    public List<Comment> getCommentsPaging(File file, int first,
            int count) {
        return commentDao.getCommentsPaging(file, first, count);
    }

}
