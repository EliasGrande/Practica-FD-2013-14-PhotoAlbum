package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;

import org.springframework.dao.DataIntegrityViolationException;

import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.UserService;

public class UserServiceMock {
    
    
    public static UserService mock = new UserService() {
        public void create(User user) {
            if (user.getEmail().equals(USER_EMAIL_EXIST)) {
                throw new DataIntegrityViolationException("");
            }
        }

        public void delete(User user) {
        }

        public void update(User user) {
        }

        public User getUser(String email, String password) {
            if ((email.equals(USER_EMAIL_EXIST))
                    && (password.equals(USER_PASS_YES))) {
                return new User(1, email, password);
            } else {
                return null;
            }
        }

        public User getUser(User userEmail) {
            return new User(1,
                    USER_EMAIL_EXIST, USER_PASS_YES);
        }

        public User getById(Integer id) {
            return new User(1,
                    USER_EMAIL_EXIST, USER_PASS_YES);
        }

        public ArrayList<User> getUsersSharingWith(int userId) {
            ArrayList<User> list = new ArrayList<User>();
            list.add(new User(1,
                    USER_EMAIL_EXIST, USER_PASS_YES));
            return list;
        }  
    };
}
