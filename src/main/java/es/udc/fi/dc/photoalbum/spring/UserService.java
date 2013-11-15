package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.User;

/**
 * Interface for the {@link UserService}
 */
public interface UserService {

    /**
     * Allows to create an {@link User}
     * 
     * @param user
     *            {@link User} that will be created.
     */
    void create(User user);

    /**
     * Allows to delete an {@link User}
     * 
     * @param user
     *            {@link User} that will be removed.
     */
    void delete(User user);

    /**
     * Allows to update an {@link User}
     * 
     * @param user
     *            {@link User} that will be updated.
     */
    void update(User user);

    /**
     * Get an {@link User} from an {@link User} email and an
     * {@link User} password.
     * 
     * @param email
     *            Email of the {@link User}.
     * @param password
     *            Password of the {@link User}.
     * @return {@link User} with that email and password.
     */
    User getUser(String email, String password);

    /**
     * Get an {@link User} searching by {@link User} email.
     * 
     * @param userEmail
     *            Email of the {@link User}.
     * @return {@link User} who has the email specified.
     */
    User getUser(User userEmail);

    /**
     * Search for an {@link User} using its identifier.
     * 
     * @param id
     *            Identifier of the {@link User}.
     * @return User with that email.
     */
    User getById(Integer id);

    /**
     * Returns a list of {@link User} which an user has shared
     * something.
     * 
     * @param userId
     *            {@link User} identifier for the search.
     * @return A list of users with whom you have shared something. 
     */
    List<User> getUsersSharingWith(int userId);
}
