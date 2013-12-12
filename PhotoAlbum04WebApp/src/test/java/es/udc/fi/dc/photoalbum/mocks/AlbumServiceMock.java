package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_NOT_EXIST;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.AlbumService;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

public class AlbumServiceMock {
    public static AlbumService mock = new AlbumService() {
        public void rename(Album album, String newName) {
        }

        public Album getAlbum(String name, int userId) {
            if(name.compareTo(ALBUM_NAME_NOT_EXIST)==0){
                return null;
            }
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
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            album.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            return album;
        }

        public ArrayList<Album> getAlbums(Integer id) {
            ArrayList<Album> list = new ArrayList<Album>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            album.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(album);
            return list;
        }

        public ArrayList<Album> getPublicAlbums() {
            ArrayList<Album> list = new ArrayList<Album>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            album.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(album);
            return list;
        }

        public void changePrivacyLevel(Album album,
                String privacyLevel) {
            album.setPrivacyLevel(privacyLevel);
        }

        public ArrayList<Album> getAlbumsSharedWith(Integer id,
                String ownerEmail) {
            ArrayList<Album> list = new ArrayList<Album>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            album.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(album);
            return list;
        }

        public Album getSharedAlbum(String albumName,
                int userSharedToId, String userSharedEmail) {
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            album.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            return album;
        }

        public ArrayList<Album> getAlbumsByTag(int userId,
                String tag) {
            ArrayList<Album> list = new ArrayList<Album>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            album.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(album);
            return list;
        }

        @Override
        public List<Album> getFiles(String keywords, boolean name,
                boolean comment, boolean tag, String orderBy,
                Calendar fechaMin, Calendar fechaMax, int first,
                int count) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<Album> getFiles(String orderBy, int first,
                int count) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<Album> getFiles(String orderBy,
                Calendar fechaMin, Calendar fechaMax, int first,
                int count) {
            // TODO Auto-generated method stub
            return null;
        }
    };
}