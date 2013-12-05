package es.udc.fi.dc.photoalbum.model.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.AlbumTag;

/**
 * Interface for the {@link AlbumTagService}
 */
public interface AlbumTagService {

    /**
     * Method that allows to create an {@link AlbumTag}
     * 
     * @param albumTag
     *            {@link AlbumTag} that will be created
     */
    void create(AlbumTag albumTag);

    /**
     * Method that allows to delete an {@link AlbumTag}
     * 
     * @param albumTag
     *            {@link AlbumTag} that will be deleted
     */
    void delete(AlbumTag albumTag);

    /**
     * Method for get an {@link AlbumTag} searching by {@link Album}
     * identifier and a tag name
     * 
     * @param albumId
     *            {@link Album} identifier
     * @param tag
     *            {@link AlbumTag} tag
     * @return {@link AlbumTag} object that corresponds with that
     *         {@link Album} identifier and tag.
     */
    AlbumTag getTag(int albumId, String tag);

    /**
     * Get a list of {@link AlbumTag} searching by {@link Album}
     * identifier
     * 
     * @param albumId
     *            An {@link Album} identifier.
     * @return A list of {@link AlbumTag} whose {@link Album}
     *         identifier is the {@link Album} id.
     */
    List<AlbumTag> getTags(int albumId);
}
