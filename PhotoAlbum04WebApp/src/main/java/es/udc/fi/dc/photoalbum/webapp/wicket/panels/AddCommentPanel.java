package es.udc.fi.dc.photoalbum.webapp.wicket.panels;

import org.apache.wicket.Session;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.Comment;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.CommentService;
import es.udc.fi.dc.photoalbum.model.spring.UserService;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;

/**
 * Reusable panel for adding comments to files or albums.
 */
@SuppressWarnings("serial")
public class AddCommentPanel extends Panel {

    /**
     * @see UserService
     */
    @SpringBean
    private UserService userService;

    /**
     * @see CommentService
     */
    @SpringBean
    private CommentService commentService;

    /**
     * The album, or null if the panel is for a file.
     */
    private Album album;

    /**
     * The file, or null if the panel is for an album.
     */
    private File file;

    /**
     * Defines an {@link AddCommentPanel} object of an album.
     * 
     * @param id
     *            This panel {@code wicket:id}
     * @param commentsPanel
     *            Comment panel, to reload it on adding comments, it
     *            can be null
     * @param album
     *            The {@link Album} being commented
     */
    public AddCommentPanel(String id,
            ShowCommentsPanel commentsPanel, Album album) {
        this(id, commentsPanel, album, null);
    }

    /**
     * Defines an {@link AddCommentPanel} object of a file.
     * 
     * @param id
     *            This panel {@code wicket:id}
     * @param commentsPanel
     *            Comment panel, to reload it on adding comments, it
     *            can be null
     * @param file
     *            The {@link File} being commented
     */
    public AddCommentPanel(String id,
            ShowCommentsPanel commentsPanel, File file) {
        this(id, commentsPanel, null, file);
    }

    /**
     * Defines an {@link AddCommentPanel} object of a file or an
     * album, either album or file must be null, but not both.
     * 
     * @param id
     *            This panel {@code wicket:id}
     * @param commentsPanel
     *            Comment panel, to reload it on adding comments, it
     *            can be {@code null}
     * @param album
     *            The {@link #album}, or {@code null} for a file panel
     * @param file
     *            The {@link #file}, or {@code null} for an album
     *            panel
     */
    private AddCommentPanel(String id,
            ShowCommentsPanel commentsPanel, Album album, File file) {
        super(id);
        this.album = album;
        this.file = file;

        final ShowCommentsPanel showCommentsPanel = commentsPanel;
        final TextArea<String> text = new TextArea<String>("text",
                Model.of(""));
        text.setRequired(true);

        Form<Void> form = new Form<Void>("addCommentForm") {
            @Override
            protected void onSubmit() {
                String textString = cleanComment(text
                        .getModelObject());
                if (validateComment(textString)) {
                    createComment(textString);
                    if (showCommentsPanel != null) {
                        showCommentsPanel.reload();
                    }
                }
                setResponsePage(this.getPage());
            }
        };
        add(form);
        form.add(text);
        form.add(new Label("maxLength", String
                .valueOf(Comment.MAX_TEXT_LENGTH)));
    }

    /**
     * Gets rid of \r characters and anywhere there are more than 2
     * consecutive \n delete the extra \n characters.
     * 
     * @param text
     *            The comment text
     * @return Cleaned text
     */
    private String cleanComment(String text) {
        return text.replaceAll("\\r", "").replaceAll("\\n\\n+",
                "\n\n");
    }

    /**
     * Returns {@code true} if the comment text is valid or shows an
     * error feedback and returns {@code false} if not.
     * 
     * @param text
     *            The comment text
     * @return {@code true} if the text is valid
     */
    private boolean validateComment(String text) {
        int maxLength = Comment.MAX_TEXT_LENGTH;
        if (text.length() > maxLength) {
            error(new StringResourceModel(
                    "comments.add.error.maxlength", this, null)
                    .getString().replace("{MAX_LENGTH}",
                            String.valueOf(maxLength)));
            return false;
        } else {
            return true;
        }
    }

    /**
     * Adds a comment and shows the success feedback.
     * 
     * @param text
     *            The text of the comment
     */
    private void createComment(String text) {
        User user = userService.getById(((MySession) Session.get())
                .getuId());
        if (album == null) {
            commentService.create(user, file, text);
        } else {
            commentService.create(user, album, text);
        }
        info(new StringResourceModel("comments.add.created", this,
                null).getString());
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem
                .forReference(new JavaScriptResourceReference(
                        AddCommentPanel.class, "AddCommentPanel.js")));
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(
                        CommentAndVotePanel.class,
                        "CommentAndVotePanel.css")));
    }
}
