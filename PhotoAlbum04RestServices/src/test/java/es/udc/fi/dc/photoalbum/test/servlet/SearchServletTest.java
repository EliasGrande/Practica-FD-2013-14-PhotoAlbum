package es.udc.fi.dc.photoalbum.test.servlet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.spi.spring.container.SpringComponentProviderFactory;

public class SearchServletTest {

    final URI BASE_URI = getBaseURI();
    HttpServer server;

    private URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(9998)
                .build();
    }

    @Before
    public void startServer() throws IOException {
        ConfigurableApplicationContext cac = new ClassPathXmlApplicationContext(
                "classpath:applicationContextForTest.xml");

        ResourceConfig rc = new PackagesResourceConfig(
                "es.udc.fi.dc.photoalbum.restservices.servlets");
        IoCComponentProviderFactory ioc = new SpringComponentProviderFactory(
                rc, cac);
        server = GrizzlyServerFactory.createHttpServer(BASE_URI
                + "PhotoAlbum04/", rc, ioc);
    }

    @After
    public void stopServer() {
        server.stop();
    }

    @Test
    public void testTypeNull() throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search/").get(String.class);
        assertFalse(response.contains("file-dtos"));
        assertFalse(response.contains("album-dtos"));
        assertTrue(response
                .contains("Url syntax error: Type param must be necessary"));
    }

    @Test
    public void testNonExistingType() throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "incorrect").get(String.class);
        assertFalse(response.contains("file-dtos"));
        assertFalse(response.contains("album-dtos"));
        System.out.println(response);
        assertTrue(response
                .contains("Url syntax error: Especific a correct type: file, album, all or hottest-pics"));
    }

    @Test
    public void testIncorrectDate() throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "file")
                .queryParam("dateFirst", "01/11/12")
                .queryParam("dateEnd", "02/11/12")
                .get(String.class);
        assertFalse(response.contains("file-dtos"));
        assertFalse(response.contains("album-dtos"));
        
        assertTrue(response
                .contains("Url syntax error: Incorrect format for date, the correct is dd/mm/yyyy"));
    }
    
    @Test
    public void testIncorrectDate2() throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "file")
                .queryParam("dateFirst", "01/11/2012")
                .queryParam("dateEnd", "02/11/12")
                .get(String.class);
        assertFalse(response.contains("file-dtos"));
        assertFalse(response.contains("album-dtos"));
        
        assertTrue(response
                .contains("Url syntax error: Incorrect format for date, the correct is dd/mm/yyyy"));
    }
    
    @Test
    public void testIncorrectDate3() throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "file")
                .queryParam("dateFirst", "01/11/2012")
                .queryParam("dateEnd", "02/11/2011")
                .get(String.class);
        assertFalse(response.contains("file-dtos"));
        assertFalse(response.contains("album-dtos"));
        
        assertTrue(response
                .contains("Url syntax error: The dateEnd are earlier than dateFirst"));
    }
    
    @Test
    public void testIncorrectOrderBy() throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "file")
                .queryParam("orderBy", "votos")
                .get(String.class);
        assertFalse(response.contains("file-dtos"));
        assertFalse(response.contains("album-dtos"));
        
        assertTrue(response
                .contains("Url syntax error: The result can be order by date, like, dislike"));
    }
    
    @Test
    public void testHottestPics() throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "hottest-pics").get(String.class);
        System.out.println(response);

        assertTrue(response.contains("Search with only orderBy"));

    }

}
