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
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.share.SharedAlbums;

public class TestSharedAlbumsPage {
	private WicketApp wicketApp;
	private WicketTester tester;
	
	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();
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
						return null;
					}

					public Album getAlbum(String name, int userId) {
						return null;
					}

					public ArrayList<Album> getAlbums(Integer id) {
						return null;
					}

					public ArrayList<Album> getAlbumsSharedWith(Integer id,
							String ownerEmail) {
						ArrayList<Album> list = new ArrayList<Album>();
						list.add(new Album(1,"ALBUM_NAME_EXIST",null,null,null/**/,PrivacyLevel.PRIVATE));
						return list;
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
				UserService mockUser = new UserService() {
					public void create(User user) { }
					public void delete(User user) {	}
					public void update(User user) {	}
					public User getUser(String email, String password) { return new User(1, email, password); }
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
				context.putBean("shareBean", mockAlbum);
				context.putBean("userBean", mockUser);
				getComponentInstantiationListeners().add(new SpringComponentInjector(this, context));
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
		Page page = new SharedAlbums(pars);
		this.tester.startPage(page);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(SharedAlbums.class);
	}
}
