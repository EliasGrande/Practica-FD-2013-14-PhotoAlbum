package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

public class FileServiceMock {

    public static FileService mock = new FileService() {
        public void create(File file) {
        }

        public void delete(File file) {
        }

        public File getFileOwn(int id, String name, int userId) {
            return new File(1, "1", new byte[1], new byte[1],
                    new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE));
        }

        public File getFileShared(int id, String name, int userId) {
            return new File(1, "1", new byte[1], new byte[1],
                    new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE));
        }

        public void changeAlbum(File file, Album album) {
            file.setPrivacyLevel(album.getPrivacyLevel());
        }

        public File getById(Integer id) {
            return new File(1, "1", new byte[1], new byte[1],
                    new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE));
        }

        public ArrayList<File> getAlbumFilesOwn(int albumId) {
            ArrayList<File> list = new ArrayList<File>();
            list.add(new File(1, "1", new byte[1], new byte[1],
                    new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE)));
            return list;
        }

        public ArrayList<File> getAlbumFilesOwnPaging(int albumId,
                int first, int count) {
            ArrayList<File> list = new ArrayList<File>();
            list.add(new File(1, "1", new byte[1], new byte[1],
                    new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE)));
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
            return new File(1, "1", new byte[1], new byte[1],
                    new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE));
        }

        public ArrayList<File> getAlbumFilesShared(int albumId,
                int userId) {
            ArrayList<File> list = new ArrayList<File>();
            list.add(new File(1, "1", new byte[1], new byte[1],
                    new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE)));
            return list;
        }

        public ArrayList<File> getAlbumFilesSharedPaging(
                int albumId, int userId, int first, int count) {
            ArrayList<File> list = new ArrayList<File>();
            list.add(new File(1, "1", new byte[1], new byte[1],
                    new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE)));
            return list;
        }

        public ArrayList<File> getAlbumFilesPublic(int albumId,
                int userId) {
            ArrayList<File> list = new ArrayList<File>();
            list.add(new File(1, "1", new byte[1], new byte[1],
                    new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE)));
            return list;
        }

        public ArrayList<File> getAlbumFilesPublicPaging(
                int albumId, int userId, int first, int count) {
            ArrayList<File> list = new ArrayList<File>();
            list.add(new File(1, "1", new byte[1], new byte[1],
                    new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE)));
            return list;
        }

        public ArrayList<File> getFilesByTag(int userId, String tag) {
            ArrayList<File> list = new ArrayList<File>();
            list.add(new File(1, "1", new byte[1], new byte[1],
                    new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE)));
            return list;
        }

        public ArrayList<File> getFilesByTagPaging(int userId,
                String tag, int first, int count) {
            ArrayList<File> list = new ArrayList<File>();
            list.add(new File(1, "1", new byte[1], new byte[1],
                    new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE)));
            return list;
        }
    };
}
