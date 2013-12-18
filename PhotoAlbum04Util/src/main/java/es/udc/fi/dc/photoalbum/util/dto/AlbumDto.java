package es.udc.fi.dc.photoalbum.util.dto;

/**
 * Class DTO from an Album object.
 */
public class AlbumDto {
    /**
     * The id of the AlbumDto.
     */
    private Integer id;
    /**
     * The name of the AlbumDto.
     */
    private String name;

    /**
     * Empty constructor for AlbumDto.
     */
    public AlbumDto() {

    }

    /**
     * Constructor for AlbumDto.
     * 
     * @param id
     *            Integer {@link #id}
     * @param name
     *            String {@link #name}
     */
    public AlbumDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Obtains the {@link #id} of the AlbumDto.
     * 
     * @return Integer The id of the AlbumDto.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set an {@link #id} to an AlbumDto.
     * 
     * @param id
     *            The id to set to the AlbumDto.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtains the {@link #name} of the AlbumDto.
     * 
     * @return String The name of the AlbumDto.
     */
    public String getName() {
        return name;
    }

    /**
     * Set an {@link #name} to an AlbumDto.
     * 
     * @param name
     *            The {@link #name} to set to the AlbumDto.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AlbumDto [id=" + id + ", name=" + name + "]";
    }
}
