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
 * Hibernate entity for storing an album tag.
 */
@Entity
@Table(name = "FILE_TAG")
@SuppressWarnings("serial")
public class FileTag implements Serializable {

    /**
     * @see {@link #getId()}
     */
    private Integer id;

    /**
     * @see {@link #getFile()}
     */
    private File file;

    /**
     * @see {@link #getTag()}
     */
    private String tag;

    /**
     * Empty constructor.
     */
    public FileTag() {
    }

    /**
     * Defines an {@link FileTag} setting its main properties.
     * 
     * @param file
     *            {@link #getFile() File being tagged}
     * @param tag
     *            {@link #getTag() Keyword}
     */
    public FileTag(File file, String tag) {
        this.file = file;
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
     * File tagged by the {@link #getTag() tag}.
     * 
     * @return Tagged file
     */
    @ManyToOne
    @JoinColumn(name = "FILE_ID")
    public File getFile() {
        return file;
    }

    /**
     * Setter for {@link #getFile() file}.
     * 
     * @param file
     *            File being tagged
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Keyword or term which helps describe the {@link #getFile()
     * file} and allows it to be found by tag searching.
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
