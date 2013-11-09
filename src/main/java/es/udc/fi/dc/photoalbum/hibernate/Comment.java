package es.udc.fi.dc.photoalbum.hibernate;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "COMMENT")
@SuppressWarnings("serial")
public class Comment implements Serializable {

    private Integer id;
    private LikeAndDislike likeAndDislike;
    private User user;
    private Calendar date;
    private String text;
    private Album album;
    private File file;

    public static final int MAX_TEXT_LENGTH = 500;

    public Comment() {
        this.date = Calendar.getInstance();
    }

    public Comment(LikeAndDislike likeAndDislike, User user,
            String text, Album album, File file) {
        this.likeAndDislike = likeAndDislike;
        this.user = user;
        this.text = text;
        this.album = album;
        this.file = file;
        this.date = Calendar.getInstance();
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "LIKE_DISLIKE_ID")
    public LikeAndDislike getLikeAndDislike() {
        return likeAndDislike;
    }

    public void setLikeAndDislike(LikeAndDislike likeAndDislike) {
        this.likeAndDislike = likeAndDislike;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Column(name = "TEXT")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // El optional dice que Album puede ser null
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "ALBUM_ID")
    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
