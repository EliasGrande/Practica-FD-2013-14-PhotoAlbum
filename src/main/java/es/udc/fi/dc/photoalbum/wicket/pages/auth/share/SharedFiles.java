package es.udc.fi.dc.photoalbum.wicket.pages.auth.share;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.ShareInformation;
import es.udc.fi.dc.photoalbum.spring.ShareInformationService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.FileListDataProvider;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.models.FilesModel;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.ErrorPage404;

import java.sql.Blob;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SharedFiles extends BasePageAuth {

	@SpringBean
	private ShareInformationService shareInformationService;
	private Album album;
	private static final int ITEMS_PER_PAGE = 10;

	public SharedFiles(final PageParameters parameters) {
		super(parameters);
		if ((parameters.getNamedKeys().contains("album"))
				&& (parameters.getNamedKeys().contains("user"))) {
			String name = parameters.get("album").toString();
			String email = parameters.get("user").toString();
			ShareInformation share = shareInformationService.getShare(name,
					((MySession) Session.get()).getuId(), email);
			if (share == null) {
				throw new RestartResponseException(ErrorPage404.class);
			}
			this.album = share.getAlbum();
		} else {
			throw new RestartResponseException(ErrorPage404.class);
		}
		add(new BookmarkablePageLink<Void>("linkBack", SharedAlbums.class,
				parameters.remove("album")));
		add(new AjaxDataView("dataContainer", "navigator", createDataView()));
	}

	private DataView<File> createDataView() {
		LoadableDetachableModel<ArrayList<File>> ldm = new FilesModel(
				this.album.getId(), PrivacyLevel.SHAREABLE);
		DataView<File> dataView = new DataView<File>("pageable",
				new FileListDataProvider(ldm.getObject().size(),
						this.album.getId(), PrivacyLevel.SHAREABLE)) {
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

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("css/SharedFiles.css");
	}
}
