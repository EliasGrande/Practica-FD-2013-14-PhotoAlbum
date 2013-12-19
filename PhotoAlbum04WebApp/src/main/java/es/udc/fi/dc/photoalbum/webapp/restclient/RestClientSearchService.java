package es.udc.fi.dc.photoalbum.webapp.restclient;

import java.util.List;

import es.udc.fi.dc.photoalbum.util.dto.FileDto;

public interface RestClientSearchService {
    
    public List<FileDto> getHottestPics();
    
    public List<FileDto> getHottestPicsPaging(int first, int count);

}
