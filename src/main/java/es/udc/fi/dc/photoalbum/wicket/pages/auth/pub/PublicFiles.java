package es.udc.fi.dc.photoalbum.wicket.pages.auth.pub;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.AlbumTagService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.PublicFileListDataProvider;
import es.udc.fi.dc.photoalbum.wicket.models.PublicFilesModel;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.tag.BaseTags;
import es.udc.fi.dc.photoalbum.wicket.panels.CommentAndVotePanel;

@SuppressWarnings("serial")
public class PublicFiles extends BasePageAuth {

    @SpringBean
    private AlbumService albumService;
    @SpringBean
    private UserService userService;
    @SpringBean
    private AlbumTagService albumTagService;
    private static final int ITEMS_PER_PAGE = 10;
    private static final int TAG_PER_PAGE = 5;
    private Album album;

    public PublicFiles(final PageParameters parameters) {
        super(parameters);
        int albumId = parameters.get("albumId").toInt();
        this.album = albumService.getById(albumId);

        add(new BookmarkablePageLink<Void>("linkBack",
                PublicAlbums.class, null));
        add(new AjaxDataView("dataContainer", "navigator",
                createDataView()));
        add(new AjaxDataView("albumTagDataContainer",
                "albumTagNavigator", createAlbumTagsDataView()));
        add(new CommentAndVotePanel("commentAndVote", this, album));
    }

    private DataView<File> createDataView() {
        int userId = ((MySession) Session.get()).getuId();
        LoadableDetachableModel<ArrayList<File>> ldm = new PublicFilesModel(
                album.getId(), userId);
        DataView<File> dataView = new DataView<File>(
                "pageable",
                new PublicFileListDataProvider(
                        ldm.getObject().size(), album.getId(), userId)) {
            public void populateItem(final Item<File> item) {
                PageParameters pars = new PageParameters();
                int idFile = item.getModelObject().getId();

                pars.add("fid", idFile);
                pars.add("albumId", album.getId());
                BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
                        "big", PublicFilesBig.class, pars);
                bpl.add(new NonCachingImage("img",
                        new BlobImageResource() {
                            protected Blob getBlob() {
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

    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderCSSReference("css/SharedFiles.css");
    }
}
