package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;
import java.util.Locale;

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
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.pub.PublicFiles;

public class TestPublicFilesPage {

	private WicketApp wicketApp;
	private WicketTester tester;
	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();
				UserService mockUser = new UserService() {
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
				AlbumService mockAlbum = new AlbumService() {

					public void create(Album album) {
						
					}

					public void delete(Album album) {
						
					}

					public void rename(Album album, String newName) {
						
					}

					public void changePrivacyLevel(Album album,
							String privacyLevel) {
						
					}

					public Album getById(Integer id) {
						return new Album(1,ALBUM_NAME_EXIST,new User(1, USER_EMAIL_EXIST, USER_PASS_YES),null,null,PrivacyLevel.PRIVATE);

					}

					public Album getAlbum(String name, int userId) {
						return null;
					}

					public ArrayList<Album> getAlbums(Integer id) {
						return null;
					}

					public ArrayList<Album> getAlbumsSharedWith(Integer id,
							String ownerEmail) {
						return null;
					}

					public ArrayList<Album> getPublicAlbums() {
						return null;
					}

					public Album getSharedAlbum(String albumName,
							int userSharedToId, String userSharedEmail) {
						return null;
					}

					public ArrayList<Album> getAlbumsByTag(int userId,
							String tag) {
						return null;
					}
					
					
				};
				FileService mockFile = new FileService() {
					public File getFileShared(int id, String name, int userId) {
						return null;
					}

					public File getFileOwn(int id, String name, int userId) {
						return null;
					}

					public File getById(Integer id) {
						return new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE));
					}

					public ArrayList<File> getAlbumFilesOwn(int albumId) {
						return new ArrayList<File>();
					}

					public void delete(File file) {
					}

					public void create(File file) {
					}

					public void changeAlbum(File file, Album album) {
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
				context.putBean("userBean", mockUser);
				context.putBean("albumBean", mockAlbum);
				context.putBean("filBean", mockFile);
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
		Page page = new PublicFiles(pars);
		this.tester.startPage(page);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(PublicFiles.class);
	}
}
