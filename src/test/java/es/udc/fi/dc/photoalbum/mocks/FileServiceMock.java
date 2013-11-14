package es.udc.fi.dc.photoalbum.mocks;


import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.FILE_NAME_EXIST;




import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

public class FileServiceMock {
    
    private static Set<File> set = new HashSet<File>();

    public static FileService mock = new FileService() {
        public void create(File file) {
        }

        public void delete(File file) {
        }

        public File getFileOwn(int id, String name, int userId) {
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            return file;
        }

        public File getFileShared(int id, String name, int userId) {
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            return file;
        }

        public void changeAlbum(File file, Album album) {
            file.setPrivacyLevel(album.getPrivacyLevel());
        }

        public File getById(Integer id) {
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            return file;
        }

        public ArrayList<File> getAlbumFilesOwn(int albumId) {
            ArrayList<File> list = new ArrayList<File>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(file);
            return list;
        }

        public ArrayList<File> getAlbumFilesOwnPaging(int albumId,
                int first, int count) {
            ArrayList<File> list = new ArrayList<File>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(file);
            return list;
        }

        public Long getCountAlbumFiles(int albumId) {
            return (long) 0;
        }

        public void changePrivacyLevel(File file,
                String privacyLevel) {
            file.setPrivacyLevel(privacyLevel);
        }

        public File getFilePublic(int id, String name, int userId) {
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            return file;
        }

        public ArrayList<File> getAlbumFilesShared(int albumId,
                int userId) {
            ArrayList<File> list = new ArrayList<File>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(file);
            return list;
        }

        public ArrayList<File> getAlbumFilesSharedPaging(
                int albumId, int userId, int first, int count) {
            ArrayList<File> list = new ArrayList<File>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(file);
            return list;
        }

        public ArrayList<File> getAlbumFilesPublic(int albumId,
                int userId) {
            ArrayList<File> list = new ArrayList<File>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(file);
            return list;
        }

        public ArrayList<File> getAlbumFilesPublicPaging(
                int albumId, int userId, int first, int count) {
            ArrayList<File> list = new ArrayList<File>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(file);
            return list;
        }

        public ArrayList<File> getFilesByTag(int userId, String tag) {
            ArrayList<File> list = new ArrayList<File>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(file);
            return list;
        }

        public ArrayList<File> getFilesByTagPaging(int userId,
                String tag, int first, int count) {
            ArrayList<File> list = new ArrayList<File>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(file);
            return list;
        }
    };
}