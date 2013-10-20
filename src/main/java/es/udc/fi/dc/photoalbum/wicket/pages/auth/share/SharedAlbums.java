package es.udc.fi.dc.photoalbum.wicket.pages.auth.share;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.ErrorPage404;

import java.util.List;

@SuppressWarnings("serial")
public class SharedAlbums extends BasePageAuth {

	@SpringBean
	private UserService userService;
	@SpringBean
	private AlbumService albumService;
	private static final int ITEMS_PER_PAGE = 10;
	private User user;

	public SharedAlbums(final PageParameters parameters) {
		super(parameters);
		if (parameters.getNamedKeys().contains("user")) {
			final String email = parameters.get("user").toString();
			User user = new User();
			user.setEmail(email);
			if (userService.getUser(user) == null) {
				throw new RestartResponseException(ErrorPage404.class);
			}
			String name = parameters.get("album").toString();
			add(new Label("album", name));
			this.user = user;
			add(new AjaxDataView("dataContainer", "navigator",
					createShareDataView()));
		} else {
			throw new RestartResponseException(ErrorPage404.class);
		}
	}

	private DataView<Album> createShareDataView() {
		List<Album> list = albumService.getAlbumsSharedWith(
				((MySession) Session.get()).getuId(), user.getEmail());
		DataView<Album> dataView = new DataView<Album>(
				"pageable", new ListDataProvider<Album>(list)) {
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

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("css/SharedAlbums.css");
	}
}
