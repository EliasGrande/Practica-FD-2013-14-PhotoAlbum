package es.udc.fi.dc.photoalbum.wicket.pages.auth.pub;

import java.sql.Blob;
import java.util.ArrayList;

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
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.ShareInformationService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.PublicFileListDataProvider;
import es.udc.fi.dc.photoalbum.wicket.models.FilesModel;
import es.udc.fi.dc.photoalbum.wicket.models.PublicFilesModel;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.ErrorPage404;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.share.SharedAlbums;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.share.SharedBig;

@SuppressWarnings("serial")
public class PublicFiles extends BasePageAuth {

	@SpringBean
	private AlbumService albumService;

	private static final int ITEMS_PER_PAGE = 10;
	private Album album;
	
	// si no hay ninguna foto que no salga
	public PublicFiles(final PageParameters parameters) {
		super(parameters);
		int albumId = parameters.get("albumId").toInt();
		this.album = albumService.getById(albumId);

		add(new BookmarkablePageLink<Void>("linkBack", PublicAlbums.class, null));
		add(new AjaxDataView("dataContainer", "navigator", createDataView()));
	}

	private DataView<File> createDataView() {
		LoadableDetachableModel<ArrayList<File>> ldm = new PublicFilesModel(
				album.getId());
		DataView<File> dataView = new DataView<File>("pageable",
				new PublicFileListDataProvider(ldm.getObject().size(),
						album.getId())) {
			public void populateItem(final Item<File> item) {
				PageParameters pars = new PageParameters();
				int idFile = item.getModelObject().getId();
				//int index = publicFiles.indexOf(item.getModelObject());
				
				pars.add("fid", idFile);
				pars.add("albumId", album.getId());
				//pars.add("fid", Integer.toString(item.getModelObject().getId()));
				BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
						"big", PublicFilesBig.class, pars);
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