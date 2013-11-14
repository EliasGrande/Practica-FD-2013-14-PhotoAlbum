package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.LIKE;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.hibernate.Voted;
import es.udc.fi.dc.photoalbum.spring.VotedService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

public class VotedServiceMock {

    public static VotedService mock = new VotedService() {

        public Voted getVoted(int likeAndDislikeId, int userId) {   
            User user = new User(2, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, null, null, PrivacyLevel.PUBLIC);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            //album.setLikeAndDislike(likeAndDislike);
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
            //album.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            Voted voted = new Voted(likeAndDislike, album.getUser(), LIKE);
            list.add(voted);
            return list;
        }
        
    };
}

/*
private static Album album = new Album();
private static User user = new User();
private static File file = new File();
private static LikeAndDislike likeAndDislike = new LikeAndDislike();

private static Set<File> set = new HashSet<File>();

public static LikeAndDislike createLikeAndDislike () {
    likeAndDislike.setId(50);
    likeAndDislike.setDislike(2);
    likeAndDislike.setId(2);
    return likeAndDislike;
}

public static User createUser () {
    user.setId(1);
    user.setEmail(USER_EMAIL_EXIST);
    user.setPassword(USER_PASS_YES);
    return user;
}

public static Album createAlbum () {
    album.setId(1);
    album.setName(ALBUM_NAME_EXIST);
    album.setUser(user);
    file.setId(1);
    file.setFile(new byte[1]);
    file.setFileSmall(new byte[1]);
    file.setLikeAndDislike(likeAndDislike);
    file.setPrivacyLevel(PrivacyLevel.PUBLIC);
    file.setAlbum(album);
    set.add(file);
    album.setFiles(set);
    album.setLikeAndDislike(likeAndDislike);
    album.setPrivacyLevel(PrivacyLevel.PUBLIC);
    return album;
}
*/
