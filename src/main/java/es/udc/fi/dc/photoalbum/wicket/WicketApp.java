package es.udc.fi.dc.photoalbum.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import es.udc.fi.dc.photoalbum.wicket.pages.auth.Albums;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.ErrorPage404;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.Image;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.Profile;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.SignOut;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.Upload;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.share.Share;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.share.SharedAlbums;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.share.SharedBig;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.share.SharedFiles;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.share.SharedUsers;
import es.udc.fi.dc.photoalbum.wicket.pages.nonAuth.ForgotPassword;
import es.udc.fi.dc.photoalbum.wicket.pages.nonAuth.Login;
import es.udc.fi.dc.photoalbum.wicket.pages.nonAuth.Register;
import es.udc.fi.dc.photoalbum.wicket.pages.nonAuth.RegistryCompleted;

public class WicketApp extends WebApplication {

	public WicketApp() {
	}

	public Class<? extends Page> getHomePage() {
		return Login.class;
	}

	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(
				new SpringComponentInjector(this));
		getSecuritySettings().setAuthorizationStrategy(
				new AuthorizationStrategy());
		mountPage("albums", Albums.class);
		mountPage("error404", ErrorPage404.class);
		mountPage("bigPic", Image.class);
		mountPage("register", Register.class);
		mountPage("registerCompleted", RegistryCompleted.class);
		mountPage("share", Share.class);
		mountPage("sharedAlbums", SharedAlbums.class);
		mountPage("sharedBig", SharedBig.class);
		mountPage("sharedFiles", SharedFiles.class);
		mountPage("sharedUsers", SharedUsers.class);
		mountPage("pics", Upload.class);
		mountPage("profile", Profile.class);
		mountPage("forgotPassword", ForgotPassword.class);
		mountPage("signOut", SignOut.class);
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new MySession(request);
	}
}
