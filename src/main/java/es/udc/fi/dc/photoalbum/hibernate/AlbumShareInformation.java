package es.udc.fi.dc.photoalbum.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Hibernate entity for storing album share information data.
 */
@Entity
@Table(name = "ALBUM_SHARE_INFORMATION")
@SuppressWarnings("serial")
public class AlbumShareInformation implements Serializable {

    /**
     * @see #getId()
     */
    private Integer id;

    /**
     * @see #getAlbum()
     */
    private Album album;

    /**
     * @see #getUser()
     */
    private User user;

    /**
     * Empty constructor.
     */
    public AlbumShareInformation() {
    }

    /**
     * Defines an {@link AlbumShareInformation} setting its main
     * properties.
     * 
     * @param id
     *            {@link #getId() Unique id}
     * @param album
     *            {@link #getAlbum() Album being shared}
     * @param user
     *            {@link #getUser() Receiver of the sharing}
     */
    public AlbumShareInformation(Integer id, Album album, User user) {
        this.id = id;
        this.album = album;
        this.user = user;
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
     * Album which is being shared to the {@link #getUser() user}.
     * 
     * @return Album being shared
     */
    @ManyToOne
    @JoinColumn(name = "ALBUM_ID")
    public Album getAlbum() {
        return album;
    }

    /**
     * Setter for {@link #getAlbum() album}.
     * 
     * @param album
     *            Album being shared
     */
    public void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * User with whom the {@link #getAlbum() album} has been shared.
     * 
     * @return Receiver of the sharing
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
     *            Receiver of the sharing
     */
    public void setUser(User user) {
        this.user = user;
    }
}
