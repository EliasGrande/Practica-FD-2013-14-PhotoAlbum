package es.udc.fi.dc.photoalbum.util.dto;

import java.util.List;

/**
 * Class DTO that constains a list of {@link AlbumDto} and/or
 * {@link FileDto}, or a description of an error.
 */
public class ResultDto {
    /**
     * The list of {@link AlbumDto}.
     */
    private List<AlbumDto> albumDtos;
    /**
     * The list of {@link FileDto}.
     */
    private List<FileDto> fileDtos;
    /**
     * The description of error, if there is.
     */
    private String error;

    /**
     * An empty constructor for ResultDto.
     */
    public ResultDto() {
    }

    /**
     * Constructor for an error ResultDto.
     * 
     * @param error
     *            The description of error produced.
     */
    public ResultDto(String error) {
        this.error = error;
    }

    /**
     * Constructor for a correct ResultDto.
     * 
     * @param albumDtos
     *            {@link #albumDtos}.
     * @param fileDtos
     *            {@link #fileDtos}.
     */
    public ResultDto(List<AlbumDto> albumDtos, List<FileDto> fileDtos) {
        this.albumDtos = albumDtos;
        this.fileDtos = fileDtos;
    }

    /**
     * Obtains the {@link #albumDtos} of a ResultDto.
     * 
     * @return List<AlbumDto> The {@link #albumDtos} of the ResultDto.
     */
    public List<AlbumDto> getAlbumDtos() {
        return albumDtos;
    }

    /**
     * Set an {@link #albumDtos} to a ResultDto.
     * 
     * @param albumDtos
     *            The {@link #albumDtos} to set to the ResultDto.
     */
    public void setAlbumDtos(List<AlbumDto> albumDtos) {
        this.albumDtos = albumDtos;
    }

    /**
     * Obtains the {@link #fileDtos} of a FileDto.
     * 
     * @return List<FileDto> The {@link #fileDtos} of the ResultDto.
     */
    public List<FileDto> getFileDtos() {
        return fileDtos;
    }

    /**
     * Set an {@link #fileDtos} to a ResultDto.
     * 
     * @param fileDtos
     *            The {@link #fileDtos} to set to the ResultDto.
     */
    public void setFileDtos(List<FileDto> fileDtos) {
        this.fileDtos = fileDtos;
    }

    /**
     * Obtains the {@link #error} of a FileDto.
     * 
     * @return String The {@link #error} of the ResultDto.
     */
    public String getError() {
        return error;
    }

    /**
     * Set an {@link #error} to a ResultDto.
     * 
     * @param error
     *            The {@link #error} to set to the ResultDto.
     */
    public void setError(String error) {
        this.error = error;
    }

}
