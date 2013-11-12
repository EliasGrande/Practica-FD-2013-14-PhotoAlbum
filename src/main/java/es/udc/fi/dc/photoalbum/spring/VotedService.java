package es.udc.fi.dc.photoalbum.spring;

import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.Voted;

public interface VotedService {

    Voted getVoted(int likeAndDislikeId, int userId);

    List<Voted> getVoted(
            List<Integer> likeAndDislikeIdList, int userId);
}
