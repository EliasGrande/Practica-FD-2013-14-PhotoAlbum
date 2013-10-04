package es.udc.fi.dc.photoalbum.wicket.pages.auth;

import java.sql.Blob;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.NavigateForm;
import es.udc.fi.dc.photoalbum.wicket.models.AlbumModel;
import es.udc.fi.dc.photoalbum.wicket.models.AlbumsModel;
import es.udc.fi.dc.photoalbum.wicket.models.FileOwnModel;

@SuppressWarnings("serial")
public class Image extends BasePageAuth {

	@SpringBean
	private FileService fileService;
	private FileOwnModel fileOwnModel;
	private PageParameters parameters;
	private Album selected;

	public Image(final PageParameters parameters) {
		super(parameters);
		if (parameters.getNamedKeys().contains("fid")
				&& parameters.getNamedKeys().contains("album")) {
			int id = parameters.get("fid").toInt();
			String name = parameters.get("album").toString();
			AlbumModel am = new AlbumModel(name);
			FileOwnModel fileOwnModel = new FileOwnModel(id, name,
					((MySession) Session.get()).getuId());
			this.fileOwnModel = fileOwnModel;
			if (fileOwnModel.getObject() == null) {
				throw new RestartResponseException(ErrorPage404.class);
			}
			this.parameters = parameters;
			add(new NavigateForm<Void>("formNavigate", am.getObject().getId(),
					fileOwnModel.getObject().getId(), Image.class));
			add(createNonCachingImage());
			add(createFormDelete());
			add(createFormMove());
			//add(createFormPrivacy());
			add(new BookmarkablePageLink<Void>("linkBack", Upload.class,
					(new PageParameters()).add("album", name)));
		} else {
			throw new RestartResponseException(ErrorPage404.class);
		}
	}

	private NonCachingImage createNonCachingImage() {
		return new NonCachingImage("img", new BlobImageResource() {
			protected Blob getBlob() {
				return BlobFromFile.getBig(fileOwnModel.getObject());
			}
		});
	}

	private Form<Void> createFormDelete() {
		return new Form<Void>("formDelete") {
			@Override
			public void onSubmit() {
				fileService.delete(fileOwnModel.getObject());
				info(new StringResourceModel("image.deleted", this, null)
						.getString());
				setResponsePage(new Upload(parameters.remove("fid")));
			}
		};
	}

	private Form<Void> createFormMove() {
		Form<Void> form = new Form<Void>("formMove") {
			@Override
			public void onSubmit() {
				fileService.changeAlbum(fileOwnModel.getObject(), selected);
				info(new StringResourceModel("image.moved", this, null)
						.getString());
				setResponsePage(new Upload(parameters.remove("fid")));
			}
		};
		DropDownChoice<Album> listAlbums = new DropDownChoice<Album>("albums",
				new PropertyModel<Album>(this, "selected"), new AlbumsModel(
						fileOwnModel.getObject().getAlbum()),
				new ChoiceRenderer<Album>("name", "id"));
		listAlbums.setRequired(true);
		listAlbums.setLabel(new StringResourceModel("image.moveAlbum", this,
				null));
		form.add(listAlbums);
		form.add(new FeedbackPanel("feedback"));
		return form;
	}

	/*private Form<Void> createFormPrivacy() {
		final List<String> privacityLevels = Arrays.asList(new String[] {
				"PUBLIC", "SHAREABLE", "PRIVATE" });
		Form<Void> form = new Form<Void>("formPrivacitySelector");
		add(form);
		DropDownChoice<String> choice = new DropDownChoice<String>(
				("privacitySelector"), new PropertyModel<String>(this,
						"privacity"), privacityLevels);
		Form<Void> form = new Form<Void>("formPrivacitySelector");

		DropDownChoice<String> choice = new DropDownChoice<String>(
				("privacitySelector"), new PropertyModel<String>(this,
						"privacity"), privacityLevels);
		choice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				
				 * fileService.update(fileOwnModel.getObject()); 
				 * info(new StringResourceModel("image.updated", this, null)
				 * .getString());
				 
			}
		});
		form.add(choice);
		return form;
	}*/

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("css/Image.css");
	}
}
