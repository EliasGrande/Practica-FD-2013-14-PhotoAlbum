package es.udc.fi.dc.photoalbum.util.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "result")
@XmlType(name = "resultDto", propOrder = { "albumDtos", "fileDtos" })
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultDto {
    @XmlElement(name = "album-dtos")
    private List<AlbumDto> albumDtos;
    @XmlElement(name = "file-dtos")
    private List<FileDto> fileDtos;
    @XmlElement
    private String error;

    public ResultDto() {
    }
    
    public ResultDto(String error){
        this.error = error;
    }
    
    public ResultDto(List<AlbumDto> albumDtos, List<FileDto> fileDtos) {
        this.albumDtos = albumDtos;
        this.fileDtos = fileDtos;
    }

    public List<AlbumDto> getAlbumDtos() {
        return albumDtos;
    }

    public void setAlbumDtos(List<AlbumDto> albumDtos) {
        this.albumDtos = albumDtos;
    }

    public List<FileDto> getFileDtos() {
        return fileDtos;
    }

    public void setFileDtos(List<FileDto> fileDtos) {
        this.fileDtos = fileDtos;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
