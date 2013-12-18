package es.udc.fi.dc.photoalbum.webapp.restclient.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Class DTO from an Album object with JAX annotation.
 */
@XmlRootElement(name = "album")
@XmlType(name = "albumType", propOrder = { "id", "name" })
@XmlAccessorType(XmlAccessType.FIELD)
public class AlbumDtoJax {
    /**
     * The id of the AlbumDtoJax.
     */
    @XmlElement(name = "album-id", required = true)
    private Integer id;
    /**
     * The name of the AlbumDtoJax.
     */
    @XmlElement(required = true)
    private String name;

    /**
     * Empty constructor for AlbumDtoJax.
     */
    public AlbumDtoJax() {

    }

    /**
     * Constructor for AlbumDtoJax.
     * 
     * @param id
     *            Integer {@link #id}
     * @param name
     *            String {@link #name}
     */
    public AlbumDtoJax(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Obtains the {@link #id} of the AlbumDtoJax.
     * 
     * @return Integer The id of the AlbumDtoJax.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set an {@link #id} to an AlbumDtoJax.
     * 
     * @param id
     *            The id to set to the AlbumDtoJax.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtains the {@link #name} of the AlbumDtoJax.
     * 
     * @return String The name of the AlbumDtoJax.
     */
    public String getName() {
        return name;
    }

    /**
     * Set an {@link #name} to an AlbumDtoJax.
     * 
     * @param name
     *            The {@link #name} to set to the AlbumDtoJax.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AlbumDto [id=" + id + ", name=" + name + "]";
    }
}
