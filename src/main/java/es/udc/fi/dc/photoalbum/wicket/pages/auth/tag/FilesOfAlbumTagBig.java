package es.udc.fi.dc.photoalbum.wicket.pages.auth.tag;

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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.FileTag;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.FileTagService;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.FilesOfAlbumTagNavigateForm;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;

@SuppressWarnings("serial")
public class FilesOfAlbumTagBig extends BasePageAuth {

    @SpringBean
    private FileService fileService;
    @SpringBean
    private FileTagService fileTagService;

    private File file;

    private static final int TAG_PER_PAGE = 5;

    public FilesOfAlbumTagBig(final PageParameters parameters) {
        super(parameters);
        int id = parameters.get("fid").toInt();
        int albumId = parameters.get("albumId").toInt();
        String tag = parameters.get("tag").toString();
        File auxFile = fileService.getById(id);
        this.file = auxFile;

        add(new FilesOfAlbumTagNavigateForm<Void>("formNavigate", tag, albumId,
                ((MySession) Session.get()).getuId(), id,
                FilesOfAlbumTagBig.class));
        add(createNonCachingImage());
        add(new AjaxDataView("fileTagDataContainer", "fileTagNavigator",
                createFileTagsDataView()));
        PageParameters newPars = new PageParameters();
        newPars.add("albumId", albumId);
        newPars.add("tag", tag);

        add(new BookmarkablePageLink<Void>("linkBack", FilesOfAlbumTag.class,
                newPars));
    }

    private NonCachingImage createNonCachingImage() {
        return new NonCachingImage("img", new BlobImageResource() {
            protected Blob getBlob() {
                return BlobFromFile.getBig(file);
            }
        });
    }

    private DataView<FileTag> createFileTagsDataView() {
        final List<FileTag> list = new ArrayList<FileTag>(
                fileTagService.getTags(file.getId()));
        DataView<FileTag> dataView = new DataView<FileTag>("pageable",
                new ListDataProvider<FileTag>(list)) {

            @Override
            protected void populateItem(Item<FileTag> item) {
                PageParameters pars = new PageParameters();
                pars.add("tagName", item.getModelObject().getTag());
                BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
                        "link", BaseTags.class, pars);
                bpl.add(new Label("tagName", item.getModelObject().getTag()));
                item.add(bpl);
            }
        };
        dataView.setItemsPerPage(TAG_PER_PAGE);
        return dataView;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderCSSReference("css/SharedBig.css");
    }
}
