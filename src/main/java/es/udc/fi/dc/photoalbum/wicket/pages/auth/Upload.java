package es.udc.fi.dc.photoalbum.wicket.pages.auth;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;

import es.udc.fi.dc.photoalbum.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.AlbumTagService;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.utils.ResizeImage;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.FileListDataProvider;
import es.udc.fi.dc.photoalbum.wicket.MyAjaxButton;
import es.udc.fi.dc.photoalbum.wicket.models.AlbumModel;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.tag.BaseTags;

@SuppressWarnings("serial")
public class Upload extends BasePageAuth {

	@SpringBean
	private FileService fileService;
	@SpringBean
	private AlbumService albumService;
	@SpringBean
	private AlbumTagService albumTagService;
	private AlbumModel am;
	private static final int ITEMS_PER_PAGE = 10;
	private static final int MAX_UPLOAD = 10000;
	private static final int SIZE = 200;
	private FeedbackPanel feedback;
	private PageParameters parameters;
	private static final int TAG_PER_PAGE = 5;

	public Upload(PageParameters parameters) {
		super(parameters);
		if (parameters.getNamedKeys().contains("album")) {
			String name = parameters.get("album").toString();
			add(new Label("album", name));
			AlbumModel am = new AlbumModel(name);
			this.am = am;
			if (am.getObject() == null) {
				throw new RestartResponseException(ErrorPage404.class);
			}
		} else {
			throw new RestartResponseException(ErrorPage404.class);
		}
		FeedbackPanel feedback = new FeedbackPanel("uploadFeedback");
		feedback.setOutputMarkupId(true);
		add(feedback);
		this.feedback = feedback;
		this.parameters = parameters;
		add(createUplooadForm());
		add(new AjaxDataView("dataContainer", "navigator", createFileDataView()));
		add(createAddTagForm());
		add(new AjaxDataView("dataAlbumTagContainer","albumTagNavigator",createAlbumTagsDataView()));
	}

	private DataView<File> createFileDataView() {
		int count = fileService.getCountAlbumFiles(am.getObject().getId())
				.intValue();
		DataView<File> dataView = new DataView<File>("pageable",
				new FileListDataProvider(count, am.getObject().getId())) {
			public void populateItem(final Item<File> item) {
				PageParameters pars = new PageParameters();
				pars.add("album", am.getObject().getName());
				pars.add("fid", Integer.toString(item.getModelObject().getId()));
				BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
						"big", Image.class, pars);
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

	private Form<Void> createUplooadForm() {
		final FileUploadField fileUploadField;
		fileUploadField = new FileUploadField("fileInput");

		Form<Void> form = new Form<Void>("upload") {
			@Override
			protected void onSubmit() {
				final List<FileUpload> uploads = fileUploadField
						.getFileUploads();
				if (uploads != null) {
					for (FileUpload upload : uploads) {
						try {
							byte[] bFile = upload.getBytes();
							System.out.println(upload.getContentType());
							if (upload.getClientFileName().matches(
									"(.+(\\.(?i)(jpg|jpeg|bmp|png))$)")) {
								File file = new File(null,
										upload.getClientFileName(), bFile,
										ResizeImage.resize(bFile, SIZE,
												upload.getContentType()),
										am.getObject());
								fileService.create(file);
								Upload.this.info("saved file: "
										+ upload.getClientFileName());
								setResponsePage(new Upload(getPageParameters()));
							} else {
								Upload.this.error(new StringResourceModel(
										"upload.wrongFormat", this, null)
										.getString()
										+ upload.getClientFileName());
							}
						} catch (Exception e) {
							Upload.this.error(new StringResourceModel(
									"upload.wrongFormat", this, null)
									.getString());
						}
					}
				} else {
					error(new StringResourceModel("upload.noFiles", this, null)
							.getString());
				}
			}
		};
		form.add(fileUploadField);
		form.setMultiPart(true);
		form.setMaxSize(Bytes.kilobytes(MAX_UPLOAD));
		form.add(new MyAjaxButton("ajax-button", form, feedback));
		return form;
	}

	private Form<AlbumTag> createAddTagForm() {
		Form<AlbumTag> form = new Form<AlbumTag>("formAddTag") {
			@Override
			public void onSubmit() {
				//FIXME Al insertar un tag repetido que ponga tag repetido
				//FIXME Separar tags por palabras¿?¿?¿?
				AlbumTag aTag = getModelObject();
				aTag.setAlbum(am.getObject());
				albumTagService.create(aTag);
				info(new StringResourceModel("tag.added", this, null)
						.getString());
				setResponsePage(new Upload(parameters));
			}
		};
		AlbumTag aTag = new AlbumTag();
		form.setDefaultModel(new Model<AlbumTag>(aTag));
		RequiredTextField<String> newTag = new RequiredTextField<String>(
				"newTag", new PropertyModel<String>(aTag, "tag"));
		newTag.setLabel(new StringResourceModel("upload.tagField", this, null));
		form.add(newTag);
		form.add(new MyAjaxButton("ajax-button", form, feedback));
		return form;
	}

	private DataView<AlbumTag> createAlbumTagsDataView() {
		final List<AlbumTag> list = new ArrayList<AlbumTag>(
				albumTagService.getTags(this.am.getObject().getId()));
		DataView<AlbumTag> dataView = new DataView<AlbumTag>("pageable",
				new ListDataProvider<AlbumTag>(list)) {

			@Override
			protected void populateItem(final Item<AlbumTag> item) {
				PageParameters pars = new PageParameters();
				pars.add("tagName", item.getModelObject().getTag());
				BookmarkablePageLink<Void> bpl = new BookmarkablePageLink<Void>(
						"link", BaseTags.class, pars);
				bpl.add(new Label("tagName", item.getModelObject().getTag()));
				item.add(bpl);
				item.add(new Link<Void> ("delete") {
					public void onClick() {
						albumTagService.delete(item.getModelObject());
						info(new StringResourceModel("tag.deleted", this,
								null).getString());
						setResponsePage(new Upload(parameters));
					}
					
				});
			}
		};
		dataView.setItemsPerPage(TAG_PER_PAGE);
		return dataView;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("css/Upload.css");
	}
}
