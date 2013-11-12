package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.LIKE;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;
import java.util.List;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.hibernate.Voted;
import es.udc.fi.dc.photoalbum.spring.VotedService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

public class VotedServiceMock {

    public static VotedService mock = new VotedService() {

        public Voted getVoted(int likeAndDislikeId, int userId) {   
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            user.getAlbums().add(album);
            album.getLikeAndDislike().setId(50);
            LikeAndDislike lal = new LikeAndDislike();
            lal.setId(1);
            return new Voted(lal, user, LIKE);
        }

        public List<Voted> getVoted(
                List<Integer> likeAndDislikeIdList, int userId) {
            ArrayList<Voted> list = new ArrayList<Voted>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            user.getAlbums().add(album);
            album.getLikeAndDislike().setId(51);
            LikeAndDislike lal = new LikeAndDislike();
            lal.setId(1);
            list.add(new Voted(lal, user, LIKE));
            return list;
        }
        
    };
}
