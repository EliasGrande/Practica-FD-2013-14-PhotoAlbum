package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;

import java.util.Locale;

import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.mocks.CommentServiceMock;
import es.udc.fi.dc.photoalbum.mocks.FileServiceMock;
import es.udc.fi.dc.photoalbum.mocks.FileTagServiceMock;
import es.udc.fi.dc.photoalbum.mocks.LikeAndDislikeServiceMock;
import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.mocks.VotedServiceMock;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.SharedBig;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.tag.FileTagBig;

public class TestSharedBigPage {
    private WicketApp wicketApp;
    private WicketTester tester;

    {
        this.wicketApp = new WicketApp() {
            @Override
            protected void init() {
                ApplicationContextMock context = new ApplicationContextMock();

                context.putBean("userBean", UserServiceMock.mock);
                context.putBean("fileBean", FileServiceMock.mock);
                context.putBean("fileTagBean", FileTagServiceMock.mock);
                context.putBean("likeAndDislikeBean", LikeAndDislikeServiceMock.mock);
                context.putBean("votedServiceBean", VotedServiceMock.mock);
                context.putBean("commentServiceBean", CommentServiceMock.mock);

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
        pars.add("album", ALBUM_NAME_EXIST);
        pars.add("fid", 1);
        Page page = new SharedBig(pars);
        this.tester.startPage(page);
        tester.assertVisible("signout");
        this.tester.getSession().setLocale(new Locale("ru", "RU"));
    }

    @Test
    public void testRendered() {
        tester.assertRenderedPage(SharedBig.class);
    }
    
    @Test
    public void testForwardPhoto() {
        FormTester formTester = this.tester.newFormTester("formNavigate");
        formTester.submit("forward");
        this.tester.assertNoErrorMessage();
        tester.assertRenderedPage(SharedBig.class);
    }
    
    @Test
    public void testBackPhoto() {
        PageParameters pars = new PageParameters();
        pars.add("album", ALBUM_NAME_EXIST);
        pars.add("fid", 2);
        Page page = new SharedBig(pars);
        this.tester.startPage(page);
        
        FormTester formTester2 = this.tester.newFormTester("formNavigate");
        formTester2.submit("back");
        this.tester.assertNoErrorMessage();
        tester.assertRenderedPage(SharedBig.class);
    }
    
    @Test
    (expected=RestartResponseException.class)
    public void testErrorLoadPageWithoutAlbum(){
        PageParameters pars = new PageParameters();
        pars.add("fid", 1);
        Page page = new SharedBig(pars);
        this.tester.startPage(page);
    }
    
    @Test
    (expected=RestartResponseException.class)
    public void testErrorLoadPageWithoutFid(){
        PageParameters pars = new PageParameters();
        pars.add("album", ALBUM_NAME_EXIST);
        Page page = new SharedBig(pars);
        this.tester.startPage(page);
    }
    
    @Test
    (expected=RestartResponseException.class)
    public void testErrorLoadPageWithIncorrectShare(){
        PageParameters pars = new PageParameters();
        pars.add("album", ConstantsForTests.ALBUM_NAME_NOT_EXIST);
        pars.add("fid", 1);
        Page page = new SharedBig(pars);
        this.tester.startPage(page);
    }
    
    @Test
    (expected=RestartResponseException.class)
    public void testErrorLoadPageWithIncorrectShare2(){
        PageParameters pars = new PageParameters();
        pars.add("album", ConstantsForTests.ALBUM_NAME_ERROR);
        pars.add("fid", 1);
        Page page = new SharedBig(pars);
        this.tester.startPage(page);
    }
}