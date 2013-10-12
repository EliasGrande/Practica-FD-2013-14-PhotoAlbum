package es.udc.fi.dc.photoalbum.wicket.pages.auth.share;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.SharedNavigateForm;
import es.udc.fi.dc.photoalbum.wicket.models.FileSharedAlbum;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.ErrorPage404;

import java.sql.Blob;

@SuppressWarnings("serial")
public class SharedBig extends BasePageAuth {

	private FileSharedAlbum fileSharedAlbum;

	public SharedBig(final PageParameters parameters) {
		super(parameters);
		if (parameters.getNamedKeys().contains("fid")
				&& parameters.getNamedKeys().contains("album")) {
			int id = parameters.get("fid").toInt();
			String name = parameters.get("album").toString();
			int userId = ((MySession) Session.get()).getuId();
			FileSharedAlbum fileSharedAlbum = new FileSharedAlbum(id, name,
					userId);
			this.fileSharedAlbum = fileSharedAlbum;
			if ((fileSharedAlbum.getObject() == null)
					|| (!(fileSharedAlbum.getObject().getAlbum().getName()
							.equals(name)))) {
				throw new RestartResponseException(ErrorPage404.class);
			}
			add(new SharedNavigateForm<Void>("formNavigate", fileSharedAlbum
					.getObject().getAlbum().getId(), userId, fileSharedAlbum
					.getObject().getId(),SharedBig.class));
			add(createNonCachingImage());
			PageParameters newPars = new PageParameters();
			newPars.add("album", name);
			newPars.add("user", fileSharedAlbum.getObject().getAlbum()
					.getUser().getEmail());
			add(new BookmarkablePageLink<Void>("linkBack", SharedFiles.class,
					newPars));
		} else {
			throw new RestartResponseException(ErrorPage404.class);
		}
	}

	private NonCachingImage createNonCachingImage() {
		return new NonCachingImage("img", new BlobImageResource() {
			protected Blob getBlob() {
				return BlobFromFile.getBig(fileSharedAlbum.getObject());
			}
		});
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("css/SharedBig.css");
	}
}
