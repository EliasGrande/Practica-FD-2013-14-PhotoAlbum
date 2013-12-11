package es.udc.fi.dc.photoalbum.test.pages;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.nonAuth.BasePage;

public class TestBasePage {
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
            this.tester.startPage(BasePage.class);
            tester.assertVisible("signout");
            this.tester.getSession().setLocale(new Locale("en", "EN"));
        }
        
        @Test
        public void changeLocaleToSpanish(){
            this.tester.clickLink("goSpanish");
            this.tester.getSession().getLocale().equals(new Locale("es", "ES"));
        }
        
        @Test
        public void changeLocaleToEnglish(){
            this.tester.clickLink("goSpanish");
            
            this.tester.clickLink("goEnglish");
            this.tester.getSession().getLocale().equals(new Locale("en", "EN"));
        }
}
