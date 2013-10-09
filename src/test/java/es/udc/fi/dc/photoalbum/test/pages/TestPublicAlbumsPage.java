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
import org.springframework.dao.DataIntegrityViolationException;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.pub.PublicAlbums;

public class TestPublicAlbumsPage {
	private WicketApp wicketApp;
	private WicketTester tester;
	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();
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
				};
				AlbumService mockAlbum = new AlbumService() {
					public void rename(Album album, String newName) {
					}

					public Album getAlbum(String name, int userId) {
						return null;
					}

					public void delete(Album album) {
						album.getUser().getAlbums().remove(album);
					}

					public void create(Album album) {
						if (album.getName().equals(ALBUM_NAME_EXIST)) {
							throw new DataIntegrityViolationException("");
						}
						album.getUser().getAlbums().add(album);
					}

					public Album getById(Integer id) {
						return null;
					}

					public ArrayList<Album> getAlbums(Integer id) {
						return new ArrayList<Album>();
					}

					public ArrayList<Album> getPublicAlbums() {
						return new ArrayList<Album>();
					}

					public void changePrivacyLevel(Album album,
							String privacyLevel) {
						album.setPrivacyLevel(privacyLevel);
					}
				};
				//context.putBean("shareBean", mockShare);
				context.putBean("albumBean", mockAlbum);
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
		pars.add("user", USER_EMAIL_EXIST);
		Page page = new PublicAlbums(pars);
		this.tester.startPage(page);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(PublicAlbums.class);
	}
}
