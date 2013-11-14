package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.TAG_NAME_EXIST;

import java.util.Locale;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
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
import es.udc.fi.dc.photoalbum.mocks.LikeAndDislikeServiceMock;
import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.mocks.VotedServiceMock;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.Upload;

public class TestUploadPage {

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
				context.putBean("votedServiceBean", VotedServiceMock.mock);
				context.putBean("commentServiceBean", CommentServiceMock.mock);
				context.putBean("likeAndDislikeBean", LikeAndDislikeServiceMock.mock);
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
		Page page = new Upload(pars);
		this.tester.startPage(page);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("es", "ES"));
	}
	
	@Test
	public void testRendered() {
		tester.assertRenderedPage(Upload.class);
		tester.assertComponent("upload:fileInput", FileUploadField.class);
	}
	
	@Test
	public void testAddTag() {
		FormTester formTester = this.tester.newFormTester("formAddTag");
		formTester.setValue("newTag", TAG_NAME_EXIST);
		formTester.submit();
		this.tester.assertNoErrorMessage();
		//tester.assertRenderedPage(Upload.class);
	}
	
}
