package es.udc.fi.dc.photoalbum.test.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import es.udc.fi.dc.photoalbum.util.dto.AlbumDto;
import es.udc.fi.dc.photoalbum.util.dto.FileDto;
import es.udc.fi.dc.photoalbum.util.dto.ResultDto;

public class ResultDtoTest {
    @Test
    public void testResultDtoJax(){
        ResultDto rd = new ResultDto();
        rd.setAlbumDtos(new ArrayList<AlbumDto>());
        rd.setFileDtos(new ArrayList<FileDto>());
        rd.setError("Test result");
        
        assertEquals(new ArrayList<AlbumDto>(),rd.getAlbumDtos());
        assertEquals(new ArrayList<FileDto>(),rd.getFileDtos());
        assertEquals(rd.getError(),"Test result");
        
        ResultDto rd1 = new ResultDto("Test result");
        assertEquals(rd1.getError(),"Test result");
        
        ResultDto rd2 = new ResultDto(new ArrayList<AlbumDto>(),new ArrayList<FileDto>());
        assertEquals(new ArrayList<AlbumDto>(),rd.getAlbumDtos());
        assertEquals(new ArrayList<FileDto>(),rd.getFileDtos());
    }
}
