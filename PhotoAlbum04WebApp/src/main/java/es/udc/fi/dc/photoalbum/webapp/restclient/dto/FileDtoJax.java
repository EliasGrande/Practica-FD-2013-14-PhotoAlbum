package es.udc.fi.dc.photoalbum.webapp.restclient.dto;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Class DTO from an File object with JAX annotation.
 */
@XmlRootElement(name = "file")
@XmlType(name = "fileType", propOrder = { "id", "name", "fileSmall" })
@XmlAccessorType(XmlAccessType.FIELD)
public class FileDtoJax {
    /**
     * The id of the FileDtoJax.
     */
    @XmlElement(name = "file-id", required = true)
    private Integer id;
    /**
     * The name of the FileDtoJax.
     */
    @XmlElement(required = true)
    private String name;
    /**
     * An array that stored the File in small format.
     */
    @XmlElement(name = "file-small", required = true)
    private byte[] fileSmall;

    /**
     * The empty constructor for FileDtoJax to use the annotations.
     */
    public FileDtoJax() {
    }

    /**
     * Constructor for FileDtoJax.
     * 
     * @param id
     *            The {@link #id} of the FileDtoJax that want to
     *            create.
     * @param name
     *            The {@link #name} of the FileDtoJax that want to
     *            create.
     * @param fileSmall
     *            The {@link #fileSmall} of the FileDtoJax that want
     *            to create.
     */
    public FileDtoJax(Integer id, String name, byte[] fileSmall) {
        this.id = id;
        this.name = name;
        this.fileSmall = Arrays.copyOf(fileSmall, fileSmall.length);
    }

    /**
     * Obtains the {@link #id} of a FileDtoJax.
     * 
     * @return Integer The {@link #id} of the FileDtoJax.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set an {@link #id} to a FileDtoJax.
     * 
     * @param id
     *            The id to set to the FileDtoJax.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtains the {@link #name} of a FileDtoJax.
     * 
     * @return String The {@link #name} of the FileDtoJax.
     */
    public String getName() {
        return name;
    }

    /**
     * Set an {@link #name} to a FileDtoJax.
     * 
     * @param name
     *            The {@link #name} to set to the FileDtoJax.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtains the {@link #fileSmall} of a FileDtoJax.
     * 
     * @return byte[] The {@link #fileSmall} of the FileDtoJax.
     */
    public byte[] getFileSmall() {
        return Arrays.copyOf(fileSmall, fileSmall.length);
    }

    /**
     * Set an {@link #fileSmall} to a FileDtoJax.
     * 
     * @param fileSmall
     *            The {@link #fileSmall} to set to the FileDtoJax.
     */
    public void setFileSmall(byte[] fileSmall) {
        this.fileSmall = Arrays.copyOf(fileSmall, fileSmall.length);
    }

    @Override
    public String toString() {
        return "FileDto [id=" + id + ", name=" + name
                + ", fileSmall=" + Arrays.toString(fileSmall) + "]";
    }

}
