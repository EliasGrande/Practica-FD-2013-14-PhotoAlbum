package es.udc.fi.dc.photoalbum.test.pages;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.Albums;
import es.udc.fi.dc.photoalbum.wicket.pages.nonAuth.Login;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.*;

public class TestLoginPage {

	private WicketApp wicketApp;
	private WicketTester tester;

	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();
				UserService mock = new UserService() {
					public void create(User user) {
					}

					public void delete(User user) {
					}

					public void update(User user) {
					}

					public User getUser(String email, String password) {
						if ((email.equals(USER_EMAIL_EXIST))
								&& (password.equals(USER_PASS_YES))) {
							return new User(1, email, password);
						} else {
							return null;
						}
					}

					public User getUser(User userEmail) {
						return null;
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
					}

					public void create(Album album) {
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
		this.tester.startPage(Login.class);
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testNoInputAndLocale() {
		tester.assertInvisible("signout");
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", "");
		formTester.setValue("password", "");
		formTester.submit();
		this.tester.assertErrorMessages(
				"Поле 'Email' обязательно для ввода.",
				"Поле 'Password' обязательно для ввода.");

		this.tester.getSession().setLocale(Locale.ENGLISH);
		formTester = this.tester.newFormTester("form");
		formTester.setValue("email", "");
		formTester.setValue("password", "");
		formTester.submit();
		this.tester.assertErrorMessages("Field 'Email' is required.",
				"Field 'Password' is required.");
	}

	@Test
	public void testNotEmailNoPass() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", USER_EMAIL_NOT);
		formTester.setValue("password", "");
		formTester.submit();
		this.tester.assertErrorMessages(
				"'123' не является правильным адресом e-mail.",
				"Поле 'Password' обязательно для ввода.");
	}

	@Test
	public void testNotEmailPass() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", USER_EMAIL_NOT);
		formTester.setValue("password", USER_PASS_NO);
		formTester.submit();
		this.tester
				.assertErrorMessages("'123' не является правильным адресом e-mail.");
	}

	@Test
	public void testNoEmailPass() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", "");
		formTester.setValue("password", USER_PASS_NO);
		formTester.submit();
		this.tester
				.assertErrorMessages("Поле 'Email' обязательно для ввода.");
	}

	@Test
	public void testNoUser() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", USER_EMAIL_EXIST);
		formTester.setValue("password", USER_PASS_NO);
		formTester.submit();
		this.tester
				.assertErrorMessages("No user with this email/password");
	}

	@Test
	public void testUser() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", USER_EMAIL_EXIST);
		formTester.setValue("password", USER_PASS_YES);
		formTester.submit();
		tester.assertRenderedPage(Albums.class);
		tester.assertVisible("signout");
	}

	@Test
	public void testHeader() {
		tester.assertLabel("header", "Photo Albums");
	}
}
