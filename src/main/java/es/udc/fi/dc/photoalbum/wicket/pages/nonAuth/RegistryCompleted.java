package es.udc.fi.dc.photoalbum.wicket.pages.nonAuth;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@SuppressWarnings("serial")
public class RegistryCompleted extends BasePage {
	public RegistryCompleted(final PageParameters parameters) {
		super(parameters);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("css/RegistryCompleted.css");
	}
}
