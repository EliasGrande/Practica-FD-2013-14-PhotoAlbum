package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.TAG_NAME_EXIST;

import java.util.Locale;

import junit.framework.Assert;

import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.mocks.AlbumServiceMock;
import es.udc.fi.dc.photoalbum.mocks.AlbumTagServiceMock;
import es.udc.fi.dc.photoalbum.mocks.CommentServiceMock;
import es.udc.fi.dc.photoalbum.mocks.FileServiceMock;
import es.udc.fi.dc.photoalbum.mocks.FileShareInformationServiceMock;
import es.udc.fi.dc.photoalbum.mocks.FileTagServiceMock;
import es.udc.fi.dc.photoalbum.mocks.LikeAndDislikeServiceMock;
import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.mocks.VotedServiceMock;
import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;
import es.udc.fi.dc.photoalbum.webapp.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.ErrorPage404;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.Image;
import es.udc.fi.dc.photoalbum.webapp.wicket.pages.auth.Upload;

public class TestImagePage {
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
                context.putBean("fileShareInformationBean", FileShareInformationServiceMock.mock);
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
		pars.add("fid", "1");
		Page page = new Image(pars);
		this.tester.startPage(page);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(Image.class);
		tester.assertComponent("img", NonCachingImage.class);
	}

	@Test
	public void testBackLink() {
		tester.assertRenderedPage(Image.class);
		PageParameters parameters = new PageParameters();
		parameters.add("album", ALBUM_NAME_EXIST);
		tester.assertBookmarkablePageLink("linkBack", Upload.class, parameters);
	}

	@Test
	public void testMove() {
		tester.assertRenderedPage(Image.class);
		@SuppressWarnings("unchecked")
		DropDownChoice<Album> dropDownChoice = (DropDownChoice<Album>) tester
				.getComponentFromLastRenderedPage("formMove:albums");
		Assert.assertEquals(0, dropDownChoice.getChoices().size());
		FormTester formTester = this.tester.newFormTester("formMove");
		formTester.submit();
	}
	
	@Test
	public void testChangePrivacy() {
		tester.assertRenderedPage(Image.class);
		@SuppressWarnings("unchecked")
		DropDownChoice<File> dropDownChoice = (DropDownChoice<File>) tester
				.getComponentFromLastRenderedPage("formPrivacyLevel:privacyLevels");
		Assert.assertEquals(3, dropDownChoice.getChoices().size());
	}
	
	@Test
	public void testAddTag() {
		FormTester formTester = this.tester.newFormTester("formAddTag");
		formTester.setValue("newTag", TAG_NAME_EXIST);
		formTester.submit();
		this.tester.assertNoErrorMessage();
		tester.assertRenderedPage(Image.class);
		
		this.tester.clickLink("fileTagDataContainer:pageable:1:delete");
	}
	
	@Test
    public void testForwardPhoto() {
        FormTester formTester = this.tester.newFormTester("formNavigate");
        formTester.submit("forward");
        this.tester.assertNoErrorMessage();
        tester.assertRenderedPage(Image.class);
    }
    
	//FIXME
    /*@Test
    public void testBackPhoto() {
        PageParameters pars = new PageParameters();
        pars.add("album", ALBUM_NAME_EXIST);
        pars.add("fid", 2);
        Page page = new Image(pars);
        this.tester.startPage(page);
        
        FormTester formTester2 = this.tester.newFormTester("formNavigate");
        formTester2.submit("back");
        this.tester.assertNoErrorMessage();
        tester.assertRenderedPage(Image.class);
    }*/
	
	@Test
	public void testShareFormWithOwner(){
	    PageParameters pars = new PageParameters();
        pars.add("album", ALBUM_NAME_EXIST);
        pars.add("fid", "2");
        Page page = new Image(pars);
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
        pars.add("fid", "2");
        Page page = new Image(pars);
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
        pars.add("fid", "2");
        Page page = new Image(pars);
        this.tester.startPage(page);
        
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("shareEmail", ConstantsForTests.USER_EMAIL_EXIST2);
        formTester.submit("ajax-button");
        
        this.tester.assertNoErrorMessage();
        
    }
	
	@Test
    public void testDeleteForm() {
        FormTester formTester = this.tester.newFormTester("formDelete");
        formTester.submit();
        this.tester.assertNoErrorMessage();
        tester.assertRenderedPage(Upload.class);
    }
    
	@Test
	public void testError(){
        this.tester.startPage(Image.class);
        this.tester.assertRenderedPage(ErrorPage404.class);
    }
	
	@Test
	(expected=RestartResponseException.class)
    public void testError2(){
	    PageParameters pars = new PageParameters();
        pars.add("album", ALBUM_NAME_EXIST);
        pars.add("fid", "3");
        Page page = new Image(pars);
        this.tester.startPage(page);
    }
	
	@Test
    (expected=RestartResponseException.class)
    public void testError3(){
        PageParameters pars = new PageParameters();
        pars.add("fid", "1");
        Page page = new Image(pars);
        this.tester.startPage(page);
    }
	
	@Test
    public void testRemoveSharedUser(){
	    PageParameters pars = new PageParameters();
        pars.add("album", ALBUM_NAME_EXIST);
        pars.add("fid", "2");
        Page page = new Image(pars);
        this.tester.startPage(page);
        
        FormTester formTester = this.tester.newFormTester("form");
        formTester.setValue("shareEmail", ConstantsForTests.USER_EMAIL_EXIST2);
        formTester.submit("ajax-button");
        
        this.tester.assertNoErrorMessage();
        
        this.tester.clickLink("pageable:1:delete");
    }
}
