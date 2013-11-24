package es.udc.fi.dc.photoalbum.hibernate;

/**
 * DAO interface for the {@link LikeAndDislike} entity.
 */
public interface LikeAndDislikeDao extends GenericDao<LikeAndDislike> {

    /**
     * Updates the database using the given {@link LikeAndDislike}
     * object as source.
     * 
     * @param likeAndDislike
     *            Vote count info object
     * @return The given {@link LikeAndDislike} object.
     */
    LikeAndDislike update(LikeAndDislike likeAndDislike);

    /**
     * Gets the {@link LikeAndDislike} identified by the given
     * {@code likeAndDislikeId}.
     * 
     * @param likeAndDislikeId
     *            LikeAndDislike id
     * @return LikeAndDislike object
     */
    LikeAndDislike get(int likeAndDislikeId);
}
