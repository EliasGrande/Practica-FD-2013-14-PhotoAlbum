package es.udc.fi.dc.photoalbum.util.dto;

import java.util.Arrays;

public class FileDto {
    private Integer id;
    private String name;
    private byte[] fileSmall;

    public FileDto() {

    }

    public FileDto(Integer id, String name, byte[] fileSmall) {
        this.id = id;
        this.name = name;
        this.fileSmall = Arrays.copyOf(fileSmall, fileSmall.length);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFileSmall() {
        return fileSmall;
    }

    public void setFileSmall(byte[] fileSmall) {
        this.fileSmall = fileSmall;
    }

    @Override
    public String toString() {
        return "FileDto [id=" + id + ", name=" + name
                + ", fileSmall=" + Arrays.toString(fileSmall) + "]";
    }

}
