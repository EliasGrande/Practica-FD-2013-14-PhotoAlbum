package es.udc.fi.dc.photoalbum.test.pages;

import java.util.Locale;


import org.apache.wicket.Session;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.Profile;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.*;

public class TestProfilePage {

	private WicketApp wicketApp;
	private WicketTester tester;

	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();
				UserService mock = new UserService() {
					public void create(User user) { }
					public void delete(User user) { }
					public void update(User user) { }
					public User getUser(String email, String password) {
						if ((email.equals(USER_EMAIL_EXIST)) && (password.equals(USER_PASS_YES))) {
							return new User(null, email, password);
						} else {
							return null;
						}
					}
					public User getUser(User userEmail) { return null; }
					public User getById(Integer id) {
						return new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
					}
				};
				context.putBean("albumBean", mock);
				getComponentInstantiationListeners().add(new SpringComponentInjector(this, context));
			}
		};
	}

	@Before
	public void setUp() {
		this.tester = new WicketTester(this.wicketApp);
		((MySession) Session.get()).setuId(1);
		this.tester.startPage(Profile.class);
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
		tester.assertVisible("signout");
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(Profile.class);
	}

	@Test
	public void testNoinput() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("password", "");
		formTester.setValue("newPassword", "");
		formTester.submit();
		this.tester.assertErrorMessages("Поле 'Old password' обязательно для ввода.",
                "Поле 'New password' обязательно для ввода.");
	}

	@Test
	public void testNoNew() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("password", USER_PASS_YES);
		formTester.setValue("newPassword", "");
		formTester.submit();
		this.tester.assertErrorMessages("Поле 'New password' обязательно для ввода.");
	}

	@Test
	public void testLength() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("password", USER_PASS_YES);
		formTester.setValue("newPassword", USER_PASS_LENGTH);
		formTester.submit();
		this.tester.assertErrorMessages("Password must be at least 8 symbols");
	}

	@Test
	public void testNoLetters() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("password", USER_PASS_YES);
		formTester.setValue("newPassword", USER_PASS_NO_LETTERS);
		formTester.submit();
		this.tester.assertErrorMessages("Password must contain at least one lower case letter",
                "Password must contain at least one upper case letter");
	}

	@Test
	public void testWrongOld() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("password", USER_PASS_NO);
		formTester.setValue("newPassword", USER_PASS_NO);
		formTester.submit();
		this.tester.assertErrorMessages("Password must contain at least one upper case letter");
	}

	@Test
	public void testEditPass() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("password", USER_PASS_YES);
		formTester.setValue("newPassword", USER_PASS_NO);
		formTester.submit();
	}
}
