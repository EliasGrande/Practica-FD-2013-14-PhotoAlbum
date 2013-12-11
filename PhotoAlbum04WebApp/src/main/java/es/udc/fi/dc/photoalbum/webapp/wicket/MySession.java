package es.udc.fi.dc.photoalbum.webapp.wicket;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.cookies.CookieUtils;

import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.UserService;

/**
 * Session that holds current user
 */
@SuppressWarnings("serial")
public class MySession extends WebSession {

    /**
     * @see UserService
     */
    @SpringBean
    private UserService userService;

    /**
     * The id of the {@link User} owner of the session.
     */
    private Integer uId;

    /**
     * Method that return the id of the {@link User} owner of the
     * session.
     * 
     * @return Integer {@link #uId}
     */
    public Integer getuId() {
        return uId;
    }

    /**
     * Method that allows to hold the id of the owner of the session.
     * 
     * @param uId
     *            {@link #uId}
     */
    public void setuId(Integer uId) {
        this.uId = uId;
    }

    /**
     * Constructor for MySession.
     * 
     * @param request
     *            Request object.
     */
    public MySession(Request request) {
        super(request);
        Injector.get().inject(this);
    }

    /**
     * Checks if user is Authenticated.
     * 
     * 
     * @return boolean Return true if the {@link User} is
     *         authenticated, otherwise return false.
     */
    public boolean isAuthenticated() {
        return (this.uId != null);
    }

    /**
     * Checks isAuthenticatedWithCookies.
     * 
     * @return boolean Return true if the {@link User} is
     *         authenticated and stored their cookies, otherwise
     *         return false.
     */
    public boolean isAuthenticatedWithCookies() {
        CookieUtils cu = new CookieUtils();
        String email = cu.load("email");
        String password = cu.load("password");
        if ((email != null) && (password != null)) {
            User user = new User(null, email, password);
            User userDB = userService.getUser(user);
            if (userDB == null) {
                return false;
            }
            if (userDB.getPassword().equals(password)) {
                this.uId = userDB.getId();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
