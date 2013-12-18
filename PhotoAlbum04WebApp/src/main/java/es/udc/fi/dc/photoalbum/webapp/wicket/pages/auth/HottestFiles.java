package es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.util.dto.FileDto;
import es.udc.fi.dc.photoalbum.webapp.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.webapp.wicket.HottestFileListDataProvider;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.HottestFilesModel;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.pub.PublicFilesBig;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.SharedFiles;

/**
 * The page that allow to view the hottest {@link File files}.
 */
@SuppressWarnings("serial")
public class HottestFiles extends BasePageAuth {

    /**
     * Dataview container {@code wicket:id}.
     */
    public static final String DATA_CONTAINER_ID = "dataContainer";

    /**
     * Dataview navigator {@code wicket:id}.
     */
    public static final String NAVIGATOR_ID = "navigator";

    /**
     * Dataview repeater {@code wicket:id}.
     */
    public static final String REPEATER_ID = "pageable";

    /**
     * Dataview image link {@code wicket:id}.
     */
    public static final String IMG_LINK_ID = "big";

    /**
     * The maximum number {@link File files} per page.
     */
    private static final int ITEMS_PER_PAGE = 10;

    /**
     * Constructor for HottestFiles.
     * 
     * @param parameters
     *            The necessary parameters for the page.
     */
    public HottestFiles(final PageParameters parameters) {
        super(parameters);
        add(new AjaxDataView(DATA_CONTAINER_ID, NAVIGATOR_ID,
                createDataView()));
    }

    /**
     * Creates a DataView that shown a list of the hottest
     * {@link File files}.
     * 
    
     * @return Hottest files dataview */
    private DataView<FileDto> createDataView() {
        LoadableDetachableModel<List<FileDto>> ldm = new HottestFilesModel();
        DataView<FileDto> dataView = new HottestFilesDataView(
                REPEATER_ID, new HottestFileListDataProvider(ldm
                        .getObject().size()));
        dataView.setItemsPerPage(ITEMS_PER_PAGE);
        return dataView;
    }

    /**
     * @see HottestFiles#createDataView()
     */
    private class HottestFilesDataView extends DataView<FileDto> {

        /**
         * Calls the inherit constructor.
         * @param id String
         * @param dataProvider IDataProvider<FileDto>
         */
        protected HottestFilesDataView(String id,
                IDataProvider<FileDto> dataProvider) {
            super(id, dataProvider);
        }

        /**
         * Method populateItem.
         * @param item Item<FileDto>
         */
        @Override
        public void populateItem(final Item<FileDto> item) {
            PageParameters pars = new PageParameters();
            int idFile = item.getModelObject().getId();

            // TODO Change to HottestFilesBig if necessary
            pars.add("fid", idFile);
            BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
                    IMG_LINK_ID, PublicFilesBig.class, pars);

            bpl.add(new NonCachingImage("img",
                    new BlobImageResource() {
                        @Override
                        protected Blob getBlob(Attributes arg0) {
                            try {
                                return new SerialBlob(item
                                        .getModelObject()
                                        .getFileSmall());
                            } catch (SQLException e) {
                                return null;
                            }
                        }
                    }));
            item.add(bpl);
        }
    }

    /**
     * Method renderHead.
     * @param response IHeaderResponse
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
