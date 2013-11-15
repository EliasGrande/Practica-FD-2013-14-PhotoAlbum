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

/**
 * Hibernate entity for storing comment data.
 */
@Entity
@Table(name = "COMMENT")
@SuppressWarnings("serial")
public class Comment implements Serializable {

    /**
     * @see #getId()
     */
    private Integer id;

    /**
     * @see #getLikeAndDislike()
     */
    private LikeAndDislike likeAndDislike;

    /**
     * @see #getUser()
     */
    private User user;

    /**
     * @see #getDate()
     */
    private Calendar date;

    /**
     * @see #getText()
     */
    private String text;

    /**
     * @see #getAlbum()
     */
    private Album album;

    /**
     * @see #getFile()
     */
    private File file;

    /**
     * Maximum allowed length of the {@link #getText() text}.
     */
    public static final int MAX_TEXT_LENGTH = 500;

    /**
     * Almost empty constructor, it only sets the {@link #getDate()
     * date} to the current date.
     */
    public Comment() {
        this.date = Calendar.getInstance();
    }

    /**
     * Defines a {@link Comment} object setting its main properties.
     * 
     * @param likeAndDislike
     *            {@link #getLikeAndDislike() Vote count info}
     * @param user
     *            {@link #getUser() Owner user}
     * @param text
     *            {@link #getText() Text content}
     * @param album
     *            {@link #getAlbum() Commented album}, or {@code null}
     *            for a file comment
     * @param file
     *            {@link #getFile() Commented file}, or {@code null}
     *            for an album comment
     */
    public Comment(LikeAndDislike likeAndDislike, User user,
            String text, Album album, File file) {
        this.likeAndDislike = likeAndDislike;
        this.user = user;
        this.text = text;
        this.album = album;
        this.file = file;
        this.date = Calendar.getInstance();
    }

    /**
     * Unique auto-incremental numeric identifier.
     * 
     * @return Unique id
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    /**
     * Setter for {@link #getId() id}.
     * 
     * @param id
     *            New id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Vote count information.
     * 
     * @return Vote count information
     */
    @OneToOne
    @JoinColumn(name = "LIKE_DISLIKE_ID")
    public LikeAndDislike getLikeAndDislike() {
        return likeAndDislike;
    }

    /**
     * Setter for {@link #getLikeAndDislike() likeAndDislike}.
     * 
     * @param likeAndDislike
     *            New vote count information
     */
    public void setLikeAndDislike(LikeAndDislike likeAndDislike) {
        this.likeAndDislike = likeAndDislike;
    }

    /**
     * Owner user.
     * 
     * @return Owner
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    /**
     * Setter for {@link #getUser() user}.
     * 
     * @param user
     *            New owner
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Creation date.
     * 
     * @return Creation date
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    public Calendar getDate() {
        return date;
    }

    /**
     * Setter for {@link #getDate() date}.
     * 
     * @param date
     *            New creation date
     */
    public void setDate(Calendar date) {
        this.date = date;
    }

    /**
     * Text content.
     * 
     * @return Text
     */
    @Column(name = "TEXT")
    public String getText() {
        return text;
    }

    /**
     * Setter for {@link #getText() text}.
     * 
     * @param text
     *            New text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * The comment is attached to this album, it's null if the comment
     * is attached to a file.
     * 
     * @return Commented album, or {@code null} for a file comment
     */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "ALBUM_ID")
    public Album getAlbum() {
        return album;
    }

    /**
     * Setter for {@link #getAlbum() album}.
     * 
     * @param album
     *            New album
     */
    public void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * The comment is attached to this file, it's null if the comment
     * is attached to an album.
     * 
     * @return Commented file, or {@code null} for an album comment
     */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    public File getFile() {
        return file;
    }

    /**
     * Setter for {@link #getFile() file}.
     * 
     * @param file
     *            New file
     */
    public void setFile(File file) {
        this.file = file;
    }

}
