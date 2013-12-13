package es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.FileShareInformation;
import es.udc.fi.dc.photoalbum.model.hibernate.FileTag;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.FileService;
import es.udc.fi.dc.photoalbum.model.spring.FileShareInformationService;
import es.udc.fi.dc.photoalbum.model.spring.FileTagService;
import es.udc.fi.dc.photoalbum.model.spring.UserService;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.webapp.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.webapp.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.webapp.wicket.MyAjaxButton;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.NavigateForm;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.AlbumModel;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.AlbumsModel;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.FileOwnModel;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.PrivacyLevelOption;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.PrivacyLevelsModel;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.Share;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.BaseTags;
import es.udc.fi.dc.photoalbum.webapp.wicket.panels.CommentAndVotePanel;

/**
 * Administration {@link WebPage} for a {@link File} owned by the
 * user.
 */
@SuppressWarnings("serial")
public class Image extends BasePageAuth {

    /**
     * @see FileService
     */
    @SpringBean
    private FileService fileService;

    /**
     * @see UserService
     */
    @SpringBean
    private UserService userService;

    /**
     * @see FileShareInformationService
     */
    @SpringBean
    private FileShareInformationService shareInformationService;

    /**
     * @see FileTagService
     */
    @SpringBean
    private FileTagService fileTagService;

    /**
     * Number of {@link FileTag tags} showed on the "Tags" list per
     * page.
     */
    public static final int TAG_PER_PAGE = 5;

    /**
     * Number of {@link User#getEmail() e-mails} showed on the
     * "File shared to" list per page.
     */
    public static final int EMAIL_PER_PAGE = 5;

    /**
     * Share data view {@code wicket:id}.
     */
    public static final String SHARE_DATA_VIEW_ID = "pageable";

    /**
     * Model for the image.
     */
    private FileOwnModel fileOwnModel;

    /**
     * PageParameters used to instantiate the response page for the
     * forms of this page.
     */
    private PageParameters parameters;

    /**
     * Album selected on the "Move to album" combo-box.
     */
    private Album selectedAlbum;

    /**
     * PrivacyLevel selected on the "Change privacy level" combo-box.
     */
    private PrivacyLevelOption selectedPrivacyLevel;

    /**
     * Feedback panel.
     */
    private FeedbackPanel feedback;

    /**
     * Defines an {@link Image} page.
     * 
     * @param parameters
     *            PageParameters containing fid={@link Image#getId()
     *            image_id} and album={@link Album#getName()
     *            album_name} and the parameters used by the inherit
     *            constructor
     *            {@link BasePageAuth#BasePageAuth(PageParameters)}
     */
    public Image(final PageParameters parameters) {
        super(parameters);
        if (parameters.getNamedKeys().contains("fid")
                && parameters.getNamedKeys().contains("album")) {
            int id = parameters.get("fid").toInt();
            String albumName = parameters.get("album").toString();
            AlbumModel albumModel = new AlbumModel(albumName);
            setFileOwnModel(id, albumName);
            this.parameters = parameters;
            createFeedbackPanel();
            add(new NavigateForm<Void>("formNavigate", albumModel
                    .getObject().getId(), fileOwnModel.getObject()
                    .getId(), Image.class));
            DataView<FileShareInformation> dataView = createShareDataView();
            add(dataView);
            add(new PagingNavigator("navigator", dataView));
            add(createNonCachingImage());
            add(createFormDelete());
            add(createFormMove());
            add(createFormPrivacyLevel());
            add(createAddTagForm());
            add(new BookmarkablePageLink<Void>("linkBack",
                    Upload.class, (new PageParameters()).add("album",
                            albumName)));
            add(createShareForm());
            add(new AjaxDataView("fileTagDataContainer",
                    "fileTagNavigator", createFileTagsDataView()));
            add(new CommentAndVotePanel("commentAndVote",
                    fileOwnModel.getObject()));
        } else {
            throw new RestartResponseException(ErrorPage404.class);
        }
    }

    /**
     * Setter for {@link #fileOwnModel}.
     * 
     * @param id
     *            Image id
     * @param albumName
     *            Album name
     */
    private void setFileOwnModel(int id, String albumName) {
        FileOwnModel fileOwnModel = new FileOwnModel(id, albumName,
                ((MySession) Session.get()).getuId());
        this.fileOwnModel = fileOwnModel;
        if (fileOwnModel.getObject() == null) {
            throw new RestartResponseException(ErrorPage404.class);
        }
    }

