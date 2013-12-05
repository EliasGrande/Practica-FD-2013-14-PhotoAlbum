package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.AlbumShareInformation;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.AlbumShareInformationService;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

public class AlbumShareInformationServiceMock {

    public static AlbumShareInformationService mock = new AlbumShareInformationService() {
        public void create(AlbumShareInformation shareInformation) {
            shareInformation.getUser().getShareInformation()
                    .add(shareInformation);
            shareInformation.getAlbum().getShareInformation()
                    .add(shareInformation);
        }

        public void delete(AlbumShareInformation shareInformation) {
        }

        public AlbumShareInformation getShare(String albumName,
                int userSharedToId, String userSharedEmail) {
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            user.getAlbums().add(album);
            AlbumShareInformation albumShareInformation = new AlbumShareInformation(1, album, user);
            return albumShareInformation;
        }

        public ArrayList<AlbumShareInformation> getAlbumShares(
                int albumId) {
            ArrayList<AlbumShareInformation> list = new ArrayList<AlbumShareInformation>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            user.getAlbums().add(album);
            AlbumShareInformation albumShareInformation = new AlbumShareInformation(1, album, user);
            list.add(albumShareInformation);
            return list;
        }
    };
}
