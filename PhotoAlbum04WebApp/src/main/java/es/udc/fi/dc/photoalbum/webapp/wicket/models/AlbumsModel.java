package es.udc.fi.dc.photoalbum.webapp.wicket.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.Album;
import es.udc.fi.dc.photoalbum.model.spring.AlbumService;
import es.udc.fi.dc.photoalbum.util.utils.AlbumsComparator;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;

/**
 * The model for an {@link Album}. This model return an array of
 * albums of the user without repeated {@link Album}'s.
 */
@SuppressWarnings("serial")
public class AlbumsModel extends LoadableDetachableModel<List<Album>> {

    /**
     * @see AlbumService
     */
    @SpringBean
    private AlbumService albumService;

    /**
     * An {@link Album} object.
     */
    private Album album;

    /**
     * Constructor for AlbumsModel.
     * 
     * @param album
     *            {@link #album}
     */
    public AlbumsModel(Album album) {
        this.album = album;
        Injector.get().inject(this);
    }

    /**
     * Method load.
     * 
    
     * @return List<Album> Return an {@link Album}'s list without
     *         repeated items. */
    protected List<Album> load() {
        List<Album> list = new ArrayList<Album>(
                albumService.getAlbums(((MySession) Session.get())
                        .getuId()));
        Iterator<Album> itr = list.iterator();
        while (itr.hasNext()) {
            Album album = itr.next();
            if (album.getId().equals(this.album.getId())) {
                itr.remove();
                break;
            }
        }
        Collections.sort(list);
        return (ArrayList<Album>) list;
    }
}
