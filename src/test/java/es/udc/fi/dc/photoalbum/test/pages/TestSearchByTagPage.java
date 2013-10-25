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
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.AlbumShareInformationService;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.tag.SearchByTag;

public class TestSearchByTagPage {
	private WicketApp wicketApp;
	private WicketTester tester;
	private Set<File> files = new HashSet<File>();
	private Set<AlbumShareInformation> shares = new HashSet<AlbumShareInformation>();

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
								null, null, PrivacyLevel.PRIVATE);
						files.add(new File(1, "1", new byte[1], new byte[1],
								album));
						album.setFiles(files);
						album.setShareInformation(shares);
						user.getAlbums().add(album);
						return album;
					}

					public void delete(Album album) {
					}

					public void create(Album album) {
						if (album.getName().equals(ALBUM_NAME_EXIST)) {
							throw new DataIntegrityViolationException("");
						}
						album.getUser().getAlbums().add(album);
					}

					public Album getById(Integer id) {
						return new Album(1, ALBUM_NAME_EXIST, new User(1,
								USER_EMAIL_EXIST, USER_PASS_YES), null, null,
								PrivacyLevel.PRIVATE);
					}

					public ArrayList<Album> getAlbums(Integer id) {
						ArrayList<Album> list = new ArrayList<Album>();
						list.add(new Album(1, ALBUM_NAME_EXIST, new User(1,
								USER_EMAIL_EXIST, USER_PASS_YES), null, null,
								PrivacyLevel.PRIVATE));
						return list;
					}

					public ArrayList<Album> getPublicAlbums() {
						ArrayList<Album> list = new ArrayList<Album>();
						list.add(new Album(1, ALBUM_NAME_EXIST, new User(1,
								USER_EMAIL_EXIST, USER_PASS_YES), null, null,
								PrivacyLevel.PRIVATE));
						return list;
					}

					public void changePrivacyLevel(Album album,
							String privacyLevel) {
						album.setPrivacyLevel(privacyLevel);
					}

					public ArrayList<Album> getAlbumsSharedWith(Integer id,
							String ownerEmail) {
						ArrayList<Album> list = new ArrayList<Album>();
						list.add(new Album(1, ALBUM_NAME_EXIST, new User(1,
								USER_EMAIL_EXIST, USER_PASS_YES), null, null,
								PrivacyLevel.PRIVATE));
						return list;
					}

					public Album getSharedAlbum(String albumName,
							int userSharedToId, String userSharedEmail) {
						return null;
					}

					public ArrayList<Album> getAlbumsByTag(int userId,
							String tag) {
						ArrayList<Album> list = new ArrayList<Album>();
						list.add(new Album(1, ALBUM_NAME_EXIST, new User(1,
								USER_EMAIL_EXIST, USER_PASS_YES), null, null,
								PrivacyLevel.PRIVATE));
						return list;
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
						return new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1,
										USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE));
					}

					public ArrayList<File> getAlbumFilesOwn(int albumId) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1,
										USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getAlbumFilesOwnPaging(int albumId,
							int first, int count) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1,
										USER_EMAIL_EXIST, USER_PASS_YES), null,
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
								new Album(1, ALBUM_NAME_EXIST, new User(1,
										USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getAlbumFilesSharedPaging(
							int albumId, int userId, int first, int count) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1,
										USER_EMAIL_EXIST, USER_PASS_YES), null,
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
								new Album(1, ALBUM_NAME_EXIST, new User(1,
										USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getFilesByTagPaging(int userId,
							String tag, int first, int count) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1,
										USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}
				};
				AlbumShareInformationService mockShare = new AlbumShareInformationService() {
					public void create(AlbumShareInformation shareInformation) {
						shareInformation.getUser().getShareInformation()
								.add(shareInformation);
						shareInformation.getAlbum().getShareInformation()
								.add(shareInformation);
					}

					public void delete(AlbumShareInformation shareInformation) {
					}

					public AlbumShareInformation getShare(String albumName,
							int userSharedToId, String userSharedEmail) {
						return null;
					}

					public ArrayList<AlbumShareInformation> getAlbumShares(
							int albumId) {
						ArrayList<AlbumShareInformation> list = new ArrayList<AlbumShareInformation>();
						User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
						list.add(new AlbumShareInformation(null, new Album(1,
								ALBUM_NAME_EXIST, user, null, null,
								PrivacyLevel.PRIVATE), user));
						return list;
					}
				};
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
						if (userEmail.getEmail().equals(USER_EMAIL_EXIST)) {
							return userEmail;
						} else {
							return null;
						}
					}

					public User getById(Integer id) {
						return new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
					}

					public ArrayList<User> getUsersSharingWith(int userId) {
						return null;
					}
				};
				context.putBean("albumBean", mockAlbum);
				context.putBean("fileBean", mockFile);
				context.putBean("shareBean", mockShare);
				context.putBean("userBean", mockUser);
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
		pars.add("tagName", "pruebaTag");
		Page page = new SearchByTag(pars);
		this.tester.startPage(page);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(SearchByTag.class);
	}

	@Test
	public void createTagForm() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("tagName", "pruebaTag");
		formTester.submit();
		this.tester.assertNoErrorMessage();
	}

}
