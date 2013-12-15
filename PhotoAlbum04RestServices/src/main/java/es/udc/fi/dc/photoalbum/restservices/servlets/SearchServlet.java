package es.udc.fi.dc.photoalbum.restservices.servlets;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.spring.FileService;
import es.udc.fi.dc.photoalbum.restservices.util.FileToFileDtoConversor;
import es.udc.fi.dc.photoalbum.util.dto.AlbumDto;
import es.udc.fi.dc.photoalbum.util.dto.FileDto;
import es.udc.fi.dc.photoalbum.util.dto.ResultDto;

@Path("/search")
public class SearchServlet {

    @Autowired
    FileService fileService;

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ResultDto getMsg(@QueryParam("type") String type,
            @QueryParam("by") String by) {
        if (type == null) {
            return new ResultDto("Type param must be necessary");
        }
        switch (type) {

            case "file":
                return new ResultDto();
            case "album":
                return new ResultDto();
            case "all":
                return new ResultDto();
            /*localhost:8080/PhotoAlbum04/search?type=hottest-pics*/
            case "hottest-pics":
                // FIXME Ajustar el
                List<File> files = fileService
                        .getFiles("LIKE", 0, 10);

                return new ResultDto(new ArrayList<AlbumDto>(),
                        FileToFileDtoConversor.toFileDto(files));
            /*Any incorrect type*/
            default:
                return new ResultDto(
                        "Especific a correct type: file, album, all or hottest-pics");
        }

    }
}
