package es.udc.fi.dc.photoalbum.wicket.pages.auth.share;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.AlbumTagService;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.SharedFileListDataProvider;
import es.udc.fi.dc.photoalbum.wicket.auth.tag.BaseTags;
import es.udc.fi.dc.photoalbum.wicket.models.AlbumModel;
import es.udc.fi.dc.photoalbum.wicket.models.SharedFilesModel;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.ErrorPage404;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class SharedFiles extends BasePageAuth {

	@SpringBean
	private AlbumService albumService;
	@SpringBean
	private AlbumTagService albumTagService;
	private AlbumModel am;
	private Album album;
	private static final int ITEMS_PER_PAGE = 10;

	public SharedFiles(final PageParameters parameters) {
		super(parameters);
		if ((parameters.getNamedKeys().contains("album"))
				&& (parameters.getNamedKeys().contains("user"))) {
			String name = parameters.get("album").toString();
			String email = parameters.get("user").toString();
			this.album = albumService.getSharedAlbum(name,
					((MySession) Session.get()).getuId(), email);
			if (this.album == null) {
				throw new RestartResponseException(ErrorPage404.class);
			}
			add(new Label("album", name));
			AlbumModel am = new AlbumModel(name);
			this.am = am;
			if (am.getObject() == null) {
				throw new RestartResponseException(ErrorPage404.class);
			}
		} else {
			throw new RestartResponseException(ErrorPage404.class);
		}
		add(new BookmarkablePageLink<Void>("linkBack", SharedAlbums.class,
				parameters.remove("album")));
		add(new AjaxDataView("dataContainer", "navigator", createDataView()));
		DataView<AlbumTag> dataView = AlbumTagsDataView();
		add(dataView);
	}

	private DataView<File> createDataView() {
		int userId = ((MySession) Session.get()).getuId();
		LoadableDetachableModel<ArrayList<File>> ldm = new SharedFilesModel(
				this.album.getId(), userId);
		DataView<File> dataView = new DataView<File>("pageable",
				new SharedFileListDataProvider(ldm.getObject().size(),
						this.album.getId(), userId)) {
			public void populateItem(final Item<File> item) {
				PageParameters pars = new PageParameters();
				pars.add("album", album.getName());
				pars.add("fid", Integer.toString(item.getModelObject().getId()));
				BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
						"big", SharedBig.class, pars);
				bpl.add(new NonCachingImage("img", new BlobImageResource() {
					protected Blob getBlob() {
						return BlobFromFile.getSmall(item.getModelObject());
					}
				}));
				item.add(bpl);
			}
		};
		dataView.setItemsPerPage(ITEMS_PER_PAGE);
		return dataView;
	}
	
	private DataView<AlbumTag> AlbumTagsDataView() {
		final List<AlbumTag> list = new ArrayList<AlbumTag>(
				albumTagService.getTags(this.am.getObject().getId()));
		DataView<AlbumTag> dataView = new DataView<AlbumTag>("pageable",
				new ListDataProvider<AlbumTag>(list)) {

			@Override
			protected void populateItem(Item<AlbumTag> item) {
				PageParameters pars = new PageParameters();
				pars.add("tag", item.getModelObject().getTag());
				BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
						"link", BaseTags.class, pars);
				bpl.add(new Label("tagName", item.getModelObject().getTag()));
				item.add(bpl);
			}
		};
		return dataView;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("css/SharedFiles.css");
	}
}
