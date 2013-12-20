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

/**
 * Client to use the Rest Service.
 */
public class RestClientSearchServiceImpl implements RestClientSearchService {
    /**
     * Type of content of responses.
     */
    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_XML_TYPE;
    /**
     * The url to connect with the rest service.
     */
    private static final String ENDPOINT_ADDRESS = "http://localhost:8081/PhotoAlbum04";
    /**
     * Initializes the client.
     */
    private static Client client = null;
    /**
     * Number of pics to the method that not paginated.
     */
    private static final Integer NUMBER_OF_PICS = 50;

    /**
     * Method getClient.
     * 
     * @return Client Return a default client.
     */
    private static Client getClient() {
        if (client == null) {
            ClientConfig cc = new DefaultClientConfig();
            client = Client.create(cc);
        }
        return client;
    }

    /**
     * Empty constructor for the class necessary to spring.
     */
    public RestClientSearchServiceImpl() {
    }

    /**
     * Method getHottestPics, obtains a concret number of pics..
     * 
     * @return List<FileDto> The list that contains the pics.
     */
    public List<FileDto> getHottestPics() {
        return getHottestPicsPaging(0, NUMBER_OF_PICS);
    }

    /**
     * Method that obtains the hottest-pics at paginated form.
     * 
     * @param first
     *            The first element to return.
     * @param count
     *            The number of elements to return.
     * @return List<FileDto> A list with the hottest-pics.
     */
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
