package es.udc.fi.dc.photoalbum.wicket.panels;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.CommentService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.MySession;
/**
 * Reusable panel for adding comments to files or albums.
 */
@SuppressWarnings("serial")
public class AddCommentPanel extends Panel {

    /**
     * @see {@link UserService}
     */
    @SpringBean
    private UserService userService;

    /**
     * @see {@link CommentService}
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
     *            wicket:id of the panel
     * @param album
     *            the {@link #album}
     */
    public AddCommentPanel(String id, Album album) {
        this(id, album, null);
    }

    /**
     * Defines an {@link AddCommentPanel} object of a file.
     * 
     * @param id
     *            wicket:id of the panel
     * @param file
     *            the {@link #file}
     */
    public AddCommentPanel(String id, File file) {
        this(id, null, file);
    }

    /**
     * Defines an {@link AddCommentPanel} object of a file or an
     * album, either album or file must be null, but not both.
     * 
     * @param id
     *            wicket:id of the panel
     * @param album
     *            the {@link #album}, or null for a file panel
     * @param file
     *            the {@link #file}, or null for an album panel
     */
    private AddCommentPanel(String id, Album album, File file) {
        super(id);
        this.album = album;
        this.file = file;

        final TextArea<String> text = new TextArea<String>("text",
                Model.of(""));
        text.setRequired(true);

        Form<Void> form = new Form<Void>("addCommentForm") {
            @Override
            protected void onSubmit() {
                String textString = text.getModelObject();
                if (validateComment(textString)) {
                    createComment(textString);
                }
                setResponsePage(newResponsePage());
            }
        };
        add(form);
        form.add(text);
        form.add(new Label("maxLength", String
                .valueOf(Comment.MAX_TEXT_LENGTH)));
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

    /**
     * Overrides {@link Panel#renderHead(IHeaderResponse)} adding the
     * needed javascript and cascade style sheet references for this
     * panel.
     * 
     * @param response
     *            Response object
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderJavaScriptReference("http://ajax.googleap"
                + "is.com/ajax/libs/jquery/1.10.2/jquery.min.js");
        response.renderJavaScriptReference("js/AddCommentPanel.js");
        response.renderCSSReference("css/CommentAndVote.css");
    }

    /**
     * Returns a new instance of the page holding this component using
     * the actual page class {@link Constructor} which overrides
     * {@link Page(PageParameters)}, with the same parameters, to use
     * it as response page on submit new comments.
     * 
     * @return New instance of the page holding this component
     */
    private WebPage newResponsePage() {
        Exception ex;
        try {
            @SuppressWarnings("rawtypes")
            Class[] parameterTypes = new Class[] { PageParameters.class };
            @SuppressWarnings("rawtypes")
            Constructor constructor = this.getPage().getClass()
                    .getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            return (WebPage) constructor.newInstance(this.getPage()
                    .getPageParameters());
        } catch (NoSuchMethodException e) {
            ex = e;
        } catch (InvocationTargetException e) {
            ex = e;
        } catch (IllegalAccessException e) {
            ex = e;
        } catch (InstantiationException e) {
            ex = e;
        }
        // this should never happen
        AbortWithHttpErrorCodeException internalError = new AbortWithHttpErrorCodeException(
                HttpURLConnection.HTTP_INTERNAL_ERROR,
                ex.getMessage());
        internalError.setStackTrace(ex.getStackTrace());
        throw internalError;
    }
}
