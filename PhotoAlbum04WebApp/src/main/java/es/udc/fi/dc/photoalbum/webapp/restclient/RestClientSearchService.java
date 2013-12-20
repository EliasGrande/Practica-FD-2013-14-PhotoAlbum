package es.udc.fi.dc.photoalbum.webapp.restclient;

import java.util.List;

import es.udc.fi.dc.photoalbum.util.dto.FileDto;

/**
 * Client interface to use the Rest Service.
 */
public interface RestClientSearchService {
    
    /**
     * Method getHottestPics, obtains a concret number of pics..
     * 
     * @return List<FileDto> The list that contains the pics.
     */
    List<FileDto> getHottestPics();
    
    /**
     * Method that obtains the hottest-pics at paginated form.
     * 
     * @param first
     *            The first element to return.
     * @param count
     *            The number of elements to return.
     * @return List<FileDto> A list with the hottest-pics.
     */
    List<FileDto> getHottestPicsPaging(int first, int count);

}
