package es.udc.fi.dc.photoalbum.util.utils;

/**
 * Interface for comparing entities by id used by {@link ComparatorById}.
 */
public interface ComparableById {

    /**
     * Unique auto-incremental numeric identifier.
     * 
     * @return Unique id
     */
    Integer getId();

}
