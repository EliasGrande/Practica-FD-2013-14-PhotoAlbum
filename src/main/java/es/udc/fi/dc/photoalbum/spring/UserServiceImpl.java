package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.hibernate.UserDao;

@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserDao getUserDao() {
        return this.userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void create(User user) {
        userDao.create(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public User getUser(String email, String password) {
        return userDao.getUser(email, password);
    }

    public User getUser(User userEmail) {
        return userDao.getUser(userEmail);
    }

    public User getById(Integer id) {
        return userDao.getById(id);
    }

    public ArrayList<User> getUsersSharingWith(int userId) {
        return userDao.getUsersSharingWith(userId);
    }

}
