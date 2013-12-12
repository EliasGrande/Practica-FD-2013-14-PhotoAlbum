package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;

import java.util.Locale;

import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.mocks.AlbumServiceMock;
import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.ErrorPage404;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.SharedAlbums;

public class TestSharedAlbumsPage {
    private WicketApp wicketApp;
    private WicketTester tester;

    {
        this.wicketApp = new WicketApp() {
            @Override
            protected void init() {
                ApplicationContextMock context = new ApplicationContextMock();
                context.putBean("userBean", UserServiceMock.mock);
                context.putBean("albumBean", AlbumServiceMock.mock);
                getComponentInstantiationListeners().add(
                        new SpringComponentInjector(this, context));
            }
        };
    }

    @Before
    public void setUp() {
        this.tester = new WicketTester(this.wicketApp);
        ((MySession) Session.get()).setuId(1);
        PageParameters pars = new PageParameters();
        pars.add("user", USER_EMAIL_EXIST);
        Page page = new SharedAlbums(pars);
        this.tester.startPage(page);
        tester.assertVisible("signout");
        this.tester.getSession().setLocale(new Locale("ru", "RU"));
    }

    @Test
    public void testRendered() {
        tester.assertRenderedPage(SharedAlbums.class);
    }
    
    @Test
    (expected=RestartResponseException.class)
    public void testLoadPageError(){
        PageParameters pars = new PageParameters();
        pars.add("user", ConstantsForTests.USER_EMAIL_NOT_EXIST);
        Page page = new SharedAlbums(pars);
        this.tester.startPage(page);
    }
    
    @Test
    public void testLoadPageError2(){
        this.tester.startPage(SharedAlbums.class);
        
        tester.assertRenderedPage(ErrorPage404.class);
    }
}
