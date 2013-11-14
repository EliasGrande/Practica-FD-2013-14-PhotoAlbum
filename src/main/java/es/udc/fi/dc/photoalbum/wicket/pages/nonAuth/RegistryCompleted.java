package es.udc.fi.dc.photoalbum.wicket.pages.nonAuth;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;

@SuppressWarnings("serial")
public class RegistryCompleted extends BasePage {
	public RegistryCompleted(final PageParameters parameters) {
		super(parameters);
	}

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(
                        RegistryCompleted.class, "RegistryCompleted.css")));
    }
}
