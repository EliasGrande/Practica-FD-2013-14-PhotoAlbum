package es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share;

import java.util.List;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.AlbumService;
import es.udc.fi.dc.photoalbum.model.spring.UserService;
import es.udc.fi.dc.photoalbum.webapp.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.ErrorPage404;

/**
 * Page that allows to view the shared {@link Album}s.
 */
@SuppressWarnings("serial")
public class SharedAlbums extends BasePageAuth {
    /**
     * @see UserService
     */
    @SpringBean
    private UserService userService;
    /**
     * @see AlbumService
     */
    @SpringBean
    private AlbumService albumService;
    /**
     *  The maximum number ({@link Album}s or {@link File}) per
     *  page.
     */
    private static final int ITEMS_PER_PAGE = 10;
    /**
     * The {@link User} who wants to view the {@link Album}s.
     */
    private User user;

    /**
     * Constructor for SharedAlbums.
     * 
     * @param parameters The parameters necessaries for load the page.
     */
    public SharedAlbums(final PageParameters parameters) {
        super(parameters);
        if (parameters.getNamedKeys().contains("user")) {
            final String email = parameters.get("user").toString();
            User user = new User();
            user.setEmail(email);
            if (userService.getUser(user) == null) {
                throw new RestartResponseException(ErrorPage404.class);
            }
            this.user = user;
            add(new AjaxDataView("dataContainer", "navigator",
                    createShareDataView()));
        } else {
            throw new RestartResponseException(ErrorPage404.class);
        }
    }

    /**
     * Method createShareDataView that shown a list of shared {@link Album}s.
     * 
     * @return DataView<{@link Album}> the data view with the list of {@link Album}.
     */
    private DataView<Album> createShareDataView() {
        List<Album> list = albumService
                .getAlbumsSharedWith(
                        ((MySession) Session.get()).getuId(),
                        user.getEmail());
        DataView<Album> dataView = new DataView<Album>("pageable",
                new ListDataProvider<Album>(list)) {
            public void populateItem(final Item<Album> item) {
                final Album album = item.getModelObject();
                PageParameters pars = new PageParameters();
                pars.add("user", user.getEmail());
                pars.add("album", album.getName());
                BookmarkablePageLink<Void> bp = new BookmarkablePageLink<Void>(
                        "link", SharedFiles.class, pars);
                bp.add(new Label("name", album.getName()));
                item.add(bp);
            }
        };
        dataView.setItemsPerPage(ITEMS_PER_PAGE);
        return dataView;
    }

    /**
     * Method renderHead, that allow to use CSS to render the page.
     * 
     * @param response IHeaderResponse
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
