package es.udc.fi.dc.photoalbum.test.mocks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.AlbumService;
import es.udc.fi.dc.photoalbum.util.utils.MD5;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;


public class AlbumServiceMock implements AlbumService{
    
    public List<Album> getAlbums(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy,
            Calendar fechaMin, Calendar fechaMax, int first, int count){
        User user = new User(1, "123", MD5.getHash("pass"));
        Album album = new Album(1, "Search with all parameters", user, null, null,
                PrivacyLevel.PRIVATE);
        
        List<Album> albums = new ArrayList<Album>();
        albums.add(album);
        
        return albums;
    }

    public List<Album> getAlbums(String orderBy, int first, int count){
        User user = new User(1, "123", MD5.getHash("pass"));
        Album album = new Album(1, "Search with only orderBy", user, null, null,
                PrivacyLevel.PRIVATE);
        
        List<Album> albums = new ArrayList<Album>();
        albums.add(album);
        
        return albums;
    }

    public List<Album> getAlbums(String orderBy, Calendar fechaMin,
            Calendar fechaMax, int first, int count){
        User user = new User(1, "123", MD5.getHash("pass"));
        Album album = new Album(1, "Search with orderBy and filter by date", user, null, null,
                PrivacyLevel.PRIVATE);
        
        List<Album> albums = new ArrayList<Album>();
        albums.add(album);
        
        return albums;
    }
    
    public List<Album> getAlbums(String keywords, boolean name,
            boolean comment, boolean tag, String orderBy,
            int first, int count){
        User user = new User(1, "123", MD5.getHash("pass"));
        Album album = new Album(1, "All parameters without filter dates", user, null, null,
                PrivacyLevel.PRIVATE);
        
        List<Album> albums = new ArrayList<Album>();
        albums.add(album);
        
        return albums;
    }

    @Override
    public void create(Album album) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Album album) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void rename(Album album, String newName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changePrivacyLevel(Album album, String privacyLevel) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Album getById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Album getAlbum(String name, int userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Album> getAlbums(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Album> getAlbumsSharedWith(Integer id,
            String ownerEmail) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Album> getPublicAlbums() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Album getSharedAlbum(String albumName, int userSharedToId,
            String userSharedEmail) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Album> getAlbumsByTag(int userId, String tag) {
        // TODO Auto-generated method stub
        return null;
    }
}
