package es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.spring.AlbumService;
import es.udc.fi.dc.photoalbum.model.spring.UserService;
import es.udc.fi.dc.photoalbum.webapp.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.webapp.wicket.AlbumListDataProvider;
import es.udc.fi.dc.photoalbum.webapp.wicket.MyAjaxButton;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.AlbumsModelFull;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.Share;

/**
 * Administration {@link WebPage} for the {@link Album albums} owned
 * by the user.
 */
@SuppressWarnings("serial")
public class Albums extends BasePageAuth {

    /**
     * @see AlbumService
     */
    @SpringBean
    private AlbumService albumService;

    /**
     * @see UserService
     */
    @SpringBean
    private UserService userService;

    /**
     * Modal window width in pixels.
     */
    private static final int MODAL_WINDOW_WIDTH = 500;

    /**
     * Modal window height in pixels.
     */
    private static final int MODAL_WINDOW_HEIGHT = 120;

    /**
     * Number of albums to show on each page.
     */
    public static final int ALBUMS_PER_PAGE = 10;

    /**
     * Ajax data view container {@code wicket:id}.
     */
    public static final String DATA_VIEW_CONTAINER_ID = "dataContainer";

    /**
     * Ajax data view navigator {@code wicket:id}.
     */
    public static final String DATA_VIEW_NAVIGATOR_ID = "navigator";

    /**
     * Data view {@code wicket:id}.
     */
    public static final String DATA_VIEW_ID = "pageable";

    /**
     * Form {@code wicket:id}.
     */
    public static final String FORM_ID = "form";

    /**
     * Defines an {@link Albums} page.
     * 
     * @param parameters
     *            PageParameters used by the inherit constructor
     *            {@link BasePageAuth#BasePageAuth(PageParameters)}
     */
    public Albums(final PageParameters parameters) {
        super(parameters);
        add(createAlbumForm());
        add(new AjaxDataView(DATA_VIEW_CONTAINER_ID,
                DATA_VIEW_NAVIGATOR_ID, createAlbumDataView()));
    }

    /**
     * Creates a {@link DataView} for the list of albums and returns
     * it.
     * 
     * @return Album list DataView
     */
    private DataView<Album> createAlbumDataView() {
        LoadableDetachableModel<List<Album>> ldm = new AlbumsModelFull();
        DataView<Album> dataView = new AlbumDataView(
                new AlbumListDataProvider(ldm.getObject().size()));
        ldm.detach();
        dataView.setItemsPerPage(ALBUMS_PER_PAGE);
        return dataView;
    }

    /**
     * @see Albums#createAlbumDataView()
     */
    private class AlbumDataView extends DataView<Album> {

        /**
         * Invokes super constructor with {@link Albums#DATA_VIEW_ID}
         * and the given data provider.
         * 
         * @param dataProvider
         *            Album data provider
         */
        public AlbumDataView(IDataProvider<Album> dataProvider) {
            super(DATA_VIEW_ID, dataProvider);
        }

        @Override
        protected void populateItem(final Item<Album> item) {
            PageParameters pars = new PageParameters();
            pars.add("album", item.getModelObject().getName());
            BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
                    "link", Upload.class, pars);
            bpl.add(new Label("name", item.getModelObject().getName()));
            item.add(bpl);
            item.add(new Link<Void>("delete") {
                @Override
                public void onClick() {
                    info(new StringResourceModel("albums.deleted",
                            this, null).getString());
                    albumService.delete(item.getModelObject());
                    setResponsePage(new Albums(null));
                }
            });
            final ModalWindow modal = new ModalWindow("modal");
            item.add(modal);
            modal.setPageCreator(new ModalWindow.PageCreator() {
                @Override
                public Page createPage() {
                    return new ModalRename(item.getModelObject(),
                            modal);
                }
            });
            modal.setTitle(new StringResourceModel("albums.rename",
                    this, null));
            modal.setResizable(false);
            modal.setInitialWidth(MODAL_WINDOW_WIDTH);
            modal.setInitialHeight(MODAL_WINDOW_HEIGHT);
            modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                @Override
                public void onClose(AjaxRequestTarget target) {
                    setResponsePage(Albums.class);
                }
            });
            item.add(new AjaxLink<Void>("rename") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    modal.show(target);
                }
            });
            BookmarkablePageLink<Void> bp2 = new BookmarkablePageLink<Void>(
                    "share", Share.class, pars);
            item.add(bp2);

        }
    }

    /**
     * Creates a {@link Form} to create new albums and returns it.
     * 
     * @return Create album form
     */
    private Form<Album> createAlbumForm() {
        Form<Album> form = new AlbumForm();
        Album album = new Album();
        form.setDefaultModel(new Model<Album>(album));
        RequiredTextField<String> albumName = new RequiredTextField<String>(
                "AlbumName", new PropertyModel<String>(album, "name"));
        albumName.setLabel(new StringResourceModel(
                "albums.albumNameField", this, null));
        form.add(albumName);
        FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        form.add(feedback);
        form.add(new MyAjaxButton("ajax-button", form, feedback));
        return form;
    }

    /**
     * @see Albums#createAlbumForm()
     */
    private class AlbumForm extends Form<Album> {

        /**
         * Invokes super constructor with {@link Albums#FORM_ID}.
         */
        public AlbumForm() {
            super(FORM_ID);
        }

        @Override
        protected void onSubmit() {
            Album album = getModelObject();
            album.setUser(userService.getById(((MySession) Session
                    .get()).getuId()));
            try {
                albumService.create(album);
                info(new StringResourceModel("albums.created", this,
                        null).getString());
                setResponsePage(new Albums(null));
            } catch (RuntimeException e) {
                error(new StringResourceModel("albums.existed", this,
                        null).getString());
            }
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(Albums.class,
                        "Albums.css")));
    }
}
