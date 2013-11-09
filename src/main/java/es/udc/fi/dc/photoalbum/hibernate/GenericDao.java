package es.udc.fi.dc.photoalbum.hibernate;

public interface GenericDao<T> {

    void create(T t);

    void delete(T t);
}
