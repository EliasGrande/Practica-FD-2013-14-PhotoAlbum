package es.udc.fi.dc.photoalbum.webapp.wicket.pages.nonAuth;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.UserService;
import es.udc.fi.dc.photoalbum.util.utils.MD5;
import es.udc.fi.dc.photoalbum.util.utils.RandomString;
import es.udc.fi.dc.photoalbum.webapp.wicket.MyAjaxButton;

/**
 * The page contains a form that allows recover the password.
 */
@SuppressWarnings("serial")
public class ForgotPassword extends BasePage {
    /**
     * @see UserService
     */
    @SpringBean
    private UserService userService;
    /**
     * {@link FeedbackPanel}
     */
    private FeedbackPanel feedback;

    /**
     * Constructor for ForgotPassword.
     * 
     * @param parameters
     *            The parameters necessary for the page render.
     */
    public ForgotPassword(final PageParameters parameters) {
        super(parameters);
        this.feedback = new FeedbackPanel("feedback");
        this.feedback.setOutputMarkupId(true);
        add(this.feedback);
        add(createFormFroget());
    }

    /**
     * Method that create a form to restore password.
     * 
     * 
     * @return Form<{@link User}> that contains a form that allows
     *         recover the password.
     * */
    private Form<User> createFormFroget() {
        Form<User> form = new Form<User>("form",
                new CompoundPropertyModel<User>(new User())) {
            @Override
            protected void onSubmit() {
                User user = getModelObject();
                User existedUser = userService.getUser(user);
                if (existedUser == null) {
                    error(new StringResourceModel("share.noUser",
                            this, null).getString());
                } else {
                    String randomPass = RandomString.generate();
                    existedUser.setPassword(MD5.getHash(randomPass));
                    info(new StringResourceModel(
                            "forgotPassword.complete", this, null)
                            .getString());
                }
            }
        };
        RequiredTextField<String> email = new RequiredTextField<String>(
                "email");
        email.setLabel(new StringResourceModel("login.emailField",
                this, null));
        email.add(EmailAddressValidator.getInstance());
        form.add(email);
        form.add(new MyAjaxButton("ajax-button", form, feedback));
        return form;
    }

    /**
     * Method renderHead allows to use specific css in this page.
     * 
     * @param response
     *            IHeaderResponse
     * 
     * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(IHeaderResponse)
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(
                        ForgotPassword.class, "ForgotPassword.css")));
    }
}
