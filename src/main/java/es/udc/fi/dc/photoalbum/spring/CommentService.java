package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;

public interface CommentService {
	
	void create(User userOwnComment, Album album, String text);
	
	void create(User userOwnComment, File file, String text);
	
	void delete(Comment comment);
	
	void LikeComment(Comment comment);
	
	void DislikeComment(Comment comment);
	
	ArrayList<Comment> getComments(Album album);

	ArrayList<Comment> getComments(File file);
	
	ArrayList<Comment> getCommentsPaging(Album album, int first, int count);
	
	ArrayList<Comment> getCommentsPaging(File file, int first, int count);
	
	
}
