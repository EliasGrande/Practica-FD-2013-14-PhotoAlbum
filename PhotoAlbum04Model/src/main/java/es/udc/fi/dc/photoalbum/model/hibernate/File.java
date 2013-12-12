package es.udc.fi.dc.photoalbum.model.hibernate;

import java.io.Serializable;
import java.util.Arrays;
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

import es.udc.fi.dc.photoalbum.util.utils.PrivacyLevel;

/**
 * Hibernate entity for storing file data.
 */
@Entity
@Table(name = "ARCHIVO")
@SuppressWarnings("serial")
public class File implements Serializable, Comparable<File> {

    /**
     * @see #getId()
     */
    private Integer id;

    /**
     * @see #getName()
     */
    private String name;

    /**
     * @see #getPrivacyLevel()
     */
    private String privacyLevel;

    /**
     * @see #getFile()
     */
    private byte[] file;

    /**
     * @see #getFileSmall()
     */
    private byte[] fileSmall;

    /**
     * @see #getAlbum()
     */
    private Album album;

    /**
     * @see #getShareInformation()
     */
    private Set<FileShareInformation> shareInformation = new HashSet<FileShareInformation>();

    /**
     * @see #getLikeAndDislike()
     */
    private LikeAndDislike likeAndDislike;

    /**
     * Almost empty constructor, it only sets the
     * {@link #getPrivacyLevel() privacy level} to its default value,
     * {@link PrivacyLevel#PRIVATE private}.
     */
    public File() {
        privacyLevel = PrivacyLevel.INHERIT_FROM_ALBUM;
    }

    /**
     * Defines a {@link File} setting its main properties.
     * 
     * @param id
     *            {@link #getId() Unique id}
     * @param name
     *            {@link #getName() Display name}
     * @param file
     *            {@link #getFile() File data}
     * @param fileSmall
     *            {@link #getFileSmall() Thumbnail data}
     * @param album
     *            {@link #getAlbum() Album}
     */
    public File(Integer id, String name, byte[] file,
            byte[] fileSmall, Album album) {
        this.id = id;
        this.name = name;
        this.privacyLevel = PrivacyLevel.INHERIT_FROM_ALBUM;
        this.file = Arrays.copyOf(file, file.length);
        this.fileSmall = Arrays.copyOf(fileSmall, fileSmall.length);
        this.album = album;
    }

    /**
     * {@link AlbumShareInformation} set of the album.
     * 
     * @return Share information set
     */
    @OneToMany(mappedBy = "file", fetch = FetchType.LAZY)
    public Set<FileShareInformation> getShareInformation() {
        return shareInformation;
    }

    /**
     * Setter for {@link #getShareInformation() shareInformation}.
     * 
     * @param shareInformation
     *            New share information set
     */
    public void setShareInformation(
            Set<FileShareInformation> shareInformation) {
        this.shareInformation = shareInformation;
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
        return this.id;
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
        return this.name;
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
     * File data.
     * 
     * @return Data
     */
    @Column(name = "FILE")
    public byte[] getFile() {
        return file;
    }

    /**
     * Setter for {@link #getFile() file}.
     * 
     * @param file
     *            New data
     */
    public void setFile(byte[] file) {
        this.file = Arrays.copyOf(file, file.length);
    }

    /**
     * Small version of the data.
     * 
     * @return Thumbnail data
     */
    @Column(name = "FILE_SMALL")
    public byte[] getFileSmall() {
        return fileSmall;
    }

    /**
     * Setter for {@link #getFileSmall() fileSmall}.
     * 
     * @param fileSmall
     *            New thumbnail data
     */
    public void setFileSmall(byte[] fileSmall) {
        this.fileSmall = Arrays.copyOf(fileSmall, fileSmall.length);
    }

    /**
     * Album containing this file
     * 
     * @return Album
     */
    @ManyToOne
    @JoinColumn(name = "ALBUM_ID")
    public Album getAlbum() {
        return this.album;
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

    @Override
    public int compareTo(File otherFile) {
        return this.getId() - otherFile.getId();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result
                + ((name == null) ? 0 : name.hashCode());
        result = prime
                * result
                + ((privacyLevel == null) ? 0 : privacyLevel
                        .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        File other = (File) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (privacyLevel == null) {
            if (other.privacyLevel != null)
                return false;
        } else if (!privacyLevel.equals(other.privacyLevel))
            return false;
        return true;
    }
}
