package es.udc.fi.dc.photoalbum.wicket.pages.auth.tag;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import es.udc.fi.dc.photoalbum.wicket.MyAjaxButton;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;

/**
 */
@SuppressWarnings("serial")
public class SearchByTag extends BasePageAuth {

    /**
     * Constructor for SearchByTag.
     * 
     * @param parameters
     *            PageParameters
     */
    public SearchByTag(PageParameters parameters) {
        super(parameters);
        add(createTagForm());
    }

    /**
     * Method createTagForm.
     * 
     * @return Form<Tag>
     */
    private Form<Tag> createTagForm() {
        Form<Tag> form = new Form<Tag>("form") {
            @Override
            protected void onSubmit() {
                Tag tag = getModelObject();
                PageParameters pars = new PageParameters();
                pars.add("tagName", tag.getValue());
                setResponsePage(BaseTags.class, pars);

            }
        };
        Tag tag = new Tag();
        form.setDefaultModel(new Model<Tag>(tag));
        RequiredTextField<String> tagName = new RequiredTextField<String>(
                "tagName", new PropertyModel<String>(tag, "value"));
        tagName.setLabel(new StringResourceModel(
                "searchByTag.typeTag", this, null));
        form.add(tagName);
        FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        form.add(feedback);
        form.add(new MyAjaxButton("ajax-button", form, feedback));
        return form;
    }

}
