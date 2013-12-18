package es.udc.fi.dc.photoalbum.webapp.restclient.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Class DTO that constains a list of {@link AlbumDtoJax} and/or
 * {@link FileDtoJax}, or a description of an error.
 */
@XmlRootElement(name = "result")
@XmlType(name = "resultDto", propOrder = { "albumDtos", "fileDtos", "error" })
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultDtoJax {
    /**
     * The list of {@link AlbumDtoJax}.
     */
    @XmlElement(name = "album-dtos")
    private List<AlbumDtoJax> albumDtos;
    /**
     * The list of {@link FileDtoJax}.
     */
    @XmlElement(name = "file-dtos")
    private List<FileDtoJax> fileDtos;
    /**
     * The description of error, if there is.
     */
    @XmlElement
    private String error;
    /**
     * An empty constructor for ResultDto.
     */
    public ResultDtoJax() {
    }
    /**
     * Constructor for an error ResultDtoJax.
     * 
     * @param error
     *            The description of error produced.
     */
    public ResultDtoJax(String error){
        this.error = error;
    }
    /**
     * Constructor for a correct ResultDtoJax.
     * 
     * @param albumDtos
     *            {@link #albumDtosJax}.
     * @param fileDtos
     *            {@link #fileDtosJax}.
     */
    public ResultDtoJax(List<AlbumDtoJax> albumDtos, List<FileDtoJax> fileDtos) {
        this.albumDtos = albumDtos;
        this.fileDtos = fileDtos;
    }
    /**
     * Obtains the {@link #albumDtosJax} of a ResultDtoJax.
     * 
     * @return List<AlbumDto> The {@link #albumDtosJax} of the
     *         ResultDtoJax.
     */
    public List<AlbumDtoJax> getAlbumDtos() {
        return albumDtos;
    }
    /**
     * Set an {@link #albumDtosJax} to a ResultDtoJax.
     * 
     * @param albumDtos
     *            The {@link #albumDtosJax} to set to the
     *            ResultDtoJax.
     */
    public void setAlbumDtos(List<AlbumDtoJax> albumDtos) {
        this.albumDtos = albumDtos;
    }
    /**
     * Obtains the {@link #fileDtosJax} of a FileDtoJax.
     * 
     * @return List<FileDto> The {@link #fileDtosJax} of the
     *         ResultDtoJax.
     */
    public List<FileDtoJax> getFileDtos() {
        return fileDtos;
    }
    /**
     * Set an {@link #fileDtosJax} to a ResultDtoJax.
     * 
     * @param fileDtos
     *            The {@link #fileDtosJax} to set to the ResultDtoJax.
     */
    public void setFileDtos(List<FileDtoJax> fileDtos) {
        this.fileDtos = fileDtos;
    }
    /**
     * Obtains the {@link #error} of a FileDtoJax.
     * 
     * @return String The {@link #error} of the ResultDtoJax.
     */
    public String getError() {
        return error;
    }
    /**
     * Set an {@link #error} to a ResultDtoJax.
     * 
     * @param error
     *            The {@link #error} to set to the ResultDtoJax.
     */
    public void setError(String error) {
        this.error = error;
    }

}
