package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.List;

/**
 * DAO interface for the {@link User} entity.
 */
public interface UserDao extends GenericDao<User> {

    /**
     * Updates the database using the given {@link User} object as
     * source.
     * 
     * @param user
     *            User
     */
    void update(User user);

    /**
     * Gets the {@link User} identified by the given {@code id}.
     * 
     * @param id
     *            User id
     * 
     * @return Requested user or {@code null} if not found
     */
    User getById(Integer id);

    /**
     * Gets the {@link User} identified by the given {@code email} and
     * {@code password}, used for login.
     * 
     * @param email
     *            User email
     * @param password
     *            User password
     * 
     * @return Requested user or {@code null} if not found
     */
    User getUser(String email, String password);

    /**
     * Gets the {@link User} identified by the given {@code email}.
     
     * @param userEmail
     *            User
     * @return Requested user or {@code null} if not found
     */
    User getUser(User userEmail);

    /**
     * list of users sharing files or albums with the given
     * {@link User} id.
     * 
     * @param userId
     *            User id
     * 
     * @return sorted User list
     */
    List<User> getUsersSharingWith(int userId);
}
