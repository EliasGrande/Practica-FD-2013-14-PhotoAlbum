package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;

import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.FileShareInformation;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.spring.FileShareInformationService;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

public class FileShareInformationServiceMock {

    public static FileShareInformationService mock = new FileShareInformationService() {

        public void create(FileShareInformation shareInformation) {
            shareInformation.getFile().getShareInformation()
                    .add(shareInformation);

        }

        public void delete(FileShareInformation shareInformation) {
        }

        public ArrayList<FileShareInformation> getFileShares(
                int fileId) {
            ArrayList<FileShareInformation> list = new ArrayList<FileShareInformation>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            File file = new File(1, "1", new byte[1], new byte[1],
                    album);
            File file2 = new File(2, "1", new byte[1], new byte[1],
                    album);
            File file3 = new File(3, "1", new byte[1], new byte[1],
                    album);
            FileShareInformation fileShareInformation = new FileShareInformation(1, file, user);
            FileShareInformation fileShareInformation2 = new FileShareInformation(2, file2, user);
            FileShareInformation fileShareInformation3 = new FileShareInformation(3, file3, user);

            list.add(fileShareInformation);
            list.add(fileShareInformation2);
            list.add(fileShareInformation3);
            return list;
        }

    };
}
