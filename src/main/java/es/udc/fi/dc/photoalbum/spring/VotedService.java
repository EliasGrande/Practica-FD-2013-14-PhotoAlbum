package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.hibernate.Voted;

/**
 * Interface for the {@link VotedService}
 */
public interface VotedService {

    /**
     * Get an {@link Voted} object searching by
     * {@link LikeAndDislikeId} identifier and {@link User}
     * identifier.
     * 
     * @param likeAndDislikeId
     *            An {@link LikeAndDislikeId} identifier.
     * @param userId
     *            An {@link User} identifier of the {@link User} who
     *            has voted.
     * @return {@link Voted} object that matches the search.
     */
    Voted getVoted(int likeAndDislikeId, int userId);

    /**
     * Get an {@link Voted} list using a list of
     * {@link LikeAndDislikeId} and a {@link User} identifier.
     * 
     * @param likeAndDislikeIdList
     *            A list of {@link LikeAndDislikeId}.
     * @param userId
     *            An {@link User} identifier of the {@link User} who
     *            has voted.
     * @return A list of {@link Voted} that matches the search.
     */
    List<Voted> getVoted(List<Integer> likeAndDislikeIdList,
            int userId);
}
