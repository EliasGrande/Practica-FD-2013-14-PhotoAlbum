package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.hibernate.Voted;
import es.udc.fi.dc.photoalbum.hibernate.VotedDao;

// Parece tontería poner esto transaccional pero como el dao se usa en un entorno transaccional
// en el LikeAndDislikeService, si no hago este también transaccional hibernate peta diciéndome
// cosas nazis de que no existe una sesion y no puede hacer una no-transaccional blablabla...
/**
 * Implementation of the {@link VotedService}
 */
@Transactional
public class VotedServiceImpl implements VotedService {

    /**
     * @see VotedDao
     */
    private VotedDao votedDao;

    /**
     * Method for get an {@link VotedDao}.
     * 
     * @return An {@link VotedDao}.
     */
    public VotedDao getVotedDao() {
        return this.votedDao;
    }

    /**
     * Method that allows to put an {@link VotedDao}.
     * 
     * @param votedDao
     *            VotedDao which will be put.
     */
    public void setVotedDao(VotedDao votedDao) {
        this.votedDao = votedDao;
    }

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
     * @see es.udc.fi.dc.photoalbum.spring.VotedService#getVoted(int,
     *      int)
     */
    public Voted getVoted(int likeAndDislikeId, int userId) {
        return votedDao.get(likeAndDislikeId, userId);
    }

    /**
     * Get an {@link Voted} list using a list of
     * {@link LikeAndDislikeId} and a {@link User} identifier.
     * 
     * @param likeAndDislikeIdList
     *            A list of {@link LikeAndDislikeId}
     * @param userId
     *            An {@link User} identifier of the {@link User} who
     *            has voted.
     * @return A list of {@link Voted} that matches the search.
     * @see es.udc.fi.dc.photoalbum.spring.VotedService#getVoted(List<
     *      Integer>, int)
     */
    public List<Voted> getVoted(List<Integer> likeAndDislikeIdList,
            int userId) {
        return votedDao.getVoted(likeAndDislikeIdList, userId);
    }
}
