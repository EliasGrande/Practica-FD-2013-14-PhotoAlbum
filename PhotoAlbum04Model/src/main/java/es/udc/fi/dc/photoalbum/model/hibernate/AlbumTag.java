package es.udc.fi.dc.photoalbum.model.hibernate;

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
 * Hibernate entity for storing an album tag.
 */
@Entity
@Table(name = "ALBUM_TAG")
@SuppressWarnings("serial")
public class AlbumTag implements Serializable {

    /**
     * @see #getId()
     */
    private Integer id;

    /**
     * @see #getAlbum()
     */
    private Album album;

    /**
     * @see #getTag()
     */
    private String tag;

    /**
     * The empty constructor necessary to spring.
     */
    public AlbumTag() {
    }

    /**
     * Defines an {@link AlbumTag} setting its main properties.
     * 
     * @param album
     *            {@link #getAlbum() Album being tagged}
     * @param tag
     *            {@link #getTag() Keyword}
     */
    public AlbumTag(Album album, String tag) {
        this.album = album;
        this.tag = tag;
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
     *            Unique id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Album tagged by the {@link #getTag() tag}.
     * 
     * @return Tagged album
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
     *            Album being tagged
     */
    public void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * Keyword or term which helps describe the {@link #getAlbum()
     * album} and allows it to be found by tag searching.
     * 
     * @return Keyword
     */
    @Column(name = "TAG")
    public String getTag() {
        return tag;
    }

    /**
     * Setter for {@link #getTag() tag}.
     * 
     * @param tag
     *            Keyword
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}
