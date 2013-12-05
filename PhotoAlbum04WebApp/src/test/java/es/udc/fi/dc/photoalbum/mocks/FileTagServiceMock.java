package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.FileTag;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.FileTagService;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

public class FileTagServiceMock {

    public static FileTagService mock = new FileTagService() {

        public void create(FileTag fileTag) {
        }

        public void delete(FileTag fileTag) {
        }

        public FileTag getTag(int fileId, String tag) {
            return new FileTag(new File(1, "1", new byte[1],
                    new byte[1], new Album(1, ALBUM_NAME_EXIST,
                            new User(1, USER_EMAIL_EXIST,
                                    USER_PASS_YES), null, null,
                            PrivacyLevel.PRIVATE)), "tag");
        }

        public ArrayList<FileTag> getTags(int fileId) {
            ArrayList<FileTag> list = new ArrayList<FileTag>();
            list.add(new FileTag(new File(1, "1", new byte[1],
                    new byte[1], new Album(1, ALBUM_NAME_EXIST,
                            new User(1, USER_EMAIL_EXIST,
                                    USER_PASS_YES), null, null,
                            PrivacyLevel.PRIVATE)), "tag"));
            return list;
        }

    };
}
