package es.udc.fi.dc.photoalbum.wicket.pages.auth;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import es.udc.fi.dc.photoalbum.wicket.pages.auth.share.SharedUsers;
import es.udc.fi.dc.photoalbum.wicket.pages.nonAuth.BasePage;

@SuppressWarnings("serial")
public class BasePageAuth extends BasePage {

	public BasePageAuth(final PageParameters parameters) {
		super(parameters);
		add(new BookmarkablePageLink<Void>("albums", Albums.class));
		add(new BookmarkablePageLink<Void>("sharedUsers", SharedUsers.class));
		add(new BookmarkablePageLink<Void>("profile", Profile.class));
	}
}
