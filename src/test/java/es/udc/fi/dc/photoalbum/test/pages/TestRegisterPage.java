package es.udc.fi.dc.photoalbum.test.pages;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.nonAuth.Register;


import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.*;

public class TestRegisterPage {

	private WicketApp wicketApp;
	private WicketTester tester;

	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();
				UserService mock = new UserService() {
					public void create(User user) {
						if (user.getEmail().equals(USER_EMAIL_EXIST)) {
							throw new DataIntegrityViolationException("");
						}
					}
					public void delete(User user) { }
					public void update(User user) { }
					public User getUser(String email, String password) { return null; }
					public User getUser(User userEmail) { return null; }
					public User getById(Integer id) { return null; }
					public ArrayList<User> getUsersSharingWith(int userId) {
						return null;
					}
				};
				context.putBean("userBean", mock);
				getComponentInstantiationListeners().add(new SpringComponentInjector(this, context));
			}
		};
	}

	@Before
	public void setUp() {
		this.tester = new WicketTester(this.wicketApp);
		this.tester.startPage(Register.class);
		this.tester.assertInvisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testNoInput() {
		tester.assertInvisible("signout");
		FormTester formTester = tester.newFormTester("form");
		formTester.submit();
		tester.assertErrorMessages("Поле 'Email' обязательно для ввода.",
                "Поле 'Password' обязательно для ввода.",
                "Поле 'Password confirmation' обязательно для ввода.");
	}

	@Test
	public void testNotEmailNoPass() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", USER_EMAIL_NOT);
		formTester.setValue("password", "");
		formTester.submit();
		this.tester.assertErrorMessages("'123' не является правильным адресом e-mail.",
                "Поле 'Password' обязательно для ввода.",
                "Поле 'Password confirmation' обязательно для ввода.");
	}

	@Test
	public void testNotEmailPassNoPassA() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", USER_EMAIL_NOT);
		formTester.setValue("password", USER_PASS_LENGTH);
		formTester.submit();
		this.tester.assertErrorMessages("'123' не является правильным адресом e-mail.",
                "Password must be at least 8 symbols",
                "Поле 'Password confirmation' обязательно для ввода.");
	}

	@Test
	public void testPassLength() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", USER_EMAIL_EXIST);
		formTester.setValue("password", USER_PASS_LENGTH);
		formTester.setValue("passwordAgain", USER_PASS_LENGTH);
		formTester.submit();
		this.tester.assertErrorMessages("Password must be at least 8 symbols");
	}

	@Test
	public void testNoLetters() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", USER_EMAIL_EXIST);
		formTester.setValue("password", USER_PASS_NO_LETTERS);
		formTester.setValue("passwordAgain", USER_PASS_NO_LETTERS);
		formTester.submit();
		this.tester.assertErrorMessages("Password must contain at least one lower case letter",
                "Password must contain at least one upper case letter");
	}

	@Test
	public void testAlreadyExist() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", USER_EMAIL_EXIST);
		formTester.setValue("password", USER_PASS_YES);
		formTester.setValue("passwordAgain", USER_PASS_YES);
		formTester.submit();
		this.tester.assertErrorMessages("Password must be at least 8 symbols");
	}

	@Test
	public void testNewUser() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", USER_EMAIL_NOT_EXIST);
		formTester.setValue("password", USER_PASS_YES);
		formTester.setValue("passwordAgain", USER_PASS_YES);
		formTester.submit();
		tester.assertRenderedPage(Register.class);
		tester.assertInvisible("signout");
	}
}
