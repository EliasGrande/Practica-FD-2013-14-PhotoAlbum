package es.udc.fi.dc.photoalbum.wicket.pages.auth.share;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;

/**
 * Page that shown the {@link User}s who shared {@link Album}s or {@link File}s with you.
 */
@SuppressWarnings("serial")
public class SharedUsers extends BasePageAuth {
    /**
     * @see UserService
     */
    @SpringBean
    private UserService userService;
    /**
     * The maximum number of {@link User}s per page.
     */
    private static final int ITEMS_PER_PAGE = 10;

    /**
     * Constructor for SharedUsers.
     * 
     * @param parameters The parameters necessaries for load the page.
     */
    public SharedUsers(final PageParameters parameters) {
        super(parameters);
        add(new AjaxDataView("dataContainer", "navigator",
                createDataView()));
    }

    /**
     * Creates a DataView that shown a list of {@link User}s who shared something with you.
     * 
     * @return DataView<String> Return the DataView with the list of 
     *         {@link User}s.
     */
    private DataView<String> createDataView() {
        final HashSet<String> set = new HashSet<String>();
        final List<User> list = userService
                .getUsersSharingWith(((MySession) Session.get())
                        .getuId());
        for (User aList : list) {
            set.add(aList.getEmail());
        }
        final List<String> list1 = new ArrayList<String>(set);
        DataView<String> dataView = new DataView<String>("pageable",
                new ListDataProvider<String>(list1)) {
            public void populateItem(final Item<String> item) {
                final String email = item.getModelObject();
                PageParameters pars = new PageParameters();
                pars.add("user", email);
                BookmarkablePageLink<Void> bp = new BookmarkablePageLink<Void>(
                        "albums", SharedAlbums.class, pars);
                bp.add(new Label("email", email));
                item.add(bp);
            }
        };
        dataView.setItemsPerPage(ITEMS_PER_PAGE);
        return dataView;
    }

    /**
     * Method renderHead allows to use specific css in this page.
     * 
     * @param response IHeaderResponse
     * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(IHeaderResponse)
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(
                        SharedUsers.class, "SharedUsers.css")));
    }
}
