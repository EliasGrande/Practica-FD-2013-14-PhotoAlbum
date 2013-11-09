package es.udc.fi.dc.photoalbum.spring;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.User;

public interface UserService {

    void create(User user);

    void delete(User user);

    void update(User user);

    User getUser(String email, String password);

    User getUser(User userEmail);

    User getById(Integer id);

    ArrayList<User> getUsersSharingWith(int userId);
}
