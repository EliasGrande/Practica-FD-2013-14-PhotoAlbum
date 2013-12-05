package es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Error 404 {@link WebPage}
 */
@SuppressWarnings("serial")
public class ErrorPage404 extends BasePageAuth {

    /**
     * Defines an {@link ErrorPage404} page.
     * 
     * @param parameters
     *            PageParameters used by the inherit constructor
     *            {@link BasePageAuth#BasePageAuth(PageParameters)}
     */
    public ErrorPage404(final PageParameters parameters) {
        super(parameters);
    }
}
