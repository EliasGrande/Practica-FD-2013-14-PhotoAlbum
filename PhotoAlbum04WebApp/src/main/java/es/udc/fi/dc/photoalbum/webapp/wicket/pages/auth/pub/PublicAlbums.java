package es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.pub;

import java.util.List;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.spring.AlbumService;
import es.udc.fi.dc.photoalbum.model.spring.UserService;
import es.udc.fi.dc.photoalbum.webapp.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.webapp.wicket.PublicAlbumListDataProvider;
import es.udc.fi.dc.photoalbum.webapp.wicket.models.PublicAlbumsModelFull;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.SharedAlbums;

/**
 * The page that allow to view the public {@link Album}s.
 */
@SuppressWarnings("serial")
public class PublicAlbums extends BasePageAuth {
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
     * The maximum number {@link Album}'s per page.
     */
    private static final int ALBUMS_PER_PAGE = 10;

    /**
     * Constructor for PublicAlbums.
     * 
     * @param parameters
     *            The necessary parameters for the page.
     */
    public PublicAlbums(final PageParameters parameters) {
        super(parameters);
        add(new AjaxDataView("dataContainer", "navigator",
                createDataView()));
    }

    /**
     * Creates a DataView that shown a list of public {@link Album}s.
     * 
     * @return DataView<{@link Album}> Return the DataView with the
     *         {@link Album}s.
     */
    private DataView<Album> createDataView() {
        LoadableDetachableModel<List<Album>> ldm = new PublicAlbumsModelFull();
        DataView<Album> dataView = new DataView<Album>("pageable",
                new PublicAlbumListDataProvider(ldm.getObject()
                        .size())) {
            public void populateItem(final Item<Album> item) {
                PageParameters pars = new PageParameters();
                pars.add("albumId", item.getModelObject().getId()
                        .toString());
                final String linkName = item.getModelObject()
                        .getName()
                        + " - "
                        + item.getModelObject().getUser().getEmail();
                BookmarkablePageLink<Void> bp = new BookmarkablePageLink<Void>(
                        "albums", PublicFiles.class, pars);
                bp.add(new Label("name_mail", linkName));
                item.add(bp);
            }
        };
        dataView.setItemsPerPage(ALBUMS_PER_PAGE);
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
                        SharedAlbums.class, "SharedAlbums.css")));
    }
}
