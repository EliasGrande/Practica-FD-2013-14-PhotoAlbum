package es.udc.fi.dc.photoalbum.mocks;

import java.util.ArrayList;
import java.util.List;

import es.udc.fi.dc.photoalbum.util.dto.FileDto;
import es.udc.fi.dc.photoalbum.webapp.restclient.RestClientSearchService;

public class RestClientSearchServiceMock {

    public static RestClientSearchService mock = new RestClientSearchServiceMockImpl();

    private static class RestClientSearchServiceMockImpl implements
            RestClientSearchService {

        public RestClientSearchServiceMockImpl() {
        }

        @Override
        public List<FileDto> getHottestPics() {
            return new ArrayList<FileDto>();
        }

        @Override
        public List<FileDto> getHottestPicsPaging(int first, int count) {
            return new ArrayList<FileDto>();
        }
    }
}
