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
 * Hibernate entity for storing file share information data.
 */
@Entity
@Table(name = "FILE_SHARE_INFORMATION")
@SuppressWarnings("serial")
public class FileShareInformation implements Serializable {

    /**
     * @see #getId()
     */
    private Integer id;

    /**
     * @see #getFile()
     */
    private File file;

    /**
     * @see #getUser()
     */
    private User user;

    /**
     * Empty constructor.
     */
    public FileShareInformation() {
    }

    /**
     * Defines an {@link FileShareInformation} setting its main
     * properties.
     * 
     * @param id
     *            {@link #getId() Unique id}
     * @param file
     *            {@link #getFile() File being shared}
     * @param user
     *            {@link #getUser() Receiver of the sharing}
     */
    public FileShareInformation(Integer id, File file, User user) {
        this.id = id;
        this.file = file;
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
     * File which is being shared to the {@link #getUser() user}.
     * 
     * @return File being shared
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
     *            File being shared
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * User with whom the {@link #getFile() file} has been shared.
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
