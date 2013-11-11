package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_NOT_EXIST;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.mocks.AlbumServiceMock;
import es.udc.fi.dc.photoalbum.mocks.UserServiceMock;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.Albums;

public class TestAlbumsPage {

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
		this.tester.startPage(Albums.class);
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(Albums.class);
		tester.assertVisible("signout");
	}

	@Test
	public void testCreateNull() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("AlbumName", "");
		formTester.submit();
		this.tester
				.assertErrorMessages("Поле 'Album name' обязательно для ввода.");
	}

	@Test
	public void testCreateAlbumExist() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("AlbumName", ALBUM_NAME_EXIST);
		formTester.submit();
		this.tester
				.assertErrorMessages("Album with this name already exist");
	}

	@Test
	public void testCreateAlbum() {
		FormTester formTester = this.tester.newFormTester("form");
		formTester.setValue("AlbumName", ALBUM_NAME_NOT_EXIST);
		formTester.submit();
		this.tester.assertNoErrorMessage();
		tester.assertRenderedPage(Albums.class);
	}
}