    /**
     * Creates the {@link #feedback} panel and adds it to the page.
     */
    private void createFeedbackPanel() {
        FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        this.feedback = feedback;
        add(feedback);
    }

    /**
     * Creates a {@link DataView} for the list of
     * {@link User#getEmail() e-mails} of the {@link User users} of
     * the share list of the current {@link File image}.
     * 
     * @return Email list DataView
     */
    private DataView<FileShareInformation> createShareDataView() {
        final List<FileShareInformation> list = new ArrayList<FileShareInformation>(
                shareInformationService
                        .getFileShares(this.fileOwnModel.getObject()
                                .getId()));
        DataView<FileShareInformation> dataView = new ShareDataView(
                new ListDataProvider<FileShareInformation>(list));
        dataView.setItemsPerPage(EMAIL_PER_PAGE);
        return dataView;
    }

    /**
     * @see Image#createShareDataView()
     */
    private class ShareDataView extends
            DataView<FileShareInformation> {

        /**
         * Invokes super constructor with
         * {@link Image#SHARE_DATA_VIEW_ID} and the given data
         * provider.
         * 
         * @param dataProvider
         *            FileShareInformation data provider
         */
        public ShareDataView(
                IDataProvider<FileShareInformation> dataProvider) {
            super(SHARE_DATA_VIEW_ID, dataProvider);
        }

        @Override
        protected void populateItem(Item<FileShareInformation> item) {
            final FileShareInformation shareInformation = item
                    .getModelObject();
            item.add(new Label("email", shareInformation.getUser()
                    .getEmail()));
            item.add(new Link<Void>("delete") {
                @Override
                public void onClick() {
                    shareInformationService.delete(shareInformation);
                    info(new StringResourceModel("share.deleted",
                            this, null).getString());
                    setResponsePage(new Image(parameters));
                }
            });
        }
    }

    /**
     * Creates a {@link NonCachingImage} of the current {@link File
     * image}.
     * 
     * @return Non caching image
     */
    private NonCachingImage createNonCachingImage() {
        return new NonCachingImage("img", new BlobImageResource() {
            @Override
            protected Blob getBlob(Attributes arg0) {
                return BlobFromFile.getBig(fileOwnModel.getObject());
            }
        });
    }

    /**
     * Creates a {@link Form} for deleting the current {@link File
     * image}.
     * 
     * @return Delete form
     */
    private Form<Void> createFormDelete() {
        return new Form<Void>("formDelete") {
            @Override
            public void onSubmit() {
                fileService.delete(fileOwnModel.getObject());
                info(new StringResourceModel("image.deleted", this,
                        null).getString());
                setResponsePage(new Upload(parameters.remove("fid")));
            }
        };
    }

    /**
     * Creates a {@link Form} for moving the current {@link File
     * image} to another {@link Album}.
     * 
     * @return Move form
     */
    private Form<Void> createFormMove() {
        Form<Void> form = new Form<Void>("formMove") {
            @Override
            public void onSubmit() {
                fileService.changeAlbum(fileOwnModel.getObject(),
                        selectedAlbum);
                info(new StringResourceModel("image.moved", this,
                        null).getString());
                setResponsePage(new Upload(parameters.remove("fid")));
            }
        };
        DropDownChoice<Album> listAlbums = new DropDownChoice<Album>(
                "albums", new PropertyModel<Album>(this,
                        "selectedAlbum"), new AlbumsModel(
                        fileOwnModel.getObject().getAlbum()),
                new ChoiceRenderer<Album>("name", "id"));
        listAlbums.setRequired(true);
        listAlbums.setLabel(new StringResourceModel(
                "image.moveAlbum", this, null));
        form.add(listAlbums);
        return form;
    }

    /**
     * Creates a {@link Form} for changing the
     * {@link File#getPrivacyLevel(String) privacy level} of the
     * current {@link File image}.
     * 
     * @return Change privacy level form
     */
    private Form<Void> createFormPrivacyLevel() {
        Form<Void> form = new Form<Void>("formPrivacyLevel") {
            @Override
            public void onSubmit() {
                if (selectedPrivacyLevel != null
                        && PrivacyLevel
                                .validateFile(selectedPrivacyLevel
                                        .getValue())) {
                    fileService.changePrivacyLevel(
                            fileOwnModel.getObject(),
                            selectedPrivacyLevel.getValue());
                    info(new StringResourceModel(
                            "privacyLevel.changed", this, null)
                            .getString());
                }
                setResponsePage(new Image(parameters));
            }
        };
        selectedPrivacyLevel = new PrivacyLevelOption(fileOwnModel
                .getObject().getPrivacyLevel(), this);
        DropDownChoice<PrivacyLevelOption> listPrivacyLevel = new DropDownChoice<PrivacyLevelOption>(
                "privacyLevels",
                new PropertyModel<PrivacyLevelOption>(this,
                        "selectedPrivacyLevel"),
                new PrivacyLevelsModel(fileOwnModel.getObject(), this),
                new ChoiceRenderer<PrivacyLevelOption>("label",
                        "value"));
        listPrivacyLevel.setRequired(true);
        listPrivacyLevel.setLabel(new StringResourceModel(
                "privacyLevel.change", this, null));
        form.add(listPrivacyLevel);
        return form;
    }

