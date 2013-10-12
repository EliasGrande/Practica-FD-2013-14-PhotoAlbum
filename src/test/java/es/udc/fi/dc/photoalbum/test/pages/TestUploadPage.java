package es.udc.fi.dc.photoalbum.test.pages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import junit.framework.Assert;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
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
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.*;

public class TestUploadPage {

	private WicketApp wicketApp;
	private WicketTester tester;
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
						User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
						Album album = new Album(1, ALBUM_NAME_EXIST, user,
								null, null, PrivacyLevel.SHAREABLE);
						set.add(new File(1, "1", new byte[1], new byte[1],
								album));
						album.setFiles(set);
						user.getAlbums().add(album);
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
						return null;
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
						return null;
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
						return new ArrayList<File>();
					}

					public ArrayList<File> getAlbumFilesOwnPaging(int albumId,
							int first, int count) {
						return null;
					}

					public Long getCountAlbumFiles(int albumId) {
						return (long) 0;
					}

					public void changePrivacyLevel(File file,
							String privacyLevel) {
						file.setPrivacyLevel(privacyLevel);
					}

					public ArrayList<File> getAlbumFilesOwn(int albumId,
							String minPrivacyLevel) {
						ArrayList<File> list = new ArrayList<File>();
						User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
						Album album = new Album(1, ALBUM_NAME_EXIST, user, null,
								null, PrivacyLevel.SHAREABLE);
						File file = new File(albumId, "1", new byte[1], new byte[1],
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
						Album album = new Album(1, ALBUM_NAME_EXIST, user, null,
								null, PrivacyLevel.SHAREABLE);
						File file = new File(albumId, "1", new byte[1], new byte[1],
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
				context.putBean("albumBean", mockAlbum);
				context.putBean("fileBean", mockFile);
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
		Page page = new Upload(pars);
		this.tester.startPage(page);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(Upload.class);
		tester.assertComponent("upload:fileInput", FileUploadField.class);
	}
	
	@Test
	public void testChangePrivacy() {
		tester.assertRenderedPage(Upload.class);
		@SuppressWarnings("unchecked")
		DropDownChoice<Album> dropDownChoice = (DropDownChoice<Album>) tester
				.getComponentFromLastRenderedPage("formPrivacyLevel:privacyLevels");
		Assert.assertEquals(3, dropDownChoice.getChoices().size());
	}
}
