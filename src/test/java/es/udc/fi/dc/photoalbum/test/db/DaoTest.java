package es.udc.fi.dc.photoalbum.test.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

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

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.FileShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.FileTag;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.AlbumShareInformationService;
import es.udc.fi.dc.photoalbum.spring.AlbumTagService;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.FileShareInformationService;
import es.udc.fi.dc.photoalbum.spring.FileTagService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.utils.MD5;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

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

		this.userService.create(user);
		System.out.println("-----------------");
		assertNotNull(this.userService.getUser(user.getEmail(), "pass"));
		assertNotNull(this.userService.getUser(user));
		user.setPassword(MD5.getHash("pass1"));
		this.userService.update(user);
		assertNotNull(this.userService.getUser(user.getEmail(), "pass1"));
		User userAux = this.userService.getUser(user);
		assertEquals(userAux, user);
		User userAux2 = this.userService.getById(user.getId());
		assertEquals(user, userAux2);
		this.userService.delete(user);
		assertNull(this.userService.getUser(user.getEmail(), "pass1"));

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
		Album albumAux = this.albumService.getAlbum(album.getName(), album
				.getUser().getId());
		assertEquals(album, albumAux);
		this.albumService.delete(album);
		Album albumAux2 = this.albumService.getById(album.getId());
		assertNull(albumAux2);
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
		Album albumAux = this.albumService.getAlbum("FirstAlbumChanged",
				user.getId());
		assertNotNull("Crash rename album", albumAux);

	}

	@Test
	// Change privacy [AlbumService]
	public void testChangePrivacyLevelAlbum() {
		User user = new User(null, "123", MD5.getHash("pass"));
		this.userService.create(user);
		Album album = new Album(null, "FirstAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
		this.albumService.create(album);
		this.albumService.changePrivacyLevel(album, PrivacyLevel.PUBLIC);
		Album albumAux = this.albumService.getAlbum("FirstAlbum", user.getId());

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
		Album albumAux = this.albumService.getAlbum("FirstAlbum", user.getId());

		assertEquals(albumAux.getPrivacyLevel(), PrivacyLevel.PRIVATE);
	}

	@Test
	// Create, update, delete and simple searches [AlbumShareInformationService]
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
		this.albumShareInformationService.create(albumShareInformation);

		AlbumShareInformation albumShareInformationAux = this.albumShareInformationService
				.getShare(album.getName(), userSharedTo.getId(),
						user.getEmail());

		assertEquals(albumShareInformation, albumShareInformationAux);

		this.albumShareInformationService.delete(albumShareInformation);
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

		this.fileService.changePrivacyLevel(file, PrivacyLevel.PRIVATE);

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
		Album album2 = new Album(null, "SecondAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
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
	// Create, delete and simple searches [FileShareInformationService]
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
		FileShareInformation fsi = new FileShareInformation(null, file, user2);
		this.fileShareInformationService.create(fsi);
		FileShareInformation fsi2 = new FileShareInformation(null, file, user3);
		this.fileShareInformationService.create(fsi2);

		ArrayList<FileShareInformation> fsil = this.fileShareInformationService
				.getFileShares(file.getId());

		assertEquals(fsi, fsil.get(0));

		assertEquals(fsi2, fsil.get(1));
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
		Album album2 = new Album(null, "SecondAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
		this.albumService.create(album2);
		Album album3 = new Album(null, "ThirdAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
		this.albumService.create(album3);

		AlbumShareInformation albumShareInformation = new AlbumShareInformation(
				null, album, user2);
		AlbumShareInformation albumShareInformation2 = new AlbumShareInformation(
				null, album, user3);
		AlbumShareInformation albumShareInformation3 = new AlbumShareInformation(
				null, album2, user2);
		this.albumShareInformationService.create(albumShareInformation);
		this.albumShareInformationService.create(albumShareInformation2);
		this.albumShareInformationService.create(albumShareInformation3);

		ArrayList<AlbumShareInformation> list1 = this.albumShareInformationService
				.getAlbumShares(album.getId());
		assertEquals(albumShareInformation, list1.get(0));
		assertEquals(albumShareInformation2, list1.get(1));

		ArrayList<AlbumShareInformation> list2 = this.albumShareInformationService
				.getAlbumShares(album2.getId());
		assertEquals(albumShareInformation3, list2.get(0));

		ArrayList<AlbumShareInformation> list3 = this.albumShareInformationService
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
		Album album2 = new Album(null, "SecondAlbum", user2, null, null,
				PrivacyLevel.PRIVATE);
		this.albumService.create(album2);
		Album album3 = new Album(null, "ThirdAlbum", user3, null, null,
				PrivacyLevel.PRIVATE);
		this.albumService.create(album3);

		AlbumShareInformation albumShareInformation = new AlbumShareInformation(
				null, album, user2);
		AlbumShareInformation albumShareInformation3 = new AlbumShareInformation(
				null, album2, user3);
		this.albumShareInformationService.create(albumShareInformation);
		this.albumShareInformationService.create(albumShareInformation3);

		File file = new File(null, "Prueba1", new byte[] { 1 },
				new byte[] { 2 }, album);
		this.fileService.create(file);
		File file2 = new File(null, "Prueba2", new byte[] { 1 },
				new byte[] { 2 }, album3);
		this.fileService.create(file2);
		FileShareInformation fsi = new FileShareInformation(null, file, user3);
		this.fileShareInformationService.create(fsi);
		FileShareInformation fsi2 = new FileShareInformation(null, file, user);
		this.fileShareInformationService.create(fsi2);

		ArrayList<User> list = this.userService.getUsersSharingWith(user
				.getId());
		ArrayList<User> list2 = this.userService.getUsersSharingWith(user2
				.getId());
		ArrayList<User> list3 = this.userService.getUsersSharingWith(user3
				.getId());
		ArrayList<User> list4 = this.userService.getUsersSharingWith(user4
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
		Album album2 = new Album(null, "SecondAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
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
		Album album2 = new Album(null, "SecondAlbum", user2, null, null,
				PrivacyLevel.PRIVATE);
		this.albumService.create(album2);
		Album album3 = new Album(null, "ThirdAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
		this.albumService.create(album3);

		ArrayList<Album> list = this.albumService.getAlbums(user.getId());
		ArrayList<Album> list2 = this.albumService.getAlbums(user2.getId());
		ArrayList<Album> list3 = this.albumService.getAlbums(user3.getId());

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
		Album album2 = new Album(null, "SecondAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
		this.albumService.create(album2);

		File file = new File(null, "Prueba1", new byte[] { 1 },
				new byte[] { 2 }, album);
		this.fileService.create(file);
		File file2 = new File(null, "Prueba2", new byte[] { 1 },
				new byte[] { 2 }, album2);
		this.fileService.create(file2);

		AlbumShareInformation asi = new AlbumShareInformation(null, album,
				user2);
		this.albumShareInformationService.create(asi);
		AlbumShareInformation asi2 = new AlbumShareInformation(null, album2,
				user2);
		this.albumShareInformationService.create(asi2);

		ArrayList<Album> list = this.albumService.getAlbumsSharedWith(
				user2.getId(), user.getEmail());
		ArrayList<Album> list2 = this.albumService.getAlbumsSharedWith(
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
		Album album2 = new Album(null, "SecondAlbum", user, null, null,
				PrivacyLevel.PUBLIC);
		this.albumService.create(album2);

		File file = new File(null, "Prueba1", new byte[] { 1 },
				new byte[] { 2 }, album);
		this.fileService.create(file);
		File file2 = new File(null, "Prueba2", new byte[] { 1 },
				new byte[] { 2 }, album2);
		this.fileService.create(file2);

		ArrayList<Album> list = this.albumService.getPublicAlbums();

		assertEquals(list.size(), 1);

		this.albumService.changePrivacyLevel(album2, PrivacyLevel.PRIVATE);

		ArrayList<Album> list2 = this.albumService.getPublicAlbums();

		assertTrue(list2.isEmpty());

		this.fileService.changePrivacyLevel(file, PrivacyLevel.PUBLIC);

		ArrayList<Album> list3 = this.albumService.getPublicAlbums();

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
		Album album2 = new Album(null, "SecondAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
		this.albumService.create(album2);

		File file = new File(null, "Prueba1", new byte[] { 1 },
				new byte[] { 2 }, album);
		this.fileService.create(file);
		File file2 = new File(null, "Prueba2", new byte[] { 1 },
				new byte[] { 2 }, album2);
		this.fileService.create(file2);

		AlbumShareInformation asi = new AlbumShareInformation(null, album,
				user2);
		this.albumShareInformationService.create(asi);
		AlbumShareInformation asi2 = new AlbumShareInformation(null, album2,
				user2);
		this.albumShareInformationService.create(asi2);

		Album alSha = this.albumService.getSharedAlbum(album.getName(),
				user2.getId(), user.getEmail());

		assertEquals(alSha, album);

		this.albumShareInformationService.delete(asi);

		Album alSha2 = this.albumService.getSharedAlbum(album.getName(),
				user2.getId(), user.getEmail());

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
		Album album2 = new Album(null, "SecondAlbum", user2, null, null,
				PrivacyLevel.PRIVATE);
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
		Album album2 = new Album(null, "SecondAlbum", user2, null, null,
				PrivacyLevel.PRIVATE);
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

		this.fileService.changePrivacyLevel(file2, PrivacyLevel.PRIVATE);
		this.fileService.changePrivacyLevel(file3, PrivacyLevel.PUBLIC);

		FileShareInformation fsi = new FileShareInformation(null, file2, user2);
		this.fileShareInformationService.create(fsi);
		AlbumShareInformation asi = new AlbumShareInformation(null, album2,
				user);
		this.albumShareInformationService.create(asi);

		File fileShared = this.fileService.getFileShared(file2.getId(),
				album.getName(), user2.getId());
		File fileShared2 = this.fileService.getFileShared(file4.getId(),
				album.getName(), user.getId());
		File fileShared3 = this.fileService.getFileShared(file.getId(),
				album.getName(), user3.getId());
		File fileShared4 = this.fileService.getFileShared(file3.getId(), null,
				user3.getId());

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

		this.fileService.changePrivacyLevel(file2, PrivacyLevel.PUBLIC);

		File filePublic = this.fileService.getFilePublic(file.getId(),
				album.getName(), user.getId());
		File filePublic2 = this.fileService.getFilePublic(file.getId(),
				album.getName(), user2.getId());
		File filePublic3 = this.fileService.getFilePublic(file2.getId(),
				album.getName(), user2.getId());

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

		ArrayList<File> filesOwn = this.fileService.getAlbumFilesOwn(album
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

		ArrayList<File> filesOwn = this.fileService.getAlbumFilesOwnPaging(
				album.getId(), 0, 2);
		ArrayList<File> filesOwn2 = this.fileService.getAlbumFilesOwnPaging(
				album.getId(), 2, 1);

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
		Album album2 = new Album(null, "SecondAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
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

		AlbumShareInformation asi = new AlbumShareInformation(null, album,
				user2);
		this.albumShareInformationService.create(asi);
		FileShareInformation fsi = new FileShareInformation(null, file3, user2);
		this.fileShareInformationService.create(fsi);

		ArrayList<File> filesShared = this.fileService.getAlbumFilesShared(
				album.getId(), user2.getId());
		ArrayList<File> filesShared2 = this.fileService.getAlbumFilesShared(
				album2.getId(), user2.getId());

		assertEquals("Shared album", filesShared.size(), 2);
		assertEquals("Shared file", filesShared2.size(), 1);
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
		Album album2 = new Album(null, "SecondAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
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

		AlbumShareInformation asi = new AlbumShareInformation(null, album,
				user2);
		this.albumShareInformationService.create(asi);
		FileShareInformation fsi = new FileShareInformation(null, file3, user2);
		this.fileShareInformationService.create(fsi);

		ArrayList<File> filesShared = this.fileService
				.getAlbumFilesSharedPaging(album.getId(), user2.getId(), 0, 1);
		ArrayList<File> filesShared2 = this.fileService
				.getAlbumFilesSharedPaging(album.getId(), user2.getId(), 1, 1);

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

		this.fileService.changePrivacyLevel(file3, PrivacyLevel.PUBLIC);

		ArrayList<File> filesPublic = this.fileService.getAlbumFilesPublic(
				album.getId(), user.getId());
		ArrayList<File> filesPublic2 = this.fileService.getAlbumFilesPublic(
				album.getId(), user2.getId());

		assertEquals("All the files (own album)", filesPublic.size(), 3);
		assertEquals("Only the files public", filesPublic2.size(), 1);

		this.albumService.changePrivacyLevel(album, PrivacyLevel.PUBLIC);

		ArrayList<File> filesPublic3 = this.fileService.getAlbumFilesPublic(
				album.getId(), user2.getId());
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
		this.albumService.changePrivacyLevel(album, PrivacyLevel.PUBLIC);

		ArrayList<File> filesPublic = this.fileService
				.getAlbumFilesPublicPaging(album.getId(), user.getId(), 0, 2);
		ArrayList<File> filesPublic2 = this.fileService
				.getAlbumFilesPublicPaging(album.getId(), user2.getId(), 2, 1);

		assertEquals("All the files (own album)", filesPublic.size(), 2);
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
		AlbumTag tag = new AlbumTag(album, "tag");
		this.albumTagService.create(tag);
		AlbumTag albumTagAux = this.albumTagService.getTag(album.getId(),
				tag.getTag());
		assertNotNull(albumTagAux);
		this.albumTagService.delete(tag);
		albumTagAux = this.albumTagService.getTag(album.getId(), tag.getTag());
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
		ArrayList<AlbumTag> tags = albumTagService.getTags(album.getId());
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
		FileTag tag = new FileTag(file, "tag");
		this.fileTagService.create(tag);
		FileTag fileTagAux = this.fileTagService.getTag(file.getId(),
				tag.getTag());
		assertNotNull(fileTagAux);
		this.fileTagService.delete(tag);
		fileTagAux = this.fileTagService.getTag(file.getId(), tag.getTag());
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
		ArrayList<FileTag> tags = fileTagService.getTags(file.getId());
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
		Album album1 = new Album(null, "FirstAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
		this.albumService.create(album1);
		Album album2 = new Album(null, "SecondAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
		this.albumService.create(album2);
		Album album3 = new Album(null, "ThirdAlbum", user, null, null,
				PrivacyLevel.PRIVATE);
		this.albumService.create(album3);
		AlbumTag tag1 = new AlbumTag(album1, "tag");
		this.albumTagService.create(tag1);
		AlbumTag tag2 = new AlbumTag(album2, "tag");
		this.albumTagService.create(tag2);
		AlbumTag tag3 = new AlbumTag(album3, "tag");
		this.albumTagService.create(tag3);
		ArrayList<Album> albums = this.albumService.getAlbumsByTag(user.getId(), "tag");
		assertEquals(albums.size(),3);
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
		ArrayList<File> files = this.fileService.getFilesByTag(user.getId(),
				"tag");
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
		
		ArrayList<File> files = this.fileService.getFilesByTagPaging(user.getId(), "tag", 1, 2);
		ArrayList<File> files2 = this.fileService.getFilesByTagPaging(user.getId(), "tag", 0, 3);
		
		assertEquals(files.size(), 2);
		assertEquals(files.get(0), file2);
		assertEquals(files.get(1), file3);
		
		assertEquals(files2.size(), 3);
		assertEquals(files2.get(0), file1);
		assertEquals(files2.get(1), file2);
		assertEquals(files2.get(2), file3);
	}

}