    /**
     * Creates a {@link Form} for sharing the current {@link File
     * image} with other {@link User users}.
     * 
     * @return Share form
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
                    FileShareInformation shareInformation = new FileShareInformation(
                            null, fileOwnModel.getObject(),
                            existedUser);
                    List<FileShareInformation> getFileShares = shareInformationService
                            .getFileShares(fileOwnModel.getObject()
                                    .getId());
                    ListIterator<FileShareInformation> iter = getFileShares
                            .listIterator();
                    boolean comp = false;
                    while (iter.hasNext() && (comp == false)) {
                        FileShareInformation item = (FileShareInformation) iter
                                .next();
                        String shMail = item.getUser().getEmail();
                        if (shMail == existedUser.getEmail()) {
                            comp = true;
                        }
                    }
                    if (!comp) {
                        shareInformationService
                                .create(shareInformation);
                        info(new StringResourceModel(
                                "share.shareFileSuccess", this, null)
                                .getString());
                        setResponsePage(new Image(getPage()
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
        if (fileOwnModel.getObject().getPrivacyLevel()
                .equals(PrivacyLevel.INHERIT_FROM_ALBUM)) {
            shareEmail.setEnabled(false);
        } else {
            shareEmail.setEnabled(true);
        }
        shareEmail.setLabel(new StringResourceModel(
                "share.emailField", this, null));
        shareEmail.add(EmailAddressValidator.getInstance());
        form.add(shareEmail);
        form.add(new MyAjaxButton("ajax-button", form, feedback));
        return form;
    }

    /**
     * Creates a {@link Form} for adding {@link FileTag tags} to the
     * current {@link File image}.
     * 
     * @return Add tag form
     */
    private Form<FileTag> createAddTagForm() {
        Form<FileTag> form = new Form<FileTag>("formAddTag") {
            @Override
            public void onSubmit() {
                FileTag fTag = getModelObject();
                fTag.setFile(fileOwnModel.getObject());
                fileTagService.create(fTag);
                info(new StringResourceModel("tag.added", this, null)
                        .getString());
                setResponsePage(new Image(parameters));
            }
        };
        FileTag fTag = new FileTag();
        form.setDefaultModel(new Model<FileTag>(fTag));
        RequiredTextField<String> newTag = new RequiredTextField<String>(
                "newTag", new PropertyModel<String>(fTag, "tag"));
        newTag.setLabel(new StringResourceModel("upload.tagField",
                this, null));
        form.add(newTag);
        form.add(new MyAjaxButton("ajax-button", form, feedback));
        return form;
    }

    /**
     * Creates a {@link DataView} for the list of {@link FileTag tags}
     * of the current {@link File image}.
     * 
     * @return Tag list DataView
     */
    private DataView<FileTag> createFileTagsDataView() {
        final List<FileTag> list = new ArrayList<FileTag>(
                fileTagService.getTags(this.fileOwnModel.getObject()
                        .getId()));
        DataView<FileTag> dataView = new DataView<FileTag>(
                "pageable", new ListDataProvider<FileTag>(list)) {
            @Override
            protected void populateItem(final Item<FileTag> item) {
                PageParameters pars = new PageParameters();
                pars.add("tagName", item.getModelObject().getTag());
                BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
                        "link", BaseTags.class, pars);
                bpl.add(new Label("tagName", item.getModelObject()
                        .getTag()));
                item.add(bpl);
                item.add(new Link<Void>("delete") {
                    public void onClick() {
                        fileTagService.delete(item.getModelObject());
                        info(new StringResourceModel("tag.deleted",
                                this, null).getString());
                        setResponsePage(new Image(parameters));
                    }

                });
            }
        };
        dataView.setItemsPerPage(TAG_PER_PAGE);
        return dataView;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(Image.class,
                        "Image.css")));
    }
}
