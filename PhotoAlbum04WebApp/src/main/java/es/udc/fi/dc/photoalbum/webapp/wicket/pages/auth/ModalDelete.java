package es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.UserService;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;

/**
 * Modal window {@link WebPage} for deleting a {@link User}.
 */
@SuppressWarnings("serial")
public class ModalDelete extends WebPage {

    /**
     * User password.
     */
    private String pass;

    /**
     * @see UserService
     */
    @SpringBean
    private UserService userService;

    /**
     * The parent window, where this window was invocated.
     */
    private ModalWindow window;

    /**
     * Feedback panel.
     */
    private FeedbackPanel feedback;
    /**
     * Constructor only for test
     */
    public ModalDelete() {
        this(new ModalWindow("modal"));
    }

    /**
     * Defines a {@link ModalDelete} window.
     * 
     * @param window
     *            {@link #window Parent window}
     */
    public ModalDelete(final ModalWindow window) {
        this.window = window;
        final Form<User> form = new Form<User>("form",
                new CompoundPropertyModel<User>(new User()));
        this.feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        form.add(feedback);
        form.add(createButtonOk());
        form.add(createButtonCancel());
        PasswordTextField password = new PasswordTextField(
                "password", new PropertyModel<String>(this, "pass"));
        password.setLabel(new StringResourceModel("modalDelete.pass",
                this, null));
        form.add(password);
        add(form);
    }

    /**
     * Creates the {@link AjaxButton} for accepting the delete action.
     * 
     * @return Accept button
     */
    private AjaxButton createButtonOk() {
        return new AjaxButton("buttonOk") {
            public void onSubmit(AjaxRequestTarget target,
                    Form<?> form) {
                User user = new User(null, userService.getById(
                        ((MySession) Session.get()).getuId())
                        .getEmail(), pass);
                User userDB = userService.getUser(user.getEmail(),
                        user.getPassword());
                if (!(userDB == null)) {
                    window.close(target);
                    userService.delete(userService
                            .getById(((MySession) Session.get())
                                    .getuId()));
                    getSession().invalidate();
                } else {
                    error(new StringResourceModel(
                            "modalDelete.wrongPass", this, null)
                            .getString());
                    target.add(feedback);
                }
            }

            public void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedback);
            }
        };
    }

    /**
     * Creates the {@link AjaxButton} for canceling the delete action.
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
                        ModalDelete.class, "ModalDelete.css")));
    }
}
