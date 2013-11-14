package es.udc.fi.dc.photoalbum.mocks;

import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.ALBUM_NAME_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.COMMENT;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_EMAIL_EXIST;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.USER_PASS_YES;
import static es.udc.fi.dc.photoalbum.test.pages.ConstantsForTests.FILE_NAME_EXIST;


import java.util.ArrayList;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.CommentService;
import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

public class CommentServiceMock {

    public static CommentService mock = new CommentService() {

        public void create(User userThatComment, Album album,
                String text) {  
        }

        public void create(User userThatComment, File file,
                String text) {
        }

        public void delete(Comment comment) {
        }

        public ArrayList<Comment> getComments(Album album) {
            ArrayList<Comment> list = new ArrayList<Comment>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album2 = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            user.getAlbums().add(album2);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            Comment comment = new Comment(album2.getLikeAndDislike(), user, COMMENT, album2, null);
            comment.setLikeAndDislike(likeAndDislike);
            list.add(comment);
            return list;
        }

        public ArrayList<Comment> getComments(File file) {
            ArrayList<Comment> list = new ArrayList<Comment>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album2 = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            user.getAlbums().add(album2);
            File file2 = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album2);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            Comment comment = new Comment(album2.getLikeAndDislike(), user, COMMENT, null, file2);
            comment.setLikeAndDislike(likeAndDislike);
            list.add(comment);
            return list;
        }

        public ArrayList<Comment> getCommentsPaging(Album album,
                int first, int count) {
            ArrayList<Comment> list = new ArrayList<Comment>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album2 = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            user.getAlbums().add(album2);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            Comment comment = new Comment(album2.getLikeAndDislike(), user, COMMENT, album2, null);
            comment.setLikeAndDislike(likeAndDislike);
            list.add(comment);
            return list;
        }

        public ArrayList<Comment> getCommentsPaging(File file,
                int first, int count) {
            ArrayList<Comment> list = new ArrayList<Comment>();
            User user = new User(1, USER_EMAIL_EXIST, USER_PASS_YES);
            Album album2 = new Album(1, ALBUM_NAME_EXIST, user,
                    null, null, PrivacyLevel.PRIVATE);
            user.getAlbums().add(album2);
            File file2 = new File(1, FILE_NAME_EXIST, new byte[1], new byte[1], album2);
            LikeAndDislike likeAndDislike = new LikeAndDislike();
            likeAndDislike.setId(50);
            Comment comment = new Comment(album2.getLikeAndDislike(), user, COMMENT, null, file2);
            comment.setLikeAndDislike(likeAndDislike);
            list.add(comment);
            return list;
        }
        
    };
}
