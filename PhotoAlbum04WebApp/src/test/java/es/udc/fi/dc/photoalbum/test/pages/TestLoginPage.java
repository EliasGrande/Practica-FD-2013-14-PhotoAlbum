package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_NOT;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_NO;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.Locale;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.mocks.AlbumServiceMock;
import es.udc.fi.dc.photoalbum.mocks.FileServiceMock;
import es.udc.fi.dc.photoalbum.mocks.RestClientSearchServiceMock;
import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.webapp.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.HottestFiles;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.nonAuth.Login;

public class TestLoginPage {

    private WicketApp wicketApp;
    private WicketTester tester;

    {
        this.wicketApp = new WicketApp() {
            @Override
            protected void init() {
                ApplicationContextMock context = new ApplicationContextMock();
                context.putBean("userServiceBean", UserServiceMock.mock);
                context.putBean("albumServiceBean", AlbumServiceMock.mock);
                context.putBean("fileServiceBean", FileServiceMock.mock);
                context.putBean("restClientSearchServiceBean",
                        RestClientSearchServiceMock.mock);
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
        this.tester.assertErrorMessages("'Email' is required.",
                "'Password' is required.");
    }

    @Test
    public void testNotEmailNoPass() {
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("email", USER_EMAIL_NOT);
        formTester.setValue("password", "");
        formTester.submit();
        this.tester.assertErrorMessages(
                "'Email' не является правильным адресом e-mail.",
                "Поле 'Password' обязательно для ввода.");
    }

    @Test
    public void testNotEmailPass() {
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("email", USER_EMAIL_NOT);
        formTester.setValue("password", USER_PASS_NO);
        formTester.submit();
        this.tester
                .assertErrorMessages("'Email' не является правильным адресом e-mail.");
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
        tester.assertRenderedPage(HottestFiles.class);
        tester.assertVisible("signout");
    }

    @Test
    public void testUser2() {
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("email", USER_EMAIL_EXIST);
        formTester.setValue("password", USER_PASS_YES);
        formTester.setValue("bool", true);
        formTester.submit();

        tester.assertRenderedPage(HottestFiles.class);
    }
}
