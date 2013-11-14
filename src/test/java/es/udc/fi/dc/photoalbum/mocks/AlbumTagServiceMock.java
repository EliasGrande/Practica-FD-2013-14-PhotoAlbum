package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumTagService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

public class AlbumTagServiceMock {

    public static AlbumTagService mock = new AlbumTagService() {

        public void create(AlbumTag albumTag) {
        }

        public void delete(AlbumTag albumTag) {
        }

        public AlbumTag getTag(int albumId, String tag) {
            return new AlbumTag(new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                    null, PrivacyLevel.PRIVATE), "tag");
        }

        public ArrayList<AlbumTag> getTags(int albumId) {
            ArrayList<AlbumTag> list = new ArrayList<AlbumTag>();
            list.add(new AlbumTag(new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
                            null, PrivacyLevel.PRIVATE), "tag"));
            return list;
        }

    };
}
