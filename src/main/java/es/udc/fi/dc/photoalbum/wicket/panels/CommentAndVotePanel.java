package es.udc.fi.dc.photoalbum.wicket.panels;

import org.apache.wicket.markup.html.panel.Panel;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;

/**
 * Reusable panel for voting an album or file, adding comments to it,
 * and showing (and voting) comments.
 */
@SuppressWarnings("serial")
public class CommentAndVotePanel extends Panel {

    /**
     * Inner {@link VotePanel} {@code wicket:id}.
     */
    public static final String VOTE_ID = "vote";

    /**
     * Inner {@link AddCommentPanel} {@code wicket:id}.
     */
    public static final String ADD_COMMENT_ID = "addComment";

    /**
     * Inner {@link ShowCommentsPanel} {@code wicket:id}.
     */
    public static final String SHOW_COMMENTS_ID = "showComments";

    /**
     * Defines a {@link CommentAndVotePanel} object of an
     * {@link Album}.
     * 
     * @param id
     *            CommentAndVotePanel {@code wicket:id}
     * @param album
     *            Album object
     */
    public CommentAndVotePanel(String id, Album album) {
        super(id);
        add(new VotePanel(VOTE_ID, album.getLikeAndDislike()));
        ShowCommentsPanel commentsPanel = new ShowCommentsPanel(
                SHOW_COMMENTS_ID, album);
        add(new AddCommentPanel(ADD_COMMENT_ID, commentsPanel, album));
        add(commentsPanel);
    }

    /**
     * Defines a {@link CommentAndVotePanel} object of a {@link File}.
     * 
     * @param id
     *            CommentAndVotePanel {@code wicket:id}
     * @param file
     *            File object
     */
    public CommentAndVotePanel(String id, File file) {
        super(id);
        add(new VotePanel(VOTE_ID, file.getLikeAndDislike()));
        ShowCommentsPanel commentsPanel = new ShowCommentsPanel(
                SHOW_COMMENTS_ID, file);
        add(new AddCommentPanel(ADD_COMMENT_ID, commentsPanel, file));
        add(commentsPanel);
    }
}
