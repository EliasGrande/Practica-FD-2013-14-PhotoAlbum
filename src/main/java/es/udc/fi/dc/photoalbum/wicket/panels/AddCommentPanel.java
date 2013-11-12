package es.udc.fi.dc.photoalbum.wicket.panels;

import java.lang.reflect.Constructor;

import org.apache.wicket.Session;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.CommentService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.Albums;

@SuppressWarnings("serial")
public class AddCommentPanel extends Panel {

	@SpringBean
	private UserService userService;
	@SpringBean
	private CommentService commentService;
	private Album album;
	private File file;
	private WebPage responsePage;

	public AddCommentPanel(String id, WebPage responsePage, Album album) {
		this(id, responsePage, album, null);
	}

	public AddCommentPanel(String id, WebPage responsePage, File file) {
		this(id, responsePage, null, file);
	}

	private AddCommentPanel(String id, WebPage responsePage, Album _album, File _file) {
		super(id);
		this.responsePage = responsePage;
		this.album = _album;
		this.file = _file;

		final TextArea<String> text = new TextArea<String>("text", Model.of(""));
		text.setRequired(true);
		
		Form<?> form = new Form<Void>("addCommentForm") {
			@Override
			protected void onSubmit() {
				User user = userService.getById(((MySession) Session.get())
						.getuId());
				String textString = text.getModelObject();
				
				int len = textString.length();
				int maxLen = Comment.MAX_TEXT_LENGTH;
				if (len > maxLen) {
					error(new StringResourceModel("comments.add.error.maxlength", this, null)
							.getString().replace("{MAX_LENGTH}",  String.valueOf(maxLen)));
					return;
				}
				try {
					if (album==null)
						commentService.create(user, file, textString);
					else
						commentService.create(user, album, textString);
					info(new StringResourceModel("comments.add.created", this, null)
							.getString());
					setResponsePage(newResponsePage());
				} catch (RuntimeException e) {
					error(new StringResourceModel("comments.add.exception", this, null)
							.getString());
				}
			}
		};
		
		add(form);
		form.add(text);

		form.add(new Label("maxLength", String.valueOf(Comment.MAX_TEXT_LENGTH)));
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem
				.forReference(new JavaScriptResourceReference(
						AddCommentPanel.class,
						"http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js")));
		response.render(JavaScriptHeaderItem
				.forReference(new JavaScriptResourceReference(
						AddCommentPanel.class, "js/AddCommentPanel.js")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				AddCommentPanel.class, "css/CommentAndVote.css")));
	}

	/**
	 * Instancia un nuevo objeto WebPage de la clase de responsePage y con los
	 * PageParameters de response page.
	 */
	@SuppressWarnings("rawtypes")
	public WebPage newResponsePage() {
		try {
			Class[] parameterTypes = new Class[] { PageParameters.class };
			Constructor constructor = this.responsePage.getClass()
					.getDeclaredConstructor(parameterTypes);
			constructor.setAccessible(true);
			return (WebPage) constructor.newInstance(this.responsePage
					.getPageParameters());
		} catch (Exception e) {
			e.printStackTrace();
			return this.responsePage;
		}
	}
}
