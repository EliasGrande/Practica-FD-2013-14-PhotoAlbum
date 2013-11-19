package es.udc.fi.dc.photoalbum.wicket.pages.auth.tag;

import java.sql.Blob;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.TagAlbumListDataProvider;
import es.udc.fi.dc.photoalbum.wicket.TagFileListDataProvider;
import es.udc.fi.dc.photoalbum.wicket.models.TagAlbumsModelFull;
import es.udc.fi.dc.photoalbum.wicket.models.TagFilesModel;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;

/**
 * The initial page to view the result for a tag search, or navigation
 * tag.
 */
@SuppressWarnings("serial")
public class BaseTags extends BasePageAuth {

    /**
     * The tag that contains all the elements of this page.
     */
    private String tag;
    /**
     * The maximum number {@link Album}'s per page.
     */
    private static final int ALBUMS_PER_PAGE = 5;
    /**
     * The maximum number for items ({@link File} or {@link Tag}) for
     * page.
     */
    private static final int ITEMS_PER_PAGE = 10;

    /**
     * Constructor for BaseTags.
     * 
     * @param parameters
     *            The necessary parameters for the page.
     */
    public BaseTags(PageParameters parameters) {
        super(parameters);
        String tag = parameters.get("tagName").toString();
        this.tag = tag;
        add(new AjaxDataView("dataAlbumContainer", "albumNavigator",
                createAlbumDataView()));
        add(new AjaxDataView("dataFileContainer", "fileNavigator",
                createFileDataView()));
    }

    /**
     * Creates a DataView that shown a list of {@link Album}s that
     * contains the {@link #tag}
     * 
     * @return DataView<Album> Return the DataView with the
     *         {@link Album}s.
     */
    private DataView<Album> createAlbumDataView() {
        int userId = ((MySession) Session.get()).getuId();
        LoadableDetachableModel<List<Album>> ldm = new TagAlbumsModelFull(
                userId, tag);

        DataView<Album> dataView = new DataView<Album>("pageable",
                new TagAlbumListDataProvider(ldm.getObject().size(),
                        userId, tag)) {
            public void populateItem(final Item<Album> item) {
                Album album = item.getModelObject();
                PageParameters pars = new PageParameters();
                pars.add("albumId", album.getId());
                pars.add("tag", tag);
                final String linkName = album.getName() + " - "
                        + album.getUser().getEmail();
                BookmarkablePageLink<Void> bp = new BookmarkablePageLink<Void>(
                        "albums", FilesOfAlbumTag.class, pars);
                bp.add(new Label("name_mail", linkName));
                item.add(bp);
            }
        };
        dataView.setItemsPerPage(ALBUMS_PER_PAGE);
        return dataView;

    }

    /**
     * Creates a DataView that shown a list of {@link File}'s that
     * contains the {@link #tag}
     * 
     * @return DataView<File> Return the DataView with the
     *         {@link File}'s.
     */
    private DataView<File> createFileDataView() {
        int userId = ((MySession) Session.get()).getuId();
        LoadableDetachableModel<List<File>> ldm = new TagFilesModel(
                tag, userId);
        DataView<File> dataView = new DataView<File>("pageable",
                new TagFileListDataProvider(ldm.getObject().size(),
                        tag, userId)) {
            public void populateItem(final Item<File> item) {
                PageParameters pars = new PageParameters();
                int idFile = item.getModelObject().getId();
                pars.add("fid", idFile);
                pars.add("tag", tag);
                BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
                        "big", FileTagBig.class, pars);
                bpl.add(new NonCachingImage("img",
                        new BlobImageResource() {

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
}
