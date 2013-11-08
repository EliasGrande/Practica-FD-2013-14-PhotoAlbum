package es.udc.fi.dc.photoalbum.wicket.pages.nonAuth;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.MD5;
import es.udc.fi.dc.photoalbum.utils.RandomString;
import es.udc.fi.dc.photoalbum.wicket.MyAjaxButton;

@SuppressWarnings("serial")
public class ForgotPassword extends BasePage {
	@SpringBean
	private UserService userService;
	private FeedbackPanel feedback;

	public ForgotPassword(final PageParameters parameters) {
		super(parameters);
		this.feedback = new FeedbackPanel("feedback");
		this.feedback.setOutputMarkupId(true);
		add(this.feedback);
		add(createFormFroget());
	}

	private Form<User> createFormFroget() {
		Form<User> form = new Form<User>("form",
				new CompoundPropertyModel<User>(new User())) {
			@Override
			protected void onSubmit() {
				User user = getModelObject();
				User existedUser = userService.getUser(user);
				if (existedUser == null) {
					error(new StringResourceModel("share.noUser", this, null)
							.getString());
				} else {
					String randomPass = RandomString.generate();
					existedUser.setPassword(MD5.getHash(randomPass));
					System.out.println(randomPass);
					info(new StringResourceModel("forgotPassword.complete",
							this, null).getString());
				}
			}
		};
		RequiredTextField<String> email = new RequiredTextField<String>("email");
		email.setLabel(new StringResourceModel("login.emailField", this, null));
		email.add(EmailAddressValidator.getInstance());
		form.add(email);
		form.add(new MyAjaxButton("ajax-button", form, feedback));
		return form;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("css/ForgotPassword.css");
	}
}
