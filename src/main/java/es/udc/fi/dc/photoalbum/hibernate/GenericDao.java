package es.udc.fi.dc.photoalbum.hibernate;

/**
 * Generic DAO interface, all other DAO interfaces extend this.
 *
 * @param <T> Entity ({@link Album}, {@link File}, {@link Comment}...)
 */
public interface GenericDao<T> {

    /**
     * Stores the object into the database.
     * 
     * @param t Entity object.
     */
    void create(T t);

    /**
     * Deletes the object from the database.
     * 
     * @param t Entity object.
     */
    void delete(T t);
}
