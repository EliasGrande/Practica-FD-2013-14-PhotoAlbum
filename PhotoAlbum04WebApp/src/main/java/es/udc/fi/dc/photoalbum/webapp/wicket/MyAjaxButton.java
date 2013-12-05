package es.udc.fi.dc.photoalbum.webapp.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * Ajax button, that adds feedback on submit and on error.
 */
@SuppressWarnings("serial")
public class MyAjaxButton extends AjaxButton {

    /**
     * The {@link FeedbackPanel} associated to the button.
     */
    private FeedbackPanel feedback;

    /**
     * The constructor of MyAjaxButton.
     * 
     * @param id
     *            The id of the button in the correspondent .hmtl.
     * @param form
     *            The form where will put the button
     * @param feedback
     *            The feedback button.
     */
    public MyAjaxButton(String id, Form<?> form,
            FeedbackPanel feedback) {
        super(id, form);
        this.feedback = feedback;
    }

    /**
     * Method onSubmit of the button.
     * 
     * @param target
     *            The custom listener.
     * @param form
     *            The form for this button.
     */
    @Override
    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        target.add(feedback);
    }

    /**
     * Method onError.
     * 
     * @param target
     *            The custom listener.
     * @param form
     *            The form for this button.
     */
    @Override
    protected void onError(AjaxRequestTarget target, Form<?> form) {
        target.add(feedback);
    }
}
