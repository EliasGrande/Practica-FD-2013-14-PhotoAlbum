package es.udc.fi.dc.photoalbum.test.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import es.udc.fi.dc.photoalbum.restservices.dto.AlbumDtoJax;
import es.udc.fi.dc.photoalbum.restservices.dto.FileDtoJax;
import es.udc.fi.dc.photoalbum.restservices.dto.ResultDtoJax;
import es.udc.fi.dc.photoalbum.restservices.util.ValidateParameters;

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
                .queryParam("type", "incorrect")
                .queryParam("dateFirst", "10/01/2012")
                .get(String.class);
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
                .queryParam("dateEnd", "02/11/12").get(String.class);
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
                .queryParam("dateEnd", "02/11/12").get(String.class);
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
                .queryParam("orderBy", "votos").get(String.class);
        assertFalse(response.contains("file-dtos"));
        assertFalse(response.contains("album-dtos"));

        assertTrue(response
                .contains("Url syntax error: The result can be order by date, like, dislike"));
    }

    /* Type = file */

    @Test
    public void testCorrectFileWithFilterDateAndFindBy()
            throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "file")
                .queryParam("findBy", "name")
                .queryParam("keywords", "evento")
                .queryParam("orderBy", "date")
                .queryParam("dateFirst", "01/11/2012")
                .queryParam("dateEnd", "02/11/2012")
                .get(String.class);
        assertFalse("Assert error failed",
                response.contains("Url syntax error"));
        assertFalse("Assert album-dtos error failed",
                response.contains("album-dtos"));

        assertTrue("Assert file-dtos failed",
                response.contains("File: Search with all parameters"));
    }

    @Test
    public void testCorrectFileWithoutFilterDateWithFindBy()
            throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "file")
                .queryParam("findBy", "name")
                .queryParam("keywords", "evento")
                .queryParam("orderBy", "date").get(String.class);
        assertFalse("Assert error failed",
                response.contains("Url syntax error"));
        assertFalse("Assert album-dtos error failed",
                response.contains("album-dtos"));

        assertTrue(
                "Assert file-dtos failed",
                response.contains("File: All parameters without filter dates"));
    }

    @Test
    public void testCorrectFileWithFilterDateWithoutFindBy()
            throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "file")
                .queryParam("orderBy", "date")
                .queryParam("dateFirst", "01/11/2012")
                .queryParam("dateEnd", "02/11/2012")
                .get(String.class);
        assertFalse("Assert error failed",
                response.contains("Url syntax error"));
        assertFalse("Assert album-dtos error failed",
                response.contains("album-dtos"));

        assertTrue(
                "File: Assert file-dtos failed",
                response.contains("File: Search with orderBy and filter by date"));
    }

    @Test
    public void testCorrectFileWithoutFilterDateWithoutFindBy()
            throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "file")
                .queryParam("orderBy", "date").get(String.class);
        assertFalse("Assert error failed",
                response.contains("Url syntax error"));
        assertFalse("Assert album-dtos error failed",
                response.contains("album-dtos"));

        assertTrue("File: Assert file-dtos failed",
                response.contains("File: Search with only orderBy"));
    }

    /* Type = album */

    @Test
    public void testCorrectAlbumWithFilterDateAndFindBy()
            throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "album")
                .queryParam("findBy", "name")
                .queryParam("keywords", "evento")
                .queryParam("orderBy", "date")
                .queryParam("dateFirst", "01/11/2012")
                .queryParam("dateEnd", "02/11/2012")
                .get(String.class);
        assertFalse("Assert error failed",
                response.contains("Url syntax error"));
        assertFalse("Assert file-dtos error failed",
                response.contains("file-dtos"));

        assertTrue(
                "Assert album-dtos failed",
                response.contains("Album: Search with all parameters"));
    }

    @Test
    public void testCorrectAlbumWithoutFilterDateWithFindBy()
            throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "album")
                .queryParam("findBy", "name")
                .queryParam("keywords", "evento")
                .queryParam("orderBy", "date").get(String.class);
        assertFalse("Assert error failed",
                response.contains("Url syntax error"));
        assertFalse("Assert file-dtos error failed",
                response.contains("file-dtos"));

        assertTrue(
                "Assert album-dtos failed",
                response.contains("Album: All parameters without filter dates"));
    }

    @Test
    public void testCorrectAlbumWithFilterDateWithoutFindBy()
            throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "album")
                .queryParam("orderBy", "date")
                .queryParam("dateFirst", "01/11/2012")
                .queryParam("dateEnd", "02/11/2012")
                .get(String.class);
        assertFalse("Assert error failed",
                response.contains("Url syntax error"));
        assertFalse("Assert file-dtos error failed",
                response.contains("file-dtos"));

        assertTrue(
                "Assert album-dtos failed",
                response.contains("Album: Search with orderBy and filter by date"));
    }

    @Test
    public void testCorrectAlbumWithoutFilterDateWithoutFindBy()
            throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "album")
                .queryParam("orderBy", "date").get(String.class);
        assertFalse("Assert error failed",
                response.contains("Url syntax error"));
        assertFalse("Assert file-dtos error failed",
                response.contains("file-dtos"));

        assertTrue("Album: Assert file-dtos failed",
                response.contains("Album: Search with only orderBy"));
    }

    /* Type = all */
    @Test
    public void testCorrectAllWithFilterDateAndFindBy()
            throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "all")
                .queryParam("findBy", "name")
                .queryParam("keywords", "evento")
                .queryParam("orderBy", "date")
                .queryParam("dateFirst", "01/11/2012")
                .queryParam("dateEnd", "02/11/2012")
                .get(String.class);
        assertFalse("Assert error failed",
                response.contains("Url syntax error"));

        assertTrue("Assert file-dtos failed",
                response.contains("File: Search with all parameters"));
        assertTrue(
                "Assert album-dtos failed",
                response.contains("Album: Search with all parameters"));
    }

    @Test
    public void testCorrectAllWithoutFilterDateWithFindBy()
            throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "all")
                .queryParam("findBy", "name")
                .queryParam("keywords", "evento")
                .queryParam("orderBy", "date").get(String.class);
        assertFalse("Assert error failed",
                response.contains("Url syntax error"));

        assertTrue(
                "Assert file-dtos failed",
                response.contains("File: All parameters without filter dates"));
        assertTrue(
                "Assert album-dtos failed",
                response.contains("Album: All parameters without filter dates"));
    }

    @Test
    public void testCorrectAllWithFilterDateWithoutFindBy()
            throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "all")
                .queryParam("orderBy", "date")
                .queryParam("dateFirst", "01/11/2012")
                .queryParam("dateEnd", "02/11/2012")
                .get(String.class);
        assertFalse("Assert error failed",
                response.contains("Url syntax error"));

        assertTrue(
                "Assert file-dtos error failed",
                response.contains("File: Search with orderBy and filter by date"));
        assertTrue(
                "Assert album-dtos failed",
                response.contains("Album: Search with orderBy and filter by date"));
    }

    @Test
    public void testCorrectAllWithoutFilterDateWithoutFindBy()
            throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "all")
                .queryParam("orderBy", "date").get(String.class);
        assertFalse("Assert error failed",
                response.contains("Url syntax error"));
        
        assertTrue("Assert file-dtos error failed",
                response.contains("File: Search with only orderBy"));
        assertTrue("Album: Assert file-dtos failed",
                response.contains("Album: Search with only orderBy"));
    }

    /* Type = hottests-pics */
    @Test
    public void testHottestPics() throws IOException {
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI()
                + "PhotoAlbum04/");
        String response = service.path("search")
                .queryParam("type", "hottest-pics").get(String.class);
        System.out.println(response);

        assertTrue(response
                .contains("File: Search with only orderBy"));

    }
    
    /*Test for dtos*/
    @Test
    public void testAlbumDtoJax(){
        AlbumDtoJax adj = new AlbumDtoJax();
        adj.setId(1);
        adj.setName("Test 1");
        
        assertEquals(adj.getId(),(Integer) 1);
        assertEquals(adj.getName(),"Test 1");
        
        assertEquals(adj.toString(),"AlbumDtoJax [id=" + 1 + ", name=" + "Test 1" + "]");
    }
    @Test
    public void testFileDtoJax(){
        FileDtoJax fdj = new FileDtoJax();
        fdj.setId(1);
        byte ej[] = new byte[1];
        fdj.setFileSmall(ej);
        fdj.setName("Test file");
        
        assertEquals(fdj.getId(),(Integer) 1);
        assertEquals(fdj.getName(),"Test file");
        assertTrue(Arrays.equals(ej, fdj.getFileSmall()));
        
        assertEquals(fdj.toString(),"FileDtoJax [id=" + 1 + ", name=" + "Test file"
                + ", fileSmall=" + Arrays.toString(new byte[1]) + "]");
    }
    @Test
    public void testResultDtoJax(){
        ResultDtoJax rdj = new ResultDtoJax();
        rdj.setAlbumDtos(new ArrayList<AlbumDtoJax>());
        rdj.setFileDtos(new ArrayList<FileDtoJax>());
        rdj.setError("Test result");
        
        assertEquals(new ArrayList<AlbumDtoJax>(),rdj.getAlbumDtos());
        assertEquals(new ArrayList<FileDtoJax>(),rdj.getFileDtos());
        assertEquals(rdj.getError(),"Test result");
    }
    
    /*Test for ValidateParameters*/
    @Test
    public void testValidateFindByAndKeywords(){
        List<String> list = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        List<String> list3 = new ArrayList<String>();
        List<String> list4 = new ArrayList<String>();
        List<String> list5 = null;
        List<String> list6 = new ArrayList<String>();
        
        list.add("name");
        list2.add("tag");
        list3.add("comment");
        list6.add("incorrect");
        
        assertTrue(ValidateParameters.validateFindByAndKeywords(list, "Evento"));
        assertTrue(ValidateParameters.validateFindByAndKeywords(list2, "Evento1"));
        assertTrue(ValidateParameters.validateFindByAndKeywords(list3, "Evento2"));
        
        assertFalse(ValidateParameters.validateFindByAndKeywords(list4, "Evento"));
        assertFalse(ValidateParameters.validateFindByAndKeywords(list5, "Evento"));
        assertFalse(ValidateParameters.validateFindByAndKeywords(list3, null));
        assertFalse(ValidateParameters.validateFindByAndKeywords(list6, "Evento"));
    }
    
    @Test
    public void testValidateStringDate(){
        assertFalse(ValidateParameters.validate("0/05/2012"));
        assertFalse(ValidateParameters.validate("-10/05/2012"));
        assertFalse(ValidateParameters.validate("00/05/2012"));
        assertFalse(ValidateParameters.validate("41/01/2012"));
        assertFalse(ValidateParameters.validate("41/02/2012"));
        assertFalse(ValidateParameters.validate("41/03/2012"));
        assertFalse(ValidateParameters.validate("41/04/2012"));
        assertFalse(ValidateParameters.validate("41/05/2012"));
        assertFalse(ValidateParameters.validate("41/06/2012"));
        assertFalse(ValidateParameters.validate("41/07/2012"));
        assertFalse(ValidateParameters.validate("41/08/2012"));
        assertFalse(ValidateParameters.validate("41/09/2012"));
        assertFalse(ValidateParameters.validate("41/10/2012"));
        assertFalse(ValidateParameters.validate("41/11/2012"));
        assertFalse(ValidateParameters.validate("41/12/2012"));
        assertFalse(ValidateParameters.validate("29/02/2013"));
        
        assertTrue(ValidateParameters.validate("21/12/2012"));
        assertTrue(ValidateParameters.validate("29/02/2012"));
        assertTrue(ValidateParameters.validate("25/02/2013"));
        
        
    }

}
