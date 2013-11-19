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

/**
 * The model for an {@link Album}. This model return an array of
 * public {@link Album}s of the user.
 */
@SuppressWarnings("serial")
public class PublicAlbumsModelFull extends
        LoadableDetachableModel<List<Album>> {

    /**
     * @see AlbumService
     */
    @SpringBean
    private AlbumService albumService;

    /**
     * Constructor of PublicModelFull.
     */
    public PublicAlbumsModelFull() {
        Injector.get().inject(this);
    }

    /**
     * Method load.
     * 
     * 
     * @return List<{@link Album}> Return a list of {@link Album}s which
     *         are public.
     */
    protected List<Album> load() {
        ArrayList<Album> list = new ArrayList<Album>(
                albumService.getPublicAlbums());
        Collections.sort(list, new AlbumsComparator());
        return list;
    }
}
