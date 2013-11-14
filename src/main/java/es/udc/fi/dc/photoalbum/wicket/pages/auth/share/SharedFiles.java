package es.udc.fi.dc.photoalbum.wicket.pages.auth.share;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.RestartResponseException;
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
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
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
import es.udc.fi.dc.photoalbum.wicket.models.SharedFilesModel;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.ErrorPage404;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.tag.BaseTags;
import es.udc.fi.dc.photoalbum.wicket.panels.CommentAndVotePanel;

@SuppressWarnings("serial")
public class SharedFiles extends BasePageAuth {

	@SpringBean
	private AlbumService albumService;
	@SpringBean
	private AlbumTagService albumTagService;

	private Album album;
	private static final int ITEMS_PER_PAGE = 10;
	private static final int TAG_PER_PAGE = 5;

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
			
		} else {
			throw new RestartResponseException(ErrorPage404.class);
		}
		add(new BookmarkablePageLink<Void>("linkBack", SharedAlbums.class,
				parameters.remove("album")));
		add(new AjaxDataView("dataContainer", "navigator", createDataView()));
		add(new AjaxDataView("albumTagDataContainer","albumTagNavigator",createAlbumTagsDataView()));
		add(new CommentAndVotePanel("commentAndVote", this, album));
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

                    protected Blob getBlob(Attributes arg0) {
                        return BlobFromFile.getSmall(item.getModelObject());
                    }
				}));
				item.add(bpl);
			}
		};
		dataView.setItemsPerPage(ITEMS_PER_PAGE);
		return dataView;
	}
	
	private DataView<AlbumTag> createAlbumTagsDataView() {
		final List<AlbumTag> list = new ArrayList<AlbumTag>(
				albumTagService.getTags(album.getId()));
		DataView<AlbumTag> dataView = new DataView<AlbumTag>("pageable",
				new ListDataProvider<AlbumTag>(list)) {

			@Override
			protected void populateItem(Item<AlbumTag> item) {
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
        super.renderHead(response);
	    response.render(CssHeaderItem
                .forReference(new CssResourceReference(
                        SharedFiles.class, "SharedFiles.css")));
	}
}
