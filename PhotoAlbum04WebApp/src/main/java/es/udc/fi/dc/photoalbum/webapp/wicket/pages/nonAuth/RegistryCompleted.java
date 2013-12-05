package es.udc.fi.dc.photoalbum.webapp.wicket.pages.nonAuth;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;

/**
 * The page that informs that register was successfully.
 */
@SuppressWarnings("serial")
public class RegistryCompleted extends BasePage {
    /**
     * Constructor for RegistryCompleted.
     * 
     * @param parameters
     *            The necessary parameters for render the page.
     */
    public RegistryCompleted(final PageParameters parameters) {
        super(parameters);
    }

    /**
     * Method renderHead, that allow to use CSS to render the page.
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
                        RegistryCompleted.class,
                        "RegistryCompleted.css")));
    }
}
