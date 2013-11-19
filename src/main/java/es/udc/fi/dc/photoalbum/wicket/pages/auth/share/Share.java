package es.udc.fi.dc.photoalbum.wicket.pages.auth.share;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.AlbumShareInformationService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.MyAjaxButton;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.models.AlbumModel;
import es.udc.fi.dc.photoalbum.wicket.models.PrivacyLevelOption;
import es.udc.fi.dc.photoalbum.wicket.models.PrivacyLevelsModel;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.ErrorPage404;

/**
 * Page that allows to share management.
 */
@SuppressWarnings("serial")
public class Share extends BasePageAuth {
    /**
     * @see AlbumService
     */
    @SpringBean
    private AlbumService albumService;
    /**
     * @see AlbumShareInformation
     */
    @SpringBean
    private AlbumShareInformationService shareInformationService;
    /**
     * @see UserService
     */
    @SpringBean
    private UserService userService;
    /**
     * The {@link Album} which is going to change sharing options.
     */
    private Album album;
    /**
     * The model of {@link #album}
     */
    private AlbumModel am;
    /**
     * The parameters necessaries for render the page.
     */
    private PageParameters parameters;
    /**
     * The maximum number ({@link Album}s or {@link File}) per
     * page.
     */
    private static final int ITEMS_PER_PAGE = 20;
    /**
     * The options for privacy availables.
     */
    private PrivacyLevelOption selectedPrivacyLevel;

    /**
     * Constructor for Share.
     * 
     * @param parameters
     *            The parameters necessaries for render the page.
     */
    public Share(PageParameters parameters) {
        super(parameters);
        this.parameters = parameters;
        if (parameters.getNamedKeys().contains("album")) {
            String name = parameters.get("album").toString();
            add(new Label("album", name));
            AlbumModel am = new AlbumModel(name);
            this.am = am;
            this.album = albumService.getAlbum(name,
                    ((MySession) Session.get()).getuId());
            if (am.getObject() == null) {
                throw new RestartResponseException(ErrorPage404.class);
            }
        } else {
            throw new RestartResponseException(ErrorPage404.class);
        }

        add(createShareForm());
        DataView<AlbumShareInformation> dataView = createShareDataView();
        add(dataView);
        add(new PagingNavigator("navigator", dataView));
        add(createFormPrivacyLevel());
    }

    /**
     * Creates a DataView that shown a list of
     * {@link AlbumShareInformation}s.
     * 
     * @return DataView<{@link AlbumShareInformation}> Return the
     *         DataView with the {@link AlbumShareInformation}s.
     */
    private DataView<AlbumShareInformation> createShareDataView() {
        final List<AlbumShareInformation> list = new ArrayList<AlbumShareInformation>(
                shareInformationService.getAlbumShares(this.album
                        .getId()));
        DataView<AlbumShareInformation> dataView = new DataView<AlbumShareInformation>(
                "pageable",
                new ListDataProvider<AlbumShareInformation>(list)) {

            public void populateItem(
                    final Item<AlbumShareInformation> item) {
                final AlbumShareInformation shareInformation = item
                        .getModelObject();
                item.add(new Label("email", shareInformation
                        .getUser().getEmail()));
                item.add(new Link<Void>("delete") {
                    public void onClick() {
                        shareInformationService
                                .delete(shareInformation);
                        info(new StringResourceModel("share.deleted",
                                this, null).getString());
                        setResponsePage(new Share(parameters));
                    }
                });
            }
        };
        dataView.setItemsPerPage(ITEMS_PER_PAGE);
        return dataView;
    }

    /**
     * Method createShareForm.
     * 
     * @return Form<User> The form that allows to share the album with
     *         an {@link User}.
     */
    private Form<User> createShareForm() {
        Form<User> form = new Form<User>("form") {
            @Override
            protected void onSubmit() {
                User user = getModelObject();
                User existedUser = userService.getUser(user);
                if (existedUser == null) {
                    error(new StringResourceModel("share.noUser",
                            this, null).getString());
                } else if (existedUser.getEmail().equals(
                        userService.getById(
                                ((MySession) Session.get()).getuId())
                                .getEmail())) {
                    error(new StringResourceModel("share.yourself",
                            this, null).getString());
                } else {
                    AlbumShareInformation shareInformation = new AlbumShareInformation(
                            null, album, existedUser);
                    if (shareInformationService.getShare(album
                            .getName(), existedUser.getId(),
                            (userService.getById(((MySession) Session
                                    .get()).getuId()).getEmail())) == null) {
                        shareInformationService
                                .create(shareInformation);
                        info(new StringResourceModel(
                                "share.shareSuccess", this, null)
                                .getString());
                        setResponsePage(new Share(getPage()
                                .getPageParameters()));
                    } else {
                        error(new StringResourceModel(
                                "share.alreadyExist", this, null)
                                .getString());
                    }
                }
            }
        };
        User user = new User();
        form.setDefaultModel(new Model<User>(user));
        RequiredTextField<String> shareEmail = new RequiredTextField<String>(
                "shareEmail",
                new PropertyModel<String>(user, "email"));
        shareEmail.setLabel(new StringResourceModel(
                "share.emailField", this, null));
        shareEmail.add(EmailAddressValidator.getInstance());
        form.add(shareEmail);
        FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        form.add(feedback);
        form.add(new MyAjaxButton("ajax-button", form, feedback));
        return form;
    }

    /**
     * Method createFormPrivacyLevel. This is a form to use a
     * DropDownChoice that allows to change the privacy for an album.
     * 
     * @see DropDownChoice
     * 
     * @return Form<Void> The form with the DropDownChoice.
     */
    private Form<Void> createFormPrivacyLevel() {
        Form<Void> form = new Form<Void>("formPrivacyLevel") {
            @Override
            public void onSubmit() {
                if (PrivacyLevel.validateAlbum(selectedPrivacyLevel
                        .getValue())) {
                    albumService.changePrivacyLevel(am.getObject(),
                            selectedPrivacyLevel.getValue());
                    info(new StringResourceModel(
                            "privacyLevel.changed", this, null)
                            .getString());
                }
                setResponsePage(new Share(parameters));
            }
        };
        selectedPrivacyLevel = new PrivacyLevelOption(am.getObject()
                .getPrivacyLevel(), this);
        DropDownChoice<PrivacyLevelOption> listPrivacyLevel = new DropDownChoice<PrivacyLevelOption>(
                "privacyLevels",
                new PropertyModel<PrivacyLevelOption>(this,
                        "selectedPrivacyLevel"),
                new PrivacyLevelsModel(am.getObject(), this),
                new ChoiceRenderer<PrivacyLevelOption>("label",
                        "value"));
        listPrivacyLevel.setRequired(true);
        listPrivacyLevel.setLabel(new StringResourceModel(
                "privacyLevel.change", this, null));
        form.add(listPrivacyLevel);

        return form;
    }

    /**
     * Method renderHead, that allow to use CSS to render the page.
     * 
     * @param response
     *            IHeaderResponse
     * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(IHeaderResponse)
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(Share.class,
                        "Share.css")));
    }
}
