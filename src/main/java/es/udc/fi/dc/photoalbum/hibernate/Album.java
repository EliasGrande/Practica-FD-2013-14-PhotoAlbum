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
     * @see {@link #getId()}
     */
    private Integer id;

    /**
     * @see {@link #getName()}
     */
    private String name;

    /**
     * @see {@link #getUser()}
     */
    private User user;

    /**
     * @see {@link #getPrivacyLevel()}
     */
    private String privacyLevel;

    /**
     * @see {@link #getFiles()}
     */
    private Set<File> files = new HashSet<File>();

    /**
     * @see {@link #getShareInformation()}
     */
    private Set<AlbumShareInformation> shareInformation = new HashSet<AlbumShareInformation>();

    /**
     * @see {@link #getLikeAndDislike()}
     */
    private LikeAndDislike likeAndDislike;

    /**
     * Defines an {@link Album} setting its privacy level to
     * {@link PrivacyLevel#PRIVATE private}.
     */
    public Album() {
        privacyLevel = PrivacyLevel.PRIVATE;
    }

    /**
     * Defines an {@link Album} setting all its properties.
     * 
     * @param id
     *            {@link #getId() Unique id}
     * @param name
     *            {@link #getName() Display name}
     * @param user
     *            {@link #getUser() Owner}
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
     *            File set
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
     * @param user
     *            Share information set
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
     *            Owner
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Privacy level.
     * 
     * @return Privacy level
     * @see {@link PrivacyLevel}
     */
    @Column(name = "PRIVACY_LEVEL")
    public String getPrivacyLevel() {
        return privacyLevel;
    }

    /**
     * Setter for {@link #getPrivacyLevel() privacyLevel}.
     * 
     * @param id
     *            Unique id
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
     *            Unique id
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
     *            Display name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Vote information.
     * 
     * @return Vote information
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
     *            Vote information
     */
    public void setLikeAndDislike(LikeAndDislike likeAndDislike) {
        this.likeAndDislike = likeAndDislike;
    }
}
