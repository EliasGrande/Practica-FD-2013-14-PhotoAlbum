package es.udc.fi.dc.photoalbum.wicket.pages.auth.share;

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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.FileTag;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.FileTagService;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.SharedNavigateForm;
import es.udc.fi.dc.photoalbum.wicket.models.FileSharedAlbum;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.ErrorPage404;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.tag.BaseTags;
import es.udc.fi.dc.photoalbum.wicket.panels.CommentAndVotePanel;

/**
 * Page that shown the {@link File}s in big format.
 */
@SuppressWarnings("serial")
public class SharedBig extends BasePageAuth {
    /**
     * @see FileTagService
     */
    @SpringBean
    private FileTagService fileTagService;
    /**
     * @see FileService
     */
    @SpringBean
    private FileService fileService;
    /**
     * @see FileSharedAlbum
     */
    private FileSharedAlbum fileSharedAlbum;
    /**
     * The {@link File} to shown.
     */
    private File file;
    /**
     * The {@link LikeAndDislike} associated for the {@link #file}.
     */
    LikeAndDislike likeAndDislike;
    /**
     * The maximum number {@link FileTag} per page.
     */
    private static final int TAG_PER_PAGE = 5;

    /**
     * Constructor for SharedBig.
     * 
     * @param parameters
     *            The parameters necessaries for load the page.
     */
    public SharedBig(final PageParameters parameters) {
        super(parameters);
        if (parameters.getNamedKeys().contains("fid")
                && parameters.getNamedKeys().contains("album")) {
            int id = parameters.get("fid").toInt();
            String name = parameters.get("album").toString();
            int userId = ((MySession) Session.get()).getuId();
            FileSharedAlbum fileSharedAlbum = new FileSharedAlbum(id,
                    name, userId);
            this.fileSharedAlbum = fileSharedAlbum;
            if ((fileSharedAlbum.getObject() == null)
                    || (!(fileSharedAlbum.getObject().getAlbum()
                            .getName().equals(name)))) {
                throw new RestartResponseException(ErrorPage404.class);
            }
            File file = fileService.getById(id);
            this.file = file;
            add(new SharedNavigateForm<Void>("formNavigate",
                    fileSharedAlbum.getObject().getAlbum().getId(),
                    userId, fileSharedAlbum.getObject().getId(),
                    SharedBig.class));
            add(new AjaxDataView("fileTagDataContainer",
                    "fileTagNavigator", createFileTagsDataView()));
            add(createNonCachingImage());
            PageParameters newPars = new PageParameters();
            newPars.add("album", name);
            newPars.add("user", fileSharedAlbum.getObject()
                    .getAlbum().getUser().getEmail());
            add(new BookmarkablePageLink<Void>("linkBack",
                    SharedFiles.class, newPars));
            add(new CommentAndVotePanel("commentAndVote", file));
        } else {
            throw new RestartResponseException(ErrorPage404.class);
        }
    }

    /**
     * Method that create a big {@link NonCachingImage}.
     * 
     * @return {@link NonCachingImage} to the {@link #file}.
     */
    private NonCachingImage createNonCachingImage() {
        return new NonCachingImage("img", new BlobImageResource() {
            @Override
            protected Blob getBlob(Attributes arg0) {
                return BlobFromFile.getBig(fileSharedAlbum
                        .getObject());
            }
        });
    }

    /**
     * Method that create a {@link FileTag} data view.
     * 
     * @return DataView<{@link FileTag}> that contains the elements to
     *         shown.
     */
    private DataView<FileTag> createFileTagsDataView() {
        final List<FileTag> list = new ArrayList<FileTag>(
                fileTagService.getTags(file.getId()));
        DataView<FileTag> dataView = new DataView<FileTag>(
                "pageable", new ListDataProvider<FileTag>(list)) {

            @Override
            protected void populateItem(Item<FileTag> item) {
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
                        SharedBig.class, "SharedBig.css")));
    }
}
