package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.Voted;
import es.udc.fi.dc.photoalbum.spring.CommentService;
import es.udc.fi.dc.photoalbum.spring.VotedService;

@SuppressWarnings("serial")
public class CommentsModel extends LoadableDetachableModel<ArrayList<Comment>> {

	@SpringBean
	private CommentService commentService;
	@SpringBean
	private VotedService votedService;
	
	private int userId;
	private Album album;
	private File file;
	private int index;
	private int count;
	private ArrayList<Comment> commentCache;
	private boolean hasMoreComments;
	private HashMap<Integer, Voted> voteCache;

	public CommentsModel(Album album, File file, int count, int userId) {
		this.userId = userId;
		this.album = album;
		this.file = file;
		this.index = 0;
		this.count = count;
		this.commentCache = new ArrayList<Comment>();
		this.hasMoreComments = true;
		this.voteCache = new HashMap<Integer, Voted>();
		Injector.get().inject(this);
	}
	
	public Voted getVoted(Comment comment) {
		return voteCache.get(comment.getLikeAndDislike().getId());
	}
	
	public boolean hasMore() {
		return hasMoreComments;
	}

	@Override
	protected ArrayList<Comment> load() {
		if (hasMore())
			getMore();
		return commentCache;
	}
	
	private void getMore() {
		// update commentCache
		ArrayList<Comment> moreComments = (file==null)
				? commentService.getCommentsPaging(album, index, count+1)
				: commentService.getCommentsPaging(file, index, count+1);
		hasMoreComments = (moreComments.size() > count);
		if (hasMoreComments)
			moreComments.remove(count);
		index += moreComments.size();
		commentCache.addAll(moreComments);
		// update voteCache
		ArrayList<Integer> likeAndDislikeIdList = new ArrayList<Integer>();
		Iterator<Comment> iterComments = moreComments.iterator();
		while (iterComments.hasNext())
			likeAndDislikeIdList.add(iterComments.next().getLikeAndDislike().getId());
		Iterator<Voted> iterVoted = votedService.getVoted(likeAndDislikeIdList,
				userId).iterator();
		Voted voted;
		while (iterVoted.hasNext()) {
			voted = iterVoted.next();
			voteCache.put(voted.getLikeAndDislike().getId(), voted);
		}
	}
}