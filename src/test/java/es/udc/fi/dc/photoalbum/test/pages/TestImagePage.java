package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import junit.framework.Assert;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.Image;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.Upload;

public class TestImagePage {
	private WicketApp wicketApp;
	private WicketTester tester;
	private Album album;
	private Set<File> set = new HashSet<File>();

	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();
				AlbumService mockAlbum = new AlbumService() {
					public void rename(Album album, String newName) {
					}

					public Album getAlbum(String name, int userId) {
						return album;
					}

					public void delete(Album album) {
					}

					public void create(Album album) {
					}

					public Album getById(Integer id) {
						return null;
					}

					public ArrayList<Album> getAlbums(Integer id) {
						ArrayList<Album> list = new ArrayList<Album>();
						list.add(album);
						return list;
					}

					public ArrayList<Album> getPublicAlbums() {
						return null;
					}

					public void changePrivacyLevel(Album album,
							String privacyLevel) {
						album.setPrivacyLevel(privacyLevel);
					}
				};
				FileService mockFile = new FileService() {
					public void create(File file) {
					}

					public void delete(File file) {
					}

					public File getFileOwn(int id, String name, int userId) {
						User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
						album = new Album(1, ALBUM_NAME_EXIST, user, null,
								null, PrivacyLevel.SHAREABLE);
						File file = new File(1, "1", new byte[1], new byte[1],
								album);
						set.add(file);
						album.setFiles(set);
						return file;
					}

					public File getFileShared(int id, String name, int userId) {
						return null;
					}

					public void changeAlbum(File file, Album album) {
					}

					public File getById(Integer id) {
						return null;
					}

					public ArrayList<File> getAlbumFilesOwn(int albumId) {
						ArrayList<File> list = new ArrayList<File>();
						User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
						album = new Album(1, ALBUM_NAME_EXIST, user, null,
								null, PrivacyLevel.SHAREABLE);
						File file = new File(1, "1", new byte[1], new byte[1],
								album);
						set.add(file);
						album.setFiles(set);
						list.add(file);
						return list;
					}

					public ArrayList<File> getAlbumFilesOwnPaging(int albumId,
							int first, int count) {
						return null;
					}

					public Long getCountAlbumFiles(int albumId) {
						return null;
					}

					public void changePrivacyLevel(File file,
							String privacyLevel) {
						file.setPrivacyLevel(privacyLevel);
					}

					public ArrayList<File> getAlbumFilesOwn(int albumId,
							String minPrivacyLevel) {
						ArrayList<File> list = new ArrayList<File>();
						User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
						album = new Album(1, ALBUM_NAME_EXIST, user, null,
								null, PrivacyLevel.SHAREABLE);
						File file = new File(1, "1", new byte[1], new byte[1],
								album);
						file.setPrivacyLevel(minPrivacyLevel);
						set.add(file);
						album.setFiles(set);
						list.add(file);
						return list;
					}

					public ArrayList<File> getAlbumFilesPaging(int albumId,
							int first, int count, String minPrivacyLevel) {
						ArrayList<File> list = new ArrayList<File>();
						User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
						album = new Album(1, ALBUM_NAME_EXIST, user, null,
								null, PrivacyLevel.SHAREABLE);
						File file = new File(1, "1", new byte[1], new byte[1],
								album);
						file.setPrivacyLevel(minPrivacyLevel);
						set.add(file);
						album.setFiles(set);
						list.add(file);
						return list;
					}
				};
				UserService mock = new UserService() {
					public void create(User user) {
					}

					public void delete(User user) {
					}

					public void update(User user) {
					}

					public User getUser(String email, String password) {
						return null;
					}

					public User getUser(User userEmail) {
						return null;
					}

					public User getById(Integer id) {
						return new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
					}
				};
				context.putBean("userBean", mock);
				context.putBean("fileBean", mockFile);
				context.putBean("albumBean", mockAlbum);
				getComponentInstantiationListeners().add(
						new SpringComponentInjector(this, context));
			}
		};
	}

	@Before
	public void setUp() {
		this.tester = new WicketTester(this.wicketApp);
		((MySession) Session.get()).setuId(1);
		PageParameters pars = new PageParameters();
		pars.add("album", ALBUM_NAME_EXIST);
		pars.add("fid", "1");
		Page page = new Image(pars);
		this.tester.startPage(page);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(Image.class);
		tester.assertComponent("img", NonCachingImage.class);
	}

	@Test
	public void testBackLink() {
		tester.assertRenderedPage(Image.class);
		PageParameters parameters = new PageParameters();
		parameters.add("album", ALBUM_NAME_EXIST);
		tester.assertBookmarkablePageLink("linkBack", Upload.class, parameters);
	}

	@Test
	public void testMove() {
		tester.assertRenderedPage(Image.class);
		@SuppressWarnings("unchecked")
		DropDownChoice<Album> dropDownChoice = (DropDownChoice<Album>) tester
				.getComponentFromLastRenderedPage("formMove:albums");
		Assert.assertEquals(0, dropDownChoice.getChoices().size());
	}
	
	@Test
	public void testChangePrivacy() {
		tester.assertRenderedPage(Image.class);
		@SuppressWarnings("unchecked")
		DropDownChoice<File> dropDownChoice = (DropDownChoice<File>) tester
				.getComponentFromLastRenderedPage("formPrivacyLevel:privacyLevels");
		Assert.assertEquals(3, dropDownChoice.getChoices().size());
	}
}
