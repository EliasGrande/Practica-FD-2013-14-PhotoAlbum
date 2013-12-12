package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_NOT;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_NOT_EXIST;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import junit.framework.Assert;

import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.AlbumShareInformation;
import es.udc.fi.dc.photoalbum.mocks.AlbumServiceMock;
import es.udc.fi.dc.photoalbum.mocks.AlbumShareInformationServiceMock;
import es.udc.fi.dc.photoalbum.mocks.FileServiceMock;
import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.ErrorPage404;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.Image;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.share.Share;

public class TestSharePage {

    private WicketApp wicketApp;
    private WicketTester tester;
    private Set<AlbumShareInformation> shares = new HashSet<AlbumShareInformation>();

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
        pars.add("album", ALBUM_NAME_EXIST);
        Page page = new Share(pars);
        this.tester.startPage(page);
        tester.assertVisible("signout");
        this.tester.getSession().setLocale(new Locale("ru", "RU"));
    }

    @Test
    public void testRendered() {
        tester.assertRenderedPage(Share.class);
    }

    @Test
    public void testEmailNotExist() {
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("shareEmail", USER_EMAIL_EXIST);
        formTester.submit();
        this.tester
                .assertErrorMessages("You dont need to share to yourself");
    }

    @Test
    public void testAddShare() {
        Assert.assertEquals("Number of files", shares.size(), 0);
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("shareEmail", USER_EMAIL_EXIST);
        formTester.submit();
        Assert.assertEquals("Number of files", shares.size(), 0);
    }

    @Test
    public void testNotEmail() {
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("shareEmail", USER_EMAIL_NOT);
        formTester.submit();
        this.tester
                .assertErrorMessages("'Email' не является правильным адресом e-mail.");
    }

    @Test
    public void testChangePrivacy() {
        tester.assertRenderedPage(Share.class);
        @SuppressWarnings("unchecked")
        DropDownChoice<Album> dropDownChoice = (DropDownChoice<Album>) tester
                .getComponentFromLastRenderedPage("formPrivacyLevel:privacyLevels");
        Assert.assertEquals(2, dropDownChoice.getChoices().size());
    }
    
    @Test
    public void testErrorLoadPage(){
        this.tester.startPage(Share.class);
        this.tester.assertRenderedPage(ErrorPage404.class);
    }
    
    @Test
    (expected=RestartResponseException.class)
    public void testErrorLoadPage2(){
        PageParameters pars = new PageParameters();
        pars.add("album", ConstantsForTests.ALBUM_NAME_NOT_EXIST);
        Page page = new Share(pars);
        this.tester.startPage(page);
    }
    
    @Test
    public void testDeleteShare(){
        this.tester.clickLink("pageable:1:delete");
        
        this.tester.assertRenderedPage(Share.class);
    }
    
    @Test
    public void testShareFormWithOwner(){
        PageParameters pars = new PageParameters();
        pars.add("album", ALBUM_NAME_EXIST);
        Page page = new Share(pars);
        this.tester.startPage(page);
        
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("shareEmail", ConstantsForTests.USER_EMAIL_EXIST);
        formTester.submit("ajax-button");
        
        this.tester.assertErrorMessages("You dont need to share to yourself");
        
    }
    
    @Test
    public void testShareFormWithNone(){
        PageParameters pars = new PageParameters();
        pars.add("album", ALBUM_NAME_EXIST);
        Page page = new Share(pars);
        this.tester.startPage(page);
        
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("shareEmail", ConstantsForTests.USER_EMAIL_NOT_EXIST);
        formTester.submit("ajax-button");
        
        this.tester.assertErrorMessages("No such user");
        
    }
    
    @Test
    public void testShareForm(){
        PageParameters pars = new PageParameters();
        pars.add("album", ALBUM_NAME_EXIST);
        Page page = new Share(pars);
        this.tester.startPage(page);
        
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("shareEmail", ConstantsForTests.USER_EMAIL_EXIST2);
        formTester.submit("ajax-button");
        
        this.tester.assertNoErrorMessage();
        
    }
    
    @Test
    public void testShareFormErrorAlreadyShare(){
        PageParameters pars = new PageParameters();
        pars.add("album", ALBUM_NAME_EXIST);
        Page page = new Share(pars);
        this.tester.startPage(page);
        
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("shareEmail", ConstantsForTests.USER_EMAIL_EXIST3);
        formTester.submit("ajax-button");
        
        this.tester.assertErrorMessages("Already shared");
        
    }
    
    @Test
    public void testDropdownChoice(){
        FormTester formTester = this.tester.newFormTester("formPrivacyLevel");
        formTester.select("privacyLevels", 1);
        formTester.submit();
        
        DropDownChoice ddc = (DropDownChoice) this.tester.getComponentFromLastRenderedPage("formPrivacyLevel:privacyLevels");
        String i = ddc.getModelValue().toString();
        
        assertEquals(i, PrivacyLevel.PRIVATE);
    }
}
