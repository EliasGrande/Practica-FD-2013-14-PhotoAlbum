package es.udc.fi.dc.photoalbum.wicket.pages.auth.share;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings("serial")
public class SharedUsers extends BasePageAuth {
    @SpringBean
    private UserService userService;
    private static final int ITEMS_PER_PAGE = 10;

    public SharedUsers(final PageParameters parameters) {
        super(parameters);
        add(new AjaxDataView("dataContainer", "navigator",
                createDataView()));
    }

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

    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderCSSReference("css/SharedUsers.css");
    }
}
