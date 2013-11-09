package es.udc.fi.dc.photoalbum.wicket.models;

import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.wicket.MySession;

@SuppressWarnings("serial")
public class AlbumModel extends LoadableDetachableModel<Album> {

    @SpringBean
    private AlbumService albumService;
    private String name;

    public AlbumModel(String name) {
        this.name = name;
        Injector.get().inject(this);
    }

    protected Album load() {
        return this.albumService.getAlbum(name,
                ((MySession) Session.get()).getuId());
    }
}
