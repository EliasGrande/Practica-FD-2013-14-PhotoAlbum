package es.udc.fi.dc.photoalbum.wicket.pages.auth.pub;

import java.sql.Blob;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.PublicNavigateForm;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.BasePageAuth;


@SuppressWarnings("serial")
public class PublicFilesBig extends BasePageAuth{

	@SpringBean
	private FileService fileService;
	
	private File file;
	
	public PublicFilesBig(final PageParameters parameters) {
		super(parameters);		
		
		int id = parameters.get("fid").toInt();
		int albumId = parameters.get("albumId").toInt();
		File auxFile = fileService.getById(id);
		this.file = auxFile;
		
		add(new PublicNavigateForm<Void>("formNavigate", albumId, id, PublicFilesBig.class));
		add(createNonCachingImage());
		PageParameters newPars = new PageParameters();
		newPars.add("albumId", albumId);
		
		add(new BookmarkablePageLink<Void>("linkBack", PublicFiles.class,
				newPars));	
	}

	private NonCachingImage createNonCachingImage() {
		return new NonCachingImage("img", new BlobImageResource() {
			protected Blob getBlob() {
				return BlobFromFile.getBig(file);
			}
		});
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("css/SharedBig.css");
	}
}
