package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.pub.PublicFilesBig;

public class TestPublicFilesBigPage {
	private WicketApp wicketApp;
	private WicketTester tester;
	private Album album;
	private Set<File> set = new HashSet<File>();

	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();

				FileService mockFile = new FileService() {
					public void create(File file) {
					}

					public void delete(File file) {
					}

					public File getFileOwn(int id, String name, int userId) {
						return null;
					}

					public File getFileShared(int id, String name, int userId) {
						User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
						album = new Album(1, ALBUM_NAME_EXIST, user, null,
								null, PrivacyLevel.PRIVATE);
						File file = new File(1, "1", new byte[1], new byte[1],
								album);
						set.add(file);
						album.setFiles(set);
						user.getAlbums().add(album);
						return file;
					}

					public void changeAlbum(File file, Album album) {
					}

					public File getById(Integer id) {
						return new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE));
					}

					public ArrayList<File> getAlbumFilesOwn(int albumId) {
						return null;
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
					}

					public File getFilePublic(int id, String name, int userId) {
						return null;
					}

					public ArrayList<File> getAlbumFilesShared(int albumId,
							int userId) {
						return null;
					}

					public ArrayList<File> getAlbumFilesSharedPaging(
							int albumId, int userId, int first, int count) {
						return null;
					}

					public ArrayList<File> getAlbumFilesPublic(int albumId,
							int userId) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getAlbumFilesPublicPaging(
							int albumId, int userId, int first, int count) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getFilesByTag(int userId, String tag) {
						return null;
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

					public ArrayList<User> getUsersSharingWith(int userId) {
						return null;
					}
				};
				context.putBean("userBean", mock);
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
		pars.add("albumId", 1);
		pars.add("fid", 1);
		Page page = new PublicFilesBig(pars);
		this.tester.startPage(page);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(PublicFilesBig.class);
	}
}

