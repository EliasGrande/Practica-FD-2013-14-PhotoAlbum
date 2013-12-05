package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_LENGTH;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_NO;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_NO_LETTERS;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.Profile;

public class TestProfilePage {

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
