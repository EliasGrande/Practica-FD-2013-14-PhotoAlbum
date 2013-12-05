package es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;

import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.pub.PublicAlbums;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.SharedUsers;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.SearchByTag;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.nonAuth.BasePage;

/**
 * Base {@link WebPage} for all the pages which requires a logged
 * user.
 */
@SuppressWarnings("serial")
public class BasePageAuth extends BasePage {

    /**
     * Defines an {@link BasePageAuth} page.
     * 
     * @param parameters
     *            PageParameters used by the inherit constructor
     *            {@link BasePage#BasePage(PageParameters)}
     */
    public BasePageAuth(final PageParameters parameters) {
        super(parameters);
        add(new BookmarkablePageLink<Void>("albums", Albums.class));
        add(new BookmarkablePageLink<Void>("sharedUsers",
                SharedUsers.class));
        add(new BookmarkablePageLink<Void>("publicAlbums",
                PublicAlbums.class));
        add(new BookmarkablePageLink<Void>("searchByTag",
                SearchByTag.class));
        add(new BookmarkablePageLink<Void>("profile", Profile.class));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(
                        BasePageAuth.class, "BasePageAuth.css")));
    }
}
