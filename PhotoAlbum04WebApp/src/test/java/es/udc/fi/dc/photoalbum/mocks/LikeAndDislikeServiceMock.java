package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;
import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.LikeAndDislikeService;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

public class LikeAndDislikeServiceMock {

    public static LikeAndDislikeService mock = new LikeAndDislikeService() {

        public LikeAndDislike voteLike(
                LikeAndDislike likeAndDislike, User user) {
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);likeAndDislike.setId(1);
            album.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            return album.getLikeAndDislike();
        }

        public LikeAndDislike voteDislike(
                LikeAndDislike likeAndDislike, User user) {
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);likeAndDislike.setId(1);
            album.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            return album.getLikeAndDislike();
        }

        public LikeAndDislike unvote(LikeAndDislike likeAndDislike,
                User user) {
            User user2 = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user2,
                    null, null, PrivacyLevel.PRIVATE);likeAndDislike.setId(1);
            album.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            return album.getLikeAndDislike();
        }

        public boolean userHasVoted(LikeAndDislike likeAndDislike,
                User user) {
            return true;
        }
        
    };
}
