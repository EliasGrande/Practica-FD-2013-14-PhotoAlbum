package es.udc.fi.dc.photoalbum.restservices.servlets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.hibernate.File;
import es.udc.fi.dc.photoalbum.model.spring.AlbumService;
import es.udc.fi.dc.photoalbum.model.spring.FileService;
import es.udc.fi.dc.photoalbum.restservices.util.AlbumToAlbumDtoConversor;
import es.udc.fi.dc.photoalbum.restservices.util.FileToFileDtoConversor;
import es.udc.fi.dc.photoalbum.restservices.util.ValidateParameters;
import es.udc.fi.dc.photoalbum.util.dto.AlbumDto;
import es.udc.fi.dc.photoalbum.util.dto.ResultDto;

@Component
@Path("/search")
public class SearchServlet {
    private final static String NAME = "name";
    private final static String TAG = "tag";
    private final static String COMMENT = "comment";

    @Autowired
    FileService fileService;

    @Autowired
    AlbumService albumService;

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ResultDto getMsg(
            @QueryParam("type") String type,
            @QueryParam("findBy") List<String> findBy,
            @QueryParam("keywords") String keywords,
            @DefaultValue("date") @QueryParam("orderBy") String orderBy,
            @QueryParam("dateFirst") String dateFirst,
            @QueryParam("dateEnd") String dateEnd,
            @DefaultValue("0") @QueryParam("first") Integer first,
            @DefaultValue("10") @QueryParam("count") Integer count) {

        Calendar dateBeginC = null;
        Calendar dateEndC = null;
        List<Album> albums = new ArrayList<Album>();
        List<File> files = new ArrayList<File>();

        /* Comprueba parametros */
        if (type == null) {
            return new ResultDto("Type param must be necessary");
        }
        if ((dateFirst != null) && (dateEnd != null)) {
            if ((ValidateParameters.validate(dateFirst))
                    && (ValidateParameters.validate(dateEnd))) {
                dateBeginC = ValidateParameters.toCalendar(dateFirst);
                dateEndC = ValidateParameters.toCalendar(dateEnd);
            } else {
                return new ResultDto(
                        "Incorrect format for date, the correct is dd/mm/yyyy");
            }
            if (!ValidateParameters.validateDates(dateBeginC,
                    dateEndC)) {
                return new ResultDto(
                        "The dateEnd are earlier than dateFirst.");
            }
        }
        if (!ValidateParameters.validateOrderBy(orderBy)) {
            return new ResultDto(
                    "The result can be order by date, like, dislike");
        }
        fileService.getAlbumFilesOwn(1);
        switch (type) {
        /* /search?type=album */
            case "album":
                if (ValidateParameters.validateFindByAndKeywords(
                        findBy, keywords)) {
                    if (dateBeginC != null) {
                        albums = albumService.getAlbums(keywords,
                                findBy.contains(NAME),
                                findBy.contains(COMMENT),
                                findBy.contains(TAG), orderBy,
                                dateBeginC, dateEndC, first, count);
                    } else {
                        albums = albumService.getAlbums(keywords,
                                findBy.contains(NAME),
                                findBy.contains(COMMENT),
                                findBy.contains(TAG), orderBy, first,
                                count);
                    }
                } else {
                    if (dateBeginC != null) {
                        albums = albumService.getAlbums(orderBy,
                                dateBeginC, dateEndC, first, count);
                    } else {
                        albums = albumService.getAlbums(orderBy,
                                first, count);
                    }
                }
                return new ResultDto(
                        AlbumToAlbumDtoConversor.toAlbumDto(albums),
                        FileToFileDtoConversor.toFileDto(files));
                /* /search?type=file */
            case "file":
                if (ValidateParameters.validateFindByAndKeywords(
                        findBy, keywords)) {
                    if (dateBeginC != null) {
                        files = fileService.getFiles(keywords,
                                findBy.contains(NAME),
                                findBy.contains(COMMENT),
                                findBy.contains(TAG), orderBy,
                                dateBeginC, dateEndC, first, count);
                    } else {
                        files = fileService.getFiles(keywords,
                                findBy.contains(NAME),
                                findBy.contains(COMMENT),
                                findBy.contains(TAG), orderBy, first,
                                count);
                    }
                } else {
                    if (dateBeginC != null) {
                        files = fileService.getFiles(orderBy,
                                dateBeginC, dateEndC, first, count);
                    } else {
                        files = fileService.getFiles(orderBy, first,
                                count);
                    }
                }
                return new ResultDto(
                        AlbumToAlbumDtoConversor.toAlbumDto(albums),
                        FileToFileDtoConversor.toFileDto(files));

                /* /search?type=all */
            case "all":
                if (ValidateParameters.validateFindByAndKeywords(
                        findBy, keywords)) {
                    if (dateBeginC != null) {
                        albums = albumService.getAlbums(keywords,
                                findBy.contains(NAME),
                                findBy.contains(COMMENT),
                                findBy.contains(TAG), orderBy,
                                dateBeginC, dateEndC, first, count);
                        files = fileService.getFiles(keywords,
                                findBy.contains(NAME),
                                findBy.contains(COMMENT),
                                findBy.contains(TAG), orderBy,
                                dateBeginC, dateEndC, first, count);
                    } else {
                        albums = albumService.getAlbums(keywords,
                                findBy.contains(NAME),
                                findBy.contains(COMMENT),
                                findBy.contains(TAG), orderBy, first,
                                count);
                        files = fileService.getFiles(keywords,
                                findBy.contains(NAME),
                                findBy.contains(COMMENT),
                                findBy.contains(TAG), orderBy, first,
                                count);
                    }
                } else {
                    if (dateBeginC != null) {
                        albums = albumService.getAlbums(orderBy,
                                dateBeginC, dateEndC, first, count);
                        files = fileService.getFiles(orderBy,
                                dateBeginC, dateEndC, first, count);
                    } else {
                        albums = albumService.getAlbums(orderBy,
                                first, count);
                        files = fileService.getFiles(orderBy, first,
                                count);
                    }
                }
                return new ResultDto(
                        AlbumToAlbumDtoConversor.toAlbumDto(albums),
                        FileToFileDtoConversor.toFileDto(files));
                /*
                 * localhost:8080/PhotoAlbum04/search?type=hottest-pics
                 * &first=_&count=_
                 */
            case "hottest-pics":
                files = fileService.getFiles("LIKE", first, count);

                return new ResultDto(new ArrayList<AlbumDto>(),
                        FileToFileDtoConversor.toFileDto(files));
                /* Any incorrect type */
            default:
                return new ResultDto(
                        "Especific a correct type: file, album, all or hottest-pics");
        }

    }
}
