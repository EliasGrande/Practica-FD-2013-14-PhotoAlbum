package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.CommentService;

@SuppressWarnings("serial")
public class CommentsModel extends LoadableDetachableModel<ArrayList<Comment>> {

	@SpringBean
	private CommentService commentService;
	
	private Album album;
	private File file;

	private ArrayList<Comment> comments;
	private boolean hasMoreComments;
	private int index;
	private int count;

	public CommentsModel(Album album, File file, int count) {
		this.album = album;
		this.file = file;
		this.index = 0;
		this.count = count;
		this.comments = new ArrayList<Comment>();
		Injector.get().inject(this);
	}

	protected ArrayList<Comment> load() {
		getMore();
		return comments;
	}
	
	public void getMore() {
		ArrayList<Comment> moreComments = (file==null)
				? commentService.getCommentsPaging(album, index, count+1)
				: commentService.getCommentsPaging(file, index, count+1);
		hasMoreComments = (moreComments.size()>count);
		if (hasMoreComments)
			moreComments.remove(count);
		index += moreComments.size();
		comments.addAll(moreComments);
	}
	
	public boolean hasMore() {
		return hasMoreComments;
	}
}