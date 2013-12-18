package es.udc.fi.dc.photoalbum.test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import es.udc.fi.dc.photoalbum.util.dto.FileDto;

public class FileDtoTest {
    @Test
    public void testFileDtoJax(){
        FileDto fd = new FileDto();
        fd.setId(1);
        byte ej[] = new byte[1];
        fd.setFileSmall(ej);
        fd.setName("Test file");
        
        assertEquals(fd.getId(),(Integer) 1);
        assertEquals(fd.getName(),"Test file");
        assertTrue(Arrays.equals(ej, fd.getFileSmall()));
        
        assertEquals(fd.toString(),"FileDto [id=" + 1 + ", name=" + "Test file"
                + ", fileSmall=" + Arrays.toString(new byte[1]) + "]");
        
        FileDto fd1 = new FileDto(1,"Test file",ej);
        
        assertEquals(fd1.getId(),(Integer) 1);
        assertEquals(fd1.getName(),"Test file");
        
        assertEquals(fd1.toString(),"FileDto [id=" + 1 + ", name=" + "Test file"
                + ", fileSmall=" + Arrays.toString(new byte[1]) + "]");
    }
    
}
