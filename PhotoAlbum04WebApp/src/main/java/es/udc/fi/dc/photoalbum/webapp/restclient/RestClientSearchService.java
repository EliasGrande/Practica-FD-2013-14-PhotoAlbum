package es.udc.fi.dc.photoalbum.webapp.restclient;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import es.udc.fi.dc.photoalbum.util.dto.FileDto;
import es.udc.fi.dc.photoalbum.webapp.restclient.dto.ResultDtoJax;
import es.udc.fi.dc.photoalbum.webapp.restclient.util.FileDtoJaxToFileDtoConversor;

public class RestClientSearchService {

    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_XML_TYPE;

    private static final String ENDPOINT_ADDRESS = "http://localhost:8081/PhotoAlbum04";

    private static Client client = null;

    private static Client getClient() {
        if (client == null) {
            ClientConfig cc = new DefaultClientConfig();
            client = Client.create(cc);
        }
        return client;
    }

    public List<FileDto> getHottestPics() {
        return getHottestPicsPaging(0, 50);
    }

    public List<FileDto> getHottestPicsPaging(int first, int count) {
        WebResource resource = getClient().resource(
                ENDPOINT_ADDRESS + "/search?type=hottest-pics&first="
                        + first + "&count=" + count);
        ClientResponse response = resource.accept(MEDIA_TYPE)
                .type(MEDIA_TYPE).get(ClientResponse.class);

        int statusCode = response.getStatus();
        int expectedStatusCode = Response.Status.OK.getStatusCode();
        if (statusCode != expectedStatusCode) {
            throw new RuntimeException("Expected status code was "
                    + expectedStatusCode + " but found " + statusCode);
        }

        ResultDtoJax responseContent = response
                .getEntity(ResultDtoJax.class);

        String error = responseContent.getError();
        if (error != null) {
            throw new RuntimeException(error);
        }

        return FileDtoJaxToFileDtoConversor.toFileDto(responseContent
                .getFileDtos());
    }
}
