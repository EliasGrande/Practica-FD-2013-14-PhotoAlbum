package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.List;

/**
 * DAO interface for the {@link Voted} entity.
 */
public interface VotedDao extends GenericDao<Voted> {

    /**
     * Updates the database using the given {@link Voted} object as
     * source.
     * 
     * @param voted
     *            Voted
     */
    void update(Voted voted);

    /**
     * Gets the {@link Voted} identified by the given
     * {@code likeAndDislikeId} and {@code userId}.
     * 
     * @param likeAndDislikeId
     *            LikeAndDislike id
     * @param userId
     *            User id
     * @return Requested vote or {@code null} if not found
     */
    Voted get(int likeAndDislikeId, int userId);

    /**
     * Same as {@link #get(int, int)} but specifying a list of
     * likeAndDislike ids instead of only one.
     * 
     * @param likeAndDislikeIdList
     *            LikeAndDislike ids
     * @param userId
     *            User id
     * @return Requested votes, only the found votes are added to the
     *         list
     */
    List<Voted> getVoted(List<Integer> likeAndDislikeIdList,
            int userId);
}
