package es.udc.fi.dc.photoalbum.wicket.pages.auth.pub;

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

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.PublicAlbumListDataProvider;
import es.udc.fi.dc.photoalbum.wicket.models.PublicAlbumsModelFull;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.share.SharedAlbums;

@SuppressWarnings("serial")
public class PublicAlbums extends BasePageAuth {

    @SpringBean
    private AlbumService albumService;
    @SpringBean
    private UserService userService;

    private static final int ALBUMS_PER_PAGE = 10;

    public PublicAlbums(final PageParameters parameters) {
        super(parameters);
        add(new AjaxDataView("dataContainer", "navigator",
                createDataView()));
    }

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

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(
                        SharedAlbums.class, "SharedAlbums.css")));
    }
}
