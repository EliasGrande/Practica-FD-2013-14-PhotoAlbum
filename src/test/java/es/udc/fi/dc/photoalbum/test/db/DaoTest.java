package es.udc.fi.dc.photoalbum.test.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
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

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.AlbumShareInformation;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.AlbumShareInformationService;
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
	private AlbumShareInformationService shareInformationService;

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
	public void testUser() {
		User user = new User(null, "123", MD5.getHash("pass"));
		User userSharedTo = new User(null, "456", MD5.getHash("pass"));
		User userSharedTo2 = new User(null, "789", MD5.getHash("pass2"));
		
		// test create&update user
		this.userService.create(user);
		this.userService.create(user);
		System.out.println("-----------------");
		assertNotNull(this.userService.getUser(user.getEmail(), "pass"));
		assertNotNull(this.userService.getUser(user));
		user.setPassword(MD5.getHash("pass1"));
		this.userService.update(user);
		assertNotNull(this.userService.getUser(user.getEmail(), "pass1"));

		// test create&update album
		Album album = new Album();
		album.setName("111");
		album.setUser(user);
		this.albumService.create(album);
		this.albumService.create(album);

		// check default privacy level is SHAREABLE
		assertEquals(
				this.albumService.getAlbum(album.getName(),
						album.getUser().getId()).getPrivacyLevel(),
				PrivacyLevel.SHAREABLE);

		assertNotNull(this.albumService.getAlbum(album.getName(), album
				.getUser().getId()));

		// Rename operation
		this.albumService.rename(album, "222");
		assertNotNull(this.albumService.getAlbum(album.getName(), album
				.getUser().getId()));

		// Change privacy level
		this.albumService.changePrivacyLevel(album, PrivacyLevel.PUBLIC);
		assertEquals(album.getPrivacyLevel(), PrivacyLevel.PUBLIC);

		// Find albums
		ArrayList<Album> foundAlbums = this.albumService.getAlbums(1);
		assertEquals(foundAlbums.size(), 1);
		assertEquals(foundAlbums.get(0).getPrivacyLevel(), PrivacyLevel.PUBLIC);

		// Find public albums
		ArrayList<Album> publicAlbums = this.albumService.getPublicAlbums();
		assertEquals(publicAlbums.size(), 1);

		// -----------------------
		
		// test create&update file
		File file = new File(null, "123", new byte[] { 1 }, new byte[] { 2 },
				album);
		this.fileService.create(file);
		assertNotNull(this.fileService.getFileOwn(file.getId(),
				album.getName(), user.getId()));

		// Change privacy level
		this.fileService.changePrivacyLevel(file, PrivacyLevel.SHAREABLE);
		assertEquals(file.getPrivacyLevel(), PrivacyLevel.SHAREABLE);

		// Find files by different criteria
		ArrayList<File> foundFiles = this.fileService.getAlbumFilesOwn(1);
		ArrayList<File> foundFiles2 = this.fileService.getAlbumFilesOwn(1,
				PrivacyLevel.PUBLIC);
		ArrayList<File> foundFiles3 = this.fileService.getAlbumFilesOwn(1,
				PrivacyLevel.PRIVATE);
		assertEquals(foundFiles.size(), 1);
		assertEquals(foundFiles2.size(), 0);
		assertEquals(foundFiles3.size(), 1);
		ArrayList<File> foundFiles4 = this.fileService.getAlbumFilesOwnPaging(1,
				0, 10);
		assertEquals(foundFiles4.size(), 1);
		ArrayList<File> foundFiles5 = this.fileService.getAlbumFilesPaging(1,
				0, 10, PrivacyLevel.SHAREABLE);
		assertEquals(foundFiles5.size(), 1);

		// test move file from album to another
		Album album2 = new Album();
		album2.setName("333");
		album2.setUser(user);
		this.albumService.create(album2);
		this.fileService.changeAlbum(file, album2);
		assertNotNull(this.fileService.getFileOwn(file.getId(),
				album2.getName(), user.getId()));
		this.fileService.changeAlbum(file, album);

		// -----------------------
		
		// test create share
		this.userService.create(userSharedTo);
		AlbumShareInformation shareInformation = new AlbumShareInformation(null, album,
				userSharedTo);
		this.shareInformationService.create(shareInformation);
		this.userService.create(userSharedTo2);
		AlbumShareInformation shareInformation2 = new AlbumShareInformation(null, album,
				userSharedTo2);
		this.shareInformationService.create(shareInformation2);
		assertNotNull(this.shareInformationService.getShare(album.getName(),
				userSharedTo.getId(), user.getEmail()));
		
		// test get shares
		List<AlbumShareInformation> shares = this.shareInformationService.getShares(
				user, userSharedTo);
		assertEquals(shares.size(), 1);
		assertEquals(shares.get(0).getAlbum().getName(), album.getName());		

		// test shares from an album
		ArrayList<AlbumShareInformation> albumShares = this.shareInformationService
				.getAlbumShares(album.getId());
		assertEquals(albumShares.size(), 2);
		assertEquals(albumShares.get(0).getAlbum().getName(), album.getName());
		assertEquals(albumShares.get(1).getAlbum().getName(), shareInformation
				.getAlbum().getName());

		// test a user shares
		ArrayList<AlbumShareInformation> userShares = this.shareInformationService
				.getUserShares(userSharedTo.getId());
		assertEquals(userShares.size(), 1);
		assertEquals(userShares.get(0).getUser().getEmail(), userSharedTo.getEmail());

		// test that when an album is private, it isn't included in the list of shares
		assertEquals(shares.size(), 1);
		this.albumService.changePrivacyLevel(album, PrivacyLevel.PRIVATE);
		shares = this.shareInformationService.getShares(
				user, userSharedTo);
		assertEquals(shares.size(), 0);
		
		// -----------------------
		
		// test cascade delete
		this.userService.delete(user);
		this.userService.delete(userSharedTo);
		assertNull(this.userService.getUser(user));
		assertNull(this.albumService.getAlbum(album.getName(), album.getUser()
				.getId()));
		assertNull(this.fileService.getFileOwn(file.getId(), album2.getName(),
				user.getId()));
		assertNull(this.shareInformationService.getShare(album.getName(),
				userSharedTo.getId(), user.getEmail()));
	}
}
