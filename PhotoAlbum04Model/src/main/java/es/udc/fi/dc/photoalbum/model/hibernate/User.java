package es.udc.fi.dc.photoalbum.model.hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import es.udc.fi.dc.photoalbum.util.utils.MD5;

/**
 * Hibernate entity for storing user data.
 */
@Entity
@Table(name = "USUARIO")
@SuppressWarnings("serial")
public class User implements Serializable {

    /**
     * @see #getId()
     */
    private Integer id;

    /**
     * @see #getEmail()
     */
    private String email;

    /**
     * @see #getPassword()
     */
    private String password;

    /**
     * @see #getAlbums()
     */
    private Set<Album> albums = new HashSet<Album>();

    /**
     * @see #getShareInformation()
     */
    private Set<AlbumShareInformation> shareInformation = new HashSet<AlbumShareInformation>();

    /**
     * Empty constructor necessary to spring..
     */
    public User() {
    }

    /**
     * Defines a {@link User} setting its main properties.
     * 
     * @param id
     *            {@link #getId() Unique id}
     * @param email
     *            {@link #getEmail() E-mail}
     * @param password
     *            {@link #getPassword() Password}
     */
    public User(Integer id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    /**
     * {@link Album Albums} owned by the user.
     * 
     * @return User albums.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    public Set<Album> getAlbums() {
        return albums;
    }

    /**
     * Setter for {@link #getAlbums() albums}.
     * 
     * @param albums
     *            Albums
     */
    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    /**
     * {@link AlbumShareInformation} set of this user, to know the
     * albums shared to the user.
     * 
     * @return Share information set
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
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
     * E-mail.
     * 
     * @return E-mail
     */
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    /**
     * Setter for {@link #getEmail() email}.
     * 
     * @param email
     *            New email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Password, exactly as it was settled, this class doesn't
     * encrypt/decrypt it.
     * 
     * @return Password
     */
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    /**
     * Setter for {@link #getPassword() password}.
     * <p>
     * It should be stored encrypted, using
     * {@link MD5#getHash(String) md5} or any other hash function, but
     * this method don't take care about doing so.
     * 
     * @param password
     *            New password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
