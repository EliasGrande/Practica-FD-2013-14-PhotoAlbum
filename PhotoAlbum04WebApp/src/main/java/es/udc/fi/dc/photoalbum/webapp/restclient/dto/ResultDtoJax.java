package es.udc.fi.dc.photoalbum.webapp.restclient.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "result")
@XmlType(name = "resultDto", propOrder = { "albumDtos", "fileDtos", "error" })
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultDtoJax {
    @XmlElement(name = "album-dtos")
    private List<AlbumDtoJax> albumDtos;
    @XmlElement(name = "file-dtos")
    private List<FileDtoJax> fileDtos;
    @XmlElement
    private String error;

    public ResultDtoJax() {
    }
    
    public ResultDtoJax(String error){
        this.error = error;
    }
    
    public ResultDtoJax(List<AlbumDtoJax> albumDtos, List<FileDtoJax> fileDtos) {
        this.albumDtos = albumDtos;
        this.fileDtos = fileDtos;
    }

    public List<AlbumDtoJax> getAlbumDtos() {
        return albumDtos;
    }

    public void setAlbumDtos(List<AlbumDtoJax> albumDtos) {
        this.albumDtos = albumDtos;
    }

    public List<FileDtoJax> getFileDtos() {
        return fileDtos;
    }

    public void setFileDtos(List<FileDtoJax> fileDtos) {
        this.fileDtos = fileDtos;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
