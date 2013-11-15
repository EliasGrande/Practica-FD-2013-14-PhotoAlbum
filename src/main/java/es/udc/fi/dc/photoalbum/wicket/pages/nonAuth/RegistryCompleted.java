package es.udc.fi.dc.photoalbum.wicket.pages.nonAuth;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;

/**
 */
@SuppressWarnings("serial")
public class RegistryCompleted extends BasePage {
    /**
     * Constructor for RegistryCompleted.
     * 
     * @param parameters
     *            PageParameters
     */
    public RegistryCompleted(final PageParameters parameters) {
        super(parameters);
    }

    /**
     * Method renderHead.
     * 
     * @param response
     *            IHeaderResponse
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
