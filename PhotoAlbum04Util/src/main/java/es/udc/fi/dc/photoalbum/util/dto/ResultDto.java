package es.udc.fi.dc.photoalbum.util.dto;

import java.util.List;

public class ResultDto {
    private List<AlbumDto> albumDtos;
    private List<FileDto> fileDtos;
    private String error;

    public ResultDto() {
    }

    public ResultDto(String error) {
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
