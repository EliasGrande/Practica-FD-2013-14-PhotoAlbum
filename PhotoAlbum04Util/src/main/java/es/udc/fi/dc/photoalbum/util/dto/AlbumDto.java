package es.udc.fi.dc.photoalbum.util.dto;

public class AlbumDto {

    private Integer id;
    private String name;

    public AlbumDto() {

    }

    public AlbumDto(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "AlbumDto [id=" + id + ", name=" + name + "]";
    }
}
