package es.udc.fi.dc.photoalbum.hibernate;

import java.util.List;

public interface VotedDao extends GenericDao<Voted> {

    void update(Voted voted);

    Voted get(int likeAndDislikeId, int userId);

    List<Voted> getVoted(
            List<Integer> likeAndDislikeIdList, int userId);
}
