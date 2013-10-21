package es.udc.fi.dc.photoalbum.wicket.pages.auth;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.FileShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.FileTag;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.FileShareInformationService;
import es.udc.fi.dc.photoalbum.spring.FileTagService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.AjaxDataView;
import es.udc.fi.dc.photoalbum.wicket.BlobFromFile;
import es.udc.fi.dc.photoalbum.wicket.MyAjaxButton;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.NavigateForm;
import es.udc.fi.dc.photoalbum.wicket.auth.tag.BaseTags;
import es.udc.fi.dc.photoalbum.wicket.models.AlbumModel;
import es.udc.fi.dc.photoalbum.wicket.models.AlbumsModel;
import es.udc.fi.dc.photoalbum.wicket.models.FileOwnModel;
import es.udc.fi.dc.photoalbum.wicket.models.PrivacyLevelOption;
import es.udc.fi.dc.photoalbum.wicket.models.PrivacyLevelsModel;

@SuppressWarnings("serial")
public class Image extends BasePageAuth {

	@SpringBean
	private FileService fileService;
	@SpringBean
	private UserService userService;
	@SpringBean
	private FileShareInformationService shareInformationService;
	@SpringBean
	private FileTagService fileTagService;
	private FileOwnModel fileOwnModel;
	private PageParameters parameters;
	private Album selectedAlbum;
	private PrivacyLevelOption selectedPrivacyLevel;
	private static final int ITEMS_PER_PAGE = 20;
	private static final int TAG_PER_PAGE = 5;

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
			DataView<FileShareInformation> dataView = createShareDataView();
			add(dataView);
			add(new PagingNavigator("navigator", dataView));
			add(createNonCachingImage());
			add(createFormDelete());
			add(createFormMove());
			add(createFormPrivacyLevel());
			add(createAddTagForm());
			add(new BookmarkablePageLink<Void>("linkBack", Upload.class,
					(new PageParameters()).add("album", name)));
			add(createShareForm());
			add(new AjaxDataView("fileTagDataContainer","fileTagNavigator",createFileTagsDataView()));
		} else {
			throw new RestartResponseException(ErrorPage404.class);
		}
	}

	private DataView<FileShareInformation> createShareDataView() {
		final List<FileShareInformation> list = new ArrayList<FileShareInformation>(
				shareInformationService.getFileShares(this.fileOwnModel
						.getObject().getId()));
		DataView<FileShareInformation> dataView = new DataView<FileShareInformation>(
				"pageable", new ListDataProvider<FileShareInformation>(list)) {

			protected void populateItem(Item<FileShareInformation> item) {
				final FileShareInformation shareInformation = item
						.getModelObject();
				item.add(new Label("email", shareInformation.getUser()
						.getEmail()));
				item.add(new Link<Void>("delete") {
					public void onClick() {
						shareInformationService.delete(shareInformation);
						info(new StringResourceModel("share.deleted", this,
								null).getString());
						setResponsePage(new Image(parameters));
					}

				});
			}
		};
		dataView.setItemsPerPage(ITEMS_PER_PAGE);
		return dataView;
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
				fileService
						.changeAlbum(fileOwnModel.getObject(), selectedAlbum);
				info(new StringResourceModel("image.moved", this, null)
						.getString());
				setResponsePage(new Upload(parameters.remove("fid")));
			}
		};
		DropDownChoice<Album> listAlbums = new DropDownChoice<Album>("albums",
				new PropertyModel<Album>(this, "selectedAlbum"),
				new AlbumsModel(fileOwnModel.getObject().getAlbum()),
				new ChoiceRenderer<Album>("name", "id"));
		listAlbums.setRequired(true);
		listAlbums.setLabel(new StringResourceModel("image.moveAlbum", this,
				null));
		form.add(listAlbums);
		form.add(new FeedbackPanel("feedback"));
		return form;
	}

	private Form<Void> createFormPrivacyLevel() {
		Form<Void> form = new Form<Void>("formPrivacyLevel") {
			@Override
			public void onSubmit() {
				if (selectedPrivacyLevel != null
						&& PrivacyLevel.validateFile(selectedPrivacyLevel
								.getValue())) {
					fileService.changePrivacyLevel(fileOwnModel.getObject(),
							selectedPrivacyLevel.getValue());
					info(new StringResourceModel("privacyLevel.changed", this,
							null).getString());
				}
				setResponsePage(new Image(parameters));
			}
		};
		selectedPrivacyLevel = new PrivacyLevelOption(fileOwnModel.getObject()
				.getPrivacyLevel(), this);
		DropDownChoice<PrivacyLevelOption> listPrivacyLevel = new DropDownChoice<PrivacyLevelOption>(
				"privacyLevels", new PropertyModel<PrivacyLevelOption>(this,
						"selectedPrivacyLevel"), new PrivacyLevelsModel(
						fileOwnModel.getObject(), this),
				new ChoiceRenderer<PrivacyLevelOption>("label", "value"));
		listPrivacyLevel.setRequired(true);
		listPrivacyLevel.setLabel(new StringResourceModel(
				"privacyLevel.change", this, null));
		form.add(listPrivacyLevel);

		return form;
	}

	private Form<User> createShareForm() {
		Form<User> form = new Form<User>("form") {
			@Override
			protected void onSubmit() {
				User user = getModelObject();
				User existedUser = userService.getUser(user);
				if (existedUser == null) {
					error(new StringResourceModel("share.noUser", this, null)
							.getString());
				} else if (existedUser.getEmail().equals(
						userService.getById(
								((MySession) Session.get()).getuId())
								.getEmail())) {
					error(new StringResourceModel("share.yourself", this, null)
							.getString());
				} else {
					FileShareInformation shareInformation = new FileShareInformation(
							null, fileOwnModel.getObject(), existedUser);
					ArrayList<FileShareInformation> getFileShares = shareInformationService
							.getFileShares(fileOwnModel.getObject().getId());
					ListIterator<FileShareInformation> iter = getFileShares
							.listIterator();
					boolean comp = false;
					while (iter.hasNext() && (comp == false)) {
						FileShareInformation item = (FileShareInformation) iter
								.next();
						String shMail = item.getUser().getEmail();
						if (shMail == existedUser.getEmail()) {
							comp = true;
						}
					}
					if (!comp) {
						shareInformationService.create(shareInformation);
						info(new StringResourceModel("share.shareFileSuccess",
								this, null).getString());
						setResponsePage(new Image(getPage().getPageParameters()));
					} else {
						error(new StringResourceModel("share.alreadyExist",
								this, null).getString());
					}
				}
			}
		};
		User user = new User();
		form.setDefaultModel(new Model<User>(user));
		RequiredTextField<String> shareEmail = new RequiredTextField<String>(
				"shareEmail", new PropertyModel<String>(user, "email"));
		if (fileOwnModel.getObject().getPrivacyLevel()
				.equals(PrivacyLevel.INHERIT_FROM_ALBUM)) {
			shareEmail.setEnabled(false);
		} else {
			shareEmail.setEnabled(true);
		}
		shareEmail.setLabel(new StringResourceModel("share.emailField", this,
				null));
		shareEmail.add(EmailAddressValidator.getInstance());
		form.add(shareEmail);
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		form.add(feedback);
		form.add(new MyAjaxButton("ajax-button", form, feedback));
		return form;
	}

	private Form<FileTag> createAddTagForm() {
		Form<FileTag> form = new Form<FileTag>("formAddTag") {
			@Override
			public void onSubmit() {
				//FIXME Al insertar un tag repetido que ponga tag repetido
				//FIXME Separar tags por palabras¿?¿?¿?
				FileTag fTag = getModelObject();
				fTag.setFile(fileOwnModel.getObject());
				fileTagService.create(fTag);
				info(new StringResourceModel("tag.added", this, null)
						.getString());
				setResponsePage(new Image(parameters));
			}
		};
		FileTag fTag = new FileTag();
		form.setDefaultModel(new Model<FileTag>(fTag));
		RequiredTextField<String> newTag = new RequiredTextField<String>(
				"newTag", new PropertyModel<String>(fTag, "tag"));
		newTag.setLabel(new StringResourceModel("upload.tagField", this, null));
		form.add(newTag);
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		form.add(feedback);
		form.add(new MyAjaxButton("ajax-button", form, feedback));
		return form;
	}

	private DataView<FileTag> createFileTagsDataView() {
		final List<FileTag> list = new ArrayList<FileTag>(
				fileTagService.getTags(this.fileOwnModel.getObject().getId()));
		DataView<FileTag> dataView = new DataView<FileTag>("pageable",
				new ListDataProvider<FileTag>(list)) {

			@Override
			protected void populateItem(Item<FileTag> item) {
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
		response.renderCSSReference("css/Image.css");
	}
}
