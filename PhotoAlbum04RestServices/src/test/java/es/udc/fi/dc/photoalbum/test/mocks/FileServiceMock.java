package es.udc.fi.dc.photoalbum.test.mocks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.FileService;
import es.udc.fi.dc.photoalbum.util.utils.MD5;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

public class FileServiceMock implements FileService {

    public List<File> getFiles(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy,
            Calendar fechaMin, Calendar fechaMax, int first, int count) {

        User user = new User(1, "123", MD5.getHash("pass"));
        Album album = new Album(1, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        File file = new File(1, "Search with all parameters",
                new byte[] { 1 }, new byte[] { 2 }, album);

        List<File> files = new ArrayList<File>();
        files.add(file);

        return files;

    }

    public List<File> getFiles(String orderBy, int first, int count) {
        User user = new User(1, "123", MD5.getHash("pass"));
        Album album = new Album(1, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        File file = new File(1, "Search with only orderBy",
                new byte[] { 1 }, new byte[] { 2 }, album);

        List<File> files = new ArrayList<File>();
        files.add(file);

        return files;
    }

    public List<File> getFiles(String orderBy, Calendar fechaMin,
            Calendar fechaMax, int first, int count) {
        User user = new User(1, "123", MD5.getHash("pass"));
        Album album = new Album(1, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        File file = new File(1,
                "Search with orderBy and filter by date",
                new byte[] { 1 }, new byte[] { 2 }, album);

        List<File> files = new ArrayList<File>();
        files.add(file);

        return files;
    }

    public List<File> getFiles(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy, int first,
            int count) {
        User user = new User(1, "123", MD5.getHash("pass"));
        Album album = new Album(1, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        File file = new File(1,
                "All parameters without filter dates",
                new byte[] { 1 }, new byte[] { 2 }, album);

        List<File> files = new ArrayList<File>();
        files.add(file);

        return files;
    }

    @Override
    public void create(File file) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(File file) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changeAlbum(File file, Album album) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changePrivacyLevel(File file, String privacyLevel) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public File getById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getFileOwn(int id, String name, int userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getFileShared(int id, String name, int userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getFilePublic(int id, String name, int userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<File> getAlbumFilesOwn(int albumId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<File> getAlbumFilesOwnPaging(int albumId, int first,
            int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<File> getAlbumFilesShared(int albumId, int userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<File> getAlbumFilesSharedPaging(int albumId,
            int userId, int first, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<File> getAlbumFilesPublic(int albumId, int userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<File> getAlbumFilesPublicPaging(int albumId,
            int userId, int first, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long getCountAlbumFiles(int albumId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<File> getFilesByTag(int userId, String tag) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<File> getFilesByTagPaging(int userId, String tag,
            int first, int count) {
        // TODO Auto-generated method stub
        return null;
    }
}
