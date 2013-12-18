package es.udc.fi.dc.photoalbum.util.dto;

import java.util.Arrays;

/**
 * Class DTO from an File object.
 */
public class FileDto {
    /**
     * The id of the FileDto.
     */
    private Integer id;
    /**
     * The name of the FileDto.
     */
    private String name;
    /**
     * An array that stored the File in small format.
     */
    private byte[] fileSmall;

    /**
     * Empty constructor for FileDto.
     */
    public FileDto() {
    }

    /**
     * Constructor for FileDto.
     * 
     * @param id
     *            The {@link #id} of the FileDto that want to create.
     * @param name
     *            The {@link #name} of the FileDto that want to
     *            create.
     * @param fileSmall
     *            The {@link #fileSmall} of the FileDto that want to
     *            create.
     */
    public FileDto(Integer id, String name, byte[] fileSmall) {
        this.id = id;
        this.name = name;
        this.fileSmall = Arrays.copyOf(fileSmall, fileSmall.length);
    }

    /**
     * Obtains the {@link #id} of a FileDto.
     * 
     * @return Integer The {@link #id} of the FileDto.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set an {@link #id} to a FileDto.
     * 
     * @param id
     *            The id to set to the FileDto.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtains the {@link #name} of a FileDto.
     * 
     * @return String The {@link #name} of the FileDto.
     */
    public String getName() {
        return name;
    }

    /**
     * Set an {@link #name} to a FileDto.
     * 
     * @param name
     *            The {@link #name} to set to the FileDto.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtains the {@link #fileSmall} of a FileDto.
     * 
     * @return byte[] The {@link #fileSmall} of the FileDto.
     */
    public byte[] getFileSmall() {
        return Arrays.copyOf(fileSmall, fileSmall.length);
    }

    /**
     * Set an {@link #fileSmall} to a FileDto.
     * 
     * @param fileSmall
     *            The {@link #fileSmall} to set to the FileDto.
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
