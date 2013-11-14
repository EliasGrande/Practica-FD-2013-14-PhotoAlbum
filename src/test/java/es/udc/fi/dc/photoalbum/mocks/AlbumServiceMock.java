package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;

import org.springframework.dao.DataIntegrityViolationException;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

public class AlbumServiceMock {
    public static AlbumService mock = new AlbumService() {
        public void rename(Album album, String newName) {
        }

        public Album getAlbum(String name, int userId) {
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            album.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            return album;
        }

        public void delete(Album album) {
            album.getUser().getAlbums().remove(album);
        }

        public void create(Album album) {
            if (album.getName().equals(ALBUM_NAME_EXIST)) {
                throw new DataIntegrityViolationException("");
            }
            album.getUser().getAlbums().add(album);
        }

        public Album getById(Integer id) {
            return new Album(1, ALBUM_NAME_EXIST, new User(1,
                    USER_EMAIL_EXIST, USER_PASS_YES), null, null,
                    PrivacyLevel.PRIVATE);
        }

        public ArrayList<Album> getAlbums(Integer id) {
            ArrayList<Album> list = new ArrayList<Album>();
            list.add(new Album(1, ALBUM_NAME_EXIST, new User(1,
                    USER_EMAIL_EXIST, USER_PASS_YES), null, null,
                    PrivacyLevel.PRIVATE));
            return list;
        }

        public ArrayList<Album> getPublicAlbums() {
            ArrayList<Album> list = new ArrayList<Album>();
            list.add(new Album(1, ALBUM_NAME_EXIST, new User(1,
                    USER_EMAIL_EXIST, USER_PASS_YES), null, null,
                    PrivacyLevel.PRIVATE));
            return list;
        }

        public void changePrivacyLevel(Album album,
                String privacyLevel) {
            album.setPrivacyLevel(privacyLevel);
        }

        public ArrayList<Album> getAlbumsSharedWith(Integer id,
                String ownerEmail) {
            ArrayList<Album> list = new ArrayList<Album>();
            list.add(new Album(1, ALBUM_NAME_EXIST, new User(1,
                    USER_EMAIL_EXIST, USER_PASS_YES), null, null,
                    PrivacyLevel.PRIVATE));
            return list;
        }

        public Album getSharedAlbum(String albumName,
                int userSharedToId, String userSharedEmail) {
            return new Album(1, ALBUM_NAME_EXIST, new User(1,
                    USER_EMAIL_EXIST, USER_PASS_YES), null, null,
                    PrivacyLevel.PRIVATE);
        }

        public ArrayList<Album> getAlbumsByTag(int userId,
                String tag) {
            ArrayList<Album> list = new ArrayList<Album>();
            list.add(new Album(1, ALBUM_NAME_EXIST, new User(1,
                    USER_EMAIL_EXIST, USER_PASS_YES), null, null,
                    PrivacyLevel.PRIVATE));
            return list;
        }
    };
}
