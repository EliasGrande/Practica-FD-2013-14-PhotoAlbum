package es.udc.fi.dc.photoalbum.wicket.pages.auth.tag;

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

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.FileTag;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.FileTagService;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.TagNavigateForm;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;

/**
 * This page contains a big view of {@link File}. Allows navigate
 * between {@link File}s that have the necessary {@link Tag}.
 */
@SuppressWarnings("serial")
public class FileTagBig extends BasePageAuth {

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
     * The {@link File} to shown.
     */
    private File file;
    /**
     * The number of maximum {@link Tag} per page.
     */
    private static final int TAG_PER_PAGE = 5;

    /**
     * Constructor for FileTagBig.
     * 
     * @param parameters
     *            The parameters necessaries for render the page.
     */
    public FileTagBig(PageParameters parameters) {
        super(parameters);
        int id = parameters.get("fid").toInt();
        String tag = parameters.get("tag").toString();

        File auxFile = fileService.getById(id);
        this.file = auxFile;

        add(new TagNavigateForm<Void>("formNavigate", tag,
                ((MySession) Session.get()).getuId(), id,
                FileTagBig.class));
        add(createNonCachingImage());
        add(new AjaxDataView("fileTagDataContainer",
                "fileTagNavigator", createFileTagsDataView()));

        PageParameters newPars = new PageParameters();
        newPars.add("tagName", tag);

        add(new BookmarkablePageLink<Void>("linkBack",
                BaseTags.class, newPars));
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
                return BlobFromFile.getBig(file);
            }
        });
    }

    /**
     * Method that create a {@link FileTag} data view.
     * 
     * @return DataView<FileTag> that contains the elements to shown.
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
                        FileTagBig.class, "css/SharedBig.css")));
    }
}
