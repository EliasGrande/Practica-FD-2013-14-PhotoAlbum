package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.LIKE;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;
import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.hibernate.Voted;
import es.udc.fi.dc.photoalbum.model.spring.VotedService;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

public class VotedServiceMock {

    public static VotedService mock = new VotedService() {

        public Voted getVoted(int likeAndDislikeId, int userId) {   
            User user = new User(2, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, null, null, PrivacyLevel.PUBLIC);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            user.getAlbums().add(album);
            Voted voted = new Voted(likeAndDislike, album.getUser(), LIKE);
            return voted;
        }

        public List<Voted> getVoted(
                List<Integer> likeAndDislikeIdList, int userId) {
            ArrayList<Voted> list = new ArrayList<Voted>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, null, null, PrivacyLevel.PUBLIC);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            user.getAlbums().add(album);
            Voted voted = new Voted(likeAndDislike, album.getUser(), LIKE);
            list.add(voted);
            return list;
        }
        
    };
}
