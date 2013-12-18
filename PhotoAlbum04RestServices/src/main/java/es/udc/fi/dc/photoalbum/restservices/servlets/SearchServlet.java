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
import es.udc.fi.dc.photoalbum.restservices.dto.AlbumDtoJax;
import es.udc.fi.dc.photoalbum.restservices.dto.ResultDtoJax;
import es.udc.fi.dc.photoalbum.restservices.util.AlbumToAlbumDtoJaxConversor;
import es.udc.fi.dc.photoalbum.restservices.util.FileToFileDtoJaxConversor;
import es.udc.fi.dc.photoalbum.restservices.util.ValidateParameters;

/**Servlet that allows to search files, albums or both, using diferents parameters to 
 * determine the items result.*/
@Component
@Path("/search")
public class SearchServlet {
    /**
     * One of the allowed findBy parameters.
     */
    private static final String NAME = "name";
    /**
     * One of the allowed findBy parameters.
     */
    private static final String TAG = "tag";
    /**
     * One of the allowed findBy parameters.
     */
    private static final String COMMENT = "comment";
    /**
     * One of the allowed orderBy parameters.
     */
    private static final String LIKE = "like";
    /**
     * @see FileService
     */
    @Autowired
    private FileService fileService;
    /**
     * @see AlbumService
     */
    @Autowired
    private AlbumService albumService;
    
    /**
     * Method that allows to realize the search.
     * @param type The type of search: file, album, all(album and files) or hottest-pics.
     * @param findBy The field in which you want to search the keywords.
     * @param keywords The words that must contain the search in findBy.
     * @param orderBy The sort order of the result.
     * @param dateFirst Allow to filter by date (begin limit). Are necessary both date.
     * @param dateEnd Allow to filter by date (end limit). Are necessary both date.
     * @param first The id of the first element to shown.
     * @param count The number of elements shown.
    
     * @return ResultDtoJax
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ResultDtoJax getMsg(
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
            return new ResultDtoJax(
                    "Url syntax error: Type param must be necessary");
        }
        if ((dateFirst != null) && (dateEnd != null)) {
            if ((ValidateParameters.validate(dateFirst))
                    && (ValidateParameters.validate(dateEnd))) {
                dateBeginC = ValidateParameters.toCalendar(dateFirst);
                dateEndC = ValidateParameters.toCalendar(dateEnd);
            } else {
                return new ResultDtoJax(
                        "Url syntax error: Incorrect format for date, the correct is dd/mm/yyyy");
            }
            if (!ValidateParameters.validateDates(dateBeginC,
                    dateEndC)) {
                return new ResultDtoJax(
                        "Url syntax error: The dateEnd are earlier than dateFirst");
            }
        }
        if (!ValidateParameters.validateOrderBy(orderBy)) {
            return new ResultDtoJax(
                    "Url syntax error: The result can be order by date, like, dislike");
        }
        if (type.compareTo("album") == 0) {
            /* /search?type=album */
            if (ValidateParameters.validateFindByAndKeywords(findBy,
                    keywords)) {
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
                    albums = albumService.getAlbums(orderBy, first,
                            count);
                }
            }
            return new ResultDtoJax(
                    AlbumToAlbumDtoJaxConversor.toAlbumDto(albums),
                    FileToFileDtoJaxConversor.toFileDto(files));
            /* /search?type=file */
        } else if (type.compareTo("file") == 0) {
            if (ValidateParameters.validateFindByAndKeywords(findBy,
                    keywords)) {
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
                    files = fileService.getFiles(orderBy, dateBeginC,
                            dateEndC, first, count);
                } else {
                    files = fileService.getFiles(orderBy, first,
                            count);
                }
            }
            return new ResultDtoJax(
                    AlbumToAlbumDtoJaxConversor.toAlbumDto(albums),
                    FileToFileDtoJaxConversor.toFileDto(files));
        } else if (type.compareTo("all") == 0) {
            /* /search?type=all */
            if (ValidateParameters.validateFindByAndKeywords(findBy,
                    keywords)) {
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
                    files = fileService.getFiles(orderBy, dateBeginC,
                            dateEndC, first, count);
                } else {
                    albums = albumService.getAlbums(orderBy, first,
                            count);
                    files = fileService.getFiles(orderBy, first,
                            count);
                }
            }
            return new ResultDtoJax(
                    AlbumToAlbumDtoJaxConversor.toAlbumDto(albums),
                    FileToFileDtoJaxConversor.toFileDto(files));
        } else if (type.compareTo("hottest-pics") == 0) {
            /*
             * localhost:8080/PhotoAlbum04/search?type=hottest-pics
             * &first=_&count=_
             */
            files = fileService.getFiles(LIKE, first, count);

            return new ResultDtoJax(new ArrayList<AlbumDtoJax>(),
                    FileToFileDtoJaxConversor.toFileDto(files));
            /* Any incorrect type */
        } else {
            return new ResultDtoJax(
                    "Url syntax error: Especific a correct type: "
                            + "file, album, all or hottest-pics");
        }

    }
}
