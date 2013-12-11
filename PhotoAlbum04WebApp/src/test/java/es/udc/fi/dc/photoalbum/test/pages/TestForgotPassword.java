package es.udc.fi.dc.photoalbum.test.pages;

import java.util.Locale;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.FilesOfAlbumTagBig;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.nonAuth.ForgotPassword;

public class TestForgotPassword {
    private WicketApp wicketApp;
    private WicketTester tester;
    {
        this.wicketApp = new WicketApp() {
            @Override
            protected void init() {
                ApplicationContextMock context = new ApplicationContextMock();
                context.putBean("userBean", UserServiceMock.mock);
                getComponentInstantiationListeners().add(
                        new SpringComponentInjector(this, context));
            }
        };
    }
    
    @Before
    public void setUp() {
        this.tester = new WicketTester(this.wicketApp);
        ((MySession) Session.get()).setuId(1);
        //PageParameters pars = new PageParameters();
        //Page page = new FilesOfAlbumTagBig(pars);
        this.tester.startPage(ForgotPassword.class);
        tester.assertVisible("signout");
        this.tester.getSession().setLocale(new Locale("en", "EN"));
    }
    
    @Test
    public void testRendered(){
        this.tester.assertRenderedPage(ForgotPassword.class);
    }
    
    @Test
    public void testSubmitNotEmail() {
        FormTester formTester = this.tester.newFormTester("form");
        formTester.submit("ajax-button");
        this.tester.assertErrorMessages("'Email' is required.");
        tester.assertRenderedPage(ForgotPassword.class);
    }
    
    @Test
    public void testSubmitWithExistingEmail() {
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("email",ConstantsForTests.USER_EMAIL_EXIST);
        formTester.submit("ajax-button");
        this.tester.assertNoErrorMessage();
        tester.assertRenderedPage(ForgotPassword.class);
    }
    
    @Test
    public void testSubmitWithNotExistingEmail() {
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("email",ConstantsForTests.USER_EMAIL_NOT_EXIST);
        formTester.submit("ajax-button");
        this.tester.assertErrorMessages("No such user");
        tester.assertRenderedPage(ForgotPassword.class);
    }
}
