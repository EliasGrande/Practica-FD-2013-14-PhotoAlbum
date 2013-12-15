package es.udc.fi.dc.photoalbum.util.dto;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import es.udc.fi.dc.photoalbum.util.utils.ComparableById;

@XmlRootElement(name = "file")
@XmlType(name = "fileType", propOrder = { "id", "name", "fileSmall" })
@XmlAccessorType(XmlAccessType.FIELD)
public class FileDto {
    @XmlElement(name = "file-id", required = true)
    private Integer id;
    @XmlElement(required = true)
    private String name;
    @XmlElement(name = "file-small", required = true)
    private byte[] fileSmall;

    public FileDto(){
        
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
