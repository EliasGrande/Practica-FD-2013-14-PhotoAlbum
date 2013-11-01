package es.udc.fi.dc.photoalbum.wicket.panels;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;

@SuppressWarnings("serial")
public class CommentAndVotePanel extends Panel {
	
	public CommentAndVotePanel(String id, WebPage responsePage, Album album) {
		super(id);
		add(new VotePanel("vote", album.getLikeAndDislike()));
		add(new AddCommentPanel("addComment", responsePage, album));
		add(new ShowCommentsPanel("showComments", album));
	}

	public CommentAndVotePanel(String id, WebPage responsePage, File file) {
		super(id);
		add(new VotePanel("vote", file.getLikeAndDislike()));
		add(new AddCommentPanel("addComment", responsePage, file));
		add(new ShowCommentsPanel("showComments", file));
	}
}
