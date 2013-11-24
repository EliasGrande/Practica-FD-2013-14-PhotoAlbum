package es.udc.fi.dc.photoalbum.hibernate;

import java.util.List;

/**
 * DAO interface for the {@link AlbumTag} entity.
 */
public interface AlbumTagDao extends GenericDao<AlbumTag> {

    /**
     * Gets the {@link AlbumTag} identified by the given
     * {@code albumId} and the given {@code tag}.
     * 
     * @param albumId
     *            Album id
     * @param tag
     *            Album tag string
     * @return Album tag or {@code null} if not found
     */
    AlbumTag getTag(int albumId, String tag);

    /**
     * Gets all the {@link AlbumTag} of the given {@code albumId}.
     * 
     * @param albumId
     *            Album id
     * @return Album tag list
     */
    List<AlbumTag> getTags(int albumId);
}
