package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.utils.AlbumsComparator;

@SuppressWarnings("serial")
public class PublicAlbumsModelFull extends
        LoadableDetachableModel<List<Album>> {

    @SpringBean
    private AlbumService albumService;

    public PublicAlbumsModelFull() {
        Injector.get().inject(this);
    }

    protected List<Album> load() {
        ArrayList<Album> list = new ArrayList<Album>(
                albumService.getPublicAlbums());
        Collections.sort(list, new AlbumsComparator());
        return list;
    }
}
