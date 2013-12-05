package es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.pub;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

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

import es.udc.fi.dc.photoalbum.model.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.FileTag;
import es.udc.fi.dc.photoalbum.model.spring.FileService;
import es.udc.fi.dc.photoalbum.model.spring.FileTagService;
import es.udc.fi.dc.photoalbum.webapp.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.webapp.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.PublicNavigateForm;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.SharedBig;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.BaseTags;
import es.udc.fi.dc.photoalbum.webapp.wicket.panels.CommentAndVotePanel;

/**
 * The page that allow to view {@link File}s big.
 */
@SuppressWarnings("serial")
public class PublicFilesBig extends BasePageAuth {
    /**
     * @see FileService
     */
    @SpringBean
    private FileService fileService;
    /**
     * @see FileTagService
     */
    @SpringBean
    private FileTagService fileTagService;
    /**
     * @see File
     */
    private File file;
    /**
     * The maximum number {@link AlbumTag}'s per page.
     */
    private static final int TAG_PER_PAGE = 5;

    /**
     * Constructor for PublicFilesBig.
     * 
     * @param parameters
     *            The necessary parameters for the page.
     */
    public PublicFilesBig(final PageParameters parameters) {
        super(parameters);

        int id = parameters.get("fid").toInt();
        int albumId = parameters.get("albumId").toInt();
        File auxFile = fileService.getById(id);
        this.file = auxFile;

        add(new PublicNavigateForm<Void>("formNavigate", albumId,
                ((MySession) Session.get()).getuId(), id,
                PublicFilesBig.class));
        add(new AjaxDataView("fileTagDataContainer",
                "fileTagNavigator", createFileTagsDataView()));
        add(createNonCachingImage());
        PageParameters newPars = new PageParameters();
        newPars.add("albumId", albumId);

        add(new BookmarkablePageLink<Void>("linkBack",
                PublicFiles.class, newPars));
        add(new CommentAndVotePanel("commentAndVote", file));
    }

    /**
     * Method that create a big {@link NonCachingImage}.
     * 
     * @return {@link NonCachingImage} to the {@link #file}.
     */
    private NonCachingImage createNonCachingImage() {
        return new NonCachingImage("img", new BlobImageResource() {
            protected Blob getBlob(Attributes arg0) {
                return BlobFromFile.getBig(file);
            }
        });
    }

    /**
     * Method that create a {@link FileTag} data view.
     * 
     * @return DataView<{@link FileTag}> that contains the elements to shown.
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
