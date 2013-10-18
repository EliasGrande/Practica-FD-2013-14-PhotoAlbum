package es.udc.fi.dc.photoalbum.test.pages;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
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
import es.udc.fi.dc.photoalbum.wicket.pages.auth.Albums;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.*;

public class TestAlbumsPage {

	private WicketApp wicketApp;
	private WicketTester tester;

	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();
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

					public ArrayList<Album> getAlbumsSharedWith(Integer id,
							String ownerEmail) {
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
		this.tester.startPage(Albums.class);
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(Albums.class);
		tester.assertVisible("signout");
	}

	@Test
	public void testCreateNull() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("AlbumName", "");
		formTester.submit();
		this.tester
				.assertErrorMessages("Поле 'Album name' обязательно для ввода.");
	}

	@Test
	public void testCreateAlbumExist() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("AlbumName", ALBUM_NAME_EXIST);
		formTester.submit();
		this.tester
				.assertErrorMessages("Album with this name already exist");
	}

	@Test
	public void testCreateAlbum() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("AlbumName", ALBUM_NAME_NOT_EXIST);
		formTester.submit();
		this.tester.assertNoErrorMessage();
		tester.assertRenderedPage(Albums.class);
	}
}
