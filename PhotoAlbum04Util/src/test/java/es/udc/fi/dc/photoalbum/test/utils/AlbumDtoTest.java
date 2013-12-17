package es.udc.fi.dc.photoalbum.test.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.udc.fi.dc.photoalbum.util.dto.AlbumDto;

public class AlbumDtoTest {
    @Test
    public void testAlbumDtoJax() {
        AlbumDto ad = new AlbumDto();
        ad.setId(1);
        ad.setName("Test 1");

        assertEquals(ad.getId(), (Integer) 1);
        assertEquals(ad.getName(), "Test 1");

        assertEquals(ad.toString(), "AlbumDto [id=" + 1 + ", name="
                + "Test 1" + "]");
        AlbumDto adc = new AlbumDto(1, "Test 1");
        
        assertEquals(adc.getId(), (Integer) 1);
        assertEquals(adc.getName(), "Test 1");

        assertEquals(adc.toString(), "AlbumDto [id=" + 1 + ", name="
                + "Test 1" + "]");
    }
}
