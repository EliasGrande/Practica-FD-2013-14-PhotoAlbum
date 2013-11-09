package es.udc.fi.dc.photoalbum.wicket.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.Session;
import es.udc.fi.dc.photoalbum.wicket.MySession;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.utils.TimeAgoCalendarFormat;
import es.udc.fi.dc.photoalbum.wicket.models.CommentsModel;

@SuppressWarnings("serial")
public class ShowCommentsPanel extends Panel {

    private CommentsModel commentsModel;
    private TimeAgoCalendarFormat calendarFormat;

    private static final int COMMENTS_PER_PANEL = 10;

    public ShowCommentsPanel(String id, Album album) {
        this(id, album, null);
    }

    public ShowCommentsPanel(String id, File file) {
        this(id, null, file);
    }

    private ShowCommentsPanel(String id, Album album, File file) {
        super(id);
        int userId = ((MySession) Session.get()).getuId();
        commentsModel = new CommentsModel(album, file,
                COMMENTS_PER_PANEL, userId);
        calendarFormat = new TimeAgoCalendarFormat(this);

        // ajax data container
        final WebMarkupContainer commentContainer = new WebMarkupContainer(
                "commentContainer");
        commentContainer.setOutputMarkupId(true);
        add(commentContainer);

        ListView<Comment> commentsView = new ListView<Comment>(
                "comment", commentsModel) {
            @Override
            protected void populateItem(ListItem<Comment> item) {
                Comment comment = item.getModelObject();
                item.add(new Label("user", comment.getUser()
                        .getEmail()));
                item.add(new Label("date", calendarFormat
                        .format(comment.getDate())));
                item.add(new Label("text", comment.getText()));
                item.add(new VotePanel("vote", comment
                        .getLikeAndDislike(), commentsModel
                        .getVoted(comment)));
            }
        };
        commentContainer.add(commentsView);

        AjaxLink<String> moreLink = new AjaxLink<String>("moreLink") {
            public void onClick(AjaxRequestTarget target) {
                target.add(commentContainer);
                // avoid the annoying scrolling-down
                target.focusComponent(null);
            }

            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                if (!commentsModel.hasMore())
                    tag.put("class", "hidden");
            }

            @Override
            public boolean isEnabled() {
                return commentsModel.hasMore();
            }
        };
        commentContainer.add(moreLink);

    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderCSSReference("css/CommentAndVote.css");
    }
}
