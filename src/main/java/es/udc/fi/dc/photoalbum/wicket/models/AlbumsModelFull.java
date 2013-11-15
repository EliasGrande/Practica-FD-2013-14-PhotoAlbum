package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.spring.AlbumService;
import es.udc.fi.dc.photoalbum.utils.AlbumsComparator;
import es.udc.fi.dc.photoalbum.wicket.MySession;

/**
 * The model for an {@link Album}. This model return an array of
 * albums of the user.
 */
@SuppressWarnings("serial")
public class AlbumsModelFull extends
        LoadableDetachableModel<List<Album>> {

    /**
     * @see AlbumService.
     */
    @SpringBean
    private AlbumService albumService;

    /**
     * Constructor for AlbumsModelFull
     */
    public AlbumsModelFull() {
        Injector.get().inject(this);
    }

    /**
     * Method load.
     * 
     * @return List<Album> Return a list of {@link Album}'s which
     *         owner are the user.
     */
    protected List<Album> load() {
        List<Album> list = new ArrayList<Album>(
                albumService.getAlbums(((MySession) Session.get())
                        .getuId()));
        Collections.sort(list, new AlbumsComparator());
        return (ArrayList<Album>) list;
    }
}
