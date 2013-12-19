package es.udc.fi.dc.photoalbum.test.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.AlbumShareInformation;
import es.udc.fi.dc.photoalbum.model.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.model.hibernate.Comment;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.hibernate.FileShareInformation;
import es.udc.fi.dc.photoalbum.model.hibernate.FileTag;
import es.udc.fi.dc.photoalbum.model.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.model.hibernate.User;
import es.udc.fi.dc.photoalbum.model.hibernate.Voted;
import es.udc.fi.dc.photoalbum.model.spring.AlbumService;
import es.udc.fi.dc.photoalbum.model.spring.AlbumShareInformationService;
import es.udc.fi.dc.photoalbum.model.spring.AlbumTagService;
import es.udc.fi.dc.photoalbum.model.spring.CommentService;
import es.udc.fi.dc.photoalbum.model.spring.FileService;
import es.udc.fi.dc.photoalbum.model.spring.FileShareInformationService;
import es.udc.fi.dc.photoalbum.model.spring.FileTagService;
import es.udc.fi.dc.photoalbum.model.spring.LikeAndDislikeService;
import es.udc.fi.dc.photoalbum.model.spring.UserService;
import es.udc.fi.dc.photoalbum.model.spring.VotedService;
import es.udc.fi.dc.photoalbum.util.utils.MD5;
import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class DaoTest {

    @Autowired
    private UserService userService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private FileService fileService;
    @Autowired
    private AlbumShareInformationService albumShareInformationService;
    @Autowired
    private FileShareInformationService fileShareInformationService;
    @Autowired
    private AlbumTagService albumTagService;
    @Autowired
    private FileTagService fileTagService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeAndDislikeService likeAndDislikeService;
    @Autowired
    private VotedService votedService;

    @BeforeClass
    public static void setUpJndi() throws NamingException {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:h2:~/H2/PhotoAlbum;INIT=RUNSCRIPT FROM 'classpath:db_create.sql'");
        // p.setUrl("jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:db_create.sql';DB_CLOSE_DELAY=-1");
        p.setDriverClassName("org.h2.Driver");
        p.setUsername("herramientas");
        p.setPassword("desarrollo2013");
        DataSource ds = new DataSource();
        ds.setPoolProperties(p);
        SimpleNamingContextBuilder builder = SimpleNamingContextBuilder
                .emptyActivatedContextBuilder();
        builder.bind("java:comp/env/jdbc/PhotoAlbum", ds);
    }

    @Test
    // Create, update, delete and simple searches [UserService]
    public void testCreateAndUpdateUser() {
        User user = new User(null, "123", MD5.getHash("pass"));
        User user2 = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        assertNotNull(this.userService.getUser(user.getEmail(),
                "pass"));
        assertNotNull(this.userService.getUser(user));
        user.setPassword(MD5.getHash("pass1"));
        this.userService.update(user);
        assertNotNull(this.userService.getUser(user.getEmail(),
                "pass1"));
        User userAux = this.userService.getUser(user);
        assertEquals(userAux, user);
        User userAux2 = this.userService.getById(user.getId());
        assertEquals(user, userAux2);
        this.userService.delete(user);
        assertNull(this.userService.getUser(user.getEmail(), "pass1"));
        assertNull(this.userService.getUser(user2));
    }

    @Test
    // Create, update, delete and simple searches [AlbumService]
    public void testCreateAndUpdateAlbum() {

        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album();
        album.setName("111");
        album.setUser(user);
        this.albumService.create(album);
        album.setName("111b");
        this.albumService.create(album);
        Album albumAux = this.albumService.getAlbum(album.getName(),
                album.getUser().getId());
        assertEquals(album, albumAux);
        this.albumService.delete(album);
        Album albumAux2 = this.albumService.getById(album.getId());
        assertNull(albumAux2);
        Album alumAux3 = this.albumService.getAlbum("Prueba null",
                album.getUser().getId());
        assertNull(alumAux3);
    }

    @Test
    // Rename album [AlbumService]
    public void testRenameAlbum() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album();
        album.setName("FirstAlbum");
        album.setUser(user);

        this.albumService.create(album);
        this.albumService.rename(album, "FirstAlbumChanged");
        Album albumAux = this.albumService.getAlbum(
                "FirstAlbumChanged", user.getId());
        assertNotNull("Crash rename album", albumAux);

    }

    @Test
    // Change privacy [AlbumService]
    public void testChangePrivacyLevelAlbum3() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        this.albumService.changePrivacyLevel(album,
                PrivacyLevel.PUBLIC);
        Album albumAux = this.albumService.getAlbum("FirstAlbum",
                user.getId());

        assertEquals(album, albumAux);

    }

    @Test
    // Check if the default privacy is the correct [AlbumService]
    public void testDefaultPrivacyForAlbum() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        Album albumAux = this.albumService.getAlbum("FirstAlbum",
                user.getId());

        assertEquals(albumAux.getPrivacyLevel(), PrivacyLevel.PRIVATE);
    }

    @Test
    // Create, update, delete and simple searches
    // [AlbumShareInformationService]
    public void testCreateAndUpdateAlbumShareInformation() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User userSharedTo = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(userSharedTo);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        AlbumShareInformation albumShareInformation = new AlbumShareInformation();
        albumShareInformation.setAlbum(album);
        albumShareInformation.setUser(userSharedTo);
        this.albumShareInformationService
                .create(albumShareInformation);

        AlbumShareInformation albumShareInformationAux = this.albumShareInformationService
                .getShare(album.getName(), userSharedTo.getId(),
                        user.getEmail());

        assertEquals(albumShareInformation, albumShareInformationAux);

        this.albumShareInformationService
                .delete(albumShareInformation);
        AlbumShareInformation albumShareInformationAux2 = this.albumShareInformationService
                .getShare(album.getName(), userSharedTo.getId(),
                        user.getEmail());

        assertNull(albumShareInformationAux2);
    }

    @Test
    // Create, update, delete and simple searches [FileService]
    public void testCreateAndUpdateFile() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File auxFile = this.fileService.getById(file.getId());

        assertEquals(file, auxFile);

        this.fileService.delete(file);

        assertNull(this.fileService.getById(file.getId()));

    }

    @Test
    // Test the service ChangePrivacyLevel [FileService]
    public void testChangePrivacyLevel() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);

        this.fileService.changePrivacyLevel(file,
                PrivacyLevel.PRIVATE);

        File auxFile = this.fileService.getById(file.getId());

        assertEquals(auxFile.getPrivacyLevel(), PrivacyLevel.PRIVATE);

    }

    @Test
    // Test the service change album [FileService]
    public void testChangeAlbum() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        Album album2 = new Album(null, "SecondAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        this.albumService.create(album2);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);

        this.fileService.changeAlbum(file, album2);

        File auxFile = this.fileService.getById(file.getId());

        assertEquals(auxFile.getAlbum(), album2);
    }

    @Test
    // Create, delete and simple searches
    // [FileShareInformationService]
    public void testCreateAndUpdateFileShareInformation() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);
        User user3 = new User(null, "789", MD5.getHash("pass"));
        this.userService.create(user3);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);
        FileShareInformation fsi = new FileShareInformation(null,
                file, user2);
        this.fileShareInformationService.create(fsi);
        FileShareInformation fsi2 = new FileShareInformation();
        fsi2.setFile(file);
        fsi2.setUser(user3);
        this.fileShareInformationService.create(fsi2);

        List<FileShareInformation> fsil = this.fileShareInformationService
                .getFileShares(file.getId());

        assertEquals(fsi, fsil.get(0));
        assertEquals(fsi2, fsil.get(1));

        this.fileShareInformationService.delete(fsi2);
        fsil = this.fileShareInformationService.getFileShares(file
                .getId());
        assertEquals(fsil.size(), 1);

    }

    @Test
    // Test getAlbumShares [AlbumShareInformationService]
    public void testGetAlbumShares() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);
        User user3 = new User(null, "789", MD5.getHash("pass"));
        this.userService.create(user3);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        Album album2 = new Album(null, "SecondAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album2);
        Album album3 = new Album(null, "ThirdAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album3);

        AlbumShareInformation albumShareInformation = new AlbumShareInformation(
                null, album, user2);
        AlbumShareInformation albumShareInformation2 = new AlbumShareInformation(
                null, album, user3);
        AlbumShareInformation albumShareInformation3 = new AlbumShareInformation(
                null, album2, user2);
        this.albumShareInformationService
                .create(albumShareInformation);
        this.albumShareInformationService
                .create(albumShareInformation2);
        this.albumShareInformationService
                .create(albumShareInformation3);

        List<AlbumShareInformation> list1 = this.albumShareInformationService
                .getAlbumShares(album.getId());
        assertEquals(albumShareInformation, list1.get(0));
        assertEquals(albumShareInformation2, list1.get(1));

        List<AlbumShareInformation> list2 = this.albumShareInformationService
                .getAlbumShares(album2.getId());
        assertEquals(albumShareInformation3, list2.get(0));

        List<AlbumShareInformation> list3 = this.albumShareInformationService
                .getAlbumShares(album3.getId());
        assertEquals(list3.size(), 0);

    }

    @Test
    // Test getUserSharingWith [UserService]
    public void testGetUserSharingWith() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);
        User user3 = new User(null, "789", MD5.getHash("pass"));
        this.userService.create(user3);
        User user4 = new User(null, "1789", MD5.getHash("pass"));
        this.userService.create(user4);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        Album album2 = new Album(null, "SecondAlbum", user2, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album2);
        Album album3 = new Album(null, "ThirdAlbum", user3, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album3);

        AlbumShareInformation albumShareInformation = new AlbumShareInformation(
                null, album, user2);
        AlbumShareInformation albumShareInformation3 = new AlbumShareInformation(
                null, album2, user3);
        this.albumShareInformationService
                .create(albumShareInformation);
        this.albumShareInformationService
                .create(albumShareInformation3);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album3);
        this.fileService.create(file2);
        FileShareInformation fsi = new FileShareInformation(null,
                file, user3);
        this.fileShareInformationService.create(fsi);
        FileShareInformation fsi2 = new FileShareInformation(null,
                file, user);
        this.fileShareInformationService.create(fsi2);

        List<User> list = this.userService.getUsersSharingWith(user
                .getId());
        List<User> list2 = this.userService.getUsersSharingWith(user2
                .getId());
        List<User> list3 = this.userService.getUsersSharingWith(user3
                .getId());
        List<User> list4 = this.userService.getUsersSharingWith(user4
                .getId());

        assertEquals("Share with user", list.size(), 1);
        assertEquals("Share with user2", list2.size(), 1);
        assertEquals("Share with user3", list3.size(), 2);
        assertEquals("Share with user4", list4.size(), 0);

    }

    @Test
    // Test getCountAlbumFiles [FileService]
    public void getCountAlbumFiles() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        Album album2 = new Album(null, "SecondAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album2);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);

        Long l1 = this.fileService.getCountAlbumFiles(album.getId());
        Long l2 = this.fileService.getCountAlbumFiles(album2.getId());

        assertEquals(l1, new Long(2));
        assertEquals(l2, new Long(0));
    }

    @Test
    // Test getAlbums [AlbumService]
    public void testGetAlbums() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);
        User user3 = new User(null, "789", MD5.getHash("pass"));
        this.userService.create(user3);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        Album album2 = new Album(null, "SecondAlbum", user2, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album2);
        Album album3 = new Album(null, "ThirdAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album3);

        List<Album> list = this.albumService.getAlbums(user.getId());
        List<Album> list2 = this.albumService
                .getAlbums(user2.getId());
        List<Album> list3 = this.albumService
                .getAlbums(user3.getId());

        assertEquals(list.size(), 2);
        assertEquals(list2.size(), 1);
        assertTrue(list3.isEmpty());
    }

    @Test
    // Test getAlbumSharedWith [AlbumService]
    public void testGetAlbumSharedWith() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        Album album2 = new Album(null, "SecondAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album2);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album2);
        this.fileService.create(file2);

        AlbumShareInformation asi = new AlbumShareInformation(null,
                album, user2);
        this.albumShareInformationService.create(asi);
        AlbumShareInformation asi2 = new AlbumShareInformation(null,
                album2, user2);
        this.albumShareInformationService.create(asi2);

        List<Album> list = this.albumService.getAlbumsSharedWith(
                user2.getId(), user.getEmail());
        List<Album> list2 = this.albumService.getAlbumsSharedWith(
                user.getId(), user2.getEmail());

        assertEquals(list.size(), 2);
        assertTrue(list2.isEmpty());

    }

    @Test
    // Test getPublicAlbums [AlbumService]
    public void testGetPublicAlbums() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        Album album2 = new Album(null, "SecondAlbum", user, null,
                null, PrivacyLevel.PUBLIC);
        this.albumService.create(album2);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album2);
        this.fileService.create(file2);

        List<Album> list = this.albumService.getPublicAlbums();

        assertEquals(list.size(), 1);

        this.albumService.changePrivacyLevel(album2,
                PrivacyLevel.PRIVATE);

        List<Album> list2 = this.albumService.getPublicAlbums();

        assertTrue(list2.isEmpty());

        this.fileService
                .changePrivacyLevel(file, PrivacyLevel.PUBLIC);

        List<Album> list3 = this.albumService.getPublicAlbums();

        assertEquals(list3.size(), 1);
    }

    @Test
    // Test getSharedAlbum [AlbumService]
    public void testGetSharedAlbum() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        Album album2 = new Album(null, "SecondAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album2);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album2);
        this.fileService.create(file2);

        AlbumShareInformation asi = new AlbumShareInformation(null,
                album, user2);
        this.albumShareInformationService.create(asi);
        AlbumShareInformation asi2 = new AlbumShareInformation(null,
                album2, user2);
        this.albumShareInformationService.create(asi2);

        Album alSha = this.albumService.getSharedAlbum(
                album.getName(), user2.getId(), user.getEmail());

        assertEquals(alSha, album);

        this.albumShareInformationService.delete(asi);

        Album alSha2 = this.albumService.getSharedAlbum(
                album.getName(), user2.getId(), user.getEmail());

        assertNull(alSha2);
    }

    @Test
    // Test getFileOwn [FileService]
    public void testGetFileOwn() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        Album album2 = new Album(null, "SecondAlbum", user2, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album2);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album2);
        this.fileService.create(file2);

        File fileOwn = this.fileService.getFileOwn(file.getId(),
                album.getName(), user.getId());

        assertEquals(fileOwn, file);

        File fileOwn2 = this.fileService.getFileOwn(file.getId(),
                album.getName(), user2.getId());

        assertNull(fileOwn2);
    }

    @Test
    // Test getFileShared [FileService]
    public void testGetFileshared() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);
        User user3 = new User(null, "789", MD5.getHash("pass"));
        this.userService.create(user3);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        Album album2 = new Album(null, "SecondAlbum", user2, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album2);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);
        File file3 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album2);
        this.fileService.create(file3);
        File file4 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album2);
        this.fileService.create(file4);

        this.fileService.changePrivacyLevel(file2,
                PrivacyLevel.PRIVATE);
        this.fileService.changePrivacyLevel(file3,
                PrivacyLevel.PUBLIC);

        FileShareInformation fsi = new FileShareInformation(null,
                file2, user2);
        this.fileShareInformationService.create(fsi);
        AlbumShareInformation asi = new AlbumShareInformation(null,
                album2, user);
        this.albumShareInformationService.create(asi);

        File fileShared = this.fileService.getFileShared(
                file2.getId(), album.getName(), user2.getId());
        File fileShared2 = this.fileService.getFileShared(
                file4.getId(), album.getName(), user.getId());
        File fileShared3 = this.fileService.getFileShared(
                file.getId(), album.getName(), user3.getId());
        File fileShared4 = this.fileService.getFileShared(
                file3.getId(), null, user3.getId());

        assertEquals("File share", fileShared, file2);
        assertEquals("File share by album", fileShared2, file4);
        assertNull("File not share", fileShared3);
        assertEquals("File public", fileShared4, file3);
    }

    @Test
    // Test getFilePublic [FileService]
    public void testGetFilePublic() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);

        this.fileService.changePrivacyLevel(file2,
                PrivacyLevel.PUBLIC);

        File filePublic = this.fileService.getFilePublic(
                file.getId(), album.getName(), user.getId());
        File filePublic2 = this.fileService.getFilePublic(
                file.getId(), album.getName(), user2.getId());
        File filePublic3 = this.fileService.getFilePublic(
                file2.getId(), album.getName(), user2.getId());

        assertEquals("Own file", filePublic, file);
        assertNull("File not public, not own", filePublic2);
        assertEquals("File public, not own", file2, filePublic3);

    }

    @Test
    // Test getAlbumFilesOwn [FileService]
    public void testGetAlbumFilesOwn() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);
        File file3 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file3);

        List<File> filesOwn = this.fileService.getAlbumFilesOwn(album
                .getId());

        assertEquals("Files own", filesOwn.size(), 3);
    }

    @Test
    // Test getAlbumFilesOwnPaging() [FileService]
    public void testGetAlbumFilesOwnPaging() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);
        File file3 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file3);

        List<File> filesOwn = this.fileService
                .getAlbumFilesOwnPaging(album.getId(), 0, 2);
        List<File> filesOwn2 = this.fileService
                .getAlbumFilesOwnPaging(album.getId(), 2, 1);

        assertEquals("Files own", filesOwn.size(), 2);
        assertEquals("Files own", filesOwn2.size(), 1);
    }

    @Test
    // Test getAlbumFilesShared [FileService]
    public void testGetAlbumFilesShared() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        Album album2 = new Album(null, "SecondAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album2);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);
        File file3 = new File(null, "Prueba3", new byte[] { 1 },
                new byte[] { 2 }, album2);
        this.fileService.create(file3);
        File file4 = new File(null, "Prueba4", new byte[] { 1 },
                new byte[] { 2 }, album2);
        this.fileService.create(file4);

        AlbumShareInformation asi = new AlbumShareInformation(null,
                album, user2);
        this.albumShareInformationService.create(asi);
        FileShareInformation fsi = new FileShareInformation(null,
                file3, user2);
        this.fileShareInformationService.create(fsi);

        List<File> filesShared = this.fileService
                .getAlbumFilesShared(album.getId(), user2.getId());
        List<File> filesShared2 = this.fileService
                .getAlbumFilesShared(album2.getId(), user2.getId());

        assertEquals("Shared album", filesShared.size(), 2);
        assertEquals("Shared file", filesShared2.size(), 1);

        this.fileService.changePrivacyLevel(file,
                PrivacyLevel.INHERIT_FROM_ALBUM);

    }

    @Test
    // Test getAlbumFilesSharedPaging [FileService]
    public void testGetAlbumFilesSharedPaging() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        Album album2 = new Album(null, "SecondAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album2);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);
        File file3 = new File(null, "Prueba3", new byte[] { 1 },
                new byte[] { 2 }, album2);
        this.fileService.create(file3);
        File file4 = new File(null, "Prueba4", new byte[] { 1 },
                new byte[] { 2 }, album2);
        this.fileService.create(file4);

        AlbumShareInformation asi = new AlbumShareInformation(null,
                album, user2);
        this.albumShareInformationService.create(asi);
        FileShareInformation fsi = new FileShareInformation(null,
                file3, user2);
        this.fileShareInformationService.create(fsi);

        List<File> filesShared = this.fileService
                .getAlbumFilesSharedPaging(album.getId(),
                        user2.getId(), 0, 1);
        List<File> filesShared2 = this.fileService
                .getAlbumFilesSharedPaging(album.getId(),
                        user2.getId(), 1, 1);

        assertEquals(filesShared.size(), 1);
        assertEquals(filesShared2.size(), 1);
    }

    @Test
    // Test getAlbumFilesPublic [FileService]
    public void getAlbumFilesPublic() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);
        File file3 = new File(null, "Prueba3", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file3);

        this.fileService.changePrivacyLevel(file3,
                PrivacyLevel.PUBLIC);

        List<File> filesPublic = this.fileService
                .getAlbumFilesPublic(album.getId(), user.getId());
        List<File> filesPublic2 = this.fileService
                .getAlbumFilesPublic(album.getId(), user2.getId());

        assertEquals("All the files (own album)", filesPublic.size(),
                3);
        assertEquals("Only the files public", filesPublic2.size(), 1);

        this.albumService.changePrivacyLevel(album,
                PrivacyLevel.PUBLIC);

        List<File> filesPublic3 = this.fileService
                .getAlbumFilesPublic(album.getId(), user2.getId());
        assertEquals("All the files", filesPublic3.size(), 3);

    }

    @Test
    // Test getAlbumFilesPublicPaging [FileService]
    public void getAlbumFilesPublicPaging() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);

        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);
        File file3 = new File(null, "Prueba3", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file3);
        this.albumService.changePrivacyLevel(album,
                PrivacyLevel.PUBLIC);

        List<File> filesPublic = this.fileService
                .getAlbumFilesPublicPaging(album.getId(),
                        user.getId(), 0, 2);
        List<File> filesPublic2 = this.fileService
                .getAlbumFilesPublicPaging(album.getId(),
                        user2.getId(), 2, 1);

        assertEquals("All the files (own album)", filesPublic.size(),
                2);
        assertEquals("Only the files public", filesPublic2.size(), 1);

    }

    @Test
    // Test CreateAndDeleteAlbumTag [AlbumTagService]
    public void testCreateAndDeleteAlbumTag() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        AlbumTag tag = new AlbumTag();
        tag.setAlbum(album);
        tag.setTag("tag");
        this.albumTagService.create(tag);
        this.albumTagService.create(tag);
        AlbumTag albumTagAux = this.albumTagService.getTag(
                album.getId(), tag.getTag());
        assertNotNull(albumTagAux);
        this.albumTagService.delete(tag);
        albumTagAux = this.albumTagService.getTag(album.getId(),
                tag.getTag());
        assertNull(albumTagAux);
    }

    @Test
    // Test GetAlbumTags [AlbumTagService]
    public void testGetAlbumTags() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        AlbumTag tag1 = new AlbumTag(album, "tag1");
        this.albumTagService.create(tag1);
        AlbumTag tag2 = new AlbumTag(album, "tag2");
        this.albumTagService.create(tag2);
        List<AlbumTag> tags = albumTagService.getTags(album.getId());
        assertEquals(tag1, tags.get(0));
        assertEquals(tag2, tags.get(1));
        assertEquals(tags.size(), 2);

    }

    @Test
    // Test CreateAndDeleteFileTag [FileTagService]
    public void testCreateAndDeleteFileTag() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        FileTag tag = new FileTag();
        tag.setFile(file);
        tag.setTag("tag");
        this.fileTagService.create(tag);
        FileTag fileTagAux = this.fileTagService.getTag(file.getId(),
                tag.getTag());
        assertNotNull(fileTagAux);
        this.fileTagService.delete(tag);
        fileTagAux = this.fileTagService.getTag(file.getId(),
                tag.getTag());
        assertNull(fileTagAux);

    }

    @Test
    // Test GetFileTags [FileTagService]
    public void testGetFileTags() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        FileTag tag1 = new FileTag(file, "tag1");
        this.fileTagService.create(tag1);
        FileTag tag2 = new FileTag(file, "tag2");
        this.fileTagService.create(tag2);
        FileTag tag3 = new FileTag(file, "tag3");
        this.fileTagService.create(tag3);
        List<FileTag> tags = fileTagService.getTags(file.getId());
        assertEquals(tag1, tags.get(0));
        assertEquals(tag2, tags.get(1));
        assertEquals(tag3, tags.get(2));
        assertEquals(tags.size(), 3);
    }

    @Test
    // Test GetAlbumsByTag [AlbumService]
    public void TestGetAlbumsByTag() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album1 = new Album(null, "FirstAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album1);
        Album album2 = new Album(null, "SecondAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album2);
        Album album3 = new Album(null, "ThirdAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album3);
        AlbumTag tag1 = new AlbumTag(album1, "tag");
        this.albumTagService.create(tag1);
        AlbumTag tag2 = new AlbumTag(album2, "tag");
        this.albumTagService.create(tag2);
        AlbumTag tag3 = new AlbumTag(album3, "tag");
        this.albumTagService.create(tag3);
        List<Album> albums = this.albumService.getAlbumsByTag(
                user.getId(), "tag");
        assertEquals(albums.size(), 3);
        assertEquals(albums.get(0), album1);
        assertEquals(albums.get(1), album2);
        assertEquals(albums.get(2), album3);

    }

    @Test
    // Test GetFilesByTag [FileService]
    public void TestGetFilesByTag() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        File file1 = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file1);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);
        File file3 = new File(null, "Prueba3", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file3);
        FileTag tag1 = new FileTag(file1, "tag");
        this.fileTagService.create(tag1);
        FileTag tag2 = new FileTag(file2, "tag");
        this.fileTagService.create(tag2);
        FileTag tag3 = new FileTag(file3, "tag");
        this.fileTagService.create(tag3);
        List<File> files = this.fileService.getFilesByTag(
                user.getId(), "tag");
        assertEquals(files.size(), 3);
        assertEquals(files.get(0), file1);
        assertEquals(files.get(1), file2);
        assertEquals(files.get(2), file3);
    }

    @Test
    // Test GetFilesByTagPaging [FileService]
    public void TestGetFilesByTagPaging() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        File file1 = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file1);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);
        File file3 = new File(null, "Prueba3", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file3);
        FileTag tag1 = new FileTag(file1, "tag");
        this.fileTagService.create(tag1);
        FileTag tag2 = new FileTag(file2, "tag");
        this.fileTagService.create(tag2);
        FileTag tag3 = new FileTag(file3, "tag");
        this.fileTagService.create(tag3);

        List<File> files = this.fileService.getFilesByTagPaging(
                user.getId(), "tag", 1, 2);
        List<File> files2 = this.fileService.getFilesByTagPaging(
                user.getId(), "tag", 0, 3);

        assertEquals(files.size(), 2);
        assertEquals(files.get(0), file2);
        assertEquals(files.get(1), file3);

        assertEquals(files2.size(), 3);
        assertEquals(files2.get(0), file1);
        assertEquals(files2.get(1), file2);
        assertEquals(files2.get(2), file3);
    }

    @Test
    // Test CreateAndDeleteAlbumComment [CommentService]
    public void testCreateAndDeleteAlbumComment() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        LikeAndDislike likeAndDislike = new LikeAndDislike();
        Comment comment = new Comment(likeAndDislike, user,
                "Prueba comment", album, null);
        this.commentService.create(user, album, comment.getText());
        assertNotNull(this.commentService.getComments(album));
        this.commentService.delete(comment);
    }

    @Test
    // Test CreateAndDeleteFileComment [CommentService]
    public void testCreateAndDeleteFileComment() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        LikeAndDislike likeAndDislike = new LikeAndDislike();
        Comment comment = new Comment(likeAndDislike, user,
                "Prueba comment", null, file);
        this.commentService.create(user, file, comment.getText());
        assertNotNull(this.commentService.getComments(file));
    }

    @Test
    // Test GetAlbumComments [CommentService]
    public void testGetAlbumComments() throws InterruptedException {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        LikeAndDislike likeAndDislike = new LikeAndDislike();
        Comment comment1 = new Comment(likeAndDislike, user,
                "Prueba comment 1", album, null);
        this.commentService.create(user, album, comment1.getText());
        Comment comment2 = new Comment(likeAndDislike, user,
                "Prueba comment 2", album, null);
        Thread.sleep(10);
        this.commentService.create(user, album, comment2.getText());
        Comment comment3 = new Comment(likeAndDislike, user,
                "Prueba comment 3", album, null);
        Thread.sleep(10);
        this.commentService.create(user, album, comment3.getText());
        assertEquals(this.commentService.getComments(album).size(), 3);
        assertEquals(this.commentService.getComments(album).get(0)
                .getText(), comment3.getText());
        assertEquals(this.commentService.getComments(album).get(1)
                .getText(), comment2.getText());
        assertEquals(this.commentService.getComments(album).get(2)
                .getText(), comment1.getText());
    }

    @Test
    // Test GetFileComments [CommentService]
    public void testGetFileComments() throws InterruptedException {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        LikeAndDislike likeAndDislike = new LikeAndDislike();
        Comment comment1 = new Comment(likeAndDislike, user,
                "Prueba comment 1", null, file);
        this.commentService.create(user, file, comment1.getText());
        Comment comment2 = new Comment(likeAndDislike, user,
                "Prueba comment 2", null, file);
        Thread.sleep(10);
        this.commentService.create(user, file, comment2.getText());
        Comment comment3 = new Comment(likeAndDislike, user,
                "Prueba comment 3", null, file);
        Thread.sleep(10);
        this.commentService.create(user, file, comment3.getText());
        assertEquals(this.commentService.getComments(file).size(), 3);
        assertEquals(this.commentService.getComments(file).get(0)
                .getText(), comment3.getText());
        assertEquals(this.commentService.getComments(file).get(1)
                .getText(), comment2.getText());
        assertEquals(this.commentService.getComments(file).get(2)
                .getText(), comment1.getText());
    }

    @Test
    // Test GetAlbumCommentsPaging [CommentService]
    public void testGetAlbumCommentsPaging()
            throws InterruptedException {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        LikeAndDislike likeAndDislike = new LikeAndDislike();
        Comment comment1 = new Comment(likeAndDislike, user,
                "Prueba comment 1", null, null);
        comment1.setAlbum(album);
        this.commentService.create(user, album, comment1.getText());
        Comment comment2 = new Comment(likeAndDislike, user,
                "Prueba comment 2", album, null);
        Thread.sleep(10);
        this.commentService.create(user, album, comment2.getText());
        Comment comment3 = new Comment(likeAndDislike, user,
                "Prueba comment 3", album, null);
        Thread.sleep(10);
        this.commentService.create(user, album, comment3.getText());
        assertEquals(
                this.commentService.getCommentsPaging(album, 0, 2)
                        .size(), 2);
        assertEquals(
                this.commentService.getCommentsPaging(album, 2, 1)
                        .size(), 1);
        assertEquals(
                this.commentService.getCommentsPaging(album, 2, 2)
                        .size(), 1);
        assertEquals(
                this.commentService.getCommentsPaging(album, 3, 2)
                        .size(), 0);
    }

    @Test
    // Test GetFileCommentsPaging [CommentService]
    public void testGetFileCommentsPaging()
            throws InterruptedException {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        LikeAndDislike likeAndDislike = new LikeAndDislike();
        Comment comment1 = new Comment();
        comment1.setLikeAndDislike(likeAndDislike);
        comment1.setUser(user);
        comment1.setText("Prueba comment 1");
        comment1.setFile(file);
        comment1.setDate(Calendar.getInstance());
        this.commentService.create(user, file, comment1.getText());
        Comment comment2 = new Comment(likeAndDislike, user,
                "Prueba comment 2", null, file);
        Thread.sleep(10);
        this.commentService.create(user, file, comment2.getText());
        Comment comment3 = new Comment(likeAndDislike, user,
                "Prueba comment 3", null, file);
        Thread.sleep(10);
        this.commentService.create(user, file, comment3.getText());
        assertEquals(this.commentService
                .getCommentsPaging(file, 0, 2).size(), 2);
        assertEquals(this.commentService
                .getCommentsPaging(file, 2, 1).size(), 1);
        assertEquals(this.commentService
                .getCommentsPaging(file, 2, 2).size(), 1);
        assertEquals(this.commentService
                .getCommentsPaging(file, 3, 2).size(), 0);
    }

    @Test
    // Test VoteLikeAndDislikeAlbum [LikeAndDislikeService]
    public void testVoteLikeAndDislikeAlbum() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        this.likeAndDislikeService.voteLike(
                album.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteDislike(
                album.getLikeAndDislike(), user);
        assertTrue(this.likeAndDislikeService.userHasVoted(
                album.getLikeAndDislike(), user));
    }

    @Test
    // Test VoteLikeAndDislikeFile [LikeAndDislikeService]
    public void testVoteLikeAndDislikeFile() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        this.likeAndDislikeService.voteLike(file.getLikeAndDislike(),
                user);
        this.likeAndDislikeService.voteDislike(
                file.getLikeAndDislike(), user);
        assertTrue(this.likeAndDislikeService.userHasVoted(
                file.getLikeAndDislike(), user));
    }

    @Test
    // Test VoteLikeAndDislikeAlbumComment [LikeAndDislikeService]
    public void testVoteLikeAndDislikeAlbumComment() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        Comment comment = new Comment(album.getLikeAndDislike(),
                user, "Prueba comment", album, null);
        this.commentService.create(user, album, comment.getText());
        this.likeAndDislikeService.voteLike(
                comment.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteDislike(
                comment.getLikeAndDislike(), user);
        assertTrue(this.likeAndDislikeService.userHasVoted(
                comment.getLikeAndDislike(), user));
    }

    @Test
    // Test VoteLikeAndDislikeFileComment [LikeAndDislikeService]
    public void testVoteLikeAndDislikeFileComment() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        Comment comment = new Comment(file.getLikeAndDislike(), user,
                "Prueba comment", null, file);
        this.commentService.create(user, file, comment.getText());
        this.likeAndDislikeService.voteLike(
                comment.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteDislike(
                comment.getLikeAndDislike(), user);
        assertTrue(this.likeAndDislikeService.userHasVoted(
                comment.getLikeAndDislike(), user));
    }

    @Test
    // Test UnvoteLikeAndDisLikeAlbum [LikeAndDislikeService]
    public void testUnvoteLikeAndDisLikeAlbum() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        this.likeAndDislikeService.voteLike(
                album.getLikeAndDislike(), user);
        assertTrue(this.likeAndDislikeService.userHasVoted(
                album.getLikeAndDislike(), user));
        this.likeAndDislikeService.unvote(album.getLikeAndDislike(),
                user);
        assertFalse(this.likeAndDislikeService.userHasVoted(
                album.getLikeAndDislike(), user));
    }

    @Test
    // Test UnvoteLikeAndDisLikeFile [LikeAndDislikeService]
    public void testUnvoteLikeAndDisLikeFile() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        this.likeAndDislikeService.voteLike(file.getLikeAndDislike(),
                user);
        assertTrue(this.likeAndDislikeService.userHasVoted(
                file.getLikeAndDislike(), user));
        this.likeAndDislikeService.unvote(file.getLikeAndDislike(),
                user);
        assertFalse(this.likeAndDislikeService.userHasVoted(
                file.getLikeAndDislike(), user));
    }

    @Test
    // Test UnvoteLikeAndDisLikeAlbumComment [LikeAndDislikeService]
    public void testUnvoteLikeAndDisLikeAlbumComment() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        Comment comment = new Comment(album.getLikeAndDislike(),
                user, "Prueba comment", album, null);
        this.commentService.create(user, album, comment.getText());
        this.likeAndDislikeService.voteLike(
                comment.getLikeAndDislike(), user);
        assertTrue(this.likeAndDislikeService.userHasVoted(
                comment.getLikeAndDislike(), user));
        this.likeAndDislikeService.unvote(
                comment.getLikeAndDislike(), user);
        assertFalse(this.likeAndDislikeService.userHasVoted(
                comment.getLikeAndDislike(), user));
    }

    @Test
    // Test UnvoteLikeAndDisLikeFileComment [LikeAndDislikeService]
    public void testUnvoteLikeAndDisLikeFileComment() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        Comment comment = new Comment(file.getLikeAndDislike(), user,
                "Prueba comment", null, file);
        this.commentService.create(user, file, comment.getText());
        this.likeAndDislikeService.voteLike(
                comment.getLikeAndDislike(), user);
        assertTrue(this.likeAndDislikeService.userHasVoted(
                comment.getLikeAndDislike(), user));
        this.likeAndDislikeService.unvote(
                comment.getLikeAndDislike(), user);
        assertFalse(this.likeAndDislikeService.userHasVoted(
                comment.getLikeAndDislike(), user));
    }

    @Test
    // Test GetVotedAlbum [VotedService]
    public void testGetVotedAlbum() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        this.likeAndDislikeService.voteLike(
                album.getLikeAndDislike(), user);
        assertTrue(this.likeAndDislikeService.userHasVoted(
                album.getLikeAndDislike(), user));
        assertNotNull(this.votedService.getVoted(album
                .getLikeAndDislike().getId(), user.getId()));
        this.likeAndDislikeService.unvote(album.getLikeAndDislike(),
                user);
        assertNull(this.votedService.getVoted(album
                .getLikeAndDislike().getId(), user.getId()));
        Voted voted = new Voted();
        voted.setLikeAndDislike(album.getLikeAndDislike());
        voted.setUser(user);
        voted.setUserVote("like");
    }

    @Test
    // Test GetVotedFile [VotedService]
    public void testGetVotedFile() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        this.likeAndDislikeService.voteLike(file.getLikeAndDislike(),
                user);
        assertTrue(this.likeAndDislikeService.userHasVoted(
                file.getLikeAndDislike(), user));
        assertNotNull(this.votedService.getVoted(file
                .getLikeAndDislike().getId(), user.getId()));
        this.likeAndDislikeService.unvote(file.getLikeAndDislike(),
                user);
        assertNull(this.votedService.getVoted(file
                .getLikeAndDislike().getId(), user.getId()));
    }

    @Test
    // Test GetVotedAlbumComment [VotedService]
    public void testGetVotedAlbumComment() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        Comment comment = new Comment(album.getLikeAndDislike(),
                user, "Prueba comment", album, null);
        this.commentService.create(user, album, comment.getText());
        this.likeAndDislikeService.voteLike(
                comment.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteDislike(
                comment.getLikeAndDislike(), user);
        assertTrue(this.likeAndDislikeService.userHasVoted(
                comment.getLikeAndDislike(), user));
        assertNotNull(this.votedService.getVoted(comment
                .getLikeAndDislike().getId(), user.getId()));
        this.likeAndDislikeService.unvote(
                comment.getLikeAndDislike(), user);
        assertNull(this.votedService.getVoted(comment
                .getLikeAndDislike().getId(), user.getId()));
    }

    @Test
    // Test VoteLikeAndDislikeFileComment [LikeAndDislikeService]
    public void testGetVotedFileComment() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        File file = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file);
        Comment comment = new Comment(file.getLikeAndDislike(), user,
                "Prueba comment", null, file);
        this.commentService.create(user, file, comment.getText());
        this.likeAndDislikeService.voteLike(
                comment.getLikeAndDislike(), user);
        assertTrue(this.likeAndDislikeService.userHasVoted(
                comment.getLikeAndDislike(), user));
        assertNotNull(this.votedService.getVoted(comment
                .getLikeAndDislike().getId(), user.getId()));
        this.likeAndDislikeService.unvote(
                comment.getLikeAndDislike(), user);
        assertNull(this.votedService.getVoted(comment
                .getLikeAndDislike().getId(), user.getId()));
    }

    @Test
    // Test GetVotedListAlbum [VotedService]
    public void testGetVotedListAlbum() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album1 = new Album(null, "FirstAlbum", user, null,
                null, PrivacyLevel.PUBLIC);
        this.albumService.create(album1);
        Album album2 = new Album(null, "SecondAlbum", user, null,
                null, PrivacyLevel.PUBLIC);
        this.albumService.create(album2);
        Album album3 = new Album(null, "ThirdAlbum", user, null,
                null, PrivacyLevel.PUBLIC);
        this.albumService.create(album3);
        this.likeAndDislikeService.voteLike(
                album1.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteLike(
                album2.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteLike(
                album3.getLikeAndDislike(), user);
        ArrayList<Integer> likeAndDislikeIdList = new ArrayList<Integer>();
        likeAndDislikeIdList.add(album1.getLikeAndDislike().getId());
        likeAndDislikeIdList.add(album2.getLikeAndDislike().getId());
        likeAndDislikeIdList.add(album3.getLikeAndDislike().getId());
        assertEquals(
                this.votedService.getVoted(likeAndDislikeIdList,
                        user.getId()).size(), 3);
        ArrayList<Integer> likeAndDislikeIdList2 = new ArrayList<Integer>();
        List<Voted> list = this.votedService.getVoted(
                likeAndDislikeIdList2, user.getId());
        assertEquals(list.size(), 0);
    }

    @Test
    // Test GetVotedListFile [VotedService]
    public void testGetVotedListFile() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PRIVATE);
        this.albumService.create(album);
        File file1 = new File(null, "Prueba1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file1);
        File file2 = new File(null, "Prueba2", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file2);
        File file3 = new File(null, "Prueba3", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file3);
        this.likeAndDislikeService.voteLike(
                file1.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteLike(
                file2.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteLike(
                file3.getLikeAndDislike(), user);
        ArrayList<Integer> likeAndDislikeIdList = new ArrayList<Integer>();
        likeAndDislikeIdList.add(file1.getLikeAndDislike().getId());
        likeAndDislikeIdList.add(file2.getLikeAndDislike().getId());
        likeAndDislikeIdList.add(file3.getLikeAndDislike().getId());
        assertEquals(
                this.votedService.getVoted(likeAndDislikeIdList,
                        user.getId()).size(), 3);
    }

    @Test
    // Test GetVotedListAlbumComment [VotedService]
    public void testGetVotedListAlbumComment() {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        Comment comment1 = new Comment(album.getLikeAndDislike(),
                user, "Prueba comment 1", album, null);
        this.commentService.create(user, album, comment1.getText());
        Comment comment2 = new Comment(album.getLikeAndDislike(),
                user, "Prueba comment 2", album, null);
        this.commentService.create(user, album, comment2.getText());
        Comment comment3 = new Comment(album.getLikeAndDislike(),
                user, "Prueba comment 3", album, null);
        this.commentService.create(user, album, comment3.getText());
        this.likeAndDislikeService.voteLike(
                comment1.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteLike(
                comment2.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteLike(
                comment3.getLikeAndDislike(), user);
        ArrayList<Integer> likeAndDislikeIdList = new ArrayList<Integer>();
        likeAndDislikeIdList
                .add(comment1.getLikeAndDislike().getId());
        likeAndDislikeIdList
                .add(comment2.getLikeAndDislike().getId());
        likeAndDislikeIdList
                .add(comment3.getLikeAndDislike().getId());
        assertEquals(
                this.votedService.getVoted(likeAndDislikeIdList,
                        user.getId()).size(), 1);
    }

    @Test
    public void testGetFiles() throws InterruptedException {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);
        User user3 = new User(null, "789", MD5.getHash("pass"));
        this.userService.create(user3);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        Thread.sleep(10);
        Album album2 = new Album(null, "SecondAlbum", user, null,
                null, PrivacyLevel.PUBLIC);
        this.albumService.create(album2);
        Thread.sleep(10);
        Album album3 = new Album(null, "ThirdAlbum", user, null,
                null, PrivacyLevel.PUBLIC);
        this.albumService.create(album3);
        Thread.sleep(10);
        Album album4 = new Album(null, "FourthAlbum", user, null,
                null, PrivacyLevel.PUBLIC);
        this.albumService.create(album4);
        Thread.sleep(10);
        Album album5 = new Album(null, "FifthAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album5);

        Thread.sleep(10);
        File file1 = new File(null, "File1", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file1);
        Thread.sleep(10);
        File file2 = new File(null, "File2", new byte[] { 1 },
                new byte[] { 2 }, album);
        Thread.sleep(10);
        this.fileService.create(file2);
        File file3 = new File(null, "File3", new byte[] { 1 },
                new byte[] { 2 }, album);
        Thread.sleep(10);
        this.fileService.create(file3);
        File file4 = new File(null, "File4", new byte[] { 1 },
                new byte[] { 2 }, album);
        Thread.sleep(10);
        this.fileService.create(file4);
        File file5 = new File(null, "File5", new byte[] { 1 },
                new byte[] { 2 }, album);
        Thread.sleep(10);
        this.fileService.create(file5);
        File file6 = new File(null, "File6", new byte[] { 1 },
                new byte[] { 2 }, album);
        Thread.sleep(10);
        this.fileService.create(file6);
        File file7 = new File(null, "File7", new byte[] { 1 },
                new byte[] { 2 }, album);
        Thread.sleep(10);
        this.fileService.create(file7);
        File file8 = new File(null, "File8", new byte[] { 1 },
                new byte[] { 2 }, album5);
        Thread.sleep(10);
        this.fileService.create(file8);
        File file9 = new File(null, "File9", new byte[] { 1 },
                new byte[] { 2 }, album);
        Thread.sleep(10);
        this.fileService.create(file9);
        File file10 = new File(null, "File10", new byte[] { 1 },
                new byte[] { 2 }, album);
        this.fileService.create(file10);

        this.fileService.changePrivacyLevel(file1,
                PrivacyLevel.PUBLIC);
        this.fileService.changePrivacyLevel(file5,
                PrivacyLevel.PUBLIC);
        this.fileService.changePrivacyLevel(file10,
                PrivacyLevel.PUBLIC);
        this.fileService.changePrivacyLevel(file8,
                PrivacyLevel.PUBLIC);
        this.fileService.changePrivacyLevel(file9,
                PrivacyLevel.PRIVATE);

        this.likeAndDislikeService.voteLike(
                file1.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteLike(
                file2.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteLike(
                file1.getLikeAndDislike(), user2);
        this.likeAndDislikeService.voteDislike(
                file4.getLikeAndDislike(), user2);
        this.likeAndDislikeService.voteDislike(
                file4.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteDislike(
                file6.getLikeAndDislike(), user2);
        this.likeAndDislikeService.voteDislike(
                file9.getLikeAndDislike(), user2);
        this.likeAndDislikeService.voteDislike(
                file4.getLikeAndDislike(), user3);
        this.likeAndDislikeService.voteDislike(
                file3.getLikeAndDislike(), user3);
        this.likeAndDislikeService.voteDislike(
                file6.getLikeAndDislike(), user3);
        this.likeAndDislikeService.voteLike(
                file10.getLikeAndDislike(), user3);

        Comment comment = new Comment(file1.getLikeAndDislike(),
                user, "Prueba comment", null, file1);
        this.commentService.create(user, file1, comment.getText());

        Comment comment2 = new Comment(album5.getLikeAndDislike(),
                user, "Prueba 2", null, file4);
        this.commentService.create(user2, file4, comment2.getText());

        FileTag tag = new FileTag();
        tag.setFile(file2);
        tag.setTag("Prueba tag");
        this.fileTagService.create(tag);

        FileTag tag2 = new FileTag(file6, "Prueba 2");
        this.fileTagService.create(tag2);

        List<File> listFLkN = fileService.getFiles("File", true,
                false, false, "like", file1.getDate(),
                file10.getDate(), 0, 10);
        List<File> listFDkN = fileService.getFiles("File", true,
                false, false, "dislike", file1.getDate(),
                file10.getDate(), 0, 10);
        List<File> listFFkN = fileService.getFiles("File", true,
                false, false, "date", file5.getDate(),
                file10.getDate(), 2, 10);
        List<File> listFFkNV = fileService.getFiles("manzana", true,
                false, false, "date", file1.getDate(),
                file10.getDate(), 0, 10);
        List<File> listFFkNPag = fileService.getFiles("File", true,
                false, false, "date", file1.getDate(),
                file10.getDate(), 0, 5);

        List<File> listFLkC = fileService.getFiles("Pr", false, true,
                false, "like", file2.getDate(), file10.getDate(), 0,
                10);
        List<File> listFDkC = fileService.getFiles("Pr", false, true,
                false, "dislike", file1.getDate(), file10.getDate(),
                0, 10);
        List<File> listFFkC = fileService.getFiles("Pr", false, true,
                false, "date", file3.getDate(), file10.getDate(), 0,
                10);
        List<File> listFFkCV = fileService.getFiles("manzana", false,
                true, false, "dislike", file3.getDate(),
                file10.getDate(), 0, 10);

        List<File> listFLkT = fileService.getFiles("Prue", false,
                false, true, "like", file1.getDate(),
                file10.getDate(), 0, 10);
        List<File> listFDkT = fileService.getFiles("Prue", false,
                false, true, "dislike", file1.getDate(),
                file10.getDate(), 0, 10);
        List<File> listFFkT = fileService.getFiles("Prue", false,
                false, true, "date", file1.getDate(),
                file10.getDate(), 1, 3);
        List<File> listFFkV = fileService.getFiles("manzana", false,
                false, true, "date", file1.getDate(),
                file10.getDate(), 0, 10);

        List<File> listFLo = fileService.getFiles("like", 0, 5);
        List<File> listFDo = fileService.getFiles("dislike", 1, 3);
        List<File> listFFo = fileService.getFiles("date", 0, 5);
        List<File> listFFoV = fileService.getFiles("date", 11, 15);

        List<File> listFLof = fileService.getFiles("like",
                file1.getDate(), file10.getDate(), 0, 10);
        List<File> listFDof = fileService.getFiles("dislike",
                file1.getDate(), file10.getDate(), 0, 5);
        List<File> listFFof = fileService.getFiles("date",
                file1.getDate(), file10.getDate(), 0, 5);

        List<File> listFLokN = fileService.getFiles("File", true,
                false, false, "like", 0, 10);
        List<File> listFDokN = fileService.getFiles("File", true,
                false, false, "dislike", 0, 10);
        List<File> listFFokN = fileService.getFiles("File", true,
                false, false, "date", 0, 10);

        List<File> listFLokC = fileService.getFiles("Pru", false,
                true, false, "like", 0, 10);
        List<File> listFDokC = fileService.getFiles("Pru", false,
                true, false, "dislike", 0, 10);
        List<File> listFFokC = fileService.getFiles("Pru", false,
                true, false, "date", 0, 10);

        List<File> listFLokT = fileService.getFiles("Pru", false,
                false, true, "like", 0, 5);
        List<File> listFDokT = fileService.getFiles("Pru", false,
                false, true, "dislike", 0, 5);
        List<File> listFFokT = fileService.getFiles("Pru", false,
                false, true, "date", 0, 1);
        List<File> listFFokTPag = fileService.getFiles("Pru", false,
                false, true, "date", 1, 1);

        assertEquals(listFLkN.size(), 9);
        assertEquals(listFLkN.get(0), file1);
        assertEquals(listFLkN.get(1), file2);
        assertEquals(listFLkN.get(2), file10);
        assertEquals(listFLkN.get(3), file3);
        assertEquals(listFLkN.get(0).getName(), file1.getName());
        assertEquals(listFLkN.get(2).getAlbum(), file10.getAlbum());
        assertEquals(listFFkNPag.size(), 5);

        assertEquals(listFDkN.size(), 9);
        assertEquals(listFDkN.get(0), file4);
        assertEquals(listFDkN.get(1), file6);
        assertEquals(listFDkN.get(0).getName(), file4.getName());

        assertEquals(listFFkN.size(), 3);
        assertEquals(listFFkN.get(0), file7);
        assertEquals(listFFkN.get(1), file6);
        assertEquals(listFFkN.get(1).getAlbum(), file6.getAlbum());

        assertEquals(listFFkNV.size(), 0);
        

        assertEquals(listFLkC.size(), 1);
        assertEquals(listFLkC.get(0), file4);

        assertEquals(listFDkC.size(), 2);
        assertEquals(listFDkC.get(0), file4);
        assertEquals(listFDkC.get(1), file1);

        assertEquals(listFFkC.size(), 1);
        assertEquals(listFFkC.get(0), file4);

        assertEquals(listFFkCV.size(), 0);

        assertEquals(listFLkT.size(), 2);
        assertEquals(listFLkT.get(0), file2);
        assertEquals(listFLkT.get(1), file6);

        assertEquals(listFDkT.size(), 2);
        assertEquals(listFDkT.get(0), file6);
        assertEquals(listFDkT.get(1), file2);

        assertEquals(listFFkT.size(), 1);
        assertEquals(listFFkT.get(0), file2);

        assertEquals(listFFkV.size(), 0);

        assertEquals(listFLo.size(), 5);
        assertEquals(listFLo.get(0), file1);

        assertEquals(listFDo.size(), 3);
        assertEquals(listFDo.get(0), file6);

        assertEquals(listFFo.size(), 5);
        assertEquals(listFFo.get(0), file10);

        assertEquals(listFFoV.size(), 0);

        assertEquals(listFLof.size(), 9);
        assertEquals(listFLof.get(0), file1);
        assertEquals(listFLof.get(2), file10);
        assertEquals(listFLof.get(8), file8);

        assertEquals(listFDof.size(), 5);
        assertEquals(listFDof.get(0), file4);

        assertEquals(listFFof.size(), 5);
        assertEquals(listFFof.get(0), file10);
        assertEquals(listFFof.get(1), file8);

        assertEquals(listFLokN.size(), 9);
        assertEquals(listFLokN.get(0), file1);
        assertEquals(listFLokN.get(2), file10);

        assertEquals(listFDokN.size(), 9);
        assertEquals(listFDokN.get(0), file4);
        assertEquals(listFDokN.get(1).getName(), file6.getName());

        assertEquals(listFFokN.size(), 9);
        assertEquals(listFFokN.get(0), file10);
        assertEquals(listFFokN.get(2), file7);

        assertEquals(listFLokC.size(), 2);
        assertEquals(listFLokC.get(0), file1);
        assertEquals(listFLokC.get(1), file4);

        assertEquals(listFDokC.size(), 2);
        assertEquals(listFDokC.get(0), file4);

        assertEquals(listFFokC.size(), 2);
        assertEquals(listFDokC.get(0), file4);

        assertEquals(listFLokT.size(), 2);
        assertEquals(listFLokT.get(0), file2);
        assertEquals(listFLokT.get(1), file6);

        assertEquals(listFDokT.size(), 2);
        assertEquals(listFDokT.get(0), file6);
        assertEquals(listFDokT.get(1), file2);

        assertEquals(listFFokT.size(), 1);
        assertEquals(listFFokT.get(0), file6);

        assertEquals(listFFokTPag.size(), 1);
        assertEquals(listFFokTPag.get(0), file2);

    }

    @Test
    public void test_GetAlbums() throws InterruptedException {
        User user = new User(null, "123", MD5.getHash("pass"));
        this.userService.create(user);
        User user2 = new User(null, "456", MD5.getHash("pass"));
        this.userService.create(user2);
        User user3 = new User(null, "789", MD5.getHash("pass"));
        this.userService.create(user3);

        Album album = new Album(null, "FirstAlbum", user, null, null,
                PrivacyLevel.PUBLIC);
        this.albumService.create(album);
        Thread.sleep(10);
        Album album2 = new Album(null, "SecondAlbum", user, null,
                null, PrivacyLevel.PUBLIC);
        this.albumService.create(album2);
        Thread.sleep(10);
        Album album3 = new Album(null, "ThirdAlbum", user, null,
                null, PrivacyLevel.PUBLIC);
        this.albumService.create(album3);
        Thread.sleep(10);
        Album album4 = new Album(null, "FourthAlbum", user, null,
                null, PrivacyLevel.PUBLIC);
        this.albumService.create(album4);
        Thread.sleep(10);
        Album album5 = new Album(null, "FifthAlbum", user, null,
                null, PrivacyLevel.PRIVATE);
        this.albumService.create(album5);

        this.likeAndDislikeService.voteLike(
                album4.getLikeAndDislike(), user2);
        this.likeAndDislikeService.voteLike(
                album4.getLikeAndDislike(), user);
        this.likeAndDislikeService.voteLike(
                album2.getLikeAndDislike(), user2);
        this.likeAndDislikeService.voteDislike(
                album3.getLikeAndDislike(), user3);
        this.likeAndDislikeService.voteDislike(
                album3.getLikeAndDislike(), user2);
        this.likeAndDislikeService.voteDislike(
                album5.getLikeAndDislike(), user);

        Comment commentA = new Comment(album3.getLikeAndDislike(),
                user, "Prueba comment", album3, null);
        this.commentService.create(user, album3, commentA.getText());
        Thread.sleep(10);

        Comment commentA2 = new Comment(album4.getLikeAndDislike(),
                user, "Prueba 2", album4, null);
        this.commentService.create(user, album4, commentA2.getText());
        Thread.sleep(10);

        AlbumTag tagA = new AlbumTag(album4, "Prueba tag");
        this.albumTagService.create(tagA);

        AlbumTag tagA2 = new AlbumTag(album2, "Prueba 2");
        this.albumTagService.create(tagA2);

        List<Album> listALkN = albumService.getAlbums("Album", true,
                false, false, "like", album.getDate(),
                album5.getDate(), 0, 10);
        List<Album> listALkN2 = albumService.getAlbums("Album", true,
                false, false, "like", album.getDate(),
                album5.getDate(), 1, 10);
        List<Album> listADkN = albumService.getAlbums("Album", true,
                false, false, "dislike", album.getDate(),
                album5.getDate(), 0, 10);
        List<Album> listAFkN = albumService.getAlbums("Album", true,
                false, false, "date", album.getDate(),
                album5.getDate(), 0, 10);
        List<Album> listAFkN2 = albumService.getAlbums("Album", true,
                false, false, "date", album.getDate(),
                album5.getDate(), 2, 1);
        List<Album> listAFkNV = albumService.getAlbums("manzana",
                true, false, false, "date", album.getDate(),
                album5.getDate(), 2, 1);

        List<Album> listALkC = albumService.getAlbums("Pru", false,
                true, false, "like", album.getDate(),
                album5.getDate(), 0, 10);
        List<Album> listADkC = albumService.getAlbums("Pru", false,
                true, false, "dislike", album.getDate(),
                album5.getDate(), 0, 10);
        List<Album> listAFkC = albumService.getAlbums("Pru", false,
                true, false, "date", album.getDate(),
                album5.getDate(), 1, 1);
        List<Album> listADkCV = albumService.getAlbums("manzana",
                false, true, false, "dislike", album.getDate(),
                album5.getDate(), 0, 10);

        List<Album> listALkT = albumService.getAlbums("Pru", false,
                false, true, "like", album4.getDate(),
                album5.getDate(), 0, 10);
        List<Album> listADkT = albumService.getAlbums("Pru", false,
                false, true, "dislike", album.getDate(),
                album5.getDate(), 0, 10);
        List<Album> listAFkT = albumService.getAlbums("Pru", false,
                false, true, "date", album.getDate(),
                album5.getDate(), 1, 10);
        List<Album> listAFkTV = albumService.getAlbums("manzana",
                false, false, true, "date", album.getDate(),
                album5.getDate(), 1, 10);

        List<Album> listALo = albumService.getAlbums("like", 0, 5);
        List<Album> listADo = albumService.getAlbums("dislike", 1, 3);
        List<Album> listAFo = albumService.getAlbums("date", 2, 5);
        List<Album> listALoV = albumService.getAlbums("like", 8, 5);

        List<Album> listALof = albumService.getAlbums("like",
                album.getDate(), album5.getDate(), 0, 10);
        List<Album> listADof = albumService.getAlbums("dislike",
                album2.getDate(), album5.getDate(), 1, 2);
        List<Album> listAFof = albumService.getAlbums("date",
                album.getDate(), album5.getDate(), 1, 3);
        List<Album> listALofV = albumService.getAlbums("like",
                album.getDate(), album5.getDate(), 6, 10);

        List<Album> listALokN = albumService.getAlbums("Album", true,
                false, false, "like", 0, 10);
        List<Album> listADokN = albumService.getAlbums("Album", true,
                false, false, "dislike", 1, 10);
        List<Album> listAFokN = albumService.getAlbums("Album", true,
                false, false, "date", 1, 2);
        List<Album> listAFokVN = albumService.getAlbums("manzana",
                true, false, false, "date", 1, 2);

        List<Album> listALokC = albumService.getAlbums("Pru", false,
                true, false, "like", 0, 10);
        List<Album> listADokC = albumService.getAlbums("Pru", false,
                true, false, "dislike", 1, 3);
        List<Album> listAFokC = albumService.getAlbums("Pru", false,
                true, false, "date", 0, 3);
        List<Album> listAFokCV = albumService.getAlbums("manzana",
                false, true, false, "date", 0, 3);

        List<Album> listALokT = albumService.getAlbums("Prue", false,
                false, true, "like", 0, 3);
        List<Album> listADokT = albumService.getAlbums("Prue", false,
                false, true, "dislike", 1, 1);
        List<Album> listAFokT = albumService.getAlbums("Prue", false,
                false, true, "date", 0, 5);
        List<Album> listAFokTV = albumService.getAlbums("manzana",
                false, false, true, "date", 0, 5);

        assertEquals(listALkN.size(), 4);
        assertEquals(listALkN.get(0), album4);

        assertEquals(listALkN2.size(), 3);
        assertEquals(listALkN2.get(0), album2);
        assertEquals(listALkN2.get(1), album);

        assertEquals(listADkN.size(), 4);
        assertEquals(listADkN.get(0), album3);
        assertEquals(listADkN.get(1), album);

        assertEquals(listAFkN.size(), 4);
        assertEquals(listAFkN.get(0), album4);

        assertEquals(listAFkN2.size(), 1);
        assertEquals(listAFkN2.get(0), album2);

        assertEquals(listAFkNV.size(), 0);

        assertEquals(listALkC.size(), 2);
        assertEquals(listALkC.get(0), album4);
        assertEquals(listALkC.get(1), album3);

        assertEquals(listADkC.size(), 2);
        assertEquals(listADkC.get(0), album3);
        assertEquals(listADkC.get(1), album4);

        assertEquals(listAFkC.size(), 1);
        assertEquals(listAFkC.get(0), album3);

        assertEquals(listADkCV.size(), 0);

        assertEquals(listALkT.size(), 1);
        assertEquals(listALkT.get(0), album4);

        assertEquals(listADkT.size(), 2);
        assertEquals(listADkT.get(0), album2);
        assertEquals(listADkT.get(1), album4);
        assertEquals(listAFkT.size(), 1);
        assertEquals(listAFkT.get(0), album2);

        assertEquals(listAFkTV.size(), 0);

        assertEquals(listALo.size(), 4);
        assertEquals(listALo.get(0), album4);
        assertEquals(listALo.get(1), album2);

        assertEquals(listADo.size(), 3);
        assertEquals(listADo.get(0), album);

        assertEquals(listAFo.size(), 2);
        assertEquals(listAFo.get(0), album2);
        assertEquals(listAFo.get(1), album);

        assertEquals(listALoV.size(), 0);

        assertEquals(listALof.size(), 4);
        assertEquals(listALof.get(0), album4);
        assertEquals(listALof.get(1), album2);

        assertEquals(listADof.size(), 2);
        assertEquals(listADof.get(0), album2);
        assertEquals(listADof.get(1), album4);

        assertEquals(listAFof.size(), 3);
        assertEquals(listAFof.get(0), album3);
        assertEquals(listAFof.get(1), album2);
        assertEquals(listAFof.get(2), album);

        assertEquals(listALofV.size(), 0);

        assertEquals(listALokN.size(), 4);
        assertEquals(listALokN.get(0), album4);
        assertEquals(listALokN.get(1), album2);

        assertEquals(listADokN.size(), 3);
        assertEquals(listADokN.get(0), album);
        assertEquals(listADokN.get(1), album2);

        assertEquals(listAFokN.size(), 2);
        assertEquals(listAFokN.get(0), album3);
        assertEquals(listAFokN.get(1), album2);

        assertEquals(listAFokVN.size(), 0);

        assertEquals(listALokC.size(), 2);
        assertEquals(listALokC.get(0), album4);
        assertEquals(listALokC.get(1), album3);

        assertEquals(listADokC.size(), 1);
        assertEquals(listADokC.get(0), album4);

        assertEquals(listAFokC.size(), 2);
        assertEquals(listAFokC.get(0), album4);
        assertEquals(listAFokC.get(1), album3);

        assertEquals(listAFokCV.size(), 0);

        assertEquals(listALokT.size(), 2);
        assertEquals(listALokT.get(0), album4);
        assertEquals(listALokT.get(1), album2);

        assertEquals(listADokT.size(), 1);
        assertEquals(listADokT.get(0), album4);

        assertEquals(listAFokT.size(), 2);
        assertEquals(listAFokT.get(0), album4);
        assertEquals(listAFokT.get(1), album2);

        assertEquals(listAFokTV.size(), 0);

    }

}
