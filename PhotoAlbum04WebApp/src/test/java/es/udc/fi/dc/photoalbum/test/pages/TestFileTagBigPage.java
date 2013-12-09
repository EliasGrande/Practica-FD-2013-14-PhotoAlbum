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
import es.udc.fi.dc.photoalbum.mocks.AlbumTagServiceMock;
import es.udc.fi.dc.photoalbum.mocks.FileServiceMock;
import es.udc.fi.dc.photoalbum.mocks.FileTagServiceMock;
import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.FileTagBig;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.FilesOfAlbumTagBig;

public class TestFileTagBigPage {
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
                context.putBean("albumTagBean", AlbumTagServiceMock.mock);
                context.putBean("fileTagBean", FileTagServiceMock.mock);
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
		pars.add("fid", 1);
		pars.add("tag", "pruebaTag");
		Page page = new FileTagBig(pars);
		this.tester.startPage(page);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(FileTagBig.class);
	}
	
	@Test
    public void testForwardPhoto() {
        FormTester formTester = this.tester.newFormTester("formNavigate");
        formTester.submit("forward");
        this.tester.assertNoErrorMessage();
        tester.assertRenderedPage(FileTagBig.class);
    }
    
    @Test
    public void testBackPhoto() {
        PageParameters pars = new PageParameters();
        pars.add("fid", 2);
        pars.add("tag", "pruebaTag");
        Page page = new FileTagBig(pars);
        this.tester.startPage(page);
        
        FormTester formTester2 = this.tester.newFormTester("formNavigate");
        formTester2.submit("back");
        this.tester.assertNoErrorMessage();
        tester.assertRenderedPage(FileTagBig.class);
    }
}
