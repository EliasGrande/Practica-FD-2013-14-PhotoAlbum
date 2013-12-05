package es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth;

import static es.udc.fi.dc.photoalbum.webapp.wicket.CookiesConstants.COOKIE_EMAIL;
import static es.udc.fi.dc.photoalbum.webapp.wicket.CookiesConstants.COOKIE_PASSWORD;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.cookies.CookieUtils;

/**
 * Sign out {@link WebPage}
 */
@SuppressWarnings("serial")
public class SignOut extends WebPage {

    /**
     * Defines an {@link SignOut} page.
     * 
     * @param parameters
     *            PageParameters used by the inherit constructor
     *            {@link BasePageAuth#BasePageAuth(PageParameters)}
     */
    public SignOut(final PageParameters parameters) {
        super(parameters);
        CookieUtils cu = new CookieUtils();
        cu.getSettings().setMaxAge(0);
        cu.save(COOKIE_EMAIL, "");
        cu.save(COOKIE_PASSWORD, "");
        getSession().invalidate();
    }
}
