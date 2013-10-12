package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.AlbumShareInformationService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.share.SharedFiles;

public class TestSharedFilesPage {

	private WicketApp wicketApp;
	private WicketTester tester;
	private Set<File> set = new HashSet<File>();

	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();
				AlbumShareInformationService mockShare = new AlbumShareInformationService() {
					public void create(AlbumShareInformation shareInformation) {
					}

					public void delete(AlbumShareInformation shareInformation) {
					}

					public List<AlbumShareInformation> getShares(User userShared,
							User userSharedTo) {
						return null;
					}

					public AlbumShareInformation getShare(String albumName,
							int userSharedToId, String userSharedEmail) {
						return new AlbumShareInformation(1, new Album(1, null, null,
								null, null, PrivacyLevel.SHAREABLE), null);
					}

					public ArrayList<AlbumShareInformation> getAlbumShares(
							int albumId) {
						return null;
					}

					public ArrayList<AlbumShareInformation> getUserShares(int userId) {
						return null;
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
						return null;
					}

					public User getById(Integer id) {
						return new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
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
						return null;
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

					public ArrayList<File> getAlbumFilesOwn(int albumId,
							String minPrivacyLevel) {
						ArrayList<File> list = new ArrayList<File>();
						User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
						Album album = new Album(albumId, ALBUM_NAME_EXIST, user, null,
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
				context.putBean("userBean", mockUser);
				context.putBean("shareBean", mockShare);
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
		pars.add("album", ALBUM_NAME_EXIST);
		pars.add("user", USER_EMAIL_EXIST);
		Page page = new SharedFiles(pars);
//		this.tester.startPage(page);
//		tester.assertVisible("signout");
//		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(SharedFiles.class);
	}
}
