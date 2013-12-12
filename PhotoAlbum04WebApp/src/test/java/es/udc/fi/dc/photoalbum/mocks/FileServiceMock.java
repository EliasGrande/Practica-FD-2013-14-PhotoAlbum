package es.udc.fi.dc.photoalbum.mocks;


import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.FILE_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.FileService;
import es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

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
            if(id != 1){
                file.setPrivacyLevel(PrivacyLevel.PRIVATE);
                file.setId(2);
            }
            if(id == 3){
                return null;
            }
            user.getAlbums().add(album);
            return file;
        }

        public File getFileShared(int id, String name, int userId) {
            if(name.equals(ConstantsForTests.ALBUM_NAME_ERROR)){
                return null;
            }
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            if(id != 1){
                file.setPrivacyLevel(PrivacyLevel.PRIVATE);
                file.setId(2);
            }
            if(id == 3){
                return null;
            }
            user.getAlbums().add(album);
            return file;
        }

        public void changeAlbum(File file, Album album) {
            file.setPrivacyLevel(album.getPrivacyLevel());
        }

        public File getById(Integer id) {
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[12], new byte[123], album);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            return file;
        }

        public ArrayList<File> getAlbumFilesOwn(int albumId) {
            ArrayList<File> list = new ArrayList<File>();
            Set<File> set2 = new HashSet<File>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set2, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            File file2 = new File(2, "FILE_NAME_EXIST2", new byte[1], new byte[1], album);
            File file3 = new File(3, "FILE_NAME_EXIST3", new byte[1], new byte[1], album);
  
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            file2.setLikeAndDislike(likeAndDislike);
            file3.setLikeAndDislike(likeAndDislike);
            set2.add(file);
            set2.add(file2);
            set2.add(file3);
            
            user.getAlbums().add(album);
            list.add(file);
            list.add(file2);
            list.add(file3);
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
            return (long) 1;
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
            Set<File> set3 = new HashSet<File>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user, set3, null, PrivacyLevel.PUBLIC);
            File file = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album);
            File file2 = new File(2, "FILE_NAME_EXIST2", new byte[1], new byte[1], album);
            File file3 = new File(3, "FILE_NAME_EXIST3", new byte[1], new byte[1], album);
            set3.add(file);
            set3.add(file2);
            set3.add(file3);
            
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            file2.setLikeAndDislike(likeAndDislike);
            file3.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(file);
            list.add(file2);
            list.add(file3);
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
            File file2 = new File(2, "FILE_NAME_EXIST2", new byte[1], new byte[1], album);
            File file3 = new File(3, "FILE_NAME_EXIST3", new byte[1], new byte[1], album);
            
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            file2.setLikeAndDislike(likeAndDislike);
            file3.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(file);
            list.add(file2);
            list.add(file3);
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
            File file2 = new File(2, "FILE_NAME_EXIST2", new byte[1], new byte[1], album);
            File file3 = new File(3, "FILE_NAME_EXIST3", new byte[1], new byte[1], album);
            
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            file.setLikeAndDislike(likeAndDislike);
            file2.setLikeAndDislike(likeAndDislike);
            file3.setLikeAndDislike(likeAndDislike);
            user.getAlbums().add(album);
            list.add(file);
            list.add(file2);
            list.add(file3);
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

        @Override
        public List<File> getFiles(String keywords, boolean name,
                boolean comment, boolean tag, String orderBy,
                Calendar fechaMin, Calendar fechaMax, int first,
                int count) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<File> getFiles(String orderBy, int first,
                int count) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<File> getFiles(String orderBy, Calendar fechaMin,
                Calendar fechaMax, int first, int count) {
            // TODO Auto-generated method stub
            return null;
        }
    };
}
