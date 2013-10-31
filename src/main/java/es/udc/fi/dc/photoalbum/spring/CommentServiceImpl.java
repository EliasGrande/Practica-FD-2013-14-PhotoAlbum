package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;

public class CommentServiceImpl implements CommentService{

	@Override
	public void create(User userOwnComment, Album album, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(User userOwnComment, File file, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Comment comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Comment> getComments(Album album) {
		// TODO Auto-generated method stub
		return new ArrayList<Comment>();
	}

	@Override
	public ArrayList<Comment> getComments(File file) {
		// TODO Auto-generated method stub
		return new ArrayList<Comment>();
	}

	@Override
	public ArrayList<Comment> getCommentsPaging(Album album, int first,
			int count) {
		// TODO Auto-generated method stub
		return new ArrayList<Comment>();
	}

	@Override
	public ArrayList<Comment> getCommentsPaging(File file, int first, int count) {
		// TODO Auto-generated method stub
		return new ArrayList<Comment>();
	}

}
