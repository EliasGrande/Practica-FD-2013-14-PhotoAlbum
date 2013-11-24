package es.udc.fi.dc.photoalbum.wicket.panels;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.util.string.Strings;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.utils.TimeAgoCalendarFormat;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.models.CommentsModel;

/**
 * Reusable panel for showing and voting the comments of an album or
 * file.
 */
@SuppressWarnings("serial")
public class ShowCommentsPanel extends Panel {

    /**
     * {@link #commentContainer} {@code wicket:id}.
     */
    public static final String CONTAINER_ID = "commentContainer";

    /**
     * Comment author {@code wicket:id}.
     */
    public static final String USER_LABEL_ID = "user";

    /**
     * Comment date {@code wicket:id}.
     */
    public static final String DATE_LABEL_ID = "date";

    /**
     * Comment text {@code wicket:id}.
     */
    public static final String TEXT_LABEL_ID = "text";

    /**
     * Comment {@link VotePanel} {@code wicket:id}.
     */
    public static final String VOTE_LABEL_ID = "vote";

    /**
     * More-link {@code wicket:id}.
     */
    public static final String MORE_LINK_ID = "moreLink";

    /**
     * Class attribute of the more-link when there is no more
     * comments.
     */
    public static final String NO_MORE_COMMENTS_LINK_CLASS = "hidden";

    /**
     * Number of comments requested when the page is rendered and
     * every time the user press the more-link.
     */
    private static final int COMMENTS_PER_PANEL = 10;

    /**
     * Stores the list of comments and votes.
     */
    private CommentsModel commentsModel;

    /**
     * Comment date format.
     */
    private TimeAgoCalendarFormat calendarFormat;

    /**
     * Container for the comments-view and more-link refreshable by
     * ajax using the more-link.
     */
    private WebMarkupContainer commentContainer;

    /**
     * Defines a {@link ShowCommentsPanel} object of an {@link Album}.
     * 
     * @param id
     *            ShowCommentsPanel {@code wicket:id}
     * @param album
     *            Album object
     */
    public ShowCommentsPanel(String id, Album album) {
        this(id, album, null);
    }

    /**
     * Defines a {@link ShowCommentsPanel} object of a {@link File}.
     * 
     * @param id
     *            ShowCommentsPanel {@code wicket:id}
     * @param file
     *            File object
     */
    public ShowCommentsPanel(String id, File file) {
        this(id, null, file);
    }

    /**
     * Defines a {@link ShowCommentsPanel} object of an {@link Album}
     * or {@link File}, either the album or the file must be null, but
     * not both.
     * 
     * @param id
     *            ShowCommentsPanel {@code wicket:id}
     * @param album
     *            Album object, or null for file comments
     * @param file
     *            File object, or null for album comments
     */
    private ShowCommentsPanel(String id, Album album, File file) {
        super(id);
        int userId = ((MySession) Session.get()).getuId();
        commentsModel = new CommentsModel(album, file,
                COMMENTS_PER_PANEL, userId);
        calendarFormat = new TimeAgoCalendarFormat(this);

        makeAjaxContainer();
        makeCommentsView();
        makeMoreLink();
    }

    /**
     * Initializes {@link #commentContainer} and adds it to the panel.
     */
    private void makeAjaxContainer() {
        commentContainer = new WebMarkupContainer(CONTAINER_ID);
        commentContainer.setOutputMarkupId(true);
        add(commentContainer);
    }

    /**
     * Creates the comments-view and adds it to the
     * {@link #commentContainer}.
     */
    private void makeCommentsView() {
        ListView<Comment> commentsView = new ListView<Comment>(
                "comment", commentsModel) {
            @Override
            protected void populateItem(ListItem<Comment> item) {
                Comment comment = item.getModelObject();
                item.add(new Label(USER_LABEL_ID, comment.getUser()
                        .getEmail()));
                item.add(new Label(DATE_LABEL_ID, calendarFormat
                        .format(comment.getDate())));
                String commentHtml = Strings
                        .escapeMarkup(comment.getText()).toString()
                        .replaceAll("\\n", "<br />");
                item.add(new Label(TEXT_LABEL_ID, commentHtml)
                        .setEscapeModelStrings(false));
                item.add(new VotePanel(VOTE_LABEL_ID, comment
                        .getLikeAndDislike(), commentsModel
                        .getVoted(comment)));
            }
        };
        commentContainer.add(commentsView);
    }

    /**
     * Creates the more-link and adds it to the
     * {@link #commentContainer}.
     */
    private void makeMoreLink() {
        AjaxLink<String> moreLink = new AjaxLink<String>(MORE_LINK_ID) {
            public void onClick(AjaxRequestTarget target) {
                target.add(commentContainer);
                // avoid the annoying scrolling-down
                target.focusComponent(null);
            }

            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                if (!commentsModel.hasMore()) {
                    tag.put("class", NO_MORE_COMMENTS_LINK_CLASS);
                }
            }

            @Override
            public boolean isEnabled() {
                return commentsModel.hasMore();
            }
        };
        commentContainer.add(moreLink);
    }
    
    /**
     * Reloads the panel.
     */
    public void reload() {
        commentsModel.wipeCache();
        this.replace(commentContainer);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(
                        CommentAndVotePanel.class,
                        "CommentAndVotePanel.css")));
    }
}
