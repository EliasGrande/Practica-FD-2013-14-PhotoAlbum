package es.udc.fi.dc.photoalbum.test.pages;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.TAG_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.LIKE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumTag;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.hibernate.Voted;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.spring.AlbumTagService;
import es.udc.fi.dc.photoalbum.spring.FileService;
import es.udc.fi.dc.photoalbum.spring.LikeAndDislikeService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.spring.VotedService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;
import es.udc.fi.dc.photoalbum.wicket.MySession;
import es.udc.fi.dc.photoalbum.wicket.WicketApp;
import es.udc.fi.dc.photoalbum.wicket.pages.auth.Upload;

public class TestUploadPage {

	private WicketApp wicketApp;
	private WicketTester tester;
	private Set<File> set = new HashSet<File>();
	{
		this.wicketApp = new WicketApp() {
			@Override
			protected void init() {
				ApplicationContextMock context = new ApplicationContextMock();
				AlbumService mockAlbum = new AlbumService() {
					public void rename(Album album, String newName) {
					}

					public Album getAlbum(String name, int userId) {
						User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
						Album album = new Album(1, ALBUM_NAME_EXIST, user,
								null, null, PrivacyLevel.PRIVATE);
						set.add(new File(1, "1", new byte[1], new byte[1],
								album));
						album.setFiles(set);
						user.getAlbums().add(album);
						return album;
					}

					public void delete(Album album) {
						album.getUser().getAlbums().remove(album);
					}

					public void create(Album album) {
						if (album.getName().equals(ALBUM_NAME_EXIST)) {
							throw new DataIntegrityViolationException("");
						}
						album.getUser().getAlbums().add(album);
					}

					public Album getById(Integer id) {
						return null;
					}

					public ArrayList<Album> getAlbums(Integer id) {
						ArrayList<Album> list = new ArrayList<Album>();
						list.add(new Album(1, ALBUM_NAME_EXIST, new User(1,
								USER_EMAIL_EXIST, USER_PASS_YES), null, null,
								PrivacyLevel.PRIVATE));
						return list;
					}

					public ArrayList<Album> getPublicAlbums() {
						ArrayList<Album> list = new ArrayList<Album>();
						list.add(new Album(1, ALBUM_NAME_EXIST, new User(1,
								USER_EMAIL_EXIST, USER_PASS_YES), null, null,
								PrivacyLevel.PRIVATE));
						return list;
					}

					public void changePrivacyLevel(Album album,
							String privacyLevel) {
						album.setPrivacyLevel(privacyLevel);
					}

					public ArrayList<Album> getAlbumsSharedWith(Integer id,
							String ownerEmail) {
						ArrayList<Album> list = new ArrayList<Album>();
						list.add(new Album(1, ALBUM_NAME_EXIST, new User(1,
								USER_EMAIL_EXIST, USER_PASS_YES), null, null,
								PrivacyLevel.PRIVATE));
						return list;
					}

					public Album getSharedAlbum(String albumName,
							int userSharedToId, String userSharedEmail) {
						return null;
					}

					public ArrayList<Album> getAlbumsByTag(int userId,
							String tag) {
						ArrayList<Album> list = new ArrayList<Album>();
						list.add(new Album(1, ALBUM_NAME_EXIST, new User(1,
								USER_EMAIL_EXIST, USER_PASS_YES), null, null,
								PrivacyLevel.PRIVATE));
						return list;
					}
				};
				FileService mockFile = new FileService() {
					public void create(File file) {
					}

					public void delete(File file) {
					}

					public File getFileOwn(int id, String name, int userId) {
						return null;
					}

					public File getFileShared(int id, String name, int userId) {
						return null;
					}

					public void changeAlbum(File file, Album album) {
					}

					public File getById(Integer id) {
						return null;
					}

					public ArrayList<File> getAlbumFilesOwn(int albumId) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getAlbumFilesOwnPaging(int albumId,
							int first, int count) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public Long getCountAlbumFiles(int albumId) {
						return (long) 0;
					}

					public void changePrivacyLevel(File file,
							String privacyLevel) {
						file.setPrivacyLevel(privacyLevel);
					}

					public File getFilePublic(int id, String name, int userId) {
						return null;
					}

					public ArrayList<File> getAlbumFilesShared(int albumId,
							int userId) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getAlbumFilesSharedPaging(
							int albumId, int userId, int first, int count) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getAlbumFilesPublic(int albumId,
							int userId) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getAlbumFilesPublicPaging(
							int albumId, int userId, int first, int count) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getFilesByTag(int userId, String tag) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}

					public ArrayList<File> getFilesByTagPaging(int userId,
							String tag, int first, int count) {
						ArrayList<File> list = new ArrayList<File>();
						list.add(new File(1, "1", new byte[1], new byte[1],
								new Album(1, ALBUM_NAME_EXIST, new User(1, USER_EMAIL_EXIST, USER_PASS_YES), null,
										null, PrivacyLevel.PRIVATE)));
						return list;
					}
				};
				UserService mock = new UserService() {
					public void create(User user) {
					}

					public void delete(User user) {
					}

					public void update(User user) {
					}

					public User getUser(String email, String password) {
						return null;
					}

					public User getUser(User userEmail) {
						return null;
					}

					public User getById(Integer id) {
						return new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
					}

					public ArrayList<User> getUsersSharingWith(int userId) {
						return new ArrayList<User>();
					}
				};
				AlbumTagService mockAlbumTag = new AlbumTagService() {

					public void create(AlbumTag albumTag) {
					}

					public void delete(AlbumTag albumTag) {
					}

					public AlbumTag getTag(int albumId, String tag) {
						return null;
					}

					public ArrayList<AlbumTag> getTags(int albumId) {
						return new ArrayList<AlbumTag>();
					}
					
				};
				LikeAndDislikeService mockLikeAndDislike = new LikeAndDislikeService() {

					public LikeAndDislike voteLike(
							LikeAndDislike likeAndDislike, User user) {
						Album album = new Album(1, ALBUM_NAME_EXIST, user,
								null, null, PrivacyLevel.PRIVATE);
						set.add(new File(1, "1", new byte[1], new byte[1],
								album));
						album.setFiles(set);
						likeAndDislike.setId(1);
						album.setLikeAndDislike(likeAndDislike);
						user.getAlbums().add(album);
						return album.getLikeAndDislike();
					}

					public LikeAndDislike voteDislike(
							LikeAndDislike likeAndDislike, User user) {
						Album album = new Album(1, ALBUM_NAME_EXIST, user,
								null, null, PrivacyLevel.PRIVATE);
						set.add(new File(1, "1", new byte[1], new byte[1],
								album));
						album.setFiles(set);
						likeAndDislike.setId(1);
						album.setLikeAndDislike(likeAndDislike);
						user.getAlbums().add(album);
						return album.getLikeAndDislike();
					}

					public LikeAndDislike unvote(LikeAndDislike likeAndDislike,
							User user) {
						Album album = new Album(1, ALBUM_NAME_EXIST, user,
								null, null, PrivacyLevel.PRIVATE);
						set.add(new File(1, "1", new byte[1], new byte[1],
								album));
						album.setFiles(set);
						likeAndDislike.setId(1);
						album.setLikeAndDislike(likeAndDislike);
						user.getAlbums().add(album);
						return album.getLikeAndDislike();
					}

					public boolean userHasVoted(LikeAndDislike likeAndDislike,
							User user) {
						return true;
					}
					
				};
				VotedService mockVotedService = new VotedService() {

					public Voted getVoted(int likeAndDislikeId, int userId) {	
						User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
						Album album = new Album(1, ALBUM_NAME_EXIST, user,
								null, null, PrivacyLevel.PRIVATE);
						user.getAlbums().add(album);
						album.getLikeAndDislike().setId(50);
						return new Voted(album.getLikeAndDislike(), user, LIKE);
					}

					public ArrayList<Voted> getVoted(
							ArrayList<Integer> likeAndDislikeIdList, int userId) {
						ArrayList<Voted> list = new ArrayList<Voted>();
						User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
						Album album = new Album(1, ALBUM_NAME_EXIST, user,
								null, null, PrivacyLevel.PRIVATE);
						user.getAlbums().add(album);
						album.getLikeAndDislike().setId(50);
						list.add(new Voted(album.getLikeAndDislike(), user, LIKE));
						return list;
					}
					
				};
				
				context.putBean("userBean", mock);
				context.putBean("albumBean", mockAlbum);
				context.putBean("fileBean", mockFile);
				context.putBean("albumTagBean", mockAlbumTag);
				context.putBean("likeAndDislikeBean", mockLikeAndDislike);
				context.putBean("votedServiceBean", mockVotedService);
				getComponentInstantiationListeners().add(
						new SpringComponentInjector(this, context));
			}
		};
	}

	@Before
	public void setUp() {
		this.tester = new WicketTester(this.wicketApp);
		((MySession) Session.get()).setuId(1);
		PageParameters pars = new PageParameters();
		pars.add("album", ALBUM_NAME_EXIST);
		Page page = new Upload(pars);
		this.tester.startPage(page);
		tester.assertVisible("signout");
		this.tester.getSession().setLocale(new Locale("ru", "RU"));
	}

	@Test
	public void testRendered() {
		tester.assertRenderedPage(Upload.class);
		tester.assertComponent("upload:fileInput", FileUploadField.class);
	}
	
	@Test
	public void testAddTag() {
		FormTester formTester = this.tester.newFormTester("formAddTag");
		formTester.setValue("newTag", TAG_NAME_EXIST);
		formTester.submit();
		this.tester.assertNoErrorMessage();
		tester.assertRenderedPage(Upload.class);
	}
	
}
