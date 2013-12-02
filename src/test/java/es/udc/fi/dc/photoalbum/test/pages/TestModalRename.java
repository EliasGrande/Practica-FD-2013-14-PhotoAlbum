package es.udc.fi.dc.photoalbum.test.pages;

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
import es.udc.fi.dc.photoalbum.wicket.pages.auth.ModalRename;

public class TestModalRename {

        private WicketApp wicketApp;
        private WicketTester tester;

        {
            this.wicketApp = new WicketApp() {
                @Override
                protected void init() {
                    ApplicationContextMock context = new ApplicationContextMock();

                    context.putBean("albumBean", AlbumServiceMock.mock);
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
            this.tester.startPage(ModalRename.class);
            this.tester.getSession().setLocale(new Locale("ru", "RU"));
        }
        
        @Test
        public void testRenderedModalWindow(){
            tester.assertRenderedPage(ModalRename.class);
        }
        
        @Test
        public void testOnSubmitButtonOk(){
            //FIXME No hacen nada.
            FormTester formTester = this.tester.newFormTester("form");
            formTester.submit("buttonOk");
            
            tester.assertRenderedPage(ModalRename.class);
        }
        
        @Test
        public void testOnSubmitButtonCancel(){
            //FIXME No hacen nada.
            FormTester formTester = this.tester.newFormTester("form");
            formTester.submit("buttonCancel");
            
            tester.assertRenderedPage(ModalRename.class);
        }
}
