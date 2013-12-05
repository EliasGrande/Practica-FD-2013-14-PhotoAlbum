package es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.spring.AlbumService;
import es.udc.fi.dc.photoalbum.model.spring.AlbumTagService;
import es.udc.fi.dc.photoalbum.webapp.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.webapp.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.SharedFileListDataProvider;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.SharedFilesModel;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.ErrorPage404;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.BaseTags;
import es.udc.fi.dc.photoalbum.webapp.wicket.panels.CommentAndVotePanel;

/**
 * Page that shown the {@link File}s shared.
 */
@SuppressWarnings("serial")
public class SharedFiles extends BasePageAuth {
    /**
     * @see AlbumService
     */
    @SpringBean
    private AlbumService albumService;
    /**
     * @see AlbumTagService
     */
    @SpringBean
    private AlbumTagService albumTagService;
    /**
     * The {@link Album} owner of the {@link File}s.
     */
    private Album album;
    /**
     * The maximum number ({@link Album}s or {@link File}) per page.
     */
    private static final int ITEMS_PER_PAGE = 10;
    /**
     * The maximum number {@link AlbumTag} per page.
     */
    private static final int TAG_PER_PAGE = 5;

    /**
     * Constructor for SharedFiles.
     * 
     * @param parameters
     *            The parameters necessaries for load the page.
     */
    public SharedFiles(final PageParameters parameters) {
        super(parameters);
        if ((parameters.getNamedKeys().contains("album"))
                && (parameters.getNamedKeys().contains("user"))) {
            String name = parameters.get("album").toString();
            String email = parameters.get("user").toString();
            this.album = albumService.getSharedAlbum(name,
                    ((MySession) Session.get()).getuId(), email);
            if (this.album == null) {
                throw new RestartResponseException(ErrorPage404.class);
            }
            add(new Label("album", name));

        } else {
            throw new RestartResponseException(ErrorPage404.class);
        }
        add(new BookmarkablePageLink<Void>("linkBack",
                SharedAlbums.class, parameters.remove("album")));
        add(new AjaxDataView("dataContainer", "navigator",
                createDataView()));
        add(new AjaxDataView("albumTagDataContainer",
                "albumTagNavigator", createAlbumTagsDataView()));
        add(new CommentAndVotePanel("commentAndVote", album));
    }

    /**
     * Creates a DataView that shown a list of shared {@link File}s.
     * 
     * @return DataView<{@link File}> Return the DataView with the
     *         {@link File}s.
     */
    private DataView<File> createDataView() {
        int userId = ((MySession) Session.get()).getuId();
        LoadableDetachableModel<List<File>> ldm = new SharedFilesModel(
                this.album.getId(), userId);
        DataView<File> dataView = new DataView<File>("pageable",
                new SharedFileListDataProvider(
                        ldm.getObject().size(), this.album.getId(),
                        userId)) {
            public void populateItem(final Item<File> item) {
                PageParameters pars = new PageParameters();
                pars.add("album", album.getName());
                pars.add("fid", Integer.toString(item
                        .getModelObject().getId()));
                BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
                        "big", SharedBig.class, pars);
                bpl.add(new NonCachingImage("img",
                        new BlobImageResource() {
                            @Override
                            protected Blob getBlob(Attributes arg0) {
                                return BlobFromFile.getSmall(item
                                        .getModelObject());
                            }
                        }));
                item.add(bpl);
            }
        };
        dataView.setItemsPerPage(ITEMS_PER_PAGE);
        return dataView;
    }

    /**
     * Creates a DataView that shown a list {@link AlbumTag}s.
     * 
     * @return DataView<{@link AlbumTag}> Return the DataView with the
     *         {@link AlbumTag}s.
     */
    private DataView<AlbumTag> createAlbumTagsDataView() {
        final List<AlbumTag> list = new ArrayList<AlbumTag>(
                albumTagService.getTags(album.getId()));
        DataView<AlbumTag> dataView = new DataView<AlbumTag>(
                "pageable", new ListDataProvider<AlbumTag>(list)) {
            @Override
            protected void populateItem(Item<AlbumTag> item) {
                PageParameters pars = new PageParameters();
                pars.add("tagName", item.getModelObject().getTag());
                BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
                        "link", BaseTags.class, pars);
                bpl.add(new Label("tagName", item.getModelObject()
                        .getTag()));
                item.add(bpl);
            }
        };
        dataView.setItemsPerPage(TAG_PER_PAGE);
        return dataView;
    }

    /**
     * Method renderHead allows to use specific css in this page.
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
                        SharedFiles.class, "SharedFiles.css")));
    }
}
