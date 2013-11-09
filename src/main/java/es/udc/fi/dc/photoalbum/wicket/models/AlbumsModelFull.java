package es.udc.fi.dc.photoalbum.wicket.models;

import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.utils.AlbumsComparator;
import es.udc.fi.dc.photoalbum.wicket.MySession;

import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("serial")
public class AlbumsModelFull extends
        LoadableDetachableModel<ArrayList<Album>> {

    @SpringBean
    private AlbumService albumService;

    public AlbumsModelFull() {
        Injector.get().inject(this);
    }

    protected ArrayList<Album> load() {
        ArrayList<Album> list = new ArrayList<Album>(
                albumService.getAlbums(((MySession) Session.get())
                        .getuId()));
        Collections.sort(list, new AlbumsComparator());
        return list;
    }
}
