package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_NOT;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_NOT_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_LENGTH;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_NO_LETTERS;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.Locale;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.webapp.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.nonAuth.Register;

public class TestRegisterPage {

	private WicketApp wicketApp;
	private WicketTester tester;

	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();
				context.putBean("userBean", UserServiceMock.mock);
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
		this.tester.assertErrorMessages("'Email' не является правильным адресом e-mail.",
                "Поле 'Password' обязательно для ввода.",
                "Поле 'Password confirmation' обязательно для ввода.");
	}

	@Test
	public void testNotEmailPassNoPassA() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("email", USER_EMAIL_NOT);
		formTester.setValue("password", USER_PASS_LENGTH);
		formTester.submit();
		this.tester.assertErrorMessages("'Email' не является правильным адресом e-mail.",
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
