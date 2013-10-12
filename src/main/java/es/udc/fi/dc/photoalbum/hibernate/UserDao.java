package es.udc.fi.dc.photoalbum.hibernate;

import java.util.ArrayList;

public interface UserDao extends GenericDao<User> {

	void update(User user);

	User getById(Integer id);

	/**
	 * @param email
	 *            email of user
	 * @param password
	 *            password of user
	 * @return user instance if exists or null
	 */
	User getUser(String email, String password);

	/**
	 * @param userEmail
	 *            email of user
	 * @return user if exists or null
	 */
	User getUser(User userEmail);
	
	/**
	 * @param userId
	 *            id of user
	 * @return sorted list of users sharing files or albums with the user
	 */
	ArrayList<User> getUsersSharingWith(int userId);
}
