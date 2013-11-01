package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.CommentDao;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislikeDao;
import es.udc.fi.dc.photoalbum.hibernate.User;

@Transactional
public class CommentServiceImpl implements CommentService{
	
	/* LikeAndDislikeDao */
	private LikeAndDislikeDao likeAndDislikeDao;

	public LikeAndDislikeDao getLikeAndDislikeDao() {
		return this.likeAndDislikeDao;
	}

	public void setLikeAndDislikeDao(LikeAndDislikeDao likeAndDislikeDao) {
		this.likeAndDislikeDao = likeAndDislikeDao;
	}
	
	/* CommentDao */
	private CommentDao commentDao;

	public CommentDao getCommentDao() {
		return this.commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	/*IMPLEMENTATION*/

	public void create(User userThatComment, Album album, String text) {
		LikeAndDislike likeAndDislike = new LikeAndDislike();
		likeAndDislikeDao.create(likeAndDislike);
		Comment comment = new Comment(likeAndDislike, userThatComment, text, album, null);
		commentDao.create(comment);
	}


	public void create(User userThatComment, File file, String text) {
		LikeAndDislike likeAndDislike = new LikeAndDislike();
		likeAndDislikeDao.create(likeAndDislike);
		Comment comment = new Comment(likeAndDislike, userThatComment, text, null, file);
		commentDao.create(comment);
	}


	public void delete(Comment comment) {
		LikeAndDislike lad = comment.getLikeAndDislike();
		commentDao.delete(comment);
		likeAndDislikeDao.delete(lad);
		
	}
	
	public ArrayList<Comment> getComments(Album album) {
		return commentDao.getComments(album);
	}


	public ArrayList<Comment> getComments(File file) {
		return commentDao.getComments(file);
	}


	public ArrayList<Comment> getCommentsPaging(Album album, int first,
			int count) {
		return commentDao.getCommentsPaging(album, first, count);
	}


	public ArrayList<Comment> getCommentsPaging(File file, int first, int count) {
		return commentDao.getCommentsPaging(file, first, count);
	}

}
