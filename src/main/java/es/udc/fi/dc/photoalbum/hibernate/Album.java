package es.udc.fi.dc.photoalbum.hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

/**
 * Hibernate entity for storing album data.
 */
@Entity
@Table(name = "ALBUM")
@SuppressWarnings("serial")
public class Album implements Serializable {

    /**
     * @see #getId()
     */
    private Integer id;

    /**
     * @see #getName()
     */
    private String name;

    /**
     * @see #getUser()
     */
    private User user;

    /**
     * @see #getPrivacyLevel()
     */
    private String privacyLevel;

    /**
     * @see #getFiles()
     */
    private Set<File> files = new HashSet<File>();

    /**
     * @see #getShareInformation()
     */
    private Set<AlbumShareInformation> shareInformation = new HashSet<AlbumShareInformation>();

    /**
     * @see #getLikeAndDislike()
     */
    private LikeAndDislike likeAndDislike;

    /**
     * Almost empty constructor, it only sets the
     * {@link #getPrivacyLevel() privacy level} to its default value,
     * {@link PrivacyLevel#PRIVATE private}.
     */
    public Album() {
        privacyLevel = PrivacyLevel.PRIVATE;
    }

    /**
     * Defines an {@link Album} setting its main properties.
     * 
     * @param id
     *            {@link #getId() Unique id}
     * @param name
     *            {@link #getName() Display name}
     * @param user
     *            {@link #getUser() Owner user}
     * @param files
     *            {@link #getFiles() File set}
     * @param shareInformation
     *            {@link #getShareInformation() Share information set}
     * @param privacyLevel
     *            {@link #getPrivacyLevel() Privacy level}
     */
    public Album(Integer id, String name, User user, Set<File> files,
            Set<AlbumShareInformation> shareInformation,
            String privacyLevel) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.privacyLevel = privacyLevel;
        this.files = files;
        this.shareInformation = shareInformation;
    }

    /**
     * {@link File} set of the album.
     * 
     * @return File set.
     */
    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    public Set<File> getFiles() {
        return files;
    }

    /**
     * Setter for {@link #getFiles() files}.
     * 
     * @param files
     *            New file set
     */
    public void setFiles(Set<File> files) {
        this.files = files;
    }

    /**
     * {@link AlbumShareInformation} set of the album.
     * 
     * @return Share information set
     */
    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    public Set<AlbumShareInformation> getShareInformation() {
        return shareInformation;
    }

    /**
     * Setter for {@link #getShareInformation() shareInformation}.
     * 
     * @param shareInformation
     *            New share information set
     */
    public void setShareInformation(
            Set<AlbumShareInformation> shareInformation) {
        this.shareInformation = shareInformation;
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
     * Privacy level.
     * 
     * @return Privacy level
     * @see PrivacyLevel
     */
    @Column(name = "PRIVACY_LEVEL")
    public String getPrivacyLevel() {
        return privacyLevel;
    }

    /**
     * Setter for {@link #getPrivacyLevel() privacyLevel}.
     * 
     * @param privacyLevel
     *            New privacy level
     */
    public void setPrivacyLevel(String privacyLevel) {
        this.privacyLevel = privacyLevel;
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
     * Display name.
     * 
     * @return Name
     */
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    /**
     * Setter for {@link #getName() name}.
     * 
     * @param name
     *            New name
     */
    public void setName(String name) {
        this.name = name;
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
}
