package es.udc.fi.dc.photoalbum.model.spring;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.hibernate.UserDao;

/**
 * Implementation of the {@link UserService}
 */
@Transactional
public class UserServiceImpl implements UserService {

    /**
     * @see UserDao
     */
    private UserDao userDao;

    /**
     * Method for get an {@link UserDao}.
     * 
     * @return An {@link UserDao}.
     */
    public UserDao getUserDao() {
        return this.userDao;
    }

    /**
     * Method that allows to put an {@link UserDao}.
     * 
     * @param userDao
     *            UserDao which will be put.
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Allows to delete an {@link User}
     * 
     * @param user
     *            {@link User} that will be removed.
     * @see es.udc.fi.dc.photoalbum.model.spring.UserService#create(User)
     */
    public void create(User user) {
        userDao.create(user);
    }

    /**
     * Allows to delete an {@link User}
     * 
     * @param user
     *            {@link User} that will be removed.
     * @see es.udc.fi.dc.photoalbum.model.spring.UserService#delete(User)
     */
    public void delete(User user) {
        userDao.delete(user);
    }

    /**
     * Allows to update an {@link User}
     * 
     * @param user
     *            {@link User} that will be updated.
     * @see es.udc.fi.dc.photoalbum.model.spring.UserService#update(User)
     */
    public void update(User user) {
        userDao.update(user);
    }

    /**
     * Get an {@link User} from an {@link User} email and an
     * {@link User} password.
     * 
     * @param email
     *            Email of the {@link User}.
     * @param password
     *            Password of the {@link User}.
     * @return {@link User} with that email and password.
     * @see es.udc.fi.dc.photoalbum.model.spring.UserService#getUser(String,
     *      String)
     */
    public User getUser(String email, String password) {
        return userDao.getUser(email, password);
    }

    /**
     * Get an {@link User} searching by {@link User} email.
     * 
     * @param userEmail
     *            Email of the {@link User}.
     * @return {@link User} who has the email specified.
     * @see es.udc.fi.dc.photoalbum.model.spring.UserService#getUser(User)
     */
    public User getUser(User userEmail) {
        return userDao.getUser(userEmail);
    }

    /**
     * Search for an {@link User} using its identifier.
     * 
     * @param id
     *            Identifier of the {@link User}.
     * @return User with that email.
     * @see es.udc.fi.dc.photoalbum.model.spring.UserService#getById(Integer)
     */
    public User getById(Integer id) {
        return userDao.getById(id);
    }

    /**
     * Returns a list of {@link User} which an user has shared
     * something.
     * 
     * @param userId
     *            {@link User} identifier for the search.
     * @return A list of users with whom you have shared something.
     * @see es.udc.fi.dc.photoalbum.model.spring.UserService#getUsersSharingWith(int)
     */
    public List<User> getUsersSharingWith(int userId) {
        return userDao.getUsersSharingWith(userId);
    }

}
