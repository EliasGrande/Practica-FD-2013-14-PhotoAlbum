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

import es.udc.fi.dc.photoalbum.mocks.AlbumServiceMock;
import es.udc.fi.dc.photoalbum.mocks.AlbumShareInformationServiceMock;
import es.udc.fi.dc.photoalbum.mocks.FileServiceMock;
import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.SearchByTag;

public class TestSearchByTagPage {
    private WicketApp wicketApp;
    private WicketTester tester;
    
    {
        this.wicketApp = new WicketApp() {
            @Override
            protected void init() {
                ApplicationContextMock context = new ApplicationContextMock();
                context.putBean("userBean", UserServiceMock.mock);
                context.putBean("albumBean", AlbumServiceMock.mock);
                context.putBean("fileBean", FileServiceMock.mock);
                context.putBean("albumShareInformationBean",
                        AlbumShareInformationServiceMock.mock);
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
        pars.add("tagName", "pruebaTag");
        Page page = new SearchByTag(pars);
        this.tester.startPage(page);
        tester.assertVisible("signout");
        this.tester.getSession().setLocale(new Locale("ru", "RU"));
    }

    @Test
    public void testRendered() {
        tester.assertRenderedPage(SearchByTag.class);
    }

    @Test
    public void createTagForm() {
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("tagName", "pruebaTag");
        formTester.submit();
        this.tester.assertNoErrorMessage();
    }

}
