package es.udc.fi.dc.photoalbum.wicket.pages.auth;

/**
 * Modal window {@link WebPage} for renaming an {@link Album}.
 */
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.spring.AlbumService;

@SuppressWarnings("serial")
public class ModalRename extends WebPage {

    /**
     * @see {@link #getResult()}
     */
    private String result;

    /**
     * @see {@link AlbumService}
     */
    @SpringBean
    private AlbumService albumService;

    /**
     * The parent window, where this window was invocated.
     */
    private ModalWindow window;

    /**
     * Feedback panel.
     */
    private FeedbackPanel feedback;

    /**
     * The albums which is going to be renamed.
     */
    private Album album;

    /**
     * New {@link Album#getName() album name}.
     * 
     * @return New album name.
     */
    @SuppressWarnings("unused")
    private String getResult() {
        return this.result;
    }

    /**
     * Setter for {@link #getResult()}.
     * 
     * @param result
     *            New album name
     */
    @SuppressWarnings("unused")
    private void setResult(String result) {
        this.result = result;
    }

    /**
     * Defines a {@link ModalRename} window for an {@link album}.
     * 
     * @param album
     *            {@link #album Album} which is going to be renamed
     * @param window
     *            {@link #window Parent window}
     */
    public ModalRename(final Album album, final ModalWindow window) {
        this.result = album.getName();
        this.window = window;
        this.album = album;
        Form<Void> form = new Form<Void>("form");
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        form.add(feedback);
        this.feedback = feedback;
        RequiredTextField<String> name = new RequiredTextField<String>(
                "name", new PropertyModel<String>(this, "result"));
        name.setLabel(new StringResourceModel(
                "modalRename.albumName", this, null));
        form.add(name);
        form.add(createButtonOk());
        form.add(createButtonCancel());
        add(form);
    }

    /**
     * Creates the {@link AjaxButton} for accepting the rename action.
     * 
     * @return Accept button
     */
    private AjaxButton createButtonOk() {
        return new AjaxButton("buttonOk") {
            public void onSubmit(AjaxRequestTarget target,
                    Form<?> form) {
                albumService.rename(album, result);
                window.close(target);
            }

            public void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedback);
            }
        };
    }

    /**
     * Creates the {@link AjaxButton} for canceling the rename action.
     * 
     * @return Cancel button
     */
    private AjaxButton createButtonCancel() {
        return new AjaxButton("buttonCancel") {
            public void onSubmit(AjaxRequestTarget target,
                    Form<?> form) {
                window.close(target);
            }

            public void onError(AjaxRequestTarget target, Form<?> form) {
                window.close(target);
            }
        };
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(
                        ModalRename.class, "ModalRename.css")));
    }
}
