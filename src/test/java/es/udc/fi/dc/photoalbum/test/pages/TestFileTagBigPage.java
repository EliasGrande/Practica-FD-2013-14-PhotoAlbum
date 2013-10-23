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
import es.udc.fi.dc.photoalbum.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.FileTag;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.AlbumTagService;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.FileTagService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.tag.FileTagBig;

public class TestFileTagBigPage {
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
						return new ArrayList<User>();
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
						return new Album(1, ALBUM_NAME_EXIST, new User(1,
								USER_EMAIL_EXIST, USER_PASS_YES), null, null,
								PrivacyLevel.PRIVATE);

					}

					public Album getAlbum(String name, int userId) {
						return null;
					}

					public ArrayList<Album> getAlbums(Integer id) {
						return new ArrayList<Album>();
					}

					public ArrayList<Album> getAlbumsSharedWith(Integer id,
							String ownerEmail) {
						return new ArrayList<Album>();
					}

					public ArrayList<Album> getPublicAlbums() {
						return new ArrayList<Album>();
					}

					public Album getSharedAlbum(String albumName,
							int userSharedToId, String userSharedEmail) {
						return null;
					}

					public ArrayList<Album> getAlbumsByTag(int userId,
							String tag) {
						return new ArrayList<Album>();
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
								new Album(1, ALBUM_NAME_EXIST, new User(1,
										USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE));
					}

					public ArrayList<File> getAlbumFilesOwn(int albumId) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public void delete(File file) {
					}

					public void create(File file) {
					}

					public void changeAlbum(File file, Album album) {
					}

					public ArrayList<File> getAlbumFilesOwnPaging(int albumId,
							int first, int count) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
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
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getAlbumFilesSharedPaging(
							int albumId, int userId, int first, int count) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getAlbumFilesPublic(int albumId,
							int userId) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1,
										USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getAlbumFilesPublicPaging(
							int albumId, int userId, int first, int count) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1,
										USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getFilesByTag(int userId, String tag) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getFilesByTagPaging(int userId,
							String tag, int first, int count) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}
				};
				FileTagService mockFileTag = new FileTagService() {

					public void create(FileTag fileTag) {
					}

					public void delete(FileTag fileTag) {
					}

					public FileTag getTag(int fileId, String tag) {
						return null;
					}

					public ArrayList<FileTag> getTags(int fileId) {
						ArrayList<FileTag> list = new ArrayList<FileTag>();
						list.add(new FileTag((new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1,
										USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE))),"asdf"));
						return list;
					}

				};
				AlbumTagService mockAlbumTag = new AlbumTagService() {

					public void create(AlbumTag albumTag) {
					}

					public void delete(AlbumTag albumTag) {
					}

					public AlbumTag getTag(int albumId, String tag) {
						return null;
					}

					public ArrayList<AlbumTag> getTags(int albumId) {
						return new ArrayList<AlbumTag>();
					}

				};
				context.putBean("userBean", mockUser);
				context.putBean("albumBean", mockAlbum);
				context.putBean("filBean", mockFile);
				context.putBean("fileTagBean", mockFileTag);
				context.putBean("albumTagBean", mockAlbumTag);
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
		pars.add("fid", 1);
		pars.add("tag", "pruebaTag");
		Page page = new FileTagBig(pars);
		this.tester.startPage(page);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(FileTagBig.class);
	}
}
